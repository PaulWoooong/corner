// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: Insert.java 4531 2010-01-15 05:02:08Z ghostbb $
// created at:2006-10-13
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

package corner.orm.tapestry.component.insert;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;

import corner.util.StringUtils;

/**
 * 复写Tapestry的Insert,让Insert可以指定显示字符的长度,大于该长度的部分用...代替
 * @author Ghost
 * @version $Revision: 4531 $
 * @since 2.2.1
 */
public abstract class Insert extends org.apache.tapestry.components.Insert {

	
	@Override
	protected void printText(IMarkupWriter writer, IRequestCycle cycle, String value) {
		if(StringUtils.notBlank(value) && this.getLength()>0 && value.length()>this.getLength()){//指定的长度小于value的长度
			StringBuffer buffer = new StringBuffer(value.substring(0, this.getLength()));
			buffer.append("...");
			value=buffer.toString();
		}
		super.printText(writer, cycle, value);
	}

	
	/**
	 * 取得指定该字段显示的长度
	 * @return
	 */
	public abstract int getLength();
}
