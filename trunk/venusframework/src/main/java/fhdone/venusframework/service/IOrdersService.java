package fhdone.venusframework.service;

import java.util.List;

import fhdone.venusframework.entity.Orders;


public interface  IOrdersService {
	
	public void saveOrders(Orders orders );
	
	public void updateOrders(Orders orders );
	
	public void deleteOrders(Long id );
	
	public Orders getOrders(Long id );
	
	public List<Orders> queryList();
	
}
