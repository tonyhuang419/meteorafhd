package fhdone.venusframework.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;

import fhdone.venusframework.BaseTest;
import fhdone.venusframework.entity.Orders;

@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class OrderServiceTest extends BaseTest{


	@Autowired
	@Qualifier("ordersService")
	private IOrdersService 		ordersService;
	
	@Rollback(true)
	@Test
	public void testSaveOrders(){
		Orders o = new Orders();
		o.setOrderNum("ttttt");
		ordersService.saveOrders(o);
	}
	
}
