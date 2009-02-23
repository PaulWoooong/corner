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
/**
 *  静态资源域域名选择的工厂类接口
 * @author dong
 * @version $Revision: 718 $
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
