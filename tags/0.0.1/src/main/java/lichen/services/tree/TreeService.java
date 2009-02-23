/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: TreeService.java 629 2008-09-22 06:17:35Z jcai $
 * created at:2008-09-10
 */

package lichen.services.tree;

import java.util.List;

import lichen.entities.base.TreeAdapter;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

/**
 * 用来操作树的服务类
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 629 $
 * @since 0.0.1
 */
public interface TreeService {

	/**
	 * 保存树型结构的一个节点.此方法只是针对新建树节点时候，
	 *  <p>需要注意的是：在调用改方法之前，一定不能先保存了改节点
	 * 
	 * @param node
	 *            待保存的节点
	 * @param parentNode 父节点,如果parentNode为空，则为插入顶极节点
	 * @param clazz 待保存节点的类,目的是区分有继承关系的实体.
	 */
	@CommitAfter
	public abstract void saveTreeChildNode(TreeAdapter node,
			TreeAdapter parentNode, Class<? extends TreeAdapter> clazz);

	/**
	 * 通过给定的类，来得到对应的树结构
	 * 得到完整树
	 * 
	 * @param clazz     对应的类
	 */
	@SuppressWarnings("unchecked")
	public abstract List<? extends TreeAdapter> getTree(
			Class<? extends TreeAdapter> clazz);

	/**
	 * 同级移动节点.
	 *  <p>
	 *  当n>0的时候，为向上移动，
	 *  当 n<0的时候，为向下移动.
	 * 
	 * @param node
	 *            节点
	 * @param n        移动的位置,正数，则为向上移动，负数，向下移动
	 * @param clazz  给定的查询类 ,如果为空，则通过 node 实例来得到对应的类
	 */
	@CommitAfter
	public abstract void moveNode(TreeAdapter node, int n, Class<? extends TreeAdapter> clazz);

	/**
	 * 删除一个节点
	 * 如果clazz为空，则操作对象为<code>node</code>对应的实体类.
	 * @param node 节点
	 * @param clazz  给定的查询类 ,如果为空，则通过 node 实例来得到对应的类
	 */
	@CommitAfter
	public abstract void deleteTreeNode(TreeAdapter node, Class<? extends TreeAdapter> clazz);

}