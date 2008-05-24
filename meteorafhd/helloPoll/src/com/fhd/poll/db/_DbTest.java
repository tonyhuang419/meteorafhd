package com.fhd.poll.db;
import junit.framework.TestCase;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class _DbTest extends  TestCase {
	Session sess = null;
	
	public void test() throws Exception {
		try{
			//-------insert--------------------------------------------
			Poll p = new Poll();
			p.setName("2");
			p.setPrice(4000);

			try {
				sess = HibernateSessionFactory.currentSession();
				Transaction tran = sess.beginTransaction();
				sess.save(p);
				sess.flush();
				tran.commit();
			} 
			catch(Exception e){
				e.printStackTrace();
			}
			finally {
				HibernateSessionFactory.closeSession();
				sess = null;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
	}
	public void test2() throws Exception {
		try{
			//-------insert--------------------------------------------
			Poll p = new Poll();
			p.setName("2");
			p.setPrice(4000);

			try {
				sess = HibernateSessionFactory.currentSession();
				Transaction tran = sess.beginTransaction();
				sess.save(p);
				sess.flush();
				tran.commit();
			} 
			catch(Exception e){
				e.printStackTrace();
			}
			finally {
				HibernateSessionFactory.closeSession();
				sess = null;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
	}
}
