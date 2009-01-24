package com.exam.service.impl.manage;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.exam.ExamBaseTest;
import com.exam.entity.Employee;
import com.exam.service.manage.IRegisterMService;


@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class RegisterMServiceTest extends ExamBaseTest {



	@Autowired
	@Qualifier("registerMService")
	private IRegisterMService 		registerMService;
	
	@Rollback(true)
	@Test
	public void saveNewEmployeerTest(){
		Employee e = new Employee();
		e.setName("111");
		e.setPassword("222");
		registerMService.saveNewEmployeer(e);
		
	}
	
}
