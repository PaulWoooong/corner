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
package lichen.services.security;

import lichen.services.security.impl.SecurityCheckerImpl;
import lichen.services.security.impl.SecurityContextFilter;
import lichen.services.security.impl.SecurityWorker;

import org.apache.tapestry5.internal.services.LinkFactory;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestGlobals;

/**
 * 定义安全的module
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @author dong
 * @version $Revision: 718 $
 * @since 0.0.1
 */
public class SecurityModule {
	/**
	 * 绑定使用的service.
	 * 
	 * @param binder
	 *            Service Binder
	 * @see ServiceBinder
	 */
	public static void bind(ServiceBinder binder) {
		binder.bind(SecurityChecker.class, SecurityCheckerImpl.class);
	}

	/**
	 * 对安全控制的过滤.
	 * 
	 * @param configuration
	 *            配置.
	 * @param requestGlobals
	 *            request globals object.
	 * @param principalService
	 *            principal service.
	 */
	public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration, RequestGlobals requestGlobals, SecurityPrincipalService principalService) {
		configuration.add("securityFilter", new SecurityContextFilter(requestGlobals, principalService), "after:StoreIntoGlobals");
	}

	/**
	 * 
	 * 对安全的控制的ClassTransform worker
	 * 
	 * @param configuration Module的配置实例
	 * @param checker 安全校验的实现实例
	 * @param resolver 用于查找Component资源的实例
	 * @param linkFactory 用于构建PageLink的LinkFactory
	 */
	public static void contributeComponentClassTransformWorker(OrderedConfiguration<ComponentClassTransformWorker> configuration, SecurityChecker checker, ComponentClassResolver resolver, LinkFactory linkFactory) {
		configuration.add("security", new SecurityWorker(checker, resolver, linkFactory));
	}
}
