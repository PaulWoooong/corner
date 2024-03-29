//==============================================================================
// file :       $Id: gainPointFormPage.java 4154 2008-07-04 04:03:35Z xf $
// project:     corner
//
// last change: date:       $Date: 2008-07-04 12:03:35 +0800 (星期五, 04 7月 2008) $
//              by:         $Author: xf $
//              revision:   $Revision: 4154 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.page.gainPoint;

import java.util.List;

import org.apache.tapestry.link.ILinkRenderer;

import corner.demo.model.one2many.A;
import corner.orm.tapestry.RawURLLinkRenderer;
import corner.orm.tapestry.component.gain.GainPoint;
import corner.orm.tapestry.component.prototype.autocompleter.BaseAutocompleter;
import corner.orm.tapestry.page.relative.ReflectRelativeEntityFormPage;

/**
 * 
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @version $Revision: 4154 $
 * @since 2.3.7
 */
public abstract class gainPointFormPage extends ReflectRelativeEntityFormPage {

	/**
	 * @see corner.orm.tapestry.page.relative.ReflectRelativeEntityFormPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {
		GainPoint gp  = (GainPoint) this.getPage().getComponent("GainPointField");
		
		for(Object e : gp.getSaveOrUpdateEntitys()){
			this.setEntity(e);
			super.saveOrUpdateEntity();
		}
		
		for(Object e : gp.getDeleteEntitys()){
			this.getEntityService().deleteEntities(e);
		}
	}
	
	/**
	 * @return
	 */
	public ILinkRenderer getRenderer(){
		return new RawURLLinkRenderer();
	}
	
	public String getAutoEvaUrl(){
		BaseAutocompleter ac  = (BaseAutocompleter) this.getPage().getComponent("addressField");
		return ac.getLink().getAbsoluteURL();
	}
	
	public String getIndicatorAsset(){
		BaseAutocompleter ac  = (BaseAutocompleter) this.getPage().getComponent("addressField");
		return ac.getIndicatorAsset().buildURL();
	}
	
	public List getSource(){
		return  ((A)this.getRootedObject()).getBs();
	}
}

