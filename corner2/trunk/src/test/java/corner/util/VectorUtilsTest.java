//==============================================================================
// file :       $Id: VectorUtilsTest.java 3429 2007-09-24 01:18:12Z jcai $
// project:     corner
//
// last change: date:       $Date: 2007-09-24 09:18:12 +0800 (星期一, 24 九月 2007) $
//              by:         $Author: jcai $
//              revision:   $Revision: 3429 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import corner.orm.hibernate.v3.MatrixRow;

@Test
public class VectorUtilsTest extends Assert {

	private static final String[] COMMAEND_ARR = { ",a,b,", ",", ",,,", "a,b," };

	private static final String[] TEST_STR = { ",按时地方,b,c,d,", ",", "a,", ",,," };

	private static final String[] COMMAS_STR = { ",", ",,,,," };

	public void testNullVector() {
			assertEquals((double)0,VectorUtils.sum(null));
		
	}

	public void testSumVector() {
		Vector<String> v = new Vector<String>();
		v.add("1");
		v.add("2");
		assertEquals(3.0, VectorUtils.sum(v));

		v.add("1.2");
		assertEquals(4.2, VectorUtils.sum(v));

		v.add("1.258");
		assertEquals(5.458, VectorUtils.sum(v));

	}
	
	/**
	 * 向量相减 
	 */
	@Test
	public void testMinus(){
		
		MatrixRow<String> m1 = new MatrixRow<String>();
		m1.add("-1");
		m1.add("2");
		m1.add("3");
		m1.add("4");
		m1.add("5");
		
		MatrixRow<String> m2 = new MatrixRow<String>();
		m2.add("-1");
		m2.add("2");
		m2.add("3");
		m2.add("4");
		m2.add("5");
		
		MatrixRow<String> m = VectorUtils.minus(m1, m2);
		
		StringBuffer v = new StringBuffer();
		
		for(String s : m){
			v.append(s).append(",");
		}
		
		assertEquals("0.0,0.0,0.0,0.0,0.0,",v.toString());
	}

	@Test
	public void testSumEmptyVector() {
		Vector<String> v = new Vector<String>();
		v.add("1");
		v.add("2");
		v.add("");
		v.add("");
		v.add("");
		
		
		assertEquals(3.0, VectorUtils.sum(v));

		v.add("1.2");
		assertEquals(4.2, VectorUtils.sum(v));

		v.add("1.258");
		assertEquals(5.458, VectorUtils.sum(v));

	}

	public void testSumErrorVector() {
		Vector<String> v = new Vector<String>();
		v.add("1s");
		v.add("2");

		try {
			VectorUtils.sum(v);
			fail("can't reacheable");
		} catch (RuntimeException e) {
			// go here
		}

	}

	public void testSubmVectorList() {
		Vector<String> v1 = new Vector<String>();
		v1.add("1");
		v1.add("2");

		Vector<String> v2 = new Vector<String>();
		v2.add("1");
		v2.add("2");

		List<Vector<String>> list = new ArrayList<Vector<String>>();
		list.add(v1);
		list.add(v2);
		Vector<Double> r = VectorUtils.sumList(list);
		assertTrue(2 == r.get(0));
	}

	public void testREGEX() {
		Pattern pattern = Pattern.compile(VectorUtils.COMMAEND_STR);
		for (String str : TEST_STR) {
			Matcher match = pattern.matcher(str);
			assertEquals(true, match.find());
		}
	}

	public void testREGEXI() {
		Pattern pattern = Pattern.compile(VectorUtils.COMMAS_STR);
		for (String str : COMMAS_STR) {
			Matcher match = pattern.matcher(str);
			assertEquals(true, match.find());
		}
	}

	public void testVectorSplit() {
		List<String> strList = VectorUtils.VectorSplit(COMMAEND_ARR[0], ",");
		assertEquals(4, strList.size());
		assertEquals(strList.get(0).toString(), "");
		assertEquals(strList.get(1).toString(), "a");
		assertEquals(strList.get(2).toString(), "b");
		assertEquals(strList.get(3).toString(), "");

		List<String> strList1 = VectorUtils.VectorSplit(COMMAEND_ARR[1], ",");
		assertEquals(2, strList1.size());
		assertEquals(strList1.get(0).toString(), "");
		assertEquals(strList1.get(1).toString(), "");

		List<String> strList2 = VectorUtils.VectorSplit(COMMAEND_ARR[2], ",");
		assertEquals(4, strList2.size());
		assertEquals(strList2.get(0).toString(), "");
		assertEquals(strList2.get(1).toString(), "");

		List<String> strList3 = VectorUtils.VectorSplit(COMMAEND_ARR[3], ",");
		assertEquals(3, strList3.size());
		assertEquals(strList3.get(0).toString(), "a");
		assertEquals(strList3.get(1).toString(), "b");
		assertEquals(strList3.get(2).toString(), "");
		
		List<String> strList4 = VectorUtils.VectorSplit("1,2,,,,", ",");
		assertEquals(6,strList4.size());
		

		String[] list5=StringUtils.splitPreserveAllTokens("1,2,,,,",",");
		
		assertEquals(6,list5.length);
	}

}
