package com.ata.apps.budayaku.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ata.apps.budayaku.model.ModelEntity;
import com.ata.apps.budayaku.util.HibernateUtil;

public class DAO<T extends ModelEntity> {

	protected Class<T> clazz;

	protected DAO(Class<T> clazz) {
		this.clazz = clazz;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	@SuppressWarnings("unchecked")
	public T getById(Session session, Serializable id) {
		T bean = (T) session.get(clazz, id);
		return bean;
	}

	@SuppressWarnings("unchecked")
	public T getByCriterions(Session session, Criterion... criterions) {
		T bean = null;

		Criteria criteria = session.createCriteria(clazz.getName());
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		List<T> list = criteria.list();
		if (list != null && list.size() > 0) {
			bean = list.get(0);
		}

		return bean;
	}

	@SuppressWarnings("unchecked")
	public List<T> list(Session session) {
		List<T> list = session.createCriteria(clazz.getName()).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<T> list(Session session, int start, int limit,
			String orderProperty, boolean desc) {
		Criteria criteria = session.createCriteria(clazz.getName());
		if (start > 0) {
			criteria.setFirstResult(start);
		}
		if (limit > 0) {
			criteria.setMaxResults(limit);
		}
		if (orderProperty != null) {
			if (desc) {
				criteria.addOrder(Order.desc(orderProperty));
			} else {
				criteria.addOrder(Order.asc(orderProperty));
			}
		}
		return criteria.list();
	}

	public List<T> listByCriterions(Session session, Criterion... criterions) {
		return listByCriterions(session, 0, 0, null, criterions);
	}

	public List<T> listByCriterions(Session session, int start, int limit,
			String orderProperty, boolean desc, Criterion... criterions) {
		Order order = null;
		if (orderProperty != null) {
			order = desc ? Order.desc(orderProperty) : Order.asc(orderProperty);
		}
		return listByCriterions(session, start, limit, order, criterions);
	}

	@SuppressWarnings("unchecked")
	public List<T> listByCriterions(Session session, int start, int limit,
			Order order, Criterion... criterions) {
		Criteria criteria = session.createCriteria(clazz.getName());
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		if (start > 0) {
			criteria.setFirstResult(start);
		}
		if (limit > 0) {
			criteria.setMaxResults(limit);
		}
		if (order != null) {
			criteria.addOrder(order);
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<T> listByQuery(Session session, String queryString) {
		return (List<T>) session.createQuery(queryString).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> listByParams(final Session session,
			final Map<String, Object> params, final int start, final int limit,
			final String order, final boolean desc) {
		Criteria criteria = getCriteria(session, params);
		setCriteriaOrder(criteria, order, desc);
		setFetchLimit(criteria, start, limit);
		return criteria.list();
	}

	private Criteria getCriteria(final Session session,
			final Map<String, Object> params) {
		Criteria criteria = session.createCriteria(clazz);
		if (params != null && params.size() > 0) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if (value != null) {
					criteria.add(Restrictions.eq(key, value));
				} else {
					criteria.add(Restrictions.isNull(key));
				}
			}
		}
		return criteria;
	}

	private static void setCriteriaOrder(Criteria criteria, final String order,
			final boolean desc) {
		if (order != null) {
			if (desc) {
				criteria.addOrder(Order.asc(order));
			} else {
				criteria.addOrder(Order.desc(order));
			}
		}
		criteria.addOrder(Order.asc(ModelEntity.ID));
	}

	private static void setFetchLimit(Criteria criteria, final int start,
			final int limit) {
		if (start > 0) {
			criteria.setFirstResult(start);
		}
		if (limit > 0) {
			criteria.setMaxResults(limit);
		}
	}

	public int count(Session session) {
		return (Integer) session.createCriteria(clazz.getName())
				.setProjection(Projections.rowCount()).uniqueResult();
	}

	public int countByCriterions(Session session, Criterion... criterions) {
		Criteria criteria = session.createCriteria(clazz.getName());
		for (Criterion criterion : criterions) {
			criteria.add(criterion);
		}
		return (Integer) criteria.setProjection(Projections.rowCount())
				.uniqueResult();
	}

	public T save(Session session, T t) {
		t.setModifiedTime(new Date());
		session.saveOrUpdate(t);
		return t;
	}

	public boolean delete(Session session, T t) {
		session.delete(t);
		return true;
	}

	public static boolean isRefered(Class<?> referClazz, String referFieldName,
			Long id) {
		boolean referenced = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			String strQuery = "select count(*) from " + referClazz.getName()
					+ " where " + referFieldName + " = :id";
			Query query = session.createQuery(strQuery);
			query.setLong("id", id);
			long count = (Long) query.uniqueResult();
			referenced = (count > 0);

			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return referenced;
	}

}
