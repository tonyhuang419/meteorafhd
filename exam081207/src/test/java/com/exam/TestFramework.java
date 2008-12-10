package com.exam;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.entity.Employee;
import com.exam.entity.Orders;
import com.exam.service.ICommonService;


public class TestFramework extends ExamBaseTest {

	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;


	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.prepareTestInstance();
		super.setDefaultRollback(true);
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

		@SuppressWarnings("unchecked")
		@Test
		public void testGeneralThree() {
			List<Employee> eList = commonService.list("from Employee e where e.id = 1 ");
			System.out.print(  eList.size() );
			for( Employee e :  eList){
				Set<Orders> os  = e.getOrders();
				for(Orders o : os){
					System.out.println(o.getOrderNum());
					System.out.println(o.getFkEmployeeId().getId());
				}
			}
		}

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

}


