// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: IPdfLinkParameters.java 3678 2007-11-14 04:43:52Z jcai $
// created at:2007-07-31
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

package corner.orm.tapestry.pdf.link;

import org.apache.tapestry.annotations.Parameter;

/**
 * PdfLink组件通用的参数接口
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision: 3678 $
 * @since 2.3.7
 */
public interface IPdfLinkParameters {

	/**
	 * 是否保存为文件 true:保存为文件 false:直接在网页中打开
	 * 
	 * @return 返回一个blooean类型的参数判断是否要保存为文件
	 */
	@Parameter(defaultValue = "true")
	public abstract boolean isSaveAsFile();

	/**
	 * 取得保存为文件时候的文件名称
	 * 
	 * @return 一个String类型的文件名称,不包括扩展名,当该文件名称为空的时候会用当前时间产生一个文件名称
	 */
	@Parameter
	public abstract String getDownloadFileName();

}
