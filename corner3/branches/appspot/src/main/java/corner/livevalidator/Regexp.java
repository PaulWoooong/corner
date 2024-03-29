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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationException;
import org.apache.tapestry5.ioc.MessageFormatter;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.FormSupport;
import org.apache.tapestry5.validator.AbstractValidator;

/**
 * 对T5的Regexp的改写,使用livevalidator方式进行校验
 * 
 * @author dong
 * @version $Revision: 2089 $
 * @since 0.0.2
 */

public class Regexp extends AbstractValidator<Pattern, String> {
	private final ValidatorRenderSupport delegate;

	public Regexp(ValidatorRenderSupport delegate) {
		super(Pattern.class, String.class, "regexp");
		this.delegate = delegate;
	}

	private String buildMessage(MessageFormatter formatter, Field field,
			Pattern constraintValue) {
		return formatter.format(constraintValue.toString(), field.getLabel());
	}

	public void render(Field field, Pattern constraintValue,
			MessageFormatter formatter, MarkupWriter writer,
			FormSupport formSupport) {

		delegate.renderAssetFiles();
		JSONObject options = new JSONObject();
		options.put("failureMessage", buildMessage(formatter, field,
				constraintValue));
		options.put("pattern", constraintValue.pattern());
		delegate.addValidatorScript(field.getClientId(), "regexp", options);

	}

	public void validate(Field field, Pattern constraintValue,
			MessageFormatter formatter, String value)
			throws ValidationException {
		Matcher matcher = constraintValue.matcher(value);

		if (!matcher.matches())
			throw new ValidationException(buildMessage(formatter, field,
					constraintValue));
	}

}
