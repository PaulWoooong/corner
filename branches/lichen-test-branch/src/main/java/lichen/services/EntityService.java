/* 
 * Copyright 2008 The Lichen Team.
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
package lichen.services;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.hibernate.LockMode;
import org.hibernate.ReplicationMode;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;


/**
 * 公用的服务类.
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 392 $
 * @since 0.0.1
 */
@SuppressWarnings("unchecked")
public interface EntityService {
	

	/**
	 * @param queryString
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(java.lang.String, java.lang.Object)
	 */
	@CommitAfter
	public int bulkUpdate(String queryString, Object value)
			throws DataAccessException;

	/**
	 * @param queryString
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(java.lang.String, java.lang.Object[])
	 */
	@CommitAfter
	public int bulkUpdate(String queryString, Object[] values)
			throws DataAccessException;

	/**
	 * @param queryString
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#bulkUpdate(java.lang.String)
	 */
	@CommitAfter
	public int bulkUpdate(String queryString) throws DataAccessException;

	/**
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#delete(java.lang.Object, org.hibernate.LockMode)
	 */
	@CommitAfter
	public void delete(Object entity, LockMode lockMode) throws DataAccessException;

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#delete(java.lang.Object)
	 */
	@CommitAfter
	public void delete(Object entity) throws DataAccessException;

	/**
	 * @param entities
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#deleteAll(java.util.Collection)
	 */
	@CommitAfter
	public void deleteAll(Collection entities) throws DataAccessException;

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#evict(java.lang.Object)
	 */
	public void evict(Object entity) throws DataAccessException;


	/**
	 * @param queryString
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#find(java.lang.String, java.lang.Object)
	 */
	public List find(String queryString, Object value) throws DataAccessException;

	/**
	 * @param queryString
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#find(java.lang.String, java.lang.Object[])
	 */
	public List find(String queryString, Object[] values) throws DataAccessException;

	/**
	 * @param queryString
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#find(java.lang.String)
	 */
	public List find(String queryString) throws DataAccessException;

	/**
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByCriteria(org.hibernate.criterion.DetachedCriteria, int, int)
	 */
	public List findByCriteria(DetachedCriteria criteria, int firstResult, int maxResults)
			throws DataAccessException;

	/**
	 * @param criteria
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	public List findByCriteria(DetachedCriteria criteria) throws DataAccessException;

	/**
	 * @param exampleEntity
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByExample(java.lang.Object, int, int)
	 */
	public List findByExample(Object exampleEntity, int firstResult, int maxResults)
			throws DataAccessException;

	/**
	 * @param exampleEntity
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByExample(java.lang.Object)
	 */
	public List findByExample(Object exampleEntity) throws DataAccessException;

	/**
	 * @param queryString
	 * @param paramName
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedParam(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public List findByNamedParam(String queryString, String paramName, Object value)
			throws DataAccessException;

	/**
	 * @param queryString
	 * @param paramNames
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public List findByNamedParam(String queryString, String[] paramNames, Object[] values)
			throws DataAccessException;

	/**
	 * @param queryName
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(java.lang.String, java.lang.Object)
	 */
	public List findByNamedQuery(String queryName, Object value)
			throws DataAccessException;

	/**
	 * @param queryName
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(java.lang.String, java.lang.Object[])
	 */
	public List findByNamedQuery(String queryName, Object[] values)
			throws DataAccessException;

	/**
	 * @param queryName
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQuery(java.lang.String)
	 */
	public List findByNamedQuery(String queryName) throws DataAccessException;

	/**
	 * @param queryName
	 * @param paramName
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQueryAndNamedParam(java.lang.String, java.lang.String, java.lang.Object)
	 */
	public List findByNamedQueryAndNamedParam(String queryName, String paramName,
			Object value) throws DataAccessException;

	/**
	 * @param queryName
	 * @param paramNames
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQueryAndNamedParam(java.lang.String, java.lang.String[], java.lang.Object[])
	 */
	public List findByNamedQueryAndNamedParam(String queryName, String[] paramNames,
			Object[] values) throws DataAccessException;

	/**
	 * @param queryName
	 * @param valueBean
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByNamedQueryAndValueBean(java.lang.String, java.lang.Object)
	 */
	public List findByNamedQueryAndValueBean(String queryName, Object valueBean)
			throws DataAccessException;

	/**
	 * @param queryString
	 * @param valueBean
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#findByValueBean(java.lang.String, java.lang.Object)
	 */
	public List findByValueBean(String queryString, Object valueBean)
			throws DataAccessException;

	/**
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#flush()
	 */
	public void flush() throws DataAccessException;

	/**
	 * @param entityClass
	 * @param id
	 * @param lockMode
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(java.lang.Class, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object get(Class entityClass, Serializable id, LockMode lockMode)
			throws DataAccessException;

	/**
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(java.lang.Class, java.io.Serializable)
	 */
	public Object get(Class entityClass, Serializable id) throws DataAccessException;

