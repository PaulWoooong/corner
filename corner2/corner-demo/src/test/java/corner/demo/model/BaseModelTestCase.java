package corner.demo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import corner.orm.spring.SpringContainer;

/**
 * ����������
 * 
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision: 2150 $
 * @since 0.0.1
 */
public class BaseModelTestCase extends Assert {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(BaseModelTestCase.class);

	protected org.hibernate.classic.Session session;

	private long end;

	private long star;

	private HibernateTransactionManager transactionManager;

	// Ĭ�ϵ����ﶨ��
	private TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();

	private TransactionStatus status;

	@BeforeMethod(groups = "models")
	public void initSessionFactory() {
		star = System.currentTimeMillis();
		if (transactionManager == null) {
			initSpring();
		}
		status = transactionManager.getTransaction(transactionDefinition);
		session = transactionManager.getSessionFactory().getCurrentSession();

	}

	@AfterMethod(groups = "models")
	public void closeSessionFactory() {
		transactionManager.commit(status);
		end = System.currentTimeMillis();
		logger.info("����ʱ��" + (end - star) + "����"); //$NON-NLS-1$
	}

	protected <T> T saveOrUpdate(T obj) {
		session.saveOrUpdate(obj);
		return obj;

	}

	@SuppressWarnings("unchecked")
	protected <T> T load(Class<T> clazz, Serializable key) {
		return (T) session.load(clazz, key);
	}

	/**
	 * ���hibernate�Ļ��档
	 * 
	 */
	protected void flushAndClearSession() {
		session.flush();
		session.clear();
	}

	/**
	 * �õ�ӳ����ļ�Ŀ¼
	 * 
	 * @return �ļ�ӳ���ļ�Ŀ¼.
	 */
	protected String[] getMappingDirectoryLocations() {
		return new String[] { "classpath:/corner/demo/model" };
	}

	protected String[] getMappingResources() {
		return null;
	}

	private Resource[] getMappingDirectoryResources() {
		if (this.getMappingDirectoryLocations() == null) {
			return new Resource[0];
		}
		List<Resource> list = new ArrayList<Resource>();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		for (int i = 0; i < getMappingDirectoryLocations().length; i++) {
			list.add(resolver
					.getResource(this.getMappingDirectoryLocations()[i]));

		}
		return list.toArray(new Resource[0]);
	}

	private void initSpring() {
		//xf �����Ѿ����£�������ȫģʽ���в���
		//if ("true".equals(System.getProperty("test.single"))) {
		if(2>1){
			transactionManager = (HibernateTransactionManager) SpringContainer.getInstance().getApplicationContext().getBean("transactionManager");

		} else {
			SpringContainer container = SpringContainer
					.getInstance("classpath:/config/spring/application-database.xml");

			AnnotationSessionFactoryBean factoryBean = new org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean();
			factoryBean.setDataSource((DataSource) container
					.getApplicationContext().getBean("dataSource"));
			Properties ps = new Properties();
			ps.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
			ps.put("hibernate.cache.use_second_level_cache", "false");
			factoryBean.setHibernateProperties(ps);
			factoryBean.setConfigLocation(new ClassPathResource(
					"hibernate.cfg.xml"));
			factoryBean
					.setMappingDirectoryLocations(getMappingDirectoryResources());
			factoryBean.setLobHandler((LobHandler) container
					.getApplicationContext().getBean("defaultLobHandler"));
			if (getMappingResources() != null) {
				factoryBean.setMappingResources(getMappingResources());
			}
			try {
				factoryBean.afterPropertiesSet();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			SessionFactory factory = (SessionFactory) factoryBean.getObject();

			transactionManager = new org.springframework.orm.hibernate3.HibernateTransactionManager();
			transactionManager.setSessionFactory(factory);
		}
	}

}
