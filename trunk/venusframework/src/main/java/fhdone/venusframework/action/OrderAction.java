package fhdone.venusframework.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import fhdone.venusframework.entity.Orders;
import fhdone.venusframework.service.IOrdersService;

@Results( 
	{ @Result(name = "edit", location = "/WEB-INF/jsp/order/edit.jsp"),
	 @Result(name = "index", location = "/WEB-INF/jsp/order/index.jsp")
})
public class OrderAction extends RestBaseAction<Orders>{
	private static final long serialVersionUID = 1836789354042735602L;
	
	@Autowired
    @Qualifier("ordersService")
	private IOrdersService ordersService;  
	private List<Orders> list;
	private Orders order;  
	private Long id; 
	 
	public HttpHeaders create() {  
		logger.info("save order");
		ordersService.saveOrders(order);
		return new DefaultHttpHeaders("index");  
	}  

	public String destroy() {  
		ordersService.deleteOrders(entity.getId());  
		return "index";  
	}  

	public String edit() {  
		logger.info("into edit page");
		order = ordersService.getOrders(id);
		return "edit";  
	}  

	public String editNew() { 
		logger.info("into add page");
		return "edit";  
	}  

	public HttpHeaders index() {  
		list = ordersService.queryList();  
		return new DefaultHttpHeaders("index").disableCaching();  
	}  

	public HttpHeaders show() {  
		return new DefaultHttpHeaders("show");  
	}  

	public String update() {  
		logger.info("save edited order");
		ordersService.updateOrders(order);
		return "index";  
	}  


	@Override  
	public Orders getModel() {  
		return entity;  
	}  

	@Override  
	public void prepare() {  
		entity = new Orders();  
	}

	public IOrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<Orders> getList() {
		return list;
	}

	public void setList(List<Orders> list) {
		this.list = list;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}  
}
