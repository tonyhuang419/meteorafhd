
package com.waterKing;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;


/**
 * @author Hades Guan
 * 
 */
public class HibernateUtil
{
	private static SessionFactory factory = null;

	private static ThreadLocal<Session> local = new ThreadLocal<Session>();

	/**
	 * 运用Singleton模式初始化SessionFactory实例
	 * 
	 * @return SessionFactory实例
	 */
	private static SessionFactory getSessionFactory()
	{
		if (factory == null)
		{
			File file = new File("src/main/resources/hibernate.cfg.xml");
			factory = new AnnotationConfiguration().configure(file).buildSessionFactory();
		}
		return factory;
	}

	/**
	 * 获取Session实例
	 * 
	 * @return Session实例
	 */
	public static Session getSession()
	{
		Session s = local.get();
		if (s == null)
		{
			s = HibernateUtil.getSessionFactory().openSession();
			local.set(s);
		}
		return s;
	}

	public static void closeSession()
	{
		Session s = local.get();
		if (s != null)
			s.close();
		local.set(null);
	}
}
