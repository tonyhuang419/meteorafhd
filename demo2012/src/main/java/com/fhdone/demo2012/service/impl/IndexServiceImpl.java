package com.fhdone.demo2012.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhdone.demo2012.dao.UserLogDao;
import com.fhdone.demo2012.entity.UserLog;
import com.fhdone.demo2012.service.IndexService;
import com.fhdone.demo2012.utils.lucene.IndexUtils;

@Service("indexService")
public class IndexServiceImpl implements IndexService {

	@Autowired  
	public UserLogDao userLogDao;  

	public boolean indexLoginfo( ) {
		Long maxId = userLogDao.getUserLogByMaxId();
		System.out.println(maxId);
		Long beg = new Long(0);
		Long end = new Long(0);
		while(end<maxId){
			beg=end;
			end+=1999;
			Map<String,Long> paras = new HashMap<String,Long>();
			paras.put("id1", beg+1 );
			paras.put("id2", end );
			List<UserLog> ul = userLogDao.getUserLogsByTwoId(paras);
			if( 0!=ul.size()){
//				System.out.println(ul.size());
//				System.out.println(beg+"~"+end);
				try {
					System.out.println( "indexed:"+IndexUtils.index(ul) );
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

}
