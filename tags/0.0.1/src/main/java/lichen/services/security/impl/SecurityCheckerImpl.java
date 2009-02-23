/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: SecurityCheckerImpl.java 643 2008-09-22 06:30:41Z jcai $
 * created at:2008-09-06
 */

package lichen.services.security.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import lichen.services.security.InvlidateRoleException;
import lichen.services.security.RequiredLoginException;
import lichen.services.security.Security;
import lichen.services.security.SecurityChecker;

import org.apache.tapestry5.services.RequestGlobals;

/**
 * 安全检查类
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 643 $
 * @since 0.0.1
 */
public class SecurityCheckerImpl implements SecurityChecker {

	private RequestGlobals _requestGlobals;

	public SecurityCheckerImpl(RequestGlobals requestGlobals) {
		_requestGlobals = requestGlobals;
	}

	/**
	 * 
	 * @throws IOException
	 */
	public boolean check(Security secured) throws RequiredLoginException, InvlidateRoleException, IOException {
		HttpServletRequest request = _requestGlobals.getHTTPServletRequest();
		if (request.getUserPrincipal() == null) {
			return false;
		}
		boolean result = false;
		final String[] roles = secured.value();
		for (String role : roles) {
			if (role.length() == 0) {
				// 为空的角色,在此认为不需要进行权限检验,通过验证
				result = true;
				break;
			} else if (request.isUserInRole(role)) {
				result = true;
				break;
			}
		}
		return result;

	}

}
