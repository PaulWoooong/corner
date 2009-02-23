//==============================================================================
// file :       $Id: OneListPage.java 3677 2007-11-14 04:36:40Z jcai $
// project:     corner
//
// last change: date:       $Date: 2007-11-14 12:36:40 +0800 (星期三, 14 十一月 2007) $
//              by:         $Author: jcai $
//              revision:   $Revision: 3677 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.page.mulitable;

import org.apache.tapestry.contrib.table.model.IBasicTableModel;

import corner.orm.tapestry.page.PoListPage;
import corner.orm.tapestry.table.MulitPersistentBasicTableModel;

/**
 * @author Ghost
 * @version $Revision: 3677 $
 * @since 2.2.1
 */
public abstract class OneListPage extends PoListPage {

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityListPage#getSource()
	 */
	@Override
	public IBasicTableModel getSource() {
		return new MulitPersistentBasicTableModel(this.getEntityService(),this,this.getRequestCycle().isRewinding());
	}

}
