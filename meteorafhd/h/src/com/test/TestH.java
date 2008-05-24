package com.test;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.all.HibernateSessionFactory;
import com.entity.Std;

public class TestH {

	public TestH() {
		super();
	}

	
	public static void main(String[] args) {
		Session session = null;

//-------insert--------------------------------------------
//		Std s=new Std();
//		s.setSname("betty");
//		s.setAddr("street3");
//		
//		try 
//		{
//			session = HibernateSessionFactory.currentSession();
//				Transaction tran = session.beginTransaction();
//					session.saveOrUpdate(s);
//				tran.commit();
//		} 
//		catch(Exception e)
//		{}
//		finally 
//		{
//			HibernateSessionFactory.closeSession();
//			session = null;
//		}
//
//		System.out.println("over");
//		
		
	


//---------select-----------------------------------
		Std s=null;
		List Stds=null;
		
		try 
		{
			session = HibernateSessionFactory.currentSession();
				Transaction tran = session.beginTransaction();
					Stds=session.createQuery("from Std").list(); 			
				tran.commit();
				
		} 
		catch(Exception e)
		{
			System.out.println("error");
		}
		finally 
		{
			HibernateSessionFactory.closeSession();
			session = null;
		}	
		
		if(Stds!=null)
		{
			Iterator iter=Stds.iterator();
			
			while(iter.hasNext())
			{  
				Object o=iter.next();
				s=(Std)o;
				System.out.println(s.getSname());
			}
		}
		
		
		
//--------------delete-----------------------------------		
//		String sql = "delete Std where sname=:name";
//		
//		try 
//		{
//			session = HibernateSessionFactory.currentSession();
//				Transaction tran = session.beginTransaction();
//					Query query = session.createQuery(sql);
//					query.setString("name", "betty");
//					query.executeUpdate();
//				tran.commit();
//				
//		} 
//		catch(Exception e)
//		{}
//		finally 
//		{
//			HibernateSessionFactory.closeSession();
//			session = null;
//		}	
//		
//		
//	}
	
	
	
//-------------update-----------------------------------		
//	String sql = "update Std  set sname=:new where sname=:old" ;
//
//	
//	try 
//	{
//		session = HibernateSessionFactory.currentSession();
//			Transaction tran = session.beginTransaction();
//				Query query = session.createQuery(sql);
//				query.setString("old", "jack");
//				query.setString("new", "john");
//				query.executeUpdate();
//			tran.commit();
//			
//	} 
//	catch(Exception e)
//	{
//		System.out.println("error");
//	}
//	finally 
//	{
//		HibernateSessionFactory.closeSession();
//		session = null;
//	}	
	
	
}

}
