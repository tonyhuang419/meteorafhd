package com.baoz.yx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.service.ISequenceService;
import com.baoz.yx.utils.SequenceKey;

/**
 * 类SequenceServiceImpl.java的实现描述：生成序列号的服务
 * @author xurong Jul 23, 2008 11:32:22 AM
 */
@Service("sequenceService")
@Transactional
public class SequenceServiceImpl implements ISequenceService{
	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao commonDao;
	public Long getNextValue(SequenceKey key) {
		List<Number> numberList = (List<Number>)commonDao.listSQL("select tableid from sysid where id = ? for update ", key.getKey());
		if(numberList.size() > 0){
			// 先加1，锁住这条记录，防止并发
			commonDao.executeSQLUpdate("update sysid set tableid = tableid + 1 where id = ? " , key.getKey());
			// 然后获得next值
			Number nextValue = (Number)commonDao.uniqueSQLResult("select tableid from sysid where id = ?" , key.getKey());
			if(nextValue == null){
				throw new RuntimeException("no sequence named ["+key.toString()+"]");
			}
			return nextValue.longValue();
		}else{
			commonDao.executeSQLUpdate("insert into sysid ( tableid,id ) values ( ? ,? )" , key.getInitValue() , key.getKey());
			return key.getInitValue();
		}
	}
}
