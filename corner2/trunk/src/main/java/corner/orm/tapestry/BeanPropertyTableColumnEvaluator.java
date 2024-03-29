// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: BeanPropertyTableColumnEvaluator.java 3678 2007-11-14 04:43:52Z jcai $
// created at:2005-11-04
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

package corner.orm.tapestry;

import org.apache.tapestry.contrib.table.model.ITableColumn;
import org.apache.tapestry.contrib.table.model.simple.ITableColumnEvaluator;

import corner.util.BeanUtils;

/**
 * 
 * 根据bean的属性来对值进行获取.
 * <P>采用的是BeanUtils.
 * @author	<a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version	$Revision:3677 $
 * @since	2005-11-4
 * @see BeanUtils
 */
public final class BeanPropertyTableColumnEvaluator implements ITableColumnEvaluator{
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -2746967267411020412L;

	public Object getColumnValue(ITableColumn column, Object obj) {
		return BeanUtils.getProperty(obj,column.getColumnName());
	}
}
