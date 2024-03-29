// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: AbstractManyEntityFormPage.java 3678 2007-11-14 04:43:52Z jcai $
// created at:2006-06-21
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

import java.util.Collection;

import org.apache.tapestry.IPage;
import org.apache.tapestry.contrib.table.model.IBasicTableModel;

import corner.orm.tapestry.page.AbstractEntityFormPage;
import corner.orm.tapestry.table.RelativePersistentBasicTableModel;
import corner.util.BeanUtils;

/**
 * 抽象的many的页面的form页。
 * <p>此form页面的T为当前操作的实体，E为关联实体。
 * @author jcai
 * @version $Revision: 3678 $
 * @param <T> 当前操作的实体对象。
 * @param <E> 关联的对象。
 * @since 2.0.3
 */
public abstract class AbstractManyEntityFormPage<T, E> extends AbstractEntityFormPage<T>  implements IRelativeObjectOperatorSupport {

	
	/**
	 * 得到关联对象。
	 * 此对象用来在对关联对象循环的时候使用的临时变量。
	 * @return 关联的对象
	 */
	public abstract E getRelativeObject();
	public abstract void setRelativeObject(E obj);
	/**
	 * 得到列表的source,得到和当前实体关联的对象的列表。
	 * @param relativePropertyName 关联的属性名字，通常为复数，譬如：groups,users等。
	 * @return table model
	 */
	public  IBasicTableModel getSource(String relativePropertyName){
		return new RelativePersistentBasicTableModel<T>(this.getEntityService(),this.getEntity(),relativePropertyName,this.getRequestCycle().isRewinding());
	}

	/**
	 * 新增加一个关联对象的操作。
	 * 
	 * @param obj 供操作的对象。
	 * @param pageName 转向的页面名称。
	 * @return 操作后返回的页面。
	 * @since 2.0.5
	 */
	@SuppressWarnings("unchecked")
	public IPage doNewRelativeAction(T obj,String pageName){
		return this.getRelativeObjectOperator().doNewRelativeAction(obj, pageName);
	}
	/**
	 * 编辑一个关联对象的操作。
	 * <p>适用于one-to-many的操作。
	 * @param obj 供操作的对象。
	 * @param e 关联的对象。
	 * @param pageName 转向的页面名称。
	 * @return 操作后返回的页面。
	 * @since 2.0.5
	 */
	@SuppressWarnings("unchecked")
	public IPage doEditRelativeEntityAction(T obj,E e,String pageName){
		return this.getRelativeObjectOperator().doEditRelativeEntityAction(obj, e, pageName);
	}
	
	/**
	 * 删除对象之间的关联关系。
	 * @param t 当前的实体对象。
	 * @param e 关联的关系实体对象。
	 * @deprecated Use {@link #deleteRelationship(T,E,String)} instead
	 */
	protected void deleteRelationship(T t,E e){
		deleteRelationship(t, e, null);
	}
	/**
	 * 删除对象之间的关联关系。
	 * @param t 当前的实体对象。
	 * @param e 关联的关系实体对象。
	 * @param relativeName 关联关系的名程
	 * @since 2.1
	 */
	@SuppressWarnings("unchecked")
	protected void deleteRelationship(T t,E e, String relativeName){
		if(relativeName==null){
			throw new IllegalArgumentException("relativeName is null!");
		}
		Collection<Object> c = (Collection<Object>) BeanUtils.getProperty(t, relativeName);
		if(c==null){
			throw new IllegalStateException("从["+t+"],通过关系["+relativeName+"]得到的集合为空！");
		}
		c.remove(e);
		this.getEntityService().saveOrUpdateEntity(t);
	}
	/**
	 * 响应删除关联关系的操作。
	 * @param t 当前的实体对象。
	 * @param e 关联关系实体对象。
	 * @return 删除关联关系后的返回页面。
	 */
	public IPage doDeleteRelativeAction(T t,E e){
		this.doDeleteRelativeAction(t, e, null);
		return this;
	}
	/**
	 * 响应删除关联关系的操作。
	 * @param t 当前的实体对象。
	 * @param e 关联关系实体对象。
	 * @param relativeName 关联关系的名程
	 * @return 删除关联关系后的返回页面。
	 * @since 2.1
	 */
	public IPage doDeleteRelativeAction(T t,E e,String relativeName){
		this.deleteRelationship(t,e,relativeName);
		this.setEntity(t);
		this.flushHibernate();
		return this;
	}
	/**
	 * 删除的关联对象。
	 * <p>通常用于在one-to-many的时候删除many端的对象。
	 * @param e 关联的对象。
	 * @return 当前页面。
	 */
	@SuppressWarnings("unchecked")
	public IPage doDeleteRelativeEntityAction(E e){
		this.getEntityService().deleteEntities(e);
		return this;
	}
	
	
	/**
	 * 通常操作one-to-one时候使用
	 * @param rootObj one 主对象
	 * @param relativeObj 从对象
	 * @param pageName
	 * @return 通常是在编辑rootObj(主对象)的时候进行relativeObj(从对象)操作。当relativeObj不为空的时候，
	 * 就编辑relativeObj，当relativeObj为空的时候就新创建一个relativeObj
	 */
	public IPage doNewOrEditRelativeEntityAction(T rootObj,E relativeObj,String pageName){
		if(relativeObj!=null){
			return this.doEditRelativeEntityAction(rootObj, relativeObj, pageName);
		}
		else{
			return this.doNewRelativeAction(rootObj, pageName);
		}	
	}
	
}
