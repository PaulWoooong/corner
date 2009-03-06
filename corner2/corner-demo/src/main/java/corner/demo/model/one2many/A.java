//==============================================================================
//file :        A.java
//
//last change:	date:       $Date:2006-06-21 06:18:45Z $
//           	by:         $Author:jcai $
//           	revision:   $Revision:1196 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.model.one2many;

import java.util.Set;

import corner.demo.model.AbstractPersistModel;

/**
 * one2many中的one实体
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 * @hibernate.class table="CORNER_DEMO_ONE2MANY_ONE" comment="one2many中的one实体"
 * @hibernate.cache usage="read-write"
 * @hibernate.meta attribute="class-description" value="one2many中的one实体"
 * @hibernate.mapping auto-import="false"
 */
public class A extends AbstractPersistModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6581444354115534730L;
	/**
	 * A和B的一对多关联
	 *
	 * @hibernate.set cascade="none"  lazy="true"
	 * @hibernate.key column="A"
	 * @hibernate.one-to-many class="corner.demo.model.one2many.B"
	 */
	private Set<B> bs;
	/**
	 * @return Returns the bs.
	 */
	public Set<B> getBs() {
		return bs;
	}
	/**
	 * @param bs The bs to set.
	 */
	public void setBs(Set<B> bs) {
		this.bs = bs;
	}
    /* bean properties begin */
    public static final String BS_PRO_NAME="bs";
    /* bean properties end */
}
