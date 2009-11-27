package fhdone.venusframework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fhdone.venusframework.entity.Orders;
import fhdone.venusframework.service.ICommonService;
import fhdone.venusframework.service.IOrdersService;


@Service("ordersService")
@Transactional
public class OrdersService implements IOrdersService {

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	@Override
	public void saveOrders(Orders orders) {
		commonService.save(orders);
	}
	
	@Override
	public void updateOrders(Orders  orders ) {
		commonService.update(orders);
	}
	
	@Override
	public void deleteOrders(Long  id ){
		Orders o = this.getOrders(id);
		commonService.delete(o);
	}
	
	@Override
	public Orders getOrders(Long  id ){
		return (Orders)commonService.load(Orders.class, id);
	}
	
	@Override
	public List<Orders> queryList(){
		List l =commonService.listHql("from Orders", "");
		return l;
	}

}
