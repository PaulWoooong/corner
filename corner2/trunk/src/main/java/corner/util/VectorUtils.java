// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: VectorUtils.java 3678 2007-11-14 04:43:52Z jcai $
// created at:2006-08-17
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import antlr.RecognitionException;
import antlr.TokenStreamException;
import antlr.collections.AST;
import corner.expression.calc.ExprLexer;
import corner.expression.calc.ExprParser;
import corner.expression.calc.ExprTreeParser;
import corner.orm.hibernate.v3.MatrixRow;

/**
 * 对向量类型的操作。
 * 
 * @author jcai
 * @version $Revision: 3678 $
 * @since 2.1
 */
public final class VectorUtils {

	public static final String COMMAEND_STR = ".*,$";

	public static final String COMMAS_STR = "^,*$";
	
	/**
	 * 两个向量相减,两个向量长度需要一致
	 * @param m1 向量1
	 * @param m2 向量1
	 * @return
	 */
	public static MatrixRow<String> minus (MatrixRow<String> m1,MatrixRow<String> m2){
		if(m1 == null || m2 == null || m1.size() != m2.size()){
			return null;
		}
		
		MatrixRow<String> newM = new MatrixRow<String>();
		
		Iterator<String> m1v =  m1.iterator();
		Iterator<String> m2v =  m2.iterator();
		
		for(int i=0; i<m1.size();i++){
			newM.add(String.valueOf(CurrencyUtils.minus(Double.valueOf(m1v.next()),Double.valueOf(m2v.next()))));
		}
		
		return newM;
	}
	
	/**
	 * 对向量进行求和. 假如向量为空，返回0
	 * 
	 * @param v
	 *            向量
	 * @return 求和后的结果.
	 */
	public static <T> double sum(Vector<T> v) {
		if (v == null) {
			return 0;
		}
		StringBuffer sb = new StringBuffer();
		for (T str : v) {
			if (str != null&&str.toString().trim().length()>0) {
				sb.append(str);
				sb.append("+");
			}
		}
		sb.append("0");
		return expr(sb.toString());
	}

	/**
	 * 对向量的对象进行求和.
	 * 
	 * @param list
	 *            向量列表.
	 * @return 求和后的向量.
	 * @TODO 判断向量的维数要相同.
	 */
	public static <T> Vector<Double> sumList(List<Vector<T>> list) {
		Vector<Double> r = new Vector<Double>();
		if (list.size() == 0) {
			return r;
		}
		StringBuffer sb = new StringBuffer();

		int width = list.get(0).size();
		for (int i = 0; i < width; i++) {
			for (Vector<T> v : list) {
				sb.append(v.get(i));
				sb.append("+");
			}
			sb.append("0");

			r.add(i, expr(sb.toString()));

			sb.setLength(0);
		}

		return r;
	}

	/**
	 * 
	 * @param str
	 *            欲求值的字符串。
	 * @param expected
	 *            期望值。
	 */
	private static double expr(String str) {
		ExprLexer lexer = new ExprLexer(new StringReader(str));
		ExprParser parser = new ExprParser(lexer);
		try {
			parser.expr();
		} catch (RecognitionException e) {
			throw new RuntimeException(e);

		} catch (TokenStreamException e) {
			throw new RuntimeException(e);
		}
		AST t = parser.getAST();

		ExprTreeParser treeParser = new ExprTreeParser();
		try {
			return treeParser.expr(t);

		} catch (RecognitionException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 提供对String类的
	 * split方法的扩展,当录入的字符串以','结尾的时候,在split出来的String[]中增加一个[""]元素,并封装List返回
	 * 
	 * @param str
	 * @param regex
	 * @return
	 */
	public static List<String> VectorSplit(String str, String regex) {
		if (str == null || str.trim().length() <= 0) {
			return null;
		} else {
			
			return Arrays.asList(StringUtils.splitPreserveAllTokens(str, ","));
		}
	}
	
	/**
	 * 将一个对象包装成一个集合
	 * @param entity 对象
	 * @return 返回集合
	 */
	public static Collection getCollection(Object entity){
		List<Object> rs = new ArrayList<Object>();
		rs.add(entity);
		return rs;
	}
}
