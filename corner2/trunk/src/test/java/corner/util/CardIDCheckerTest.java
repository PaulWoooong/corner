//==============================================================================
// file :       $Id: CardIDCheckerTest.java 2149 2006-11-01 03:01:28Z jcai $
// project:     corner
//
// last change: date:       $Date: 2006-11-01 11:01:28 +0800 (星期三, 01 十一月 2006) $
//              by:         $Author: jcai $
//              revision:   $Revision: 2149 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.util;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 2149 $
 * @since 2.3
 */
public class CardIDCheckerTest extends Assert{
	@Test
	public void testCardId(){
		assertTrue(CardIDChecker.validate("413028198009121514"));
		assertFalse(CardIDChecker.validate("513028198009121514"));
		
	}
}
