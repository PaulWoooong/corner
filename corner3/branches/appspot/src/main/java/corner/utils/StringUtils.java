/* 
 * Copyright 2008 The Corner Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package corner.utils;

/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class StringUtils {

	/**
	 * 取得字符串以tokden分隔的字符分段内容
	 * 
	 * @param str
	 *            字符值
	 * @param token
	 *            分隔符,与String.split()方法中参数含义一样
	 * @param index
	 *            要取得的分段索引,从0开始
	 * @return 返回token的第index部分;如果没有找到token,则返回null
	 * @since 0.0.2
	 */
	public static String getPart(final String str, final String token,
			final int index) {
		String _str = str;
		if (str.startsWith(token)) {
			_str = str.replaceFirst(token, "");
		}
		String[] _split = _str.split(token);
		if (_split.length >= index) {
			return _split[index];
		}
		return null;
	}
}
