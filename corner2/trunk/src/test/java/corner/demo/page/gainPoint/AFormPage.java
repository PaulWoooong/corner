//==============================================================================
// file :       $Id: AFormPage.java 4121 2008-06-26 02:48:53Z xf $
// project:     corner
//
// last change: date:       $Date: 2008-06-26 10:48:53 +0800 (星期四, 26 六月 2008) $
//              by:         $Author: xf $
//              revision:   $Revision: 4121 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.page.gainPoint;

import org.apache.tapestry.IPage;

import corner.orm.tapestry.page.relative.IPageRooted;
import corner.orm.tapestry.page.relative.ReflectMultiManyEntityFormPage;
import corner.service.svn.IVersionProvider;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision: 4121 $
 * @since 2.3.7
 */
public abstract class AFormPage extends ReflectMultiManyEntityFormPage implements IVersionProvider{
	
	/**
	 * 编辑实体操作.
	 *
	 * @param entity
	 *            实体.
	 * @return 返回编辑页面.
	 * @since 2.0
	 */

	public IPage doEditEntityAction(Object entity) { // 编辑操作
		IPageRooted<Object,Object> page= (IPageRooted<Object,Object>) this.getRequestCycle().getPage("gainPoint/gainPointForm");
		page.setRootedObject(this.getEntity());
		
		return page;
	}
	
	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {
		super.saveOrUpdateEntity();
	}
}
