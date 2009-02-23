/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: TreeServiceImpl.java 629 2008-09-22 06:17:35Z jcai $
 * created at:2008-09-10
 */

package lichen.services.tree;

import java.util.List;

import lichen.entities.base.TreeAdapter;
import lichen.services.EntityService;
import lichen.utils.EntityUtil;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.BeanUtils;

/**
 * 提供了对tee组件的操作服务类.
 * 
 * 
 * @author <a href="mailto:jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 629 $
 * @since 0.0.1 
 */
public class TreeServiceImpl implements TreeService {

	//查询所有节点数量
	private static final String COUNT_ALL_NODE_HSQL="select count(*) from %s";
	//删除节点
	private static final String DELETE_NODE_HSQL="delete %s n where (n."+TreeAdapter.LEFT_PRO_NAME+" between ? and ? )";
	//更新左边节点
	private static final String UPDATE_LEFT_HSQL="update %s n set n."+TreeAdapter.LEFT_PRO_NAME+"=n."+TreeAdapter.LEFT_PRO_NAME+"+%d where n."+TreeAdapter.LEFT_PRO_NAME+">?";
	//更新右边节点
	private static final String UPDATE_RIGHT_HSQL="update %s n set n."+TreeAdapter.RIGHT_PRO_NAME+"=n."+TreeAdapter.RIGHT_PRO_NAME+"+%d where n."+TreeAdapter.RIGHT_PRO_NAME+">?";
	private EntityService entityService;
	
	public TreeServiceImpl(EntityService service){
		this.entityService = service;
	}

	
	
	
	
	
	/**
	 * @see com.ouriba.eweb.services.tree.TreeService#saveTreeChildNode(com.ouriba.eweb.entities.base.TreeAdapter, com.ouriba.eweb.entities.base.TreeAdapter, java.lang.Class)
	 */
	public void saveTreeChildNode(TreeAdapter node, TreeAdapter parentNode, Class<? extends TreeAdapter> clazz) {
		String treeClassName = getTreeClassName(node,clazz);

		if (parentNode == null) { // 插入顶级节点
			parentNode = (TreeAdapter) BeanUtils.instantiateClass(EntityUtil.getEntityClass(node));
			parentNode.setLeft(0);
			parentNode.setDepth(0);
			long rowCount = (Long) this.entityService.find(String.format(COUNT_ALL_NODE_HSQL, treeClassName)).get(0);
			parentNode.setRight((int) (rowCount * 2 + 1));
		} else { // reload parent object
			entityService.refresh(parentNode);
		}
		// 得到父节点的右边值
		int parentRight = parentNode.getRight();
		// 更新大于节点的值
		/*
		 * update table set left=left+2 where left>parentRight update table set
		 * right=right+2 where right>=parentRight
		 */

		String updateLeftHQL = String.format(UPDATE_LEFT_HSQL,treeClassName,2);
		
		entityService.bulkUpdate(updateLeftHQL, new Object[] { parentRight});

		String updateRightHQL = String.format(UPDATE_RIGHT_HSQL,treeClassName,2);
		entityService.bulkUpdate(updateRightHQL, new Object[] { parentRight - 1});

		// 更新当前节点的值
		node.setLeft(parentRight);
		node.setRight(parentRight + 1);
		node.setDepth(parentNode.getDepth() + 1);

		// 保存当前的实体
		entityService.saveOrUpdate(node);
//		if (parentNode.getId() != null) {
//			entityService.refresh(parentNode);
//		}
	}

	

	/**
	 * @see com.ouriba.eweb.services.tree.TreeService#getTree(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public List<? extends TreeAdapter> getTree(
			Class<? extends TreeAdapter> clazz) {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.addOrder(Order.asc(TreeAdapter.LEFT_PRO_NAME));

		return entityService.findByCriteria(criteria);
	}

	/**
	 * @see com.ouriba.eweb.services.tree.TreeService#moveNode(com.ouriba.eweb.entities.base.TreeAdapter, int, java.lang.Class)
	 */
	public void moveNode(TreeAdapter node, int n, Class<? extends TreeAdapter> clazz) {
		if (n == 0) {
			return;
		}

		MoveTreeNodeProcessor processor=createSubProcessor(node,n);
		
		processor.execute(node,this.entityService,n,clazz);
		
		
	}

	private MoveTreeNodeProcessor createSubProcessor(TreeAdapter node, int n) {
		if(n>0){
			return new MoveUpProcessor();
		}else{
			return new MoveDownProcessor();
		}
		
	}



	private String getTreeClassName(TreeAdapter node, Class<? extends TreeAdapter> clazz) {
		if(clazz!=null){
			return clazz.getName();
		}else{
			return EntityUtil.getEntityClass(node).getName();
		}
		
	}

	/**
	 * @see com.ouriba.eweb.services.tree.TreeService#deleteTreeNode(com.ouriba.eweb.entities.base.TreeAdapter, java.lang.Class)
	 */
	public void deleteTreeNode(TreeAdapter node, Class<? extends TreeAdapter> clazz) {
		
		String treeClassName =getTreeClassName(node,clazz);
		int left = node.getLeft();
		int right = node.getRight();

		int width = left-right - 1;

		// 删除该节点，以及节点下面所属的字节点
		entityService.bulkUpdate(String.format(DELETE_NODE_HSQL,treeClassName),
				new Object[] { left, right});

		// 更新其他节点的左右值
		entityService.bulkUpdate(String.format(UPDATE_LEFT_HSQL,treeClassName,width), new Object[] {right});
		entityService.bulkUpdate(String.format(UPDATE_RIGHT_HSQL,treeClassName,width), new Object[] {right});

	}
}