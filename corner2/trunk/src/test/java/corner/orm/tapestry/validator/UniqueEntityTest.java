//==============================================================================
// file :       $Id: UniqueEntityTest.java 2096 2006-10-26 07:36:43Z jcai $
// project:     corner
//
// last change: date:       $Date: 2006-10-26 15:36:43 +0800 (星期四, 26 十月 2006) $
//              by:         $Author: jcai $
//              revision:   $Revision: 2096 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 2096 $
 * @since 2.2.2
 */
public class UniqueEntityTest {

	@Test
	public void testRegex(){
		String className="corner.demo.A";
		String proName="name";
		UniqueEntity entity=new UniqueEntity();
		entity.setUniqueEntity("{"+className+":"+proName+"}");
		Assert.assertEquals(entity.getEntityClassName(),className);
		Assert.assertEquals(entity.getPropertyName(),proName);
		
	}
}
