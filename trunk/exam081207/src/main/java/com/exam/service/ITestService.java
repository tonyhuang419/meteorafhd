package com.exam.service;

import com.exam.entity.Book;


public interface ITestService {

	public void  testTranscation();
	
	public Book testSave();
	
	public void testSaveList();
	
	public void del();
	
	public void delBook( Book book );
	
	public void testHibernateState();

}
