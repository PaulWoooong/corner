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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.tapestry5.hibernate.HibernateSessionSource;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.test.TapestryTestCase;
import org.hibernate.SessionFactory;
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
import corner.migration.ConnectionAdapter;
import corner.migration.ConnectionAdapterSource;
import corner.migration.MigrationModule;
import corner.migration.MigrationService;
import corner.migration.impl.ConnectionAdapterSourceImpl;
import corner.migration.impl.MigrationServiceImpl;

public class MigrationServiceImplTest extends TapestryTestCase{
	private AnnotationConfiguration cfg;
	private SessionFactoryImplementor factory;
	private Logger logger;
	private HibernateSessionSource sessionSource;

	@BeforeMethod
	void setUpTestEnv(){
		cfg = new AnnotationConfiguration();
		cfg.setProperty( Environment.HBM2DDL_AUTO, "");
		cfg.addAnnotatedClass(TestA.class);
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
	public void testCreateTable(){
		replay();
		String[] expectSqls=new String[]{"create table table_a (id bigint generated by default as identity, userName varchar(255), primary key (id))"};
		MigrationService service = crateMigrationService(factory, logger,
				sessionSource,expectSqls);
		service.createTable("table_a");
		verify();
	}
	@Test
	public void testAddColumn(){
		replay();
		//先建一半的表
		executeSQL("drop table table_a if exists","create table table_a (id integer generated by default as identity, primary key (id))");
		String[] expectSqls=new String[]{"alter table table_a add column userName varchar(255)"};
		MigrationService service = crateMigrationService(factory, logger,
				sessionSource,expectSqls);
		service.addColumn("table_a");
		verify();
	}
	@Test
	public void testChangeColumn(){
		replay();
		//先建一半的表
		executeSQL("drop table table_a if exists","create table table_a (id integer generated by default as identity, userName integer, primary key (id))");
		String[] expectSqls=new String[]{"ALTER TABLE table_a ALTER COLUMN userName varchar(255)"};
		MigrationService service = crateMigrationService(factory, logger,
				sessionSource,expectSqls);
		service.changeColumn("table_a","userName");
		verify();
	}
	@Test
	public void testRemoveColumn(){
		replay();
		//先建多个字段的表
		executeSQL("drop table table_a if exists","create table table_a (id integer generated by default as identity, userName varchar(255),passwd varchar(200), primary key (id))");
		String[] expectSqls=new String[]{"ALTER TABLE table_a DROP COLUMN password"};
		MigrationService service = crateMigrationService(factory, logger,
				sessionSource,expectSqls);
		service.removeColumn("table_a","password");
		verify();
	}

	@Test
	public void testRenamesColumn(){
		replay();
		//先建多个字段的表
		executeSQL("drop table table_a if exists","create table table_a (id integer generated by default as identity, userName varchar(255),passwd varchar(200), primary key (id))");
		String[] expectSqls=new String[]{"ALTER TABLE table_a ALTER COLUMN password RENAME TO password1"};
		MigrationService service = crateMigrationService(factory, logger,
				sessionSource,expectSqls);
		service.renameColumns("table_a",new String[]{"password"},new String[]{"password1"});
		verify();
	}

	@Test
	public void testDropTable(){
		replay();
		//先建多个字段的表
		executeSQL("drop table table_a if exists","create table table_a (id integer generated by default as identity, userName varchar(255),passwd varchar(200), primary key (id))");
		String[] expectSqls=new String[]{"drop table table_a if exists"};
		MigrationService service = crateMigrationService(factory, logger,
				sessionSource,expectSqls);
		service.dropTable("table_a");
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
	/**
	 * @param factory
	 * @param logger
	 * @param sessionSource
	 * @return
	 * @since 0.0.2
	 */
	 static MigrationService crateMigrationService(SessionFactory factory,
			Logger logger, HibernateSessionSource sessionSource,final String... expectValues) {
		 final Map<Class, ConnectionAdapter> r=new HashMap<Class,ConnectionAdapter>();
		 MappedConfiguration<Class, ConnectionAdapter> configuration = new MappedConfiguration<Class,ConnectionAdapter>(){

			@Override
			public void add(Class key, ConnectionAdapter value) {
				r.put(key, value);
			}

			@Override
			public void addInstance(Class key,
					Class<? extends ConnectionAdapter> clazz) {
			}

			@Override
			public void override(Class key, ConnectionAdapter value) {
			}

			@Override
			public void overrideInstance(Class key,
					Class<? extends ConnectionAdapter> clazz) {
			}
			 
		 };
		MigrationModule.contributeConnectionAdapterSource(configuration);
		ConnectionAdapterSource adapterSource = new ConnectionAdapterSourceImpl(r);
		MigrationService service = new MigrationServiceImpl(sessionSource, adapterSource,logger){

			/**
			 * @see corner.migration.impl.MigrationServiceImpl#executeSchemaScript(java.sql.Connection, java.lang.String[])
			 */
			@Override
			protected void executeSchemaScript(Connection con, String[] sql)
					throws SQLException {
				if(expectValues == null){
					super.executeSchemaScript(con, sql);
					return;
					
				}
				assertEquals(sql,expectValues);
			}
			
		};
		return service;
	}

}
