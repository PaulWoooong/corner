/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: SecurityContextFilter.java 643 2008-09-22 06:30:41Z jcai $
 * created at:2008-09-06
 */

package lichen.services.security.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import lichen.services.security.SecurityPrincipalService;

import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestGlobals;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;

/**
 * 对安全进行控制的过滤器
 * 
 * @author Jun Tsai
 * @author dong
 * @version $Revision: 643 $
 * @since 0.0.1
 */
public class SecurityContextFilter implements RequestFilter {

	/** request globals object * */
	private RequestGlobals _requestGlobals;
	/** principal service * */
	private SecurityPrincipalService _principalService;

	/**
	 * @param requestGlobals
	 * @param principalService
	 */
	public SecurityContextFilter(RequestGlobals requestGlobals, SecurityPrincipalService principalService) {
		this._requestGlobals = requestGlobals;
		_principalService = principalService;
	}

	/**
	 * 
	 * @see org.apache.tapestry.services.RequestFilter#service(org.apache.tapestry.services.Request,
	 *      org.apache.tapestry.services.Response,
	 *      org.apache.tapestry.services.RequestHandler)
	 * @since 0.0.1
	 */
	public boolean service(Request request, Response response, RequestHandler handler) throws IOException {
		HttpServletRequest servletRequest = _requestGlobals.getHTTPServletRequest();
		String[] roles = (_principalService.getCurrentPrincipal()!= null ? _principalService.getPrincipalRoles(_principalService.getCurrentPrincipal()) : null);
		SecuredHttpServletRequestWrapper delegateRequest = new SecuredHttpServletRequestWrapper(servletRequest,this._principalService, roles);
		_requestGlobals.storeServletRequestResponse(delegateRequest, _requestGlobals.getHTTPServletResponse());
		return handler.service(request, response);

	}
}