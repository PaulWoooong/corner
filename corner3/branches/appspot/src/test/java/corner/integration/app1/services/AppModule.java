/* 
 * Copyright 2008 The Corner Team.
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
package corner.integration.app1.services;

import java.security.Principal;

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ObjectLocator;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.SubModule;

import corner.CoreModule;
import corner.services.converter.ConverterSource;
import corner.services.converter.ConverterVersion;
import corner.services.converter.ConverterVersionSource;
import corner.services.converter.impl.ConverterServiceImpl;
import corner.services.converter.impl.ConverterVersionServiceImpl;
import corner.services.http.HttpModule;
import corner.services.security.SecurityConstants;
import corner.services.security.SecurityPrincipalService;

/**
 * 
 * @version $Revision: 5021 $
 * @since 0.0.1
 */
@SubModule( {  CoreModule.class,HttpModule.class })
public class AppModule {

	public static class SecurityPrincipalServiceImpl implements
			SecurityPrincipalService {

		@Override
		public Principal getCurrentPrincipal() {
			return null;
		}

		@Override
		public String[] getPrincipalRoles(Principal princiapl) {
			return null;
		}

	}

	public static void bind(ServiceBinder binder) {
		binder.bind(SecurityPrincipalService.class,
				SecurityPrincipalServiceImpl.class);
		binder.bind(ConverterSource.class, ConverterServiceImpl.class);
		binder.bind(ConverterVersionSource.class, ConverterVersionServiceImpl.class);
	}

	public static void contributeApplicationDefaults(
			MappedConfiguration<String, String> configuration) {
		// Contributions to ApplicationDefaults will override any contributions
		// to
		// FactoryDefaults (with the same key). Here we're restricting the
		// supported
		// locales to just "en" (English). As you add localised message catalogs
		// and other assets,
		// you can extend this list of locales (it's a comma separated series of
		// locale names;
		// the first locale name is the default when there's no reasonable
		// match).

		configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");

		// The factory default is true but during the early stages of an
		// application
		// overriding to false is a good idea. In addition, this is often
		// overridden
		// on the command line as -Dtapestry.production-mode=false
		configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
		configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
		configuration.add(
				SymbolConstants.SUPPRESS_REDIRECT_FROM_ACTION_REQUESTS, "true");

		configuration.add(SecurityConstants.ENABLE_SECURITY, "false");
	}

	public static void contributeConverterVersionSource(
			MappedConfiguration<String, ConverterVersion> configuration,
			ObjectLocator objectLocator) {
		configuration.add("test", objectLocator
				.autobuild(DailyConverterVersionImpl.class));

	}
}
