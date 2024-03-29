// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: TablePages.java 4910 2011-03-03 03:17:14Z xf $
// created at:2007-03-23
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

package corner.orm.tapestry.table;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.services.DataSqueezer;
import org.apache.tapestry.util.ComponentAddress;



/**
 * 个性化设置table分页的展示
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 4910 $
 * @since 1.0.8
 */
public abstract class TablePages extends org.apache.tapestry.contrib.table.components.TablePages{
	
	/**
	 * 
	 */
	@Parameter(defaultValue = "literal:共")
	public abstract String getTablePagesPrefix();
	
	/**
	 * @return
	 */
	@Parameter(defaultValue = "literal:条")
	public abstract String getTablePagesSuffix();

	/** 得到页数 **/
	public int getPageCount(){
		return this.getTableModelSource().getTableModel().getPageCount();
	}
	/** 得到给定的样式**/
	public String getFirstLinkClass(){
		return this.getCondBack()?"nextpage":"disablepage";
	}
	public String getLastLinkClass(){
		return this.getCondFwd()?"nextpage":"disablepage";
	}
	/**
	 * 得到行数的消息
	 * @return 得到行数
	 */
	public String getRowCountMessage(){
		return getRowCount()+"";
	}
	int getRowCount(){
		return this.getTableModelSource().getTableModel().getRowCount();
	}
	/** 记录前端输入多少页数 **/
	public  abstract Integer getPn();
	
	public abstract String getComponentAddress();
	public abstract void setComponentAddress(String c);
	@InjectObject("service:tapestry.data.DataSqueezer")
	public abstract DataSqueezer getDataSqueezer();
	
	/**
	 * 记录组件地址
	 * @return
	 */
	public String getDefaultComponentAddress(){
		ComponentAddress objAddress = new ComponentAddress(getTableModelSource());
		return getDataSqueezer().squeeze(objAddress);
	}
	/** 响应前端点击GO **/
	public void goPage(IRequestCycle objCycle){
		objCycle.setListenerParameters(new Object[]{getDataSqueezer().unsqueeze(getComponentAddress()),getPn()});
		super.changePage(objCycle);
	}
}
