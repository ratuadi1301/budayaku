package com.ata.apps.budayaku.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ata.apps.budayaku.model.User;
import com.ata.apps.budayaku.util.HibernateUtil;

public class UserDAO extends DAO<User> {

	protected UserDAO() {
		super(User.class);
	}

	private static UserDAO instance;

	public static UserDAO get() {
		if (instance == null) {
			instance = new UserDAO();
		}
		return instance;
	}
	
	
	public User getById(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		User t = null;
		try {
			transaction = session.beginTransaction();
			t = (User) session.get(clazz, id);
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return t;
	}


}
