/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ganshane.net
 * file: $Id: ConfigurationSourceImpl.java 4355 2008-12-05 02:39:02Z d0ng $
 * created at:2008-10-08
 */

package corner.config.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.internal.util.CollectionFactory;
import org.apache.tapestry5.ioc.internal.util.Defense;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.ioc.util.StrategyRegistry;

import corner.config.ConfigInitable;
import corner.config.ConfigurationSource;

/**
 * 实现服务配置工厂类.
 * 
 * @author <a href="jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 4355 $
 * @since 0.0.1
 */

public class ConfigurationSourceImpl implements ConfigurationSource {

	private final StrategyRegistry<Resource> registry;

	private final Map<Class, Object> cache = CollectionFactory
			.newConcurrentMap();

	public ConfigurationSourceImpl(Map<Class, Resource> configuration) {
		registry = StrategyRegistry.newInstance(Resource.class, configuration);
	}

	/**
	 * 
	 * @see corner.config.ConfigurationSource#getServiceConfig(java.lang.Class)
	 */
	@Override
	public <T> T getServiceConfig(Class<T> type) {
		Defense.notNull(type, "type");

		T result = (T) cache.get(type);

		if (result == null) {
			Resource resource = registry.get(type);
			result = createServiceConfigObject(type, resource);
			cache.put(type, result);
		}

		return result;
	}

	private <T> T createServiceConfigObject(Class<T> type, Resource resource) {

		InputStream in;
		try {
			in = resource.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		if (in == null) {
			throw new RuntimeException("The config source[" + resource
					+ "] can't be found.");
		}
		Reader reader = new InputStreamReader(in);
		T result = load(reader, type);
		if (result instanceof ConfigInitable) {
			((ConfigInitable) result).init();
		}
		return result;

	}
	/**
	 * 从一个输入流里加载clazz类型的对象
	 * 
	 * @param <T>
	 * @param in 操作完成后,会被关闭
	 * @param clazz
	 * @return
	 * @throws RuntimeException
	 *             在加载的过程出现异常,将抛出此异常
	 */
	public static <T> T load(Reader in, Class<T> clazz) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller um = context.createUnmarshaller();
			return (T) um.unmarshal(in);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} finally {
			InternalUtils.close(in);
		}
	}

}