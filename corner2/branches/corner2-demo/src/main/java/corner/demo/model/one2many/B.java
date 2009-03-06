package corner.demo.model.one2many;

import corner.demo.model.AbstractPersistModel;

/**
 * one2many中的many实体
 * 
 * @author <a href=mailto:ghostbb88@gmail.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 * @hibernate.class table="CORNER_DEMO_ONE2MANY_MANY" comment="one2many中的many实体"
 * @hibernate.cache usage="read-write"
 * @hibernate.meta attribute="class-description" value="one2many中的many实体"
 * @hibernate.mapping auto-import="false"
 */
public class B extends AbstractPersistModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1512980745076899710L;
	
	/**
	 * B和A的多对一关联
	 * 
	 * @hibernate.many-to-one class="corner.demo.model.one2many.A"
	 * @hibernate.column name="A" comment="B和A的多对一关联" length="32" sql-type="Char(32)"
	 */
	private A a;

	/**
	 * @return Returns the a.
	 */
	public A getA() {
		return a;
	}

	/**
	 * @param a The a to set.
	 */
	public void setA(A a) {
		this.a = a;
	}
    /* bean properties begin */
    public static final String A_PRO_NAME="a";
    /* bean properties end */
}
