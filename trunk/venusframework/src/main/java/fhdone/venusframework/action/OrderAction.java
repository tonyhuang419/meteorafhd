package fhdone.venusframework.action;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fhdone.venusframework.entity.Orders;
import fhdone.venusframework.service.IOrdersService;


@Results( 
		{ @Result(name = "success", location = "/test.jsp")
})
public class OrderAction  extends BaseAction{
	private static final long serialVersionUID = 1848292069222307390L;
   
	@Autowired
    @Qualifier("ordersService")
	private IOrdersService ordersService;
	
	private Orders order; 
	
	public String saveOrder()  {  
		ordersService.saveOrders(order);
		return "success";  
	} 
	
	public String welcome(){
		return "success"; 
	}

	
	
	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public IOrdersService getOrdersService() {
		return ordersService;
	}


} 
