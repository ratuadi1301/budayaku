package com.ata.apps.budayaku.service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slim3.util.BeanUtil;
import org.slim3.util.CopyOptions;

import com.ata.apps.budayaku.dao.DAO;
import com.ata.apps.budayaku.dto.DTO;
import com.ata.apps.budayaku.model.ModelEntity;
import com.ata.apps.budayaku.util.HibernateUtil;

public abstract class Service<M extends ModelEntity, D extends DTO> {

	protected DAO<M> dao;
	protected Logger log;
	protected CopyOptions copyOptions;

	public Service(DAO<M> dao, Logger log) {
		this(dao, log, null);
	}

	public Service(DAO<M> dao, Logger log, CopyOptions copyOptions) {
		if (dao == null) {
			throw new IllegalArgumentException("Null DAO");
		}
		this.dao = dao;
		this.log = log;
		this.copyOptions = copyOptions;
	}

	public M getById(Long id) throws IllegalArgumentException {
		if (id == null) {
			throw new IllegalArgumentException("Null ID");
		}

		M bean = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			bean = dao.getById(session, id);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return bean;
	}

	public M getByCriterions(Criterion... criterions)
			throws IllegalArgumentException {
		M bean = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			bean = dao.getByCriterions(session, criterions);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return bean;
	}

	public M getByParams(final Map<String, Object> params) {
		List<M> list = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			list = dao.listByParams(session, params, 0, 1, null, false);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return (list == null || list.isEmpty()) ? null : list.get(0);
	}

	public List<M> list() {
		return list(true);
	}

