package com.fhd.test;

import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;



public class MessageTest
{

	public static void main(String[] args) throws Exception
	{
		Tool t = new Tool();
//		for(int n=0;n<10;n++){
//			t.insert();
//		}
		for(int n=0;n<10;n++){
			t.insert();
		}
		t.find();
		t.sessionClose();
	}
}
class Tool{
	Configuration conf = new Configuration().configure();
	SessionFactory sf = conf.buildSessionFactory();
	Session sess = sf.openSession();
	static int i=0,j=0;
	public void insert(){
		Transaction tx = null;		
		try{
			i++;j++;
			tx = sess.beginTransaction();
			Message n = new Message();
			n.setTitle("第"+i+"条消息");
			n.setContent("今天天晴了"+j);
			sess.save(n);
			sess.flush();
			tx.commit();		
		}catch(HibernateException e){
			e.printStackTrace();
		}
	}
	public void find(){
		Transaction tx = null;		
		try{
			tx = sess.beginTransaction();
//			List l = sess.createCriteria(Message.class)
//				.add(Restrictions.lt("id",new Integer(10)))
//				.list();
//			for(Iterator it = l.iterator();it.hasNext();){
//				Message m = (Message)it.next();
//				System.out.println(m.getTitle());
//			}
			
//			String hql = "from Message";
//			Query query = sess.createQuery(hql);
//			List list = query.list();
//			for(Iterator it = list.iterator();it.hasNext();){
//				Message m = (Message)it.next();
//				System.out.println(m.getTitle());
//			}
			
//			String hql = "update Message set title='update' where id=10";
//			Query query = sess.createQuery(hql);
//			query.executeUpdate();
//			tx.commit();
			
//			Query query = sess.getNamedQuery("test");
//			UserQuery uq = new UserQuery();
//			for(Iterator it = query.iterate();it.hasNext();){
//				Message m = (Message)it.next();
//				System.out.println(m.getTitle());
//			}
			
//			String hql = "from Message where id<:id";
//			Query query = sess.createQuery(hql);
//			
//			UserQuery uq = new UserQuery();
//			uq.setId(10);
//			query.setProperties(uq);
//			
//			List list = query.list();
//			for(Iterator it = list.iterator();it.hasNext();){
//				Message m = (Message)it.next();
//				System.out.println(m.getTitle());
//			}
			
			
			Query query = sess.getNamedQuery("test2");
			
			UserQuery uq = new UserQuery();
			uq.setId(10);
			
			query.setProperties(uq);
			for(Iterator it = query.iterate();it.hasNext();){
				Message m = (Message)it.next();
				System.out.println(m.getTitle());
			}
		}catch(HibernateException e){
			e.printStackTrace();
		}
	}
	public void sessionClose(){
		sess.close();
	}
}
