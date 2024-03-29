/* 
 * Copyright 2008 The Corner Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package corner.services.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import corner.model.PaginationList;
import corner.model.PaginationOptions;
import corner.services.EntityService;

/**
 * 公用的实体服务类的实现.
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 5477 $
 * @since 0.0.1
 */
public class EntityServiceImpl  implements EntityService{
	private final HibernateTemplate template;
	private final SessionFactory sessionFactory;
    private PaginatedEntityService paginatedService;

    public EntityServiceImpl(final Session session,final TypeCoercer typeCoercer){
		this.template = new HibernateTemplate(){
			/**
			 * @see org.springframework.orm.hibernate3.HibernateTemplate#execute(org.springframework.orm.hibernate3.HibernateCallback, boolean)
			 */
			@Override
			public Object doExecute(HibernateCallback action,boolean enforceNewSession, boolean enforceNativeSessio) throws DataAccessException {
				try {
					return action.doInHibernate(session);
				} catch (HibernateException ex) {
					throw convertHibernateAccessException(ex);
				} catch (SQLException ex) {
					throw convertJdbcAccessException(ex);
				}
			}
		};
		this.sessionFactory = session.getSessionFactory();
        this.paginatedService = new PaginatedEntityService(template,typeCoercer);
	}


    /**
	 * @param queryString
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(java.lang.String, java.lang.Object)
	 */
	
	public int bulkUpdate(String queryString, Object value)
			throws DataAccessException {
		return template.bulkUpdate(queryString, value);
	}

	/**
	 * @param queryString
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(java.lang.String, java.lang.Object[])
	 */
	
	public int bulkUpdate(String queryString, Object[] values)
			throws DataAccessException {
		return template.bulkUpdate(queryString, values);
	}

	/**
	 * @param queryString
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(java.lang.String)
	 */
	
	public int bulkUpdate(String queryString) throws DataAccessException {
		return template.bulkUpdate(queryString);
	}

	/**
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#delete(java.lang.Object, org.hibernate.LockMode)
	 */
	
	public void delete(Object entity, LockMode lockMode)
			throws DataAccessException {
		template.delete(entity, lockMode);
	}

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#delete(java.lang.Object)
	 */
	
	public void delete(Object entity) throws DataAccessException {
		template.delete(entity);
	}

	/**
	 * @param entities
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#deleteAll(java.util.Collection)
	 */
	
	public void deleteAll(Collection entities) throws DataAccessException {
		template.deleteAll(entities);
	}

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#evict(java.lang.Object)
	 */
	public void evict(Object entity) throws DataAccessException {
		template.evict(entity);
	}

    public long count(Class<?> persistClass, Object conditions) {
        return paginatedService.count(persistClass, conditions);
    }

    public Iterator<?> find(Class<?> persistClass, Object conditions, String order) {
        return paginatedService.find(persistClass, conditions, order);
    }

    public PaginationList paginate(Class<?> persistClass, Object conditions, String order, PaginationOptions options) {
        return paginatedService.paginate(persistClass, conditions, order, options);
    }

    /**
	 * @param queryString
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#find(java.lang.String, java.lang.Object)
	 */
	public Iterator<?> find(String queryString, Object value)
			throws DataAccessException {
		return find(queryString, new Object[]{value});
	}

