package com.fhd.test3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

public class Test {

	public void test(){
//		this.t1();
		this.t2();
	}
	
	public static void main(String[] args) {
		new Test().test();
		
	}
	
	private void t2(){
		Session session = HibernateSessionFactory.getSession();
		String hql = " from People p ";
		session = HibernateSessionFactory.getSession();
		session.beginTransaction();
		Query query = session.createQuery(hql);
		List<People> list = query.list();
		People p=null;
		if(list.size()>0){
			p = list.get(0);
			System.out.println("pid:"+p.getId());
		}
		if(p!=null && p.getAddresses().size()>0){
			Set<Address> aSet = p.getAddresses();
			System.out.println("has address:"+aSet.size());
			for(Address a:aSet){
				aSet.remove(a);
				break;
			}
		}
		session.update(p);
		session.getTransaction().commit();
		session.close();
	}
	

	private void t1() {
		People people = new People();
		people.setName("linda");
		
		Address address = new Address();
		address.setAddressName("yunnan");
		address.setCodeNumber("564123");
		
		people.getAddresses().add(address);
		
		Session session = HibernateSessionFactory.getSession();
		session.beginTransaction();
		session.save(people);
		session.getTransaction().commit();
		session.close();
		
//		session = HibernateSessionFactory.getSession();
//		session.beginTransaction();
//		session.save(people);
//		session.getTransaction().commit();
		
		
//		session = HibernateSessionFactory.getSession();
//		session.beginTransaction();
//		session.delete(address);
//		session.getTransaction().commit();
//		session.beginTransaction();
//		session.delete(address);
//		session.delete(people);
//		session.getTransaction().commit();
		
//		session = HibernateSessionFactory.getSession();
//		String hql = " from People p , Address a where p.id = a.people ";
//		session = HibernateSessionFactory.getSession();
//		Query query = session.createQuery(hql);
//		System.out.println(query.list().size());
		
//		session = HibernateSessionFactory.getSession();
//		address = (Address)session.load(Address.class,1L);
//		address.setAddressName("xxxxx");
//		System.out.println(address.getPeople());
//		session.beginTransaction();
//		session.saveOrUpdate(address);
//		session.getTransaction().commit();
//		session.close();
	}
}