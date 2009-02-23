/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: SecurityChecker.java 643 2008-09-22 06:30:41Z jcai $
 * created at:2008-09-06
 */

package lichen.services.security;

import java.io.IOException;

/**
 * 安全检查
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 643 $
 * @since 0.0.1
 */
public interface SecurityChecker {

	/**
	 * 通过给定的安全设置的annotation来检查。
	 * @param secured 安全设置.
	 * @return 是否成功.
	 */
	public boolean check(Security secured) throws RequiredLoginException,InvlidateRoleException,IOException;
}
