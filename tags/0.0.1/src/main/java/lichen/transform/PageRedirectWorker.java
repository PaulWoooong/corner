/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: PageRedirectWorker.java 649 2008-09-22 06:41:40Z jcai $
 * created at:2008-09-08
 */

package lichen.transform;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.internal.services.LinkFactory;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.ComponentMethodAdvice;
import org.apache.tapestry5.services.ComponentMethodInvocation;
import org.apache.tapestry5.services.TransformMethodSignature;

/**
 *  处理PageRedirect注释,目前只支持返回类型是String和Class的Action
 *  TODO  增加对返回null和Page类型的支持
 * @author dong
 * @version $Revision: 649 $
 * @since 0.0.1
 */
public class PageRedirectWorker implements ComponentClassTransformWorker {

	private final ComponentClassResolver _resolver;
	private final LinkFactory _linkFactory;

	private final ComponentMethodAdvice advice = new ComponentMethodAdvice() {
		public void advise(ComponentMethodInvocation invocation) {
			try {
				invocation.proceed();
				Object result = invocation.getResult();
				// 返回类型是void,不做处理
				Class<?> resultType = result.getClass();
				if (resultType == java.lang.Void.class) {
					return;
				}
				
				// 返回类型是Class或者String时，尝试查找result对应的Page
				String pageName = null;
				if (resultType == Class.class || resultType == String.class) {
					if (resultType == java.lang.Class.class) {
						Class<?> clazz = (Class<?>)result;
						pageName = _resolver.resolvePageClassNameToPageName(clazz.getName());
					}else if(resultType == String.class){
						pageName = (String)result;
					}
				}
				if (pageName != null) {
    				Link retLink = _linkFactory.createPageLink(pageName,false,new Object[0]);
    				invocation.overrideResult(retLink);
				}
			} catch (RuntimeException ex) {
				throw ex;
			}
		}
	};

	/**
	 * 
	 * @param resolver
	 * @param linkFactory
	 */
	public PageRedirectWorker(ComponentClassResolver resolver, LinkFactory linkFactory) {
		this._resolver = resolver;
		this._linkFactory = linkFactory;
	}

	/**
	 * 
	 * @see org.apache.tapestry.services.ComponentClassTransformWorker#transform(org.apache.tapestry.services.ClassTransformation,
	 *      org.apache.tapestry.model.MutableComponentModel)
	 * @since 0.0.1
	 */
	public void transform(ClassTransformation transformation, MutableComponentModel model) {
		for (TransformMethodSignature sig : transformation.findMethodsWithAnnotation(PageRedirect.class)) {
			transformation.advise(sig, advice);
		}
	}

}