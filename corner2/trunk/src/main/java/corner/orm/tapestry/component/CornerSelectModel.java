// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: CornerSelectModel.java 3678 2007-11-14 04:43:52Z jcai $
// created at:2006-09-05
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

package corner.orm.tapestry.component;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.hivemind.ApplicationRuntimeException;

import corner.util.TapestryHtmlFormatter;

/**
 * CornerSelect和TextAreaBox的实现Model
 * @author Ghost
 * @version $Revision: 3678 $
 * @since 2.1
 * @deprecated
 */
public class CornerSelectModel implements ISelectModel {
    
    /**
     * 根据用户的输入对返回查询结果的接口
     */
	private ISelectFilter filter;
		
    /**
     * 默认的构造函数
     *<p>提供一个默认的构造方法,需要的参数可以通过set方法注入</p>
     */
    public CornerSelectModel(){
    	
    }
	


	/**
	 * 得到cnlabelField的值
	 * @param value
	 * @return
	 */
    public String getCnLabelFor(Object value){
        try {
            
            if(value instanceof String){
            	return value.toString();
            }
            else{
            	return PropertyUtils.getProperty(value, TapestryHtmlFormatter.lowerFirstLetter(this.filter.getCnLabel())).toString();
            }
            
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }    	
    }

	/**
	 * @see org.apache.tapestry.dojo.form.IAutocompleteModel#getLabelFor(java.lang.Object)
	 */
	public String getLabelFor(Object value) {
        try {
            
            if(value instanceof String){
            	return value.toString();
            }
            else{
            	return PropertyUtils.getProperty(value, TapestryHtmlFormatter.lowerFirstLetter(this.filter.getLabel())).toString();
            }
            
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        }
	}
	
	/**
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getPrimaryKey(java.lang.Object)
	 */
	public Object getPrimaryKey(Object value) {
        return value;
	}

	/**
	 * @see org.apache.tapestry.components.IPrimaryKeyConverter#getValue(java.lang.Object)
	 */
	public Object getValue(Object primaryKey) {
        return this.getPrimaryKey(primaryKey);
	}

	public void setFilter(ISelectFilter filter) {
		this.filter=filter;
		
	}

	public List getValues(String match) {
		return this.filter.filterValues(match);
	}

}
