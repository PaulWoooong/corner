/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: Security.java 643 2008-09-22 06:30:41Z jcai $
 * created at:2008-09-06
 */

package lichen.services.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标注登录时候的角色,以及与安全相关的属性定义
 * 需要由SecurityWorker提供支持,SecurityWorker的配置默认由SecurityModule提供
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @author dong
 * @version $Revision: 643 $
 * @since 0.0.1
 */
@Target( { ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Security {
	/**
	 * Returns the list of security configuration attributes. (i.e. ROLE_USER,
	 * ROLE_ADMIN etc.)
	 * 
	 * @return String[] The secure method attributes
	 */
	public String[] value() default "";
	
	/**
	 * 返回安全校验失败后定向到的页面类
	 * @return
	 */
	public Class<?> fail() default Object.class;
}
