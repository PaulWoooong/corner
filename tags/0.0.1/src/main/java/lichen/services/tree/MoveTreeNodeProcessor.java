/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: MoveTreeNodeProcessor.java 629 2008-09-22 06:17:35Z jcai $
 * created at:2008-09-10
 */

package lichen.services.tree;

import lichen.entities.base.TreeAdapter;
import lichen.services.EntityService;

/**
 * 对树进行处理的字处理程序
 * @author <a href="mailto:jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision: 629 $
 * @since 0.0.1 
 */
public interface MoveTreeNodeProcessor {

	/**
	 * @param node 需要移动的节点
	 * @param service corner基础服务类
	 * @param n 需要移动的距离,n>0 时候 向上移动，n<0的时候向下移动
	 * @param clazz 给定的查询类，如果给定的为空，则从node中获取对应的类.
	 */
	public void execute(TreeAdapter node,EntityService service,int n, Class<? extends TreeAdapter> clazz);

}
