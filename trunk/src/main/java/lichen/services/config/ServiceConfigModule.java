/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: ServiceConfigModule.java 2424 2008-10-31 17:13:57Z d0ng $
 * created at:2008-10-08
 */

package lichen.services.config;

import lichen.services.config.impl.ServiceConfigSourceImpl;

import org.apache.tapestry5.ioc.ServiceBinder;

/**
 * ServiceConfig的配置Module
 * 
 * @author dong
 * @version $Revision: 2424 $
 * @since 0.0.1
 */
public class ServiceConfigModule {

	public static void bind(ServiceBinder binder) {
		binder.bind(ServiceConfigSource.class, ServiceConfigSourceImpl.class);
	}
}