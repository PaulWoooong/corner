/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: StaticAssetConstants.java 626 2008-09-22 06:10:00Z jcai $
 * created at:2008-09-04
 */

package lichen.services.asset;

/**
 * 定义StaticAsset所用到的常量
 * @author dong
 * @version $Revision: 626 $
 * @since 0.0.1
 */
public class StaticAssetConstants {

	/**
	 * Static ASSET的类型
	 */
	public static final String TYPE = "staticasset.type";
	/**
	 * 本地的静态资源
	 */
	public final static String LOCAL_ASSET = "local";
	/**
	 * 非李本的静态资源,多用于不同引用不同域名下的静态资源
	 */
	public final static String HTTP_ASSET = "http";
}
