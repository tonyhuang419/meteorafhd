package com.fhd.test4;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;




public class BusinessService {

	public static SessionFactory sessionFactory;

	static{
		try{
			Configuration config = new Configuration().configure();
			sessionFactory = config.buildSessionFactory();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void test() throws Exception{

		testHQLSubInquiry();

	}
	
	public void testHQLSubInquiry() {
//		select * from students s where s.id in ( select tt.id from teachers tt);
		Session session = sessionFactory.openSession();
		String hql = "from Student s,Teacher tx where s.id in (select x.sid from TsRelation x where x.tid " +
				" in ( select t.id from Teacher t ) ) " ;
		Query query = session.createQuery(hql);
		List orders = query.list();
		Iterator it = orders.iterator();
		while(it.hasNext()){
			Object[] o = (Object[])it.next();
			Student s = (Student)o[0];		
			Teacher t = (Teacher)o[1];
			System.out.print(s.getid()+"   ");
			System.out.print (s.getStudentName()+"   ");
			System.out.print(t.getTeacherName()+"   ");
			System.out.println(t.getid());
		}
	}
	
	public static void main(String args[]) throws Exception {
		new BusinessService().test();
		sessionFactory.close();
	}
}
