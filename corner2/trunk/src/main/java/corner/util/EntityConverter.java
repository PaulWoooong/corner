// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: EntityConverter.java 3919 2008-01-16 04:36:52Z xf $
// created at:2005-10-18
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

import corner.service.EntityService;

/**
 * 转换表的一些常用函数.
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision:3677 $
 */
public class EntityConverter {
	/**
	 * 转换表的名称.
	 * @param name 需要转换的值.
	 * @param wimpyCaps 是否需要首字母大写,如果为false,首字母大写,否则小写.
	 * @return 转换后的结果.
	 */
	public static String convertName(String name, boolean wimpyCaps) {
		StringBuffer buffer = new StringBuffer(name.length());
		char[] list = name.toLowerCase().toCharArray();

		for (int i = 0; i < list.length; i++) {
			if ((i == 0) && !wimpyCaps) {
				buffer.append(Character.toUpperCase(list[i]));
			} else if (
				(list[i] == '_') && ((i + 1) < list.length) && (i != 0)) {
				buffer.append(Character.toUpperCase(list[++i]));
			} else {
				buffer.append(list[i]);
			}
		}

		return buffer.toString();
	}
	/**
	 * 得到类的不含包名的名称。
	 * <p>譬如 com.abc.A 将返回 A
	 * @param obj 对象。
	 * @return 不含包名的类名
	 */
	public static  String getShortClassName(Object obj) {
		String name = EntityService.getEntityClass(obj).getName();

		name = name.substring(name.lastIndexOf(".") + 1);
		return name;
	}
	/**
	 * 通过给定的类来得到类名的复数属性，譬如: com.abc.AbCd,将返回 abCds
	 * @param object 待处理的对象。
	 * @return 类名的复数属性。
	 */
	public static String getClassNameAsCollectionProperty(Object object) {
		
		return getClassNameAsPropertyName(object)+"s";

	}
	/**
	 * 通过对象来得到classd的名称，譬如 com.abc.Abc，返回为：abc
	 * @param object 待处理的对象。
	 * @return 属性名称。
	 */
	public static String getClassNameAsPropertyName(Object object){
		String name=getShortClassName(object);
		StringBuffer sb = new StringBuffer();
		sb.append(Character.toLowerCase(name.charAt(0)));
		sb.append(name.substring(1));
		return sb.toString();
	}
}
