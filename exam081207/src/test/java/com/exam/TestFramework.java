package com.exam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.entity.Book;
import com.exam.service.ICommonService;
import com.exam.service.ITestService;


public class TestFramework extends ExamBaseTest {

	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;

	@Autowired
	@Qualifier("testService")
	private ITestService 		testService;

	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.prepareTestInstance();
		super.setDefaultRollback(false);
	}


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

	//	@SuppressWarnings("unchecked")
	//	@Test
	//	public void testGeneralTwo() {
	//		List<Orders> oList = commonService.list("from Orders o where o.orderNum = '1'");
	//		for( Orders o :  oList){
	//			System.out.print(o.getOrderNum()+"...........");
	//			System.out.println(o.getFkBookId());
	//			if( o.getFkBookId() != null){
	//				System.out.println( o.getFkBookId().getTitle());
	//			}
	//		}
	//	}

	//		@SuppressWarnings("unchecked")
	//		@Test
	//		public void testGeneralThree() {
	//			List<Employee> eList = commonService.list("from Employee e where e.id = 1 ");
	//			System.out.print(  eList.size() );
	//			for( Employee e :  eList){
	//				Set<Orders> os  = e.getOrders();
	//				for(Orders o : os){
	//					System.out.println(o.getOrderNum());
	//					System.out.println(o.getFkEmployeeId().getId());
	//				}
	//			}
	//		}

	//	@Test
	//	public void testGeneraFour() {
	//		Employee e = (Employee)commonService.uniqueResult("from Employee e where e.id = 1 ");
	//		e.setJobNum("111");
	//		commonService.saveOrUpdate(e);
	//	}

	//	@Test
	//	public void testGeneraFive() {
	//		Employee e = new Employee();
	//		e.setJobNum("11111");
	//		commonService.save(e);
	//		logger.info(e.getId());
	//		e = (Employee)commonService.load(Employee.class, e.getId());
	//		logger.info(e.getJobNum());
	//		e = (Employee)commonService.load(Employee.class, e.getId());
	//		logger.info(e.getJobNum());

	//		e = (Employee)commonService.uniqueResult(" from Employee e where e.id = ? ", e.getId());
	//		e = (Employee)commonService.uniqueResult(" from Employee e where e.id = ? ", e.getId());
	//	}


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

	public void testSave(){
//		testService.testSave();
		testService.testSaveList();
	}

}


