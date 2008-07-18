package com.baoz.yx.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import com.baoz.yx.entity.Notice;
import com.baoz.yx.service.INoticeService;
import com.baoz.core.dao.ICommonDao;
import com.baoz.core.service.ICommonService;
import com.baoz.core.utils.DateUtil;


@Service("noticeService")
@Transactional

public class NoticeService implements INoticeService {
	
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;
	
	@Autowired
	@Qualifier("commonService")	
	private ICommonService 		commonService;
	
	public ICommonDao getCommonDao() {
		return commonDao;
	}
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}
	public  void  addNotice(String content,Long emplyeeId){
		addNotice(content,emplyeeId,new Date(),DateUtils.addDays(new Date(), 7));
		return;
	}
	public  void  addNotice(String content,Long emplyeeId,Date timestart,Date timeend){
		Notice not = new Notice();
		not.setContent(content);
		not.setEmplyeeId(emplyeeId);
		not.setTimestart(timestart);
		not.setTimeend(timeend);
		not.setIs_active("1");
		commonDao.save(not);
		return;
	}
	
	public List<Notice> getValidNoticeList(Long emplyeeId) {
		StringBuffer str = new StringBuffer();
		str.append(" from Notice n where n.is_active=1 and n.timeend >= to_date('"+DateUtil.format(new Date(), "yyyy-MM-dd")+"','yyyy-MM-dd') ");
		if (emplyeeId!=null) {
			str.append(" and n.emplyeeId = ").append(emplyeeId);
		}
		str.append("order by n.timestart desc ");		
		return commonService.list(str.toString());
	}
	public ICommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
}
