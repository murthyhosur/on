package org.on.global;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DAO {
	
	private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
	
	private static final Logger log = Logger.getAnonymousLogger();
	
	@SuppressWarnings("deprecation")
	private static final SessionFactory factory = new Configuration().configure().buildSessionFactory();
	
	public static Session getSession() {
		Session session = (Session) DAO.session.get();
		if(session == null) {
			session = factory.openSession();
			DAO.session.set(session);
		}
		return session;
	}
	
	public static void begin() {
		getSession().beginTransaction();
	}
	
	public static void save(Object obj) {
		getSession().save(obj);
	}
	
	public static void saveOrUpdate(Object obj) {
		getSession().saveOrUpdate(obj);
	}
	
	public static void commit() {
		try{
			getSession().getTransaction().commit();
		} catch(HibernateException h) {
			log.log(Level.WARNING,"Cannot commit");
		}
	}

	public static void rollback() {
		try {
			getSession().getTransaction().rollback();
		} catch(HibernateException h) {
			log.log(Level.WARNING, "Cannot rollback");
		}
		close();
	}
	
	public static void close() {
		try{
			getSession().close();
		} catch(HibernateException h) {
			log.log(Level.WARNING,"Cannot close");
		}
		DAO.session.set(null);
	}
	public static void saveObject(Object obj) {	
		begin();
		saveOrUpdate(obj);
		commit();
		close();
	}
}
