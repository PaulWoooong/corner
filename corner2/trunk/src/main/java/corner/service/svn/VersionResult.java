// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: VersionResult.java 3689 2007-11-15 04:21:31Z jcai $
// created at:2007-11-01
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

import java.io.Serializable;
import java.util.Date;

/**
 * 记录版本信息的类.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 3689 $
 * @since 2.5
 */
public class VersionResult implements Comparable<VersionResult>, Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6494057726673233107L;
	
	/** 版本号 **/
	private long versionNum;
	/** 作者 **/
	private String author;
	/** 注释 **/
	private String comment;
	/** 创建日期 **/
	private Date createDate;
	/** 序列号 **/
	private int versionSeq;
	
	/**
	 * @return Returns the author.
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author The author to set.
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return Returns the comment.
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return Returns the createDate.
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate The createDate to set.
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return Returns the versionNum.
	 */
	public long getVersionNum() {
		return versionNum;
	}
	/**
	 * @param versionNum The versionNum to set.
	 */
	public void setVersionNum(long versionNum) {
		this.versionNum = versionNum;
	}
	/**
	 * @return Returns the versionSeq.
	 */
	public int getVersionSeq() {
		return versionSeq;
	}
	/**
	 * @param versionSeq The versionSeq to set.
	 */
	public void setVersionSeq(int versionSeq) {
		this.versionSeq = versionSeq;
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(VersionResult o) {
		return o.versionSeq-this.versionSeq;
	}
}
