package dev;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ata.apps.budayaku.dao.GroupDAO;
import com.ata.apps.budayaku.dao.UserDAO;
import com.ata.apps.budayaku.model.Group;
import com.ata.apps.budayaku.model.User;
import com.ata.apps.budayaku.util.BCrypt;
import com.ata.apps.budayaku.util.HibernateUtil;

public class InitData {

	public static void main(String[] args) throws Exception {
		HibernateUtil.getConfiguration().setProperty("hibernate.hbm2ddl.auto",
				"create-drop");

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = null;
		try {
			txn = session.beginTransaction();

			dataUsers(session);
			
			
			txn.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (txn != null) {
				txn.rollback();
			}
		} finally {
			session.close();
		}

		System.out.println("-- Done running "
				+ InitData.class.getSimpleName());
	}
	
	public static void execute() throws Exception{
		HibernateUtil.getConfiguration().setProperty("hibernate.hbm2ddl.auto",
				"create-drop");

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = null;
		try {
			txn = session.beginTransaction();

			dataUsers(session);
			
			
			txn.commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			if (txn != null) {
				txn.rollback();
			}
		} finally {
			session.close();
		}

		System.out.println("-- Done running "
				+ InitData.class.getSimpleName());
		
	}

	
	private static void dataUsers(Session session) {
		
		Group g, g1;

		g = new Group();
		g.setName("Admin");
		GroupDAO.get().save(session, g);

		g1 = new Group();
		g1.setName("User");
		
		GroupDAO.get().save(session, g1);

		User u = new User();
		u.setFullName("Administrator");
		u.setUsername("adm");
		u.setPwdDigest(BCrypt.hashpw("p", BCrypt.gensalt()));
		u.setGroup(g);
		UserDAO.get().save(session, u);

		User u1 = new User();
		u1.setFullName("Receptionist");
		u1.setUsername("rsp");
		u1.setPwdDigest(BCrypt.hashpw("p", BCrypt.gensalt()));
		u1.setGroup(g1);
		UserDAO.get().save(session, u1);
		
		User ap = new User();
		ap.setFullName("Adinoto P");
		ap.setUsername("ap");
		ap.setPwdDigest(BCrypt.hashpw("p", BCrypt.gensalt()));
		ap.setGroup(g);
		UserDAO.get().save(session, ap);
		
		
		
		GroupDAO.get().save(session, g);

		GroupDAO.get().save(session, g1);
		
		

	}
	
}
