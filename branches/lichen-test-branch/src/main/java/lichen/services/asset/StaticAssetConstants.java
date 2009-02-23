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
 * 定义StaticAsset所用到的常量
 * @author dong
 * @version $Revision: 718 $
 * @since 0.0.1
 */
public class StaticAssetConstants {

	/**
	 * Static ASSET的类型
	 */
	public static final String TYPE = "staticasset.type";
	/**
	 * 本地的静态资源
	 */
	public final static String LOCAL_ASSET = "local";
	/**
	 * 非李本的静态资源,多用于不同引用不同域名下的静态资源
	 */
	public final static String HTTP_ASSET = "http";
}
