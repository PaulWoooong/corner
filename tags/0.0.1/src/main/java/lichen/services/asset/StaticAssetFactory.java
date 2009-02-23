/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: StaticAssetFactory.java 626 2008-09-22 06:10:00Z jcai $
 * created at:2008-09-03
 */

package lichen.services.asset;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.services.AssetFactory;

/**
 * 提供static类型的Asset工厂实现
 * 
 * @author dong
 * @version $Revision: 626 $
 * @since 0.0.1
 */
public class StaticAssetFactory implements AssetFactory {
	private final String context;
	private final String type;
	private final StaticAssetDomainFactory domainFactory;

	/**
	 * 构建StaticAssetFactory实例
	 * 
	 * @param context
	 *            应用的上下文路径
	 *            可以通过org.apache.tapestry5.services.Request.getContext得到;
	 * @param type
	 *            实例的类型,支持local和http两种类型,参见 StaticAssecConstants中的定义
	 * @param domainFactory
	 *            用于选择静态资源域名(也可以作为静态资源的根路径),可以为空
	 */
	public StaticAssetFactory(String context, String type,
			StaticAssetDomainFactory domainFactory) {
		this.context = context;
		this.type = type;
		this.domainFactory = domainFactory;
	}

	/**
	 * @see org.apache.tapestry5.services.AssetFactory#createAsset(org.apache.tapestry5.ioc.Resource)
	 */
	public Asset createAsset(final Resource resource) {
		return new Asset() {
			public Resource getResource() {
				return resource;
			}

			public String toClientURL() {
				String domain = "";
				String path = resource.getPath();
				/*
				 * 如果是HTTP类型,尝试从domainFactory中获取其根路径
				 */
				if (StaticAssetConstants.HTTP_ASSET
						.equals(StaticAssetFactory.this.type)
						&& StaticAssetFactory.this.domainFactory != null) {
					domain = StaticAssetFactory.this.domainFactory
							.getDomain(path);
				}
				if (domain == null) {
					domain = "";
				}
				return domain + path;
			}

			@Override
			public String toString() {
				return toClientURL();
			}
		};
	}

	/**
	 * @see org.apache.tapestry5.services.AssetFactory#getRootResource()
	 */
	public Resource getRootResource() {
		if (this.type == null
				|| this.type.equals(StaticAssetConstants.LOCAL_ASSET)) {
			return new StaticResource(this.context != null ? this.context : "");
		} else {
			return new StaticResource("");
		}
	}
}
