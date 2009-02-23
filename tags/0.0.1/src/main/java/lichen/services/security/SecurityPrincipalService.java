/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: SecurityPrincipalService.java 643 2008-09-22 06:30:41Z jcai $
 * created at:2008-09-06
 */

package lichen.services.security;

import java.security.Principal;

/**
 * 用于Principal的服务类
 * 
 * @author dong
 * @version $Revision: 643 $
 * @since 0.0.1
 */
public interface SecurityPrincipalService {
	/**
	 * 取得当前上下文中的Principal
	 * 
	 * @return 
	 */
	public Principal getCurrentPrincipal();
	
	/**
	 * 取得principal的拥有的角色
	 * @param princiapl
	 * @return 如果principal没有任何角色,返回null;否则返回principal所拥有的角色
	 */
	public String[] getPrincipalRoles(Principal princiapl);

}
