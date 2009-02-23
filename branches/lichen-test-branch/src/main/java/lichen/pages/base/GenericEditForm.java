/* 
 * Copyright 2008 The Lichen Team.
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
package lichen.pages.base;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.slf4j.Logger;

/**
 * 范性化的表单页面
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 230 $
 * @since 0.0.1
 */
public class GenericEditForm<T> extends AbstractEntityForm<T>{

	
	
	

	/** logger **/
	@Inject
	private Logger logger;

	

	@Inject
	private ComponentResources resources;
	@Inject
	private ValueEncoderSource valueEncoderSource;
	

	void onActivate(String id){
		//得到对应持久化对象
		ValueEncoder<T> encoder = valueEncoderSource.getValueEncoder(this.getEntityClass());
		this.setEntity(encoder.toValue(id));
		
	}

	
	Object onPassivate(){
		return this.getEntity();
	}
	
	/**
	 * 得到返回的页面，子类可以覆盖
	 * @return 得到返回的页面
	 */
	protected String  getReturnPage() {
		//得到本页面的名称
		String pageName = resources.getPageName();
		logger.debug("entity new page name:["+pageName+"]");
		//构造列表页面的名称
		String listPageName=pageName.replaceAll("([\\w\\/]*[^\\/]*)Edit", "$1List");
		
		logger.debug("list page name:["+listPageName+"]");
		
		return listPageName;
	}

	
	

}