	/**
	 * @param entityName
	 * @param id
	 * @param lockMode
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(java.lang.String, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object get(String entityName, Serializable id, LockMode lockMode)
			throws DataAccessException;

	/**
	 * @param entityName
	 * @param id
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#get(java.lang.String, java.io.Serializable)
	 */
	public Object get(String entityName, Serializable id) throws DataAccessException;

	/**
	 * @param proxy
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#initialize(java.lang.Object)
	 */
	public void initialize(Object proxy) throws DataAccessException;

	/**
	 * @param queryString
	 * @param value
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(java.lang.String, java.lang.Object)
	 */
	public Iterator iterate(String queryString, Object value) throws DataAccessException;

	/**
	 * @param queryString
	 * @param values
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(java.lang.String, java.lang.Object[])
	 */
	public Iterator iterate(String queryString, Object[] values) throws DataAccessException;

	/**
	 * @param queryString
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#iterate(java.lang.String)
	 */
	public Iterator iterate(String queryString) throws DataAccessException;

	/**
	 * @param entityClass
	 * @param id
	 * @param lockMode
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.Class, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object load(Class entityClass, Serializable id, LockMode lockMode)
			throws DataAccessException;

	/**
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.Class, java.io.Serializable)
	 */
	public Object load(Class entityClass, Serializable id) throws DataAccessException;

	/**
	 * @param entity
	 * @param id
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.Object, java.io.Serializable)
	 */
	public void load(Object entity, Serializable id) throws DataAccessException;

	/**
	 * @param entityName
	 * @param id
	 * @param lockMode
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.String, java.io.Serializable, org.hibernate.LockMode)
	 */
	public Object load(String entityName, Serializable id, LockMode lockMode)
			throws DataAccessException;

	/**
	 * @param entityName
	 * @param id
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#load(java.lang.String, java.io.Serializable)
	 */
	public Object load(String entityName, Serializable id) throws DataAccessException;

	/**
	 * @param entityClass
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#loadAll(java.lang.Class)
	 */
	public List loadAll(Class entityClass) throws DataAccessException;



	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#persist(java.lang.Object)
	 */
	@CommitAfter
	public void persist(Object entity) throws DataAccessException;

	/**
	 * @param entityName
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#persist(java.lang.String, java.lang.Object)
	 */
	@CommitAfter
	public void persist(String entityName, Object entity) throws DataAccessException;

	/**
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#refresh(java.lang.Object, org.hibernate.LockMode)
	 */
	public void refresh(Object entity, LockMode lockMode) throws DataAccessException;

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#refresh(java.lang.Object)
	 */
	public void refresh(Object entity) throws DataAccessException;

	/**
	 * @param entity
	 * @param replicationMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#replicate(java.lang.Object, org.hibernate.ReplicationMode)
	 */
	public void replicate(Object entity, ReplicationMode replicationMode)
			throws DataAccessException;

	/**
	 * @param entityName
	 * @param entity
	 * @param replicationMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#replicate(java.lang.String, java.lang.Object, org.hibernate.ReplicationMode)
	 */
	public void replicate(String entityName, Object entity, ReplicationMode replicationMode)
			throws DataAccessException;

	/**
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#save(java.lang.Object)
	 */
	@CommitAfter
	public Serializable save(Object entity) throws DataAccessException;

	/**
	 * @param entityName
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#save(java.lang.String, java.lang.Object)
	 */
	@CommitAfter
	public Serializable save(String entityName, Object entity) throws DataAccessException;

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdate(java.lang.Object)
	 */
	@CommitAfter
	public void saveOrUpdate(Object entity) throws DataAccessException;

	/**
	 * @param entityName
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdate(java.lang.String, java.lang.Object)
	 */
	@CommitAfter
	public void saveOrUpdate(String entityName, Object entity)
			throws DataAccessException;

	/**
	 * @param entities
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#saveOrUpdateAll(java.util.Collection)
	 */
	@CommitAfter
	public void saveOrUpdateAll(Collection entities) throws DataAccessException;

	/**
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.Object, org.hibernate.LockMode)
	 */
	@CommitAfter
	public void update(Object entity, LockMode lockMode) throws DataAccessException;

	/**
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.Object)
	 */
	@CommitAfter
	public void update(Object entity) throws DataAccessException;

	/**
	 * @param entityName
	 * @param entity
	 * @param lockMode
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.String, java.lang.Object, org.hibernate.LockMode)
	 */
	@CommitAfter
	public void update(String entityName, Object entity, LockMode lockMode)
			throws DataAccessException;

	/**
	 * @param entityName
	 * @param entity
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#update(java.lang.String, java.lang.Object)
	 */
	@CommitAfter
	public void update(String entityName, Object entity) throws DataAccessException;

	/**
	 * @param action
	 * @return
	 * @throws DataAccessException
	 * @see org.springframework.orm.hibernate3.HibernateTemplate#executeFind(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	public List executeFind(HibernateCallback action) throws DataAccessException;

	

}