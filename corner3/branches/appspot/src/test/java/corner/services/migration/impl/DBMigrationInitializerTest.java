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
package corner.services.migration.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.services.ApplicationInitializer;
import org.apache.tapestry5.services.Context;
import org.apache.tapestry5.test.TapestryTestCase;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.SessionFactoryImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import corner.integration.app1.entities.TestA;
import corner.services.migration.MigrationService;

public class DBMigrationInitializerTest extends TapestryTestCase{
	private AnnotationConfiguration cfg;
	private SessionFactoryImplementor factory;
	private Logger logger;
	private HibernateSessionSource sessionSource;

	@BeforeMethod
	void setUpTestEnv(){
		cfg = new AnnotationConfiguration();
		cfg.setProperty( Environment.HBM2DDL_AUTO, "create");
		cfg.addAnnotatedClass(TestA.class);
		cfg.addAnnotatedClass(SchemaInfo.class);
		factory = (SessionFactoryImplementor) cfg.buildSessionFactory();
		logger = LoggerFactory.getLogger(MigrationServiceImpl.class);
		sessionSource = this.newMock(HibernateSessionSource.class);
		expect(sessionSource.getSessionFactory()).andReturn(factory);
		expect(sessionSource.getConfiguration()).andReturn(cfg).anyTimes();
		
	}
	@AfterMethod
	void cleanTestEnv(){
		factory.close();
	}
	@Test
	public void testInit(){
		Context context=this.mockContext();
		expect(context.getRealFile("/WEB-INF/groovy-db/")).andReturn(new File("src/test/resources/app1/WEB-INF/groovy-db"));
		ApplicationInitializer initializer = this.newMock(ApplicationInitializer.class);
		initializer.initializeApplication(context);
		HibernateSessionManager sessionManager = this.newMock(HibernateSessionManager.class);
		expect(sessionManager.getSession()).andReturn(factory.openSession());
//        sessionManager.commit();
		replay();
		String[] expectValues = null;
//		new String[]{"create table db_schema_info (id integer generated by default as identity, db_version integer, index_version integer, primary key (id))",
//				"create table table_a (id integer generated by default as identity, userName varchar(255), primary key (id))"};
		MigrationService service = MigrationServiceImplTest.crateMigrationService(factory, logger, sessionSource,expectValues);
		
		DBMigrationInitializer initer = new DBMigrationInitializer(service,sessionManager, logger);
		initer.initializeApplication(context, initializer);
		verify();
	}
	@Test
	public void testCreateTable(){
		this.executeSQL("create table db_schema_info (id integer generated by default as identity, db_version integer, index_version integer, primary key (id))");
		this.executeSQL("insert into db_schema_info(db_version,index_version) values(0,-1)");
		Context context=this.mockContext();
		expect(context.getRealFile("/WEB-INF/groovy-db/")).andReturn(new File("src/test/resources/app1/WEB-INF/groovy-db"));
		ApplicationInitializer initializer = this.newMock(ApplicationInitializer.class);
		initializer.initializeApplication(context);
		HibernateSessionManager sessionManager = this.newMock(HibernateSessionManager.class);
		expect(sessionManager.getSession()).andReturn(factory.openSession());
//        sessionManager.commit();
		replay();
		String[] expectValues = null;
//		new String[]{"create table db_schema_info (id integer generated by default as identity, db_version integer, index_version integer, primary key (id))",
//				"create table table_a (id integer generated by default as identity, userName varchar(255), primary key (id))"};
		MigrationService service = MigrationServiceImplTest.crateMigrationService(factory, logger, sessionSource,expectValues);
		DBMigrationInitializer initer = new DBMigrationInitializer(service,sessionManager, logger);
		initer.initializeApplication(context, initializer);
		verify();
	}
	private void executeSQL(String... sql) {
		try {
			Connection con = this.factory.getSettings().getConnectionProvider().getConnection();
			if (sql != null && sql.length > 0) {
				boolean oldAutoCommit = con.getAutoCommit();
				if (!oldAutoCommit) {
					con.setAutoCommit(true);
				}
				try {
					Statement stmt = con.createStatement();
					try {
						for (int i = 0; i < sql.length; i++) {
							executeSchemaStatement(stmt, sql[i]);
						}
					}
					finally {
						JdbcUtils.closeStatement(stmt);
					}
				}
				finally {
					if (!oldAutoCommit) {
						con.setAutoCommit(false);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	private void executeSchemaStatement(Statement stmt, String sql) throws SQLException {
		
		try {
			stmt.executeUpdate(sql);
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