	public List<M> list(boolean activeOnly) {
		List<M> list = new ArrayList<M>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			if (activeOnly) {
				list = dao.listByCriterions(session, 0, 0, null,
						Restrictions.eq(ModelEntity.DISABLED, false));
			} else {
				list = dao.list(session);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	public List<M> list(int start, int limit, String orderProperty, boolean desc) {
		return list(true, start, limit, orderProperty, desc);
	}

	public List<M> list(boolean activeOnly, int start, int limit,
			String orderProperty, boolean desc) {
		List<M> list = new ArrayList<M>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			if (activeOnly) {
				list = dao.listByCriterions(session, start, limit,
						orderProperty, desc,
						Restrictions.eq(ModelEntity.DISABLED, false));
			} else {
				list = dao.list(session, start, limit, orderProperty, desc);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	public List<M> listByCriterions(Criterion... criterions) {
		return listByCriterions(true, 0, 0, null, criterions);
	}

	public List<M> listByCriterions(Order sortCriterion,
			Criterion... criterions) {
		return listByCriterions(true, 0, 0, sortCriterion, criterions);
	}

	public List<M> listByCriterions(int offset, int limit, Order order,
			Criterion... criterions) {
		return listByCriterions(true, offset, limit, order, criterions);
	}

	public List<M> listByCriterions(boolean activeOnly, int offset, int limit,
			Order order, Criterion... criterions) {
		List<M> list = new ArrayList<M>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			if (activeOnly) {
				list = dao.listByCriterions(session, offset, limit, order,
						activeOnlyCriterions(criterions));
			} else {
				list = dao.listByCriterions(session, offset, limit, order,
						criterions);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	public List<M> listByCriterions(int start, int limit, String orderProperty,
			boolean desc, Criterion... criterions) {
		return listByCriterions(true, start, limit, orderProperty, desc,
				criterions);
	}

	public List<M> listByCriterions(boolean activeOnly, int start, int limit,
			String orderProperty, boolean desc, Criterion... criterions) {
		Order order = null;
		if (orderProperty != null) {
			order = desc ? Order.desc(orderProperty) : Order.asc(orderProperty);
		}

		List<M> list = new ArrayList<M>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			if (activeOnly) {
				list = dao.listByCriterions(session, start, limit,
						orderProperty, desc, activeOnlyCriterions(criterions));
			} else {
				list = dao.listByCriterions(session, start, limit, order,
						criterions);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}

	public List<M> listByParams(final Map<String, Object> params) {
		List<M> bean = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			bean = dao.listByParams(session, params, 0, 0, null, false);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return bean;
	}

	public int count() {
		return count(true);
	}

	public int count(boolean activeOnly) {
		int count = 0;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			if (activeOnly) {
				count = dao.countByCriterions(session,
						Restrictions.eq(ModelEntity.DISABLED, false));
			} else {
				count = dao.count(session);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return count;
	}

	public int countByCriterions(Criterion... criterions) {
		return countByCriterions(true, criterions);
	}

	public int countByCriterions(boolean activeOnly, Criterion... criterions) {
		int count = 0;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			if (activeOnly) {
				count = dao.countByCriterions(session,
						activeOnlyCriterions(criterions));
			} else {
				count = dao.countByCriterions(session, criterions);
			}
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return count;
	}

	public M saveAsBean(D dto) {
		M bean = getServerBean(dto);
		if (bean == null) {
			throw new IllegalArgumentException("Invalid request data");
		}

		Long id = store(bean);
		bean.setId(id);
		return bean;
	}

	public Long store(M model) {
		M m = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			m = dao.save(session, model);
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			throw e;
		} finally {
			session.close();
		}
		return (m != null) ? m.getId() : null;
	}

	public Long add(Map<String, Object> input) throws IllegalArgumentException {
		if (input == null) {
			throw new IllegalArgumentException("Null input map");
		}

		M model = null;
		if (copyOptions != null) {
			BeanUtil.copy(input, model, copyOptions);
		} else {
			BeanUtil.copy(input, model);
		}
		return store(model);
	}

	public Long edit(Long id, Map<String, Object> input)
			throws IllegalArgumentException {
		if (input == null) {
			throw new IllegalArgumentException("Null input map");
		}

		M model = getById(id);
		if (model == null) {
			throw new IllegalArgumentException(
					"No object found for the specified ID");
		}

		if (copyOptions != null) {
			BeanUtil.copy(input, model, copyOptions);
		} else {
			BeanUtil.copy(input, model);
		}
		return store(model);
	}

	public boolean delete(M bean) throws IllegalArgumentException {
		if (bean == null || bean.getId() == null) {
			throw new IllegalArgumentException("Bean or it's ID is null");
		}

		assertNoReference(bean.getId());

		boolean success = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			dao.delete(session, bean);
			success = true;
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return success;
	}

	public boolean disable(M bean) throws IllegalArgumentException {
		if (bean == null) {
			throw new IllegalArgumentException("Bean is null");
		}

		boolean success = false;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			bean.setDisabled(true);
			success = true;
			transaction.commit();
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return success;
	}

	public DAO<M> getDao() {
		return dao;
	}

	public List<D> getDataTransferObjectList(List<M> list) {
		List<D> dtoList = new ArrayList<D>();
		for (M bean : list) {
			dtoList.add(getDataTransferObject(bean));
		}
		return dtoList;
	}

	public abstract Class<D> getDtoClass();

	public D getDataTransferObject(M bean) {
		D dto = null;
		try {
			dto = getDtoClass().newInstance();
			copyBeanFieldsToDTO(bean, dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public M getServerBean(D dto) {
		M bean = null;
		try {
			bean = dao.getClazz().newInstance();
			copyDTOFieldsToBean(dto, bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	protected void copyBeanFieldsToDTO(M bean, D dto) {
		dto.setId(bean.getId());
		dto.setCreatedTime(bean.getCreatedTime());
		dto.setModifiedTime(bean.getModifiedTime());
		dto.setVersion(bean.getVersion());
		dto.setDisabled(bean.isDisabled());
	}

	protected void copyDTOFieldsToBean(D dto, M bean) {
		bean.setId(dto.getId());
		if (dto.getVersion() != null) {
			bean.setVersion(dto.getVersion());
		}
		bean.setDisabled(dto.isDisabled());
	}

	private void assertNoReference(Long id) {
		if (isReferred(id)) {
			throw new IllegalArgumentException(dao.getClazz().getSimpleName()
					+ " " + id + " is still being referred");
		}
	}

	public abstract boolean isReferred(Long id);

	private Criterion[] activeOnlyCriterions(Criterion... criterions) {
		int length = criterions != null ? criterions.length : 0;
		Criterion[] newCriterions = (Criterion[]) Array.newInstance(
				Criterion.class, length + 1);
		if (criterions != null) {
			System.arraycopy(criterions, 0, newCriterions, 0, length);
		}
		newCriterions[length] = Restrictions.eq(ModelEntity.DISABLED, false);
		return newCriterions;
	}

}