	/**
	 * @param queryString
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#find(java.lang.String, java.lang.Object[])
	 */
	public Iterator<?> find(final String queryString, final Object[] values)
			throws DataAccessException {
		return  (Iterator<?>) template.executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createQuery(queryString);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i, values[i]);
					}
				}
				return queryObject.iterate();
			}
		});
	}



	


	/**
	 * @param queryString
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#find(java.lang.String)
	 */
	public Iterator<?> find(String queryString) throws DataAccessException {
		return find(queryString,new Object[]{});
	}

	/**
	 * @param queryString
	 * @param paramName
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedParam(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public Iterator<?> findByNamedParam(String queryString, String paramName,
			Object value) throws DataAccessException {
		return findByNamedParam(queryString,new String[]{paramName},new Object[]{value});
	}

	/**
	 * @param queryString
	 * @param paramNames
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public Iterator<?> findByNamedParam(final String queryString, final String[] paramNames,
			final Object[] values) throws DataAccessException {
		if (paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}
		return (Iterator<?>) template.executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.createQuery(queryString);
					for (int i = 0; i < values.length; i++) {
						applyNamedParameterToQuery(queryObject, paramNames[i], values[i]);
					}
				return queryObject.list();
			}
		});
	}
	/**
	 * Apply the given name parameter to the given Query object.
	 * @param queryObject the Query object
	 * @param paramName the name of the parameter
	 * @param value the value of the parameter
	 * @throws HibernateException if thrown by the Query object
	 */
	protected void applyNamedParameterToQuery(Query queryObject, String paramName, Object value)
			throws HibernateException {

		if (value instanceof Collection) {
			queryObject.setParameterList(paramName, (Collection) value);
		}
		else if (value instanceof Object[]) {
			queryObject.setParameterList(paramName, (Object[]) value);
		}
		else {
			queryObject.setParameter(paramName, value);
		}
	}
	/**
	 * @param queryName
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(java.lang.String, java.lang.Object)
	 */
	public Iterator<?> findByNamedQuery(String queryName, Object value)
			throws DataAccessException {
		return findByNamedQuery(queryName,new Object[]{value});
	}

	/**
	 * @param queryName
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(java.lang.String, java.lang.Object[])
	 */
	public Iterator<?> findByNamedQuery(final String queryName, final Object[] values)
			throws DataAccessException {
		return  (Iterator<?>) template.executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.getNamedQuery(queryName);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						queryObject.setParameter(i, values[i]);
					}
				}
				return queryObject.iterate();
			}
		});	
	}

	/**
	 * @param queryName
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(java.lang.String)
	 */
	public Iterator<?> findByNamedQuery(String queryName) throws DataAccessException {
		return findByNamedQuery(queryName,new Object[]{});
	}

	/**
	 * @param queryName
	 * @param paramName
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQueryAndNamedParam(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public Iterator<?> findByNamedQueryAndNamedParam(String queryName,
			String paramName, Object value) throws DataAccessException {
		return findByNamedQueryAndNamedParam(queryName, new String[]{paramName},
				new Object[]{value});
	}

	/**
	 * @param queryName
	 * @param paramNames
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQueryAndNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public Iterator<?> findByNamedQueryAndNamedParam(final String queryName,
			final String[] paramNames, final Object[] values) throws DataAccessException {
		if (paramNames != null && values != null && paramNames.length != values.length) {
			throw new IllegalArgumentException("Length of paramNames array must match length of values array");
		}
		return (Iterator<?>) template.executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Query queryObject = session.getNamedQuery(queryName);
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						applyNamedParameterToQuery(queryObject, paramNames[i], values[i]);
					}
				}
				return queryObject.iterate();
			}
		});
	}

	

	

	/**
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#flush()
	 */
	public void flush() throws DataAccessException {
		template.flush();
	}

	/**
	 * @param entityClass
	 * @param id
	 * @param lockMode
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(java.lang.Class, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object get(Class entityClass, Serializable id, LockMode lockMode)
			throws DataAccessException {
		return template.get(entityClass, id, lockMode);
	}

	/**
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(java.lang.Class, java.io.Serializable)
	 */
	public Object get(Class entityClass, Serializable id)
			throws DataAccessException {
		return template.get(entityClass, id);
	}

	/**
	 * @param entityName
	 * @param id
	 * @param lockMode
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(java.lang.String, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object get(String entityName, Serializable id, LockMode lockMode)
			throws DataAccessException {
		return template.get(entityName, id, lockMode);
	}

	/**
	 * @param entityName
	 * @param id
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(java.lang.String, java.io.Serializable)
	 */
	public Object get(String entityName, Serializable id)
			throws DataAccessException {
		return template.get(entityName, id);
	}

	/**
	 * @param proxy
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#initialize(java.lang.Object)
	 */
	public void initialize(Object proxy) throws DataAccessException {
		template.initialize(proxy);
	}


	/**
	 * @param queryString
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(java.lang.String, java.lang.Object)
	 */
	public Iterator iterate(String queryString, Object value)
			throws DataAccessException {
		return template.iterate(queryString, value);
	}

	/**
	 * @param queryString
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(java.lang.String, java.lang.Object[])
	 */
	public Iterator iterate(String queryString, Object[] values)
			throws DataAccessException {
		return template.iterate(queryString, values);
	}

	/**
	 * @param queryString
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(java.lang.String)
	 */
	public Iterator iterate(String queryString) throws DataAccessException {
		return template.iterate(queryString);
	}

	/**
	 * @param entityClass
	 * @param id
	 * @param lockMode
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.Class, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object load(Class entityClass, Serializable id, LockMode lockMode)
			throws DataAccessException {
		return template.load(entityClass, id, lockMode);
	}

	/**
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.Class, java.io.Serializable)
	 */
	public Object load(Class entityClass, Serializable id)
			throws DataAccessException {
		return template.load(entityClass, id);
	}

	/**
	 * @param entity
	 * @param id
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.Object, java.io.Serializable)
	 */
	public void load(Object entity, Serializable id) throws DataAccessException {
		template.load(entity, id);
	}

	/**
	 * @param entityName
	 * @param id
	 * @param lockMode
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.String, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object load(String entityName, Serializable id, LockMode lockMode)
			throws DataAccessException {
		return template.load(entityName, id, lockMode);
	}

	/**
	 * @param entityName
	 * @param id
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.String, java.io.Serializable)
	 */
	public Object load(String entityName, Serializable id)
			throws DataAccessException {
		return template.load(entityName, id);
	}

	/**
	 * @param entityClass
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#loadAll(java.lang.Class)
	 */
	public List loadAll(Class entityClass) throws DataAccessException {
		return template.loadAll(entityClass);
	}

	/**
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#lock(java.lang.Object, org.hibernate.LockMode)
	 */
	public void lock(Object entity, LockMode lockMode)
			throws DataAccessException {
		template.lock(entity, lockMode);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#lock(java.lang.String, java.lang.Object, org.hibernate.LockMode)
	 */
	public void lock(String entityName, Object entity, LockMode lockMode)
			throws DataAccessException {
		template.lock(entityName, entity, lockMode);
	}

	/**
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#merge(java.lang.Object)
	 */
	public Object merge(Object entity) throws DataAccessException {
		return template.merge(entity);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#merge(java.lang.String, java.lang.Object)
	 */
	public Object merge(String entityName, Object entity)
			throws DataAccessException {
		return template.merge(entityName, entity);
	}

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#persist(java.lang.Object)
	 */
	public void persist(Object entity) throws DataAccessException {
		template.persist(entity);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#persist(java.lang.String, java.lang.Object)
	 */
	public void persist(String entityName, Object entity)
			throws DataAccessException {
		template.persist(entityName, entity);
	}

	/**
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#refresh(java.lang.Object, org.hibernate.LockMode)
	 */
	public void refresh(Object entity, LockMode lockMode)
			throws DataAccessException {
		template.refresh(entity, lockMode);
	}

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#refresh(java.lang.Object)
	 */
	public void refresh(Object entity) throws DataAccessException {
		template.refresh(entity);
	}

	/**
	 * @param entity
	 * @param replicationMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#replicate(java.lang.Object, org.hibernate.ReplicationMode)
	 */
	public void replicate(Object entity, ReplicationMode replicationMode)
			throws DataAccessException {
		template.replicate(entity, replicationMode);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @param replicationMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#replicate(java.lang.String, java.lang.Object, org.hibernate.ReplicationMode)
	 */
	public void replicate(String entityName, Object entity,
			ReplicationMode replicationMode) throws DataAccessException {
		template.replicate(entityName, entity, replicationMode);
	}

	/**
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#save(java.lang.Object)
	 */
	public Serializable save(Object entity) throws DataAccessException {
		return template.save(entity);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#save(java.lang.String, java.lang.Object)
	 */
	public Serializable save(String entityName, Object entity)
			throws DataAccessException {
		return template.save(entityName, entity);
	}

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(Object entity) throws DataAccessException {
		template.saveOrUpdate(entity);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdate(java.lang.String, java.lang.Object)
	 */
	public void saveOrUpdate(String entityName, Object entity)
			throws DataAccessException {
		template.saveOrUpdate(entityName, entity);
	}

	/**
	 * @param entities
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdateAll(java.util.Collection)
	 */
	public void saveOrUpdateAll(Collection entities) throws DataAccessException {
		template.saveOrUpdateAll(entities);
	}

	/**
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.Object, org.hibernate.LockMode)
	 */
	public void update(Object entity, LockMode lockMode)
			throws DataAccessException {
		template.update(entity, lockMode);
	}

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.Object)
	 */
	public void update(Object entity) throws DataAccessException {
		template.update(entity);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.String, java.lang.Object, org.hibernate.LockMode)
	 */
	public void update(String entityName, Object entity, LockMode lockMode)
			throws DataAccessException {
		template.update(entityName, entity, lockMode);
	}

	/**
	 * @param entityName
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.String, java.lang.Object)
	 */
	public void update(String entityName, Object entity)
			throws DataAccessException {
		template.update(entityName, entity);
	}

	/**
	 * @param action
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#executeFind(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	public List executeFind(HibernateCallback action)
			throws DataAccessException {
		return template.executeFind(action);
	}
	
	/**
	 * 
	 * @see corner.services.EntityService#executeBatchUpdate(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	public Object executeBatchUpdate(HibernateCallback action) throws DataAccessException{
		return template.execute(action);
	}
	
	/**
	 * @return the template
	 */
	public HibernateTemplate getTemplate() {
		return template;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
