/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: PageRedirect.java 649 2008-09-22 06:41:40Z jcai $
 * created at:2008-09-08
 */

package lichen.transform;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标注一个action的返回重定向到Page
 * 
 * @author dong
 * @version $Revision: 649 $
 * @since 0.0.1
 */
@Target( { ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PageRedirect {
}
