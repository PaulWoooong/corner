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
package corner.asset.impl;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import corner.asset.StaticAssetConstants;
import corner.asset.impl.StaticAssetUrlDomainSequenceHash;
import corner.asset.impl.DomainStaticAssetUrlCreatorImpl;

/**
 * @author dong
 * @version $Revision$
 * @since 0.0.2
 */
public class DomainStaticAssetUrlCreatorImplTest {
	@Test
	public void test_domain_index() {
		DomainStaticAssetUrlCreatorImpl _factory = new DomainStaticAssetUrlCreatorImpl(
				"img.eweb", false,null);
		String _url = _factory.createUrl(null,StaticAssetConstants.DEFAULT_ASSET_TYPE, "../images/a.gif", null);
		assertEquals(_url, "http://img.eweb/images/a.gif");
		_factory = new DomainStaticAssetUrlCreatorImpl(
				"http://img.web.server", false,null);
		_url = _factory.createUrl(null,StaticAssetConstants.DEFAULT_ASSET_TYPE, "../images/a.gif", null);
		assertEquals(_url, "http://img.web.server/images/a.gif");

		// 测试泛域名的解析支持
		_factory = new DomainStaticAssetUrlCreatorImpl("img.eweb", true,new StaticAssetUrlDomainSequenceHash(10));
		_url = _factory.createUrl(null,StaticAssetConstants.DEFAULT_ASSET_TYPE, "../images/a.gif", null);
		assertEquals(_url,"http://img0.eweb/images/a.gif");
		_factory = new DomainStaticAssetUrlCreatorImpl("img.eweb:8081", true,new StaticAssetUrlDomainSequenceHash(10));
		for (int i = 0; i < 10; i++) {
			_url = _factory.createUrl(null,StaticAssetConstants.DEFAULT_ASSET_TYPE, "../images/a.gif", null);
			assertEquals(_url, String.format("http://img%s.eweb:8081/images/a.gif", i));
		}
	}

}
