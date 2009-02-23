//==============================================================================
// file :       $Id: AListPage.java 3677 2007-11-14 04:36:40Z jcai $
// project:     corner
//
// last change: date:       $Date: 2007-11-14 12:36:40 +0800 (星期三, 14 十一月 2007) $
//              by:         $Author: jcai $
//              revision:   $Revision: 3677 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.page.gainPoint;

import org.apache.tapestry.IPage;

import corner.orm.tapestry.page.PoListPage;
import corner.service.svn.IVersionProvider;
import corner.service.svn.IVersionable;

/**
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision: 3677 $
 * @since 2.5
 */
public abstract class AListPage extends PoListPage implements IVersionProvider{
	/**
	 * @param entity
	 * @return
	 */
	public IPage doVersionPage(Object entity){
		AVersionListPage page = (AVersionListPage) this.getRequestCycle().getPage("gainPoint/AVersionList");
		page.setVersionEntity((IVersionable) entity);
		return page;
	}

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityListPage#doDeleteEntityAction(java.lang.Object)
	 */
	@Override
	public IPage doDeleteEntityAction(Object entity) {
		IPage page =  super.doDeleteEntityAction(entity);
		this.getSubversionService().delete((IVersionable) entity);
		return this;
	}
}
