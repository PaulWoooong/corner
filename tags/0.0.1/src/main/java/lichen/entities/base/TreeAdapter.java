/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: TreeAdapter.java 623 2008-09-22 06:06:17Z jcai $
 * created at:2008-09-10
 */

package lichen.entities.base;

import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * 抽象树的属性接口
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 623 $
 * @since 0.0.1
 */
public interface TreeAdapter {

	/**
	 * @return the left
	 */
	@Column(name = "TREE_LEFT")
	public abstract int getLeft();

	/**
	 * @param left the left to set
	 */
	public abstract void setLeft(int left);

	/**
	 * @return the right
	 */
	@Column(name = "TREE_RIGHT")
	public abstract int getRight();

	/**
	 * @param right the right to set
	 */
	public abstract void setRight(int right);

	/**
	 * @return the depth
	 */
	@Column(name = "TREE_DEPTH")
	public abstract int getDepth();

	/**
	 * @param depth the depth to set
	 */
	public abstract void setDepth(int depth);
	
	@Transient
	public String getIndentStr();
	 /* bean properties begin */
    public static final String LEFT_PRO_NAME="left";
    public static final String RIGHT_PRO_NAME="right";
    public static final String DEPTH_PRO_NAME="depth";
    /* bean properties end */
}