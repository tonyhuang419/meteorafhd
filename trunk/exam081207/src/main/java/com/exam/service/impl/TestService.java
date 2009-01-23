package com.exam.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exam.entity.Book;
import com.exam.service.ICommonService;
import com.exam.service.ITestService;

@Service("testService")
@Transactional
public class TestService implements ITestService {

	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;

	public void testTranscation() {
		System.out.println(commonService.getSession().getFlushMode());
		this.del();
		//			commonService.getSession().flush();
		System.out.println( "22222222222    "+   commonService.uniqueResult("select count(*) from Book " ));
		throw new RuntimeException();
	}

	public void del(){
		System.out.println( "1111111111    "+  commonService.uniqueResult("select count(*) from Book " ));
		List<Book> lb = commonService.listHql(" from Book b where b.id<12000","" );
		System.out.println(lb.size());
		for(Book b:lb ){
			commonService.delete(b);
		}
	}
	
	public void delBook( Book book ){
		commonService.delete( book );
	}
	
	public Book testSave(){
//		System.out.println( "1111111111    "+  commonService.uniqueResult("select count(*) from Book " ));
		Book b = new Book();
		b.setTitle("11111111");
		commonService.save(b);
		b.setTitle("2222222");
		b.setCategory("33333");  //当下一次hibernate干活时，会对set值进行更新
//		throw new RuntimeException();
//		System.out.println( "1111111111    "+  commonService.uniqueResult("select count(*) from Book " ));
		System.out.println(b.getId());
		return b;
	}
	
	public void testSaveList(){
		System.out.println( "1111111111    "+  commonService.uniqueResult("select count(*) from Book " ));
		List bList = new ArrayList<Book>();
		bList.add(new Book());
		bList.add(new Book());
		commonService.save(bList);
		System.out.println( "1111111111    "+  commonService.uniqueResult("select count(*) from Book " ));
	}
	
}
