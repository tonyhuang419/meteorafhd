package com.fhd.test3;

import org.hibernate.Session;

public class Test {

	public static void main(String[] args) {

		People people = new People();
		people.setName("linda");
		Address address = new Address();
		address.setAddressName("yunnan");
		address.setCodeNumber("564123");
		address.setPeople(people);
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
		
		session = HibernateSessionFactory.getSession();
		address = (Address)session.load(Address.class,1L);
		address.setAddressName("xxxxx");
		System.out.println(address.getPeople());
		session.beginTransaction();
		session.saveOrUpdate(address);
		session.getTransaction().commit();
		session.close();
		
	}
}