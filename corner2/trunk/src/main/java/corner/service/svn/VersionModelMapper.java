// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: VersionModelMapper.java 3678 2007-11-14 04:43:52Z jcai $
// created at:2007-11-02
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

package corner.service.svn;

import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * 针对进行版本控制的对象进行的映射.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 3678 $
 * @since 2.5
 */
public class VersionModelMapper extends MapperWrapper  {

	public static final String ENTITY_JSON_KEY="entity"; 
	public VersionModelMapper(Mapper wrapped) {
		super(wrapped);
	}
	/**
	 * @see com.thoughtworks.xstream.mapper.MapperWrapper#serializedClass(java.lang.Class)
	 */
	@Override
	public String serializedClass(Class type) {
		//注释部分为动态改变转换后的cglib的类名.
//		if (type.getName().contains("CGLIB")) {//是否为hibernate代理的类.
//			return type.getSuperclass().getName();
//		}
//		return type.getName();
		return ENTITY_JSON_KEY;
	}
}
