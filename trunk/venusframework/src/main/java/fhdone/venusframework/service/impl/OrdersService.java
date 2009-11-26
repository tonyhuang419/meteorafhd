package fhdone.venusframework.service.impl;

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

}
