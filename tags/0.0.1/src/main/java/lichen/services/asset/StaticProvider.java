/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: StaticProvider.java 626 2008-09-22 06:10:00Z jcai $
 * created at:2008-09-03
 */

package lichen.services.asset;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 使用T5注入时用于标识StaticAssetFactory的类型
 * @author dong
 * @version $Revision: 626 $
 * @since 0.0.1
 */
@Target( { PARAMETER, FIELD })
@Retention(RUNTIME)
@Documented
public @interface StaticProvider {

}
