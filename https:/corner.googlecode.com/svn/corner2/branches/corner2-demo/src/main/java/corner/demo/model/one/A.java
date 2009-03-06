package corner.demo.model.one;

import corner.demo.model.AbstractPersistModel;


/**
 * 单表实体，不包含任何关联
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 * @hibernate.class table="CORNER_DEMO_ONE" comment="单表实体，不包含任何关联"
 * @hibernate.cache usage="read-write"
 * @hibernate.meta attribute="class-description" value="单表实体，不包含任何关联"
 * @hibernate.mapping auto-import="false"
 */
public class A extends AbstractPersistModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
