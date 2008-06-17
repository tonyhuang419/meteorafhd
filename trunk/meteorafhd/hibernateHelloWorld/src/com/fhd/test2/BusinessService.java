package com.fhd.test2;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class BusinessService {
	//session工厂类
	public static SessionFactory sessionFactory;
	//实始化session工厂
	static{
		try{
			//建立配置类，添加Student类和Teacher类
			Configuration config = new Configuration().configure();
			//得到sessionFactory对象
			sessionFactory = config.buildSessionFactory();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 通过学生类，查找教师类
	 * @param student Student
	 * @throws Exception
	 * @return List
	 */
	public List findTeacherByStudent(Student student) throws Exception{
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql ="from Student as o where o.teacher.id="+student.getid();
			Query query = session.createQuery(hql);
			List orders = query.list();
			tx.commit();
			return orders;
		}catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}
	/**
	 * 查找指定id的学生类
	 * @param student_id long
	 * @throws Exception
	 * @return Student
	 */
	public Student findStudent(long student_id) throws Exception{
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Student student=(Student)session.load(Student.class,new Long(student_id));
			tx.commit();
			return student;
		}catch (Exception e) {
			if (tx != null) {
				//发生错误，回滚
				tx.rollback();
			}
			throw e;
		} finally {
			//没有错误，关闭session
			session.close();
		}
	}

	/**
	 * 级连保存Teacher对象和Student对象
	 * @throws Exception
	 */
	public void saveTeacherAndStudentWithCascade() throws Exception{
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Teacher teacher=new Teacher("myTeacher");
			Student student1=new Student("student1",teacher);
			Student student2=new Student("student2",teacher);
			session.save(student1);
			session.save(student2);
			tx.commit();
		}catch (Exception e) {
			if (tx != null) {
				//发生错误，回滚
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			// 没有错误，关闭session
			session.close();
		}
	}
	/**
	 * 保存教师和学生对象
	 * @throws Exception
	 */
	public void saveTeacherAndStudent() throws Exception{
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Teacher teacher=new Teacher("teacher1");
			//级连中无此方法
			session.save(teacher);
			Student student1=new Student("student001",teacher);
			Student student2=new Student("student002",teacher);
			session.save(student1);
			session.save(student2);
			//提交事务
			tx.commit();
		}catch (Exception e) {
			if (tx != null) {
				//发生错误，回滚
				tx.rollback();
			}
			throw e;
		} finally {
			// 没有错误，关闭session
			session.close();
		}
	}
	/**
	 * 输出学生对象集合
	 * @param students List
	 */
	public void printStudents(List students){
		for (Iterator it = students.iterator(); it.hasNext();) {
			Student student=(Student)it.next();
			System.out.println("OrderNumber of "+student.getTeacher().getTeacherName()+ " :"+student.getStudentName());
		}
	}
	/**
	 * 测试方法
	 * @throws Exception
	 */
	public void test() throws Exception{
//		saveTeacherAndStudent();
//		saveTeacherAndStudentWithCascade();
//		Student student=findStudent(1);
//		List students = findTeacherByStudent(student);
//		printStudents(students);
		testHQL();
	}
	
	public void testHQL() {
		Session session = sessionFactory.openSession();
		String hql ="from Student s,Teacher t where s.id = t.id";
		Query query = session.createQuery(hql);
		List orders = query.list();
		Iterator it = orders.iterator();
		while(it.hasNext()){
			Object[] o = (Object[])it.next();
			Student s = (Student)o[0];		
			Teacher t = (Teacher)o[1];
			System.out.print (s.getStudentName()+"    ");
			System.out.println(t.getTeacherName());
		}
	}
	
	public static void main(String args[]) throws Exception {
		new BusinessService().test();
		sessionFactory.close();
	}
}
