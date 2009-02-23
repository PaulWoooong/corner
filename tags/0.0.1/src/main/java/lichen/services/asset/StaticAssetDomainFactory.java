/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: StaticAssetDomainFactory.java 626 2008-09-22 06:10:00Z jcai $
 * created at:2008-09-04
 */

package lichen.services.asset;
/**
 *  静态资源域域名选择的工厂类接口
 * @author dong
 * @version $Revision: 626 $
 * @since 0.0.1
 */
public interface StaticAssetDomainFactory {
	/**
	 * 根据path选择相应的域名,由具体的实现决定
	 * @param path
	 * @return
	 */
	public String getDomain(String path);
}
