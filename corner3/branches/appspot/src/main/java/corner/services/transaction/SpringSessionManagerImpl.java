/*		
 * Copyright 2009 The Fepss Pty Ltd. 
 * site: http://www.fepss.com
 * file: $Id$
 * created at:2009-5-14
 */
package corner.services.transaction;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.ioc.services.ThreadCleanupListener;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 使用Spring来管理session，覆盖T5自带的Session管理
 * 
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.1
 */
public class SpringSessionManagerImpl implements HibernateSessionManager,
		ThreadCleanupListener {

	private SessionFactory sessionFactory;
	private boolean participate;
	private static final Logger logger = LoggerFactory
			.getLogger(SpringSessionManagerImpl.class);

	public SpringSessionManagerImpl(HibernateSessionSource source) {
		sessionFactory = source.getSessionFactory();// single session mode
		if (TransactionSynchronizationManager.hasResource(sessionFactory)) {
			// Do not modify the Session: just set the participate flag.
			participate = true;
		}
		else {
			logger.debug("Opening single Hibernate Session in OpenSessionInViewFilter");
			Session session = getSession(sessionFactory);
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		}
	}

	/**
	 * @see org.apache.tapestry5.hibernate.HibernateSessionManager#abort()
	 */
	@Override
	public void abort() {
		throw new UnsupportedOperationException("never call ");
	}

	/**
	 * @see org.apache.tapestry5.hibernate.HibernateSessionManager#commit()
	 */
	@Override
	public void commit() {
		throw new UnsupportedOperationException("never call ");
	}

	/**
	 * @see org.apache.tapestry5.hibernate.HibernateSessionManager#getSession()
	 */
	@Override
	public Session getSession() {
		return SessionFactoryUtils.getSession(sessionFactory,false);
	}

	protected Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		if (logger.isDebugEnabled()) {
			logger.debug("get session "
					+ Integer.toHexString(session.hashCode()));
		}
		return session;
	}

	protected void closeSession(Session session, SessionFactory sessionFactory) {
		SessionFactoryUtils.closeSession(session);
	}

	@Override
	public void threadDidCleanup() {
			if (!participate) {
					// single session mode
					SessionHolder sessionHolder =
							(SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
					logger.debug("Closing single Hibernate Session in OpenSessionInViewFilter");
					closeSession(sessionHolder.getSession(), sessionFactory);
			}
	}
}
