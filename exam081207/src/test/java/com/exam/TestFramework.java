package com.exam;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.exam.entity.OrderInfo;
import com.exam.entity.Orders;
import com.exam.service.ICommonService;
import com.exam.service.ITestService;


@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class TestFramework extends ExamBaseTest {

	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;

	@Autowired
	@Qualifier("testService")
	private ITestService 		testService;



	//	@SuppressWarnings("unchecked")
	//	@Test
	//	public void testGeneralOne() {
	//		List<Book> tList = commonService.list("from Book");
	//		System.out.println("1.........."+tList.size());
	//		for( Book b:tList  ){
	//			commonService.delete(b);    //如果存在约束数据将无法删除
	//		}
	//		tList = commonService.list("from Book");
	//		System.out.println("2........."+tList.size());
	//	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Test
	public void testX() {
		List<Orders> oList = commonService.listHql("from Orders o " , null);
		for( Orders o :  oList){
			System.out.print(o.getOrderNum()+"...........");
			Set<OrderInfo> soi =  o.getOrderInfo();
			for(OrderInfo oi : soi){
				System.out.println(oi.getFkBookId().getTitle());
			}
		}
	}


	/*
	 * 视图调用
	 * CREATE  VIEW `vbook` AS 
	 *   select *  from   `book`;
	 */
	//	public void testView() {
	//		List<VBook> bList = commonService.listHql("from VBook " , "");
	//		System.out.print(  bList.size() );
	//	}


	/*
	 * 	存储过程调用
	 * 	CREATE or replace PROCEDURE updateBook(IN bookTitle %BOOK.Title)
	 *	begin
	 *	update BOOK b set b.title = bookTitle;
	 *	end;
	 */
	//	public void testProcedure(){
	//		commonService.execModifyProcedure();
	//	}
	//
	//	public void testQueryProcedure(){
	//		commonService.execQueryProcedure();
	//	}

	//	public void testDelete(){
	//		try{
	//			testService.testTranscation();
	//		}
	//		catch( Exception e ){
	//			System.out.println("exception");
	//		}
	//	}

	@Rollback(false)
//	@Test
	public void save(){
		//		Book b1 = testService.testSave();
		//		Book b2 = testService.testSave();
		//		testService.delBook(b2);
		testService.testSave();
		//		testService.testSaveList();
	}

}


