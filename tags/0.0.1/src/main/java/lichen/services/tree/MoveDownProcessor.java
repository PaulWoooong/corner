/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: MoveDownProcessor.java 629 2008-09-22 06:17:35Z jcai $
 * created at:2008-09-10
 */

package lichen.services.tree;

import java.util.List;

import lichen.entities.base.TreeAdapter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

/**
 * 向下移动的处理程序
 * 
 * @author <a href="mailto:jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 629 $
 * @since 0.0.1 
 */
class MoveDownProcessor extends AbstractMoveTreeNodeProcessor
		implements MoveTreeNodeProcessor {

	private int moveBlockLeftStart = 0;

	private int moveBlockLeftEnd = Integer.MAX_VALUE;

	private int offset = 0;

	@Override
	protected void appendQueryReplaceNodeCriteria(Criteria criteria) {
		criteria.add(Restrictions.gt(TreeAdapter.RIGHT_PRO_NAME, getCurrentNode().getRight()))
				.add(Restrictions.lt(TreeAdapter.RIGHT_PRO_NAME, getParentNode().getRight()));
		criteria.addOrder(Order.asc(TreeAdapter.LEFT_PRO_NAME));

	}

	@Override
	protected void fetchMoveBlockInfo(List<? extends TreeAdapter> list) {
		if (list.size() == 0) { // 未找到了移动的位置，说明已经溢出
			offset = (getParentNode().getRight() - 1)
					- getCurrentNode().getRight();
			moveBlockLeftStart = getCurrentNode().getRight() + 1;
			moveBlockLeftEnd = getParentNode().getRight();
		} else {// 找到了移动的位置
			TreeAdapter replaceNode = (TreeAdapter) list.get(0);
			offset = replaceNode.getRight() - getCurrentNode().getRight();
			moveBlockLeftStart = getCurrentNode().getRight() + 1;
			moveBlockLeftEnd = replaceNode.getRight();
		}

	}

	@Override
	protected int getMoveBlockLeftEnd() {
		return this.moveBlockLeftEnd;
	}

	@Override
	protected int getMoveBlockLeftStart() {
		return this.moveBlockLeftStart;
	}

	@Override
	protected int getOffset() {
		return this.offset;
	}

	@Override
	protected int getUpdateWidth() {
		return getCurrentNode().getLeft() - getCurrentNode().getRight() - 1;
	}

	@Override
	protected TreeAdapter constructRootNode() {
		TreeAdapter rootNode;
		try {
			rootNode = (TreeAdapter) BeanUtils
					.instantiateClass(Class.forName(getTreeClassName()));
		} catch (BeanInstantiationException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		rootNode.setLeft(0);

		long rowCount = (Long) getEntityService().find("select count(*) from " + getTreeClassName()).get(0);
		rootNode.setRight((int) (rowCount * 2 + 1));

		return rootNode;
	}
}
