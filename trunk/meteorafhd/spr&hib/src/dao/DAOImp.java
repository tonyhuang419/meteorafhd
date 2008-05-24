package dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import entity.Std;

public class DAOImp extends HibernateDaoSupport implements IDAO{

	@Override
	public void delete(int id) {
		getHibernateTemplate().delete(getHibernateTemplate().get(Std.class , id));	
	}

	@Override
	public List<Std> findAll() {
		return getHibernateTemplate().find("from Std");
	}

	@Override
	public void save(Std std) {	
		getHibernateTemplate().saveOrUpdate(std);	
	}

	@Override
	public void update(Std std) {
		getHibernateTemplate().saveOrUpdate(std);
	}

	@Override
	public Std getStd(int id){
		return (Std)getHibernateTemplate().get(Std.class, id);
	}
	
	//此方法为测试方法
	@Override
	public void modify(){
		Std s = (Std)getHibernateTemplate().get(Std.class, 7);
		s.setSname("erzzzzzzw");
		s.setAddr("zzzzzz");
	}
}
