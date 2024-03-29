// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: MulitPersistentBasicTableModel.java 4186 2008-07-11 06:43:30Z ghostbb $
// created at:2006-09-28
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tapestry.contrib.table.model.ITableColumn;

import corner.service.EntityService;
import corner.util.BeanUtils;

/**
 * 扩展了BasicTableModel,实现每行显示指定数量的实体
 * 
 * @author Ghost
 * @version $Revision: 4186 $
 * @since 2.2.1
 */
public class MulitPersistentBasicTableModel extends PersistentBasicTableModel {

	/**
	 * 每行显示的纪录数量
	 */
	private static final int OBJ_PERPAGE_INT = 3;

	/**
	 * 对本也的行书进行缓存
	 */
	private int rowCount = -1;

	/**
	 * 是否为提交
	 */
	private boolean isRewinding = false;

	// 查询结果的缓存
	private List resultList = null;

	/**
	 * 构造方法
	 * 
	 * @param entityService
	 * @param callback
	 * @param isRewinding
	 */
	public MulitPersistentBasicTableModel(EntityService entityService,
			IPersistentQueriable callback, boolean isRewinding) {
		super(entityService, callback, isRewinding);
		this.isRewinding = isRewinding;
	}

	/**
	 * 取出本页显示的全部纪录进行封装，按照指定的每行显示的数量
	 * 
	 * @see corner.orm.tapestry.table.PersistentBasicTableModel#getCurrentPageRows(int,
	 *      int, org.apache.tapestry.contrib.table.model.ITableColumn, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Iterator getCurrentPageRows(int nFirst, int nPageSize,
			ITableColumn column, boolean sort) {
		if (resultList == null) {
			Iterator rowIterator = super.getCurrentPageRows(nFirst, nPageSize, column, sort);

			if (rowIterator != null && rowIterator.hasNext()) {
				List<List> returnList = new ArrayList<List>();
				List<Object> objList = new ArrayList<Object>();
				int i = 1;
				while (rowIterator.hasNext()) {
					if (i > OBJ_PERPAGE_INT && i % OBJ_PERPAGE_INT == 1) {
						objList = new ArrayList<Object>();
					}
					Object obj = rowIterator.next();
					objList.add(obj);
					if (!rowIterator.hasNext() || i % OBJ_PERPAGE_INT == 0) {
						if (!rowIterator.hasNext()) {
							if (i % OBJ_PERPAGE_INT != 0) {
								int loopCount = OBJ_PERPAGE_INT
										- (i % OBJ_PERPAGE_INT);
								for (int k = 0; k < loopCount; k++) {
									objList.add(BeanUtils.instantiateClass(EntityService.getEntityClass(obj)));

								}

							}
						}
						returnList.add(objList);
					}
					i++;
				}
				resultList = returnList;
			}
			if(resultList == null){
				resultList = new ArrayList();
			}
		}

		return resultList.iterator();
	}

	/**
	 * 根据每行显示的数量计算出本页显示的行数
	 * 
	 * @see corner.orm.tapestry.table.PersistentBasicTableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		if (isRewinding) {
			return rowCount;
		}
		if (rowCount == -1) {
			int originalCount = super.getRowCount();
			if (originalCount > OBJ_PERPAGE_INT) {
				rowCount = originalCount % OBJ_PERPAGE_INT == 0 ? originalCount
						/ OBJ_PERPAGE_INT : originalCount / OBJ_PERPAGE_INT + 1;
			} else {
				rowCount = 1;
			}
		}
		return rowCount;
	}

}
