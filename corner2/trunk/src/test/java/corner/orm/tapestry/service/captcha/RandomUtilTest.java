//==============================================================================
// file :       $Id: RandomUtilTest.java 3677 2007-11-14 04:36:40Z jcai $
// project:     corner
//
// last change: date:       $Date: 2007-11-14 12:36:40 +0800 (星期三, 14 十一月 2007) $
//              by:         $Author: jcai $
//              revision:   $Revision: 3677 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.service.captcha;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author jcai
 * @version $Revision: 3677 $
 * @since 2.3
 */
public class RandomUtilTest extends Assert {
	@Test
	public void test_createEncodeStr(){
		RandomUtil.encodeStr("hello");
	}
	@Test
	public void test_generate_randomStr(){
		assertNotNull(RandomUtil.generateUUIDString());
	}
}
