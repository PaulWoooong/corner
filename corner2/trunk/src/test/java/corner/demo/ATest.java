package corner.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.testng.annotations.Test;

import com.avdheshyadav.dbcrawler.ConfigEnum;
import com.avdheshyadav.dbcrawler.DBCrawler;
import com.avdheshyadav.dbcrawler.DbCrawlerException;
import com.avdheshyadav.dbcrawler.dbmodel.ColumnSet;
import com.avdheshyadav.dbcrawler.dbmodel.DataBase;
import com.avdheshyadav.dbcrawler.dbmodel.ForeignKey;
import com.avdheshyadav.dbcrawler.dbmodel.PrimaryKey;
import com.avdheshyadav.dbcrawler.dbmodel.Schema;
import com.avdheshyadav.dbcrawler.dbmodel.SchemaSet;
import com.avdheshyadav.dbcrawler.dbmodel.Table;
import com.avdheshyadav.dbcrawler.dbmodel.TableSet;

import corner.demo.model.one.A;
import corner.orm.hibernate.v3.MatrixRow;
import corner.service.EntityService;
import corner.test.AbstractTestCase;

public class ATest extends AbstractTestCase {
	@Test
	public void testVectorType() {
		final A a=new A();
		MatrixRow<String> v=new MatrixRow<String>();
		v.add("test");
		v.add("test2");
		a.setColors(v);
		
		this.startTransaction();
		Session session=this.getCurrentSession();
		session.save(a);
		this.commitTransaction();
		
		
		
		this.startTransaction();
		session=this.getCurrentSession();
		A a1=(A) session.load(A.class,a.getId());
		assertNotNull(a1.getColors());
		assertEquals(a1.getColors().size(),2);
		this.commitTransaction();
	}
	
	@Test
	public void testZeroCountQuery(){
		EntityService service = (EntityService)container.getApplicationContext().getBean("entityService");
		service.bulkUpdate("delete from corner.demo.model.one.A ");
		assertEquals(0, service.getExistRelativeRowCount(A.class, null));
	}
	
	@Test
	public void testDbSchema() {
		EntityService service = (EntityService)container.getApplicationContext().getBean("entityService");
		service.execute(new HibernateCallback(){

			/**
			 * @see org.springframework.orm.hibernate3.HibernateCallback#doInHibernate(org.hibernate.Session)
			 */
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				DBCrawler dbCrawler = null;
				DataBase dataBase = null;
				try {
					dbCrawler = new DBCrawler(session.connection(), ConfigEnum.MAXIMUM);
					dataBase = dbCrawler.getDatabase();
				} catch (DbCrawlerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("productName :" + dataBase.getProductName() + " version:" + dataBase.getProductVersion());

				//Return Schemas
				SchemaSet schemaSet = dataBase.getSchemaSet();
				Set<Schema> schemas = schemaSet.getSchemas();
				//Iterate to Fetch the schema information and Tables
				for(Schema schema : schemas)
				{
				   System.out.println("SchemaName :" + schema.getSchamaName());
				   TableSet tableSet = schema.getTableSet();
				   Set<Table> tables = tableSet.getTables();
				   //Iterate to fetch the tables 
				   for(Table table : tables)
				   {
				     System.out.println("tableName :" + table.getTableName());
				     PrimaryKey primaryKey = table.getPrimaryKey();
//				     System.out.println("pk_Name:"+primaryKey.getPkName());
//				     System.out.println("PrimaryKey Columns:" + primaryKey.getColumns());
				      
				     ColumnSet columnSet = table.getColumnSet();
				     System.out.println("Table Columns:"+ columnSet.getColumns());

				     Set<ForeignKey> foreignKeys = table.getForeignKeys();
				     System.out.println("foreignKeys:"+foreignKeys);
				    }
				}
				return null;
			}
			
		});
	}
}
