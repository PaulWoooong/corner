// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: ReflectRelativeSelectionListPage.java 4535 2010-03-23 02:15:54Z ghostbb $
// created at:2006-06-22
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

import corner.util.BeanUtils;
import corner.util.EntityConverter;

/**
 * <font color="red">适用于对many-to-many时候对另外一个many端进行选择的操作，提供一个选择的列表。</font>
 * <p>
 * 在增加关系的时候，供选择的对象。
 * <P>此类通常用于many-to-many的时候，提供一个供选择的页面。
 * 此类有两个重要属性，可以自定义，一个是
 *  {@link #getRelativePropertyName()} 得到关联属性的名字 
 *  另外一个是
 *  {@link #isInverse()} 
 *  判断当前的 {@link IPageRooted#getRootedObject()}是否为反相控制端。
 *  
 *  通常这两个是配合使用。
 *  譬如：有A和B两个实体关系是many-to-many.
 *  如果从A的页面来操作两者的关系。
 *  	假如 {@link IPageRooted#getRootedObject()} 为反向控制端，
 *  		则 {@link #getRelativePropertyName()} 返回 as
 *      否则 返回 bs
 *      	  
 * @author Jun Tsai
 * @version $Revision: 4535 $
 * @since 2.0.5
 */
public abstract class ReflectRelativeSelectionListPage extends
		AbstractRelativeSelectionListPage<Object,Object> {
	/**
	 * 得到关联实体的名称，作为集合的复数。
	 * <p>譬如：users,groups等。
	 * 假设我们从User实体进行编辑，进入UserGroup页面，此时UserGroup页面将对应本类，对于该页面来说
	 * User实体为:this.getRootedObject(),而Group实体为:this.getEntity().当isInverse()为true的时候，
	 * 返回"users",当isInverse()为false的时候，返回"groups",因为默认情况下isInverse()为false，因此默认返回"groups"
	 * @return 关联的额属性名称，一般为复数形式。
	 */
	protected String getRelativePropertyName()
	{
		if(isInverse()){
			return EntityConverter.getClassNameAsCollectionProperty(this.getRootedObject());
		}else{
			return EntityConverter.getClassNameAsCollectionProperty(this.getEntity());
		}
	}

	
	/**
	 * 当前的本实体是否为反向控制端。
	 * 默认为false。
	 * @return 判断当前的根实体是否为反向控制端。
	 */
	public abstract boolean isInverse();
	public abstract void setInverse(boolean inverse);
	/**
	 * 判断是否选中。
	 * @return 是否选中
	 * @see corner.orm.tapestry.page.AbstractEntityListPage#isCheckboxSelected()
	 */
	public boolean isCheckboxSelected(){
		if(isInverse())
			return this.getRelationshipCollection(this.getEntity()).contains(this.getRootedObject());
		
		else{
			return this.getRelationshipCollection(this.getRootedObject()).contains(this.getEntity());	
		}
	}
	/**
	 * 选中时候的处理。
	 * @see corner.orm.tapestry.page.AbstractEntityListPage#setCheckboxSelected(boolean)
	 */
	public void setCheckboxSelected(boolean select){
		if(isInverse()){
			doSelectCheckbox(this.getEntity(),this.getRootedObject(),select);
		}else{
			doSelectCheckbox(this.getRootedObject(),this.getEntity(),select);
		}
	}
	/**
	 * 实现实体之间关联关系的增加和删除
	 * @param obj
	 * @param objInversed
	 * @param select
	 */
	@SuppressWarnings("unchecked")
	protected void doSelectCheckbox(Object obj,Object objInversed,boolean select){
		Collection c=this.getRelationshipCollection(obj);
		if(select){
			if(!c.contains(objInversed)){
				c.add(objInversed);
				this.getEntityService().saveEntity(obj);
			}
		}else{
			if(c.contains(objInversed)){
				c.remove(objInversed);
				this.getEntityService().saveEntity(obj);
			}
		}
	}

	/**
	 * 根据给定的实体和属性名称取得对应的属性值
	 * <p>本方法中是根据传入的实体和关联的属性名称取得该实体关联的实体的集合
	 * @param obj
	 * @return
	 */
	private Collection getRelationshipCollection(Object obj){
		Collection c= (Collection) BeanUtils.getProperty(obj,this.getRelativePropertyName());
		if(c==null){
			throw new IllegalStateException("从["+obj+"] 通过属性 ["+this.getRelativePropertyName()+"] 得到的集合为空!");
		}
		return c;
	}

}
