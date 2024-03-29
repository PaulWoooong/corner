/* 
 * Copyright 2009 The Corner Team.
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
package corner;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.ActionRenderResponseGenerator;
import org.apache.tapestry5.internal.services.ActionRenderResponseGeneratorImpl;
import org.apache.tapestry5.internal.services.LinkSource;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.ServiceId;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.BindingFactory;
import org.apache.tapestry5.services.ComponentClassTransformWorker;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.PersistentFieldStrategy;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.Response;

import corner.asset.StaticAssetModule;
import corner.cache.CacheModule;
import corner.config.ConfigurationModule;
import corner.encrypt.EncryptModule;
import corner.hadoop.HadoopModule;
import corner.livevalidator.ValidationModule;
import corner.orm.OrmModule;
import corner.payment.PaymentModule;
import corner.protobuf.ProtocolBuffersModule;
import corner.security.SecurityModule;
import corner.tapestry.bindings.BindingModule;
import corner.tapestry.fckeditor.FckeditorModule;
import corner.tapestry.persistent.CookiePersistentFieldStrategy;
import corner.tapestry.services.override.RedirectMixedImmediateResponseGenerator;
import corner.tapestry.transform.PageRedirectWorker;
import corner.template.TemplateModule;
import corner.transaction.TransactionModule;

/**
 * 定义了Corner的核心module
 * 
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
@SubModule( {ValidationModule.class, StaticAssetModule.class,
		SecurityModule.class, ProtocolBuffersModule.class,
		FckeditorModule.class, PaymentModule.class,
		HadoopModule.class, ConfigurationModule.class,BindingModule.class,TransactionModule.class,
		EncryptModule.class,TemplateModule.class,OrmModule.class,CacheModule.class})
public class CoreModule {

	/**
	 * 绑定使用的service.
	 * 
	 * @param binder
	 *            Service Binder
	 * @see ServiceBinder
	 */
	public static void bind(ServiceBinder binder) {
	}

	/**
	 * 组件类。
	 * 
	 * @param configuration
	 *            library mapping configruation
	 */
	public static void contributeComponentClassResolver(
			Configuration<LibraryMapping> configuration) {
		configuration.add(new LibraryMapping("corner", "corner.tapestry"));
	}

	/**
	 * 对重定向注释PageRedirect的配置
	 * 
	 * @param configuration
	 *            Module的配置实例
	 * @param resolver
	 *            用于查找Component资源的实例
	 * @param linkFactory
	 *            用于构建PageLink的LinkFactory
	 */
	public static void contributeComponentClassTransformWorker(
			OrderedConfiguration<ComponentClassTransformWorker> configuration,
			ObjectLocator locator) {
		configuration.add("pageRedirect",locator.autobuild(PageRedirectWorker.class));
	}

	

	// 扩展一个flash前缀的binding
	public static void contributeBindingSource(
			MappedConfiguration<String, BindingFactory> configuration,
			ObjectLocator locator) {
		
	}

	// 扩展一个可以在客户端进行Persist的保存策略
	public void contributePersistentFieldManager(
			MappedConfiguration<String, PersistentFieldStrategy> configuration,
			Request request, ObjectLocator locator) {
		configuration.add("cookie", locator
				.autobuild(CookiePersistentFieldStrategy.class));

	}


	/**
	 * 对一些基础配置进行了初步的设置
	 * 
	 * @param configuration
	 *            配置
	 */
	public static void contributeFactoryDefaults(
			MappedConfiguration<String, String> configuration) {
		configuration.add(CornerConstants.REMOTE_SERVER_URL,
				"http://localhost:5180/corner/remote.hessian");
		configuration.add(CornerConstants.DEFAULT_CALLER, "hessian");
		configuration.add(CornerConstants.ENABLE_REMOTE_CALL, "false");

		configuration.add(CornerConstants.ENABLE_HTML_TEMPLATE, "false");
		configuration.add(CornerConstants.ENABLE_HTML_ACCESS, "true");

		// 配置默认的资源引用类型为classpath
		configuration.add(CornerConstants.ASSET_TYPE, "classpath");
		configuration.add(CornerConstants.COMPOENT_TABLEVIEW_ROWS_PERPAGE, "15");
	}



	//混合模式，支持@PageRedirect调用
	@ServiceId("RedirectMixedImmediateResponseGenerator")
	public static ActionRenderResponseGenerator buildRedirectMixedImmediateResponseGenerator(LinkSource linkSource,
			Request request, Response response){
		RedirectMixedImmediateResponseGenerator generator = new RedirectMixedImmediateResponseGenerator(linkSource,request,response);
		return generator;
	}
	@ServiceId("InternelActionRenderResponseGenerator")
	public static ActionRenderResponseGenerator buildActionRenderResponseGeneratorImpl(ObjectLocator locator) {
	    return locator.autobuild(ActionRenderResponseGeneratorImpl.class);
	}
	
   
	//复写内置的方式,采取复合的模式
	public static void contributeServiceOverride(MappedConfiguration<Class,Object> configuration,
			@Symbol(SymbolConstants.SUPPRESS_REDIRECT_FROM_ACTION_REQUESTS)
		    boolean immediateMode,
		    @InjectService("RedirectMixedImmediateResponseGenerator") ActionRenderResponseGenerator generator,
		    @InjectService("InternelActionRenderResponseGenerator") ActionRenderResponseGenerator internelGenerator
		    )
	  {
		 if (immediateMode){
			 configuration.add(ActionRenderResponseGenerator.class, generator);
		 } else{
			 configuration.add(ActionRenderResponseGenerator.class, internelGenerator);
		 }
	  }

}
