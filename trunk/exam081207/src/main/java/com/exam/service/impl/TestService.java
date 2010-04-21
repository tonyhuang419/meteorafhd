package com.exam.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

	@SuppressWarnings("unchecked")
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
		b.setTitle("ooooo");
		//		commonService.getSession().evict(b);
		//		commonService.getSession().update(b);
		//		b.setCategory("33333");  //当下一次hibernate干活时，会对set值进行更新
		//		throw new RuntimeException();
		//		System.out.println( "1111111111    "+  commonService.uniqueResult("select count(*) from Book " ));
		System.out.println(b.getId());
		System.out.println(b.getTitle());
		return b;
	}

	@SuppressWarnings("unchecked")
	public void testSaveList(){
		System.out.println( "1111111111    "+  commonService.uniqueResult("select count(*) from Book " ));
		List bList = new ArrayList<Book>();
		bList.add(new Book());
		bList.add(new Book());
		commonService.save(bList);
		System.out.println( "1111111111    "+  commonService.uniqueResult("select count(*) from Book " ));
	}

	public void testHibernateState(){
		System.out.println( "1111111111    "+  commonService.uniqueResult("select count(*) from Book " ));
		Book b = new Book();
		b.setTitle("aaaaaaaaaaaaaaaaaaaaaa");
		commonService.save(b);
		commonService.getSession().evict(b);
		b.setTitle("bbbbbbbbbbbbbbbbbb");
		commonService.save(b);
		Book c = new Book() ;
		c = b;
		commonService.save(c);

//		List<Book> list = commonService.listHql(" from Book b", "" );
//		System.out.println("========="+list.size());
//		for(Book book : list){
//			//if want update ,need mod isbn 
//			book.setIsbn("will update date");
//		}

//		List<Book> list = commonService.listHql("select new Book(b.created , b.createdby , b.updated , b.updatedby ," +
//				" b.isActive , b.id , b.title , b.isbn ," +
//				" b.authorName ,  b.editior , b.year , b.category, " +
//				" b.publisher , b.quantityInStock , b.price ) from Book b ", "" );
//		System.out.println("========="+list.size());
//		for(Book book : list){
//			book.setIsbn("will update date or not?");
//			//answer : not
//		}

	}

}
