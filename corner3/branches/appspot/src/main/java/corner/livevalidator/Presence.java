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
package corner.livevalidator;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ioc.MessageFormatter;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.validator.AbstractValidator;

/**
 * 定义一个必填字段的校验
 * 
 * @author Jun Tsai
 * @version $Revision: 2956 $
 * @since 0.0.1
 */
public class Presence extends AbstractValidator<Void, Object> {

	private ValidatorRenderSupport delegate;

	public Presence(ValidatorRenderSupport delegate) {
		super(null, Object.class, "presence");
		this.delegate = delegate;
	}

	/**
	 * @see org.apache.tapestry5.Validator#render(org.apache.tapestry5.Field,
	 *      java.lang.Object, org.apache.tapestry5.ioc.MessageFormatter,
	 *      org.apache.tapestry5.MarkupWriter,
	 *      org.apache.tapestry5.services.FormSupport)
	 */
	public void render(Field field, Void constraintValue,
			MessageFormatter formatter, MarkupWriter writer,
			FormSupport formSupport) {
		delegate.renderAssetFiles();

		JSONObject options = new JSONObject();
		options.put("failureMessage", buildMessage(formatter, field));

		delegate.addValidatorScript(field.getClientId(), "presence", options);

	}

	private String buildMessage(MessageFormatter formatter, Field field) {
		return formatter.format(field.getLabel());
	}

	public void validate(Field field, Void constraintValue,
			MessageFormatter formatter, Object value)
			throws ValidationException {
		if (value == null || InternalUtils.isBlank(value.toString()))
			throw new ValidationException(buildMessage(formatter, field));

	}

	@Override
	public boolean isRequired(){
		return true;
	}
}
