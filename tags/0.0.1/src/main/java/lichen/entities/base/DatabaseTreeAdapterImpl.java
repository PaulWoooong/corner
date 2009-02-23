/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: DatabaseTreeAdapterImpl.java 623 2008-09-22 06:06:17Z jcai $
 * created at:2008-09-10
 */

package lichen.entities.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * 抽象的树的实现
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 623 $
 * @since 0.0.1
 */
@MappedSuperclass
public class DatabaseTreeAdapterImpl extends BaseModel implements TreeAdapter {
	/**
	 * 左边值
	 */
	private int left;
	/**
	 * 右边值
	 */
	private int right;
	/**
	 * 深度
	 */
	private int depth;
	/**
	 * @see com.ouriba.eweb.entities.base.TreeAdapter#getLeft()
	 */
	@Column(name="TREE_LEFT")
	public int getLeft() {
		return left;
	}
	/**
	 * @see com.ouriba.eweb.entities.base.TreeAdapter#setLeft(int)
	 */
	public void setLeft(int left) {
		this.left = left;
	}
	/**
	 * @see com.ouriba.eweb.entities.base.TreeAdapter#getRight()
	 */
	@Column(name="TREE_RIGHT")
	public int getRight() {
		return right;
	}
	/**
	 * @see com.ouriba.eweb.entities.base.TreeAdapter#setRight(int)
	 */
	public void setRight(int right) {
		this.right = right;
	}
	/**
	 * @see com.ouriba.eweb.entities.base.TreeAdapter#getDepth()
	 */
	@Column(name="TREE_DEPTH")
	public int getDepth() {
		return depth;
	}
	/**
	 * @see com.ouriba.eweb.entities.base.TreeAdapter#setDepth(int)
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}
	@Transient
	public String getIndentStr(){
		StringBuffer sb = new StringBuffer();
		int i = this.getDepth();
		while (i > 1) {
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			i--;
		}
		return sb.toString();
	}
    /* bean properties begin */
    public static final String LEFT_PRO_NAME="left";
    public static final String RIGHT_PRO_NAME="right";
    public static final String DEPTH_PRO_NAME="depth";
    /* bean properties end */

}
