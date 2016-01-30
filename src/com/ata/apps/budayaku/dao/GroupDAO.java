package com.ata.apps.budayaku.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ata.apps.budayaku.model.Group;
import com.ata.apps.budayaku.util.HibernateUtil;

public class GroupDAO extends DAO<Group> {

	protected GroupDAO() {
		super(Group.class);
	}

	private static GroupDAO instance;

	public static GroupDAO get() {
		System.out.println("GroupDao");
		if (instance == null) {
			instance = new GroupDAO();
		}
		return instance;
	}

	public Group getByIdNew(Long id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		Group t = null;
		try {
			transaction = session.beginTransaction();
			t = (Group) session.get(clazz, id);
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
