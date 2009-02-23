/* 
 * Copyright 2008 The Lichen Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lichen.services.asset;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.AssetFactory;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;

/**
 * StaticModule用于提供默认的配置
 * 
 * @author dong
 * @version $Revision: 718 $
 * @since 0.0.1
 */
public class StaticAssetModule {
	private final Request request;
	private final Logger logger;

	public StaticAssetModule(Request request, Logger logger) {
		this.request = request;
		this.logger = logger;
	}

	public static void bind(ServiceBinder binder) {
		binder.bind(StaticAssetDomainFactory.class,
				DefaultStaticAssetDomainFactoryImpl.class);

	}

	/**
	 * 默认使用local配置
	 * 
	 * @param configuration
	 */
	public void contributeFactoryDefaults(
			MappedConfiguration<String, String> configuration) {
		configuration.add(StaticAssetConstants.TYPE,
				StaticAssetConstants.LOCAL_ASSET);
	}

	/**
	 * 建立StaticAssetFactory的实例
	 * 
	 * @param type
	 * @param domainFactory
	 * @return
	 */
	@Marker(StaticProvider.class)
	public AssetFactory buildStaticAssetFactory(@Inject
	@Symbol(StaticAssetConstants.TYPE)
	String type, StaticAssetDomainFactory domainFactory) {
		logger.debug("static asset type:"+type);
		return new StaticAssetFactory(request.getContextPath(), type,
				domainFactory);
	}

	/**
	 * 为Asset增加一种新的类型:static,使用样例:<code>@IncludeStylesheet({ "static:css/style2.css"})</code>
	 * 
	 * 
	 * 
	 * @param configuration
	 * @param staticAssetFactory
	 */
	public void contributeAssetSource(
			MappedConfiguration<String, AssetFactory> configuration,
			@StaticProvider
			AssetFactory staticAssetFactory) {
		configuration.add("static", staticAssetFactory);
	}

	/**
	 * StaticAssetDomainFactory的默认实现,什么也不做,只返回一个空域
	 * 
	 * @author dong
	 * @version $Revision: 718 $
	 * @since 0.0.1
	 */
	public static class DefaultStaticAssetDomainFactoryImpl implements
			StaticAssetDomainFactory {
		public String getDomain(String path) {
			return "";
		}
	}
}
