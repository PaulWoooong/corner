//==============================================================================
// file :       $Id: TestOne.java 3677 2007-11-14 04:36:40Z jcai $
// project:     corner
//
// last change: date:       $Date: 2007-11-14 12:36:40 +0800 (星期三, 14 十一月 2007) $
//              by:         $Author: jcai $
//              revision:   $Revision: 3677 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.model.mulitupload;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import corner.demo.model.AbstractModel;

/**
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision: 3677 $
 * @since 2.3.7
 */

@Entity(name="TestOne")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TestOne extends AbstractModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<TestMany> tms;

	/**
	 * @return Returns the bs.
	 */
	@OneToMany(mappedBy="testOne")
	@OnDelete(action=OnDeleteAction.CASCADE)
	public List<TestMany> getTms() {
		return tms;
	}

	/**
	 * @param bs The bs to set.
	 */
	public void setTms(List<TestMany> tms) {
		this.tms = tms;
	}
}
