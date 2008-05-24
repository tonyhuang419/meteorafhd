package com.fhd.poll.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {


    private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
    private static final ThreadLocal threadLocal = new ThreadLocal();
    private static Configuration cfg = new Configuration();
    private static org.hibernate.SessionFactory sessionFactory;

    public static Session currentSession() throws HibernateException {
        Session session = (Session) threadLocal.get();

        if (session == null) {
            if (sessionFactory == null) {
                try {     	
                    cfg = new AnnotationConfiguration().configure(CONFIG_FILE_LOCATION);
                    sessionFactory = cfg.buildSessionFactory();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            session = sessionFactory.openSession();
            threadLocal.set(session);
        }
        return session;
    }

    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);

        if (session != null) {
            session.close();
        }
    }
    private HibernateSessionFactory() {
    }
}
