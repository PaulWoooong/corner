// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: AbstractRelativeEntityListPage.java 4781 2010-11-30 01:47:40Z lj $
// created at:2006-08-08
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

package corner.orm.tapestry.page.relative;

import org.apache.tapestry.IComponent;
import org.apache.tapestry.IPage;
import org.apache.tapestry.annotations.Component;
import org.apache.tapestry.contrib.table.model.IBasicTableModel;

import corner.orm.tapestry.page.AbstractEntityListPage;
import corner.orm.tapestry.table.RelativePersistentBasicTableModel;

/**
 * 关联实体对象的列表，通常通过one端来得到关联对象的列表页。
 * @author jcai
 * @author Ghost
 * @version $Revision: 4781 $
 * @since 2.1
 */
public abstract class AbstractRelativeEntityListPage<T,E> extends AbstractEntityListPage<E> implements IPageRooted<T,E>{
	/**
	 * 得到列表的source,得到和当前实体关联的对象的列表。
	 * @param relativePropertyName 关联的属性名字，通常为复数，譬如：groups,users等。
	 * @return table model
	 */
	public  IBasicTableModel getSource(String relativePropertyName){
		return new RelativePersistentBasicTableModel<T>(this.getEntityService(),this.getRootedObject(),relativePropertyName,this.getRequestCycle().isRewinding(),this);
	}
	
	/**
	 * 得到列表的source,得到和当前实体关联的对象的列表。
	 * @param relativePropertyName 关联的属性名字，通常为复数，譬如：groups,users等。
	 * @param rootedObject 父类
	 * @return table model
	 */
	public  IBasicTableModel getSource(T rootedObject,String relativePropertyName){
		return new RelativePersistentBasicTableModel<T>(this.getEntityService(),rootedObject,relativePropertyName,this.getRequestCycle().isRewinding(),this);
	}
	

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityListPage#getSource()
	 */
	@Override
	public IBasicTableModel getSource() {
		throw new IllegalStateException("在RelativeEntityListPage不能使用entityList，请使用 getSource(relativePropertyName)");
	}

	
	/**
	 * @see corner.orm.tapestry.page.AbstractEntityListPage#doEditEntityAction(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IPage doEditEntityAction(E entity) {
		return this.getRelativeObjectOperator().doEditRelativeEntityAction(this.getRootedObject(),entity,this.getEntityFormPageStr());
	}

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityListPage#doNewEntityAction()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IPage doNewEntityAction() {
		return this.getRelativeObjectOperator().doNewRelativeAction(this.getRootedObject(), this.getEntityFormPageStr());
	}
	/**
	 * 从list页面返回根页面。
	 * @return 根页面。
	 * @deprecated Use {@link #goRootFormPage()} instead
	 */
	public IPage doReturnRootedFormAction(){
		return goRootFormPage();
	}

	/**
	 * 从list页面返回根页面。
	 * @return 根页面。
	 * @since 2.1
	 * @author Jun Tsai
	 */
	public IPage goRootFormPage(){
		return this.goEntityPageByPage(this.getRootedObject(), this.getRootFormPage());
	}

	/**
	 * 采用注释，简化从list页面返回根页面
	 */
	@Component(type="DirectLink",bindings={"listener=listener:goRootFormPage"})
	public abstract IComponent getGoRootFormPageLink();
	
	/**
	 * 采用注释，简化创建新实体的操作
	 */
	@Component(type="DirectLink",bindings={"listener=listener:doNewEntityAction"})
	public abstract IComponent getNewEntityLink();
}
