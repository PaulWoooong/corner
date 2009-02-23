//==============================================================================
// file :       $Id: RegexTest.java 2096 2006-10-26 07:36:43Z jcai $
// project:     corner
//
// last change: date:       $Date: 2006-10-26 15:36:43 +0800 (星期四, 26 十月 2006) $
//              by:         $Author: jcai $
//              revision:   $Revision: 2096 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.util;

import java.util.Arrays;

import org.testng.annotations.Test;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 2096 $
 * @since 2.2.2
 */
public class RegexTest {
	@Test
	public void test_UniqueReg(){
		String str="{className:propertyName}";
		String [] strs=str.split("\\{(^:):(^})\\}");
		System.out.println(Arrays.asList(strs));
		
	}
}
