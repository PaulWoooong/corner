//==============================================================================
// file :       $Id: AB.java 3677 2007-11-14 04:36:40Z jcai $
// project:     corner
//
// last change: date:       $Date: 2007-11-14 12:36:40 +0800 (星期三, 14 11月 2007) $
//              by:         $Author: jcai $
//              revision:   $Revision: 3677 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.model.many2many2;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import corner.demo.model.AbstractModel;

/**
 * @author Ghost
 * @version $Revision: 3677 $
 * @since 2.1
 * @hibernate.class table="many2many2AB"
 * @hibernate.cache usage="read-write"
 * @hibernate.mapping auto-import="false" 
 */
@Entity(name="many2many2AB")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class AB extends AbstractModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1555102955247011594L;

	/**
	 * @hibernate.many-to-one class="corner.demo.model.many2many2.A" column="AId" fetch="select"
	 */
	private A a;

	/**
	 * @hibernate.many-to-one class="corner.demo.model.many2many2.B" column="BId" fetch="select"
	 */
	private B b;
	
	/**
	 * 中间表中的信息
	 * <p>用于纪录中间表中的信息</p>
	 * @hibernate.property
	 */
	private String message;
	

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Returns the a.
	 */
	@ManyToOne
	@JoinColumn(name="AId")
	public A getA() {
		return a;
	}

	/**
	 * @param a The a to set.
	 */
	public void setA(A a) {
		this.a = a;
	}

	/**
	 * @return Returns the b.
	 */
	@ManyToOne
	@JoinColumn(name="BId")
	public B getB() {
		return b;
	}

	/**
	 * @param b The b to set.
	 */
	public void setB(B b) {
		this.b = b;
	}

}
