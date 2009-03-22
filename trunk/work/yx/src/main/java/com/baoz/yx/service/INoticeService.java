package com.baoz.yx.service;

import java.util.Date;
import java.util.List;

import com.baoz.yx.entity.Notice;

public interface INoticeService {
	
	/*
	 * 显示通知
	 */
	public  void  addNotice(String content,Long emplyeeId);
	
	
	public  void  addNotice(String content,Long emplyeeId,Date timestart,Date timeend);
	
	/**
	 * 获得有效的通知，即没有结束的通知
	 * @return
	 */
	public List<Notice> getValidNoticeList(Long emplyeeId);
	
    
}
