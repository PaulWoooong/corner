// Copyright 2004, 2005 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.orm.tapestry.valid;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.tapestry.form.IFormComponent;

/**
 * 验证消息处理类
 * @author <a href="mailto:xf@bjmaxinfo.com">xiafei</a>
 * @version $Revision$
 * @since 2.5.2
 */
public final class ValidationStrings {
	
	/**
	 * 验证码图片显示
	 */
	public static final String IDENTIFYING_CODE_ENTER_ERROR = "identifying-code-enter";

	/**
	 * 资源路径
	 */
	private static final String RESOURCE_BUNDLE = ValidationStrings.class.getName();

	/**
	 * 
	 */
	private ValidationStrings() {
		// Disable construction
	}

	/**
	 * Fetches the appropriate validation message pattern from the appropriate
	 * localized resource. This method should be called with the locale of the
	 * current request.
	 */
	public static String getMessagePattern(String key, Locale locale) {
		return ResourceBundle.getBundle(RESOURCE_BUNDLE, locale).getString(key);
	}
	
	/**
	 * 获得信息消息
	 * @param messageOverride
	 * @param messageKey
	 * @param arguments
	 * @param field
	 * @param locale
	 * @return
	 */
	public static String formatValidationMessage(IFormComponent field,
			Locale locale, String messageOverride, String messageKey,
			Object[] arguments) {
		
		String message = extractLocalizedMessage(field, locale, messageOverride, messageKey);

		return MessageFormat.format(message, arguments);
	}

	/**
	 * 获得国际化信息
	 * @param messageOverride
	 * @param messageKey
	 * @param field
	 * @param locale
	 * @return
	 */
	private static String extractLocalizedMessage(IFormComponent field,
			Locale locale, String messageOverride, String messageKey) {
		if (messageOverride == null)
			return getMessagePattern(messageKey, locale);

		if (messageOverride.startsWith("%")) {
			String key = messageOverride.substring(1);

			return field.getContainer().getMessages().getMessage(key);
		}

		// Otherwise, a literal string

		return messageOverride;
	}
}
