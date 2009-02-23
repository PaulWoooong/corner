//==============================================================================
// file :       $Id: NumPatternTest.java 2477 2006-12-14 03:02:46Z jcai $
// project:     corner
//
// last change: date:       $Date: 2006-12-14 11:02:46 +0800 (星期四, 14 十二月 2006) $
//              by:         $Author: jcai $
//              revision:   $Revision: 2477 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 2477 $
 * @since 2.3
 */
public class NumPatternTest extends Assert{
	@Test
	public void test_getMessage(){
		NumPattern pattern=new NumPattern();
		pattern.setNumPattern("{6:2}");
		assertEquals(pattern.getMessage(),"错误的数字格式，正确的为：小数点前面至多6位，后面至多2位.");
	}
}
