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
package lichen.livevalidator;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.json.JSONArray;
import org.apache.tapestry5.json.JSONObject;

/**
 * 对校验进行的delegate类。
 * 
 * 用来加载JavaScript,以及CSS文件
 * 
 * @author Jun Tsai
 * @version $Revision: 158 $
 * @since 0.0.1
 */
public class ValidatorRenderSupportImpl implements ValidatorRenderSupport {
	protected RenderSupport renderSupport;
	protected Asset validatorCss;

	public ValidatorRenderSupportImpl(RenderSupport renderSupport,
			@Path("/lichen/validator/validator.css")
			Asset validatorCss) {
		this.renderSupport = renderSupport;
		this.validatorCss = validatorCss;
	}

	public void renderAssetFiles() {
		renderSupport.addStylesheetLink(validatorCss, null);
		renderSupport
				.addClasspathScriptLink("${tapestry.scriptaculous}/prototype.js");
		renderSupport
				.addClasspathScriptLink("/lichen/validator/livevalidation_prototype.compressed.js");
		renderSupport.addClasspathScriptLink("/lichen/validator/lv4t5.js");

	}

	public void addValidatorScript(String fieldId, String validator,
			JSONObject options) {

		JSONArray parameterList = new JSONArray();
		parameterList.put(fieldId);
		parameterList.put(validator);
		parameterList.put(options);
		renderSupport.addInit("liveValidation", parameterList);
	}
}
