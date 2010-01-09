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
package corner.cache.services.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.ioc.Invocation;
import org.apache.tapestry5.services.ValueEncoderSource;
import org.springframework.util.StringUtils;

import corner.cache.annotations.CacheKeyParameter;
import corner.cache.annotations.CacheNsParameter;
import corner.cache.annotations.Cacheable;
import corner.cache.services.CacheManager;
import corner.cache.services.CacheStrategy;
import corner.cache.services.CacheStrategySource;
import corner.cache.services.CacheableDefinitionParser;
import corner.cache.services.impl.CacheableDefine.Definition;
import corner.cache.services.impl.CacheableDefine.Definition.Builder;

/**
 * 针对缓存定义的解析
 * @author <a href="mailto:jun.tsai@gmail.com">Jun Tsai</a>
 * @version $Revision$
 * @since 3.1
 */
public class CacheableDefinitionParserImpl implements CacheableDefinitionParser {

	private ValueEncoderSource valueEncoderSource;
	private CacheManager cacheManager;
	private CacheStrategySource source;

	public CacheableDefinitionParserImpl (ValueEncoderSource valueEncoderSource,
			 CacheManager cacheManager,
			CacheStrategySource source
			) {
		this.valueEncoderSource = valueEncoderSource;
		this.cacheManager = cacheManager;
		this.source = source;
	}

	/**
	 * @see corner.cache.services.CacheableDefinitionParser#parseAsKey(org.apache.tapestry5.ioc.Invocation, java.lang.reflect.Method, corner.cache.services.CacheManager)
	 */
	public String  parseAsKey(Invocation invocation,Method method){
		Cacheable cacheable = method.getAnnotation(Cacheable.class);
		if(cacheable == null){
			return null;
		}
		Definition define = null;
		//if (define == null) { // 如果没定义，则进行分析
			Builder defineBuilder = CacheableDefine.Definition.newBuilder();
			Annotation[][] parametersAnnotations = method
					.getParameterAnnotations();
			for (int i = 0; i < parametersAnnotations.length; i++) {
				Annotation[] pa = parametersAnnotations[i];
				for (Annotation a : pa) {
					if (a instanceof CacheKeyParameter) {
						defineBuilder.addParameterIndex(i);
					}
				}
			}
			define = defineBuilder.build();
			
		// 得到缓存的参数
		List<String> keyParameter=new ArrayList<String>();
		for (int i = 0; i < define.getParameterIndexCount(); i++) {
			int pIndex = define.getParameterIndex(i);
			ValueEncoder encoder = valueEncoderSource.getValueEncoder(method
					.getParameterTypes()[pIndex]);
			keyParameter.add(encoder.toClient(invocation.getParameter(pIndex)));
		}
		//得到缓存的真正key
		String key=null;
		String keyFormat= cacheable.keyFormat();
		if(!StringUtils.hasText(keyFormat)){
			key = DigestUtils.shaHex(method.toString()+keyParameter.toString());
		}else{
    		key =String.format(keyFormat, keyParameter.toArray(new Object[0]));
		}
		
		CacheStrategy strategy = this.source.findStrategy(cacheable.strategy());
		CacheNsParameter[] nses = cacheable.namespaces();
		if(strategy == null){
			throw new RuntimeException("fail to find cache strategy instance!");
		}
		return strategy.appendNamespace(cacheManager,cacheable.clazz(),nses,key,keyParameter.toArray());
	}
	
}
