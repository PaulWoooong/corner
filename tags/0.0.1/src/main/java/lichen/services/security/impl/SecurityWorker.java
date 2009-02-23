/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: SecurityWorker.java 643 2008-09-22 06:30:41Z jcai $
 * created at:2008-09-06
 */

package lichen.services.security.impl;


import lichen.services.security.Security;
import lichen.services.security.SecurityChecker;

import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.internal.services.LinkFactory;
import org.apache.tapestry5.ioc.util.BodyBuilder;
import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.services.ClassTransformation;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.TransformConstants;

/**
 * 对安全角色的增强器.
 * 
 * @author Jun Tsai
 * @version $Revision: 643 $
 * @since 0.0.1
 */
public class SecurityWorker implements ComponentClassTransformWorker {

	private final SecurityChecker _checker;
	private final ComponentClassResolver _resolver;
	private final LinkFactory _linkFactory;

	/**
	 * 
	 * @param checker 
	 * @param resolver
	 * @param linkFactory
	 */
	public SecurityWorker(SecurityChecker checker,ComponentClassResolver resolver,LinkFactory linkFactory) {
		this._checker = checker;
		this._resolver = resolver;
		this._linkFactory = linkFactory;
	}

	/**
	 * 
	 * @see org.apache.tapestry.services.ComponentClassTransformWorker#transform(org.apache.tapestry.services.ClassTransformation,
	 *      org.apache.tapestry.model.MutableComponentModel)
	 * @since 0.0.1
	 */
	public void transform(ClassTransformation transformation,
			MutableComponentModel model) {
		// 对类的安全注释进行检查.
		Security annotation = transformation.getAnnotation(Security.class);

		if (annotation == null)
			return;
		
		//取得在annotation中定义的fail属性，用于安全校验失败之后的返回页面
		Class<?> failClass = annotation.fail();
		
		String checker = transformation.addInjectedField(SecurityChecker.class,
				"_$checker", _checker);
		String pageSecurity = transformation.addInjectedField(Security.class,
				"_$page_security", annotation);
		BodyBuilder builder = new BodyBuilder();
		
		builder.begin();

		builder.addln("if ($1.isAborted()) return $_;");

		builder.addln("try");
		builder.begin();

		builder.addln(String.format("if (!%s.check(%s)) ", checker,
				pageSecurity));
		builder.begin();
		addCodeForMethod(builder, transformation,failClass);
		builder.end();

		builder.end(); // try

		// Runtime exceptions pass right through.

		builder.addln("catch (RuntimeException ex) { throw ex; }");

		// Wrap others in a RuntimeException to communicate them up.

		builder
				.addln("catch (Exception ex) { throw new RuntimeException(ex); } ");

		builder.end();

		transformation
				.extendMethod(TransformConstants.DISPATCH_COMPONENT_EVENT,
						builder.toString());

	}

	private void addCodeForMethod(BodyBuilder builder,
			ClassTransformation transformation,Class<?> failClass) {

		builder.addln("if ($1.matches(\"%s\", \"%s\", %d))",
				EventConstants.ACTIVATE, "", 0);
		builder.begin();

		// Ensure that we return true, because *some* event handler method was
		// invoked,
		// even if it chose not to abort the event.

		builder.addln("$_ = true;");

		String pageName = null;
		if(failClass!=null && failClass != Object.class){
			pageName = this._resolver.resolvePageClassNameToPageName(failClass.getName());
		}
		if(pageName ==null){
			pageName = "";
		}
      String linkFactory = transformation.addInjectedField(LinkFactory.class, "linkFactory",this._linkFactory);
		builder.addln("%s $_retLink = %s.createPageLink(\"%s\",false,new Object[0]);",Link.class.getName(),linkFactory,pageName);
		
		// Store the result, converting primitives to wrappers automatically.
		builder.add(String.format("if ($1.storeResult(%s)) ","$_retLink"));
		builder.addln(" return true;");
		builder.end();
	}

}