//==============================================================================
//file :        IAModel.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.model;

import java.io.Serializable;

import corner.orm.hibernate.IPersistModel;

/**
 * A,B实体抽象类
 * <p>
 * 包含了A,B实体公用的属性
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class AbstractPersistModel implements IPersistModel, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * @hibernate.id generator-class="uuid" type="string"
	 * @hibernate.column name="Recno_Pk" comment="主键值" length="32" sql-type="Char(32)"
	 */
	private String id;
	
	/**
	 * 用户登录的用户名
	 * 
	 * @hibernate.property
	 * @hibernate.column name="LOGIN_NAME" comment="用户登录的用户名." length="20"
	 * @hibernate.meta attribute="field-description" value="用户登录的用户名"
	 */
	private String loginName;

	/**
	 * 用户密码,PassWord,Char(20).
	 * 
	 * @hibernate.property
	 * @hibernate.column name="PASS_WORD" comment="用户密码." length="20"
	 * @hibernate.meta attribute="field-description"
	 *                 value="用户密码,PassWord,Char(20)."
	 */
	private String passWord;

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the passWord
	 */
	public String getPassWord() {
		return passWord;
	}

	/**
	 * @param passWord the passWord to set
	 */
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	

    /* bean properties begin */
    public static final String ID_PRO_NAME="id";
    public static final String LOGIN_NAME_PRO_NAME="loginName";
    public static final String PASS_WORD_PRO_NAME="passWord";
    /* bean properties end */
}
