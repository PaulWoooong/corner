//==============================================================================
// file :       $Id: QueryTypeWorkerTest.java 3677 2007-11-14 04:36:40Z jcai $
// project:     corner
//
// last change: date:       $Date: 2007-11-14 12:36:40 +0800 (星期三, 14 十一月 2007) $
//              by:         $Author: jcai $
//              revision:   $Revision: 3677 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.hibernate.expression.annotations;

import org.testng.Assert;
import org.testng.annotations.Test;

import corner.orm.hibernate.expression.annotations.QueryDefinition.QueryField;
@Test
public class QueryTypeWorkerTest extends Assert {

	/**
	 * Test method for {@link corner.orm.hibernate.expression.annotations.QueryTypeWorker#getQueryTypeByPropertyName(java.lang.String)}.
	 */
	public void testGetQueryTypeByPropertyName() {
		QueryTypeWorker worker=new QueryTypeWorker(A.class);
		assertEquals(QueryDefinition.QueryType.String,worker.getQueryTypeByPropertyName("userName"));
		assertEquals(QueryDefinition.QueryType.Date,worker.getQueryTypeByPropertyName("password"));
	}

}
@QueryDefinition
({
	@QueryField(propertyName="userName"),
	@QueryField(propertyName="password",queryType=QueryDefinition.QueryType.Date)
	
})
class A{

}

