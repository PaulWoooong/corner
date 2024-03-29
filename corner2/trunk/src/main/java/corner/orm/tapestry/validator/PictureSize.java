// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: PictureSize.java 3678 2007-11-14 04:43:52Z jcai $
// created at:2006-12-19
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

package corner.orm.tapestry.validator;

import java.text.DecimalFormatSymbols;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.form.FormComponentContributorContext;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.validator.BaseValidator;
import org.apache.tapestry.json.JSONLiteral;
import org.apache.tapestry.json.JSONObject;
import org.apache.tapestry.valid.ValidationConstants;
import org.apache.tapestry.valid.ValidatorException;

/**
 * @author xiafei
 * @version $Revision: 3678 $
 * @since 2.3
 */
public class PictureSize extends BaseValidator{
	
	private String [] picSize;

	/**
	 * @see org.apache.tapestry.form.validator.Validator#validate(org.apache.tapestry.form.IFormComponent, org.apache.tapestry.form.ValidationMessages, java.lang.Object)
	 */
	public void validate(IFormComponent field, ValidationMessages messages, Object object) throws ValidatorException {
		
	}

	/**
	 * @see org.apache.tapestry.form.validator.BaseValidator#renderContribution(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, org.apache.tapestry.form.FormComponentContributorContext, org.apache.tapestry.form.IFormComponent)
	 */
	@Override
	public void renderContribution(IMarkupWriter writer, IRequestCycle cycle, FormComponentContributorContext context, IFormComponent field) {
		context.addInitializationScript(field,
		"dojo.require(\"corner.validate.web\");");

		JSONObject profile = context.getProfile();
		
		if (!profile.has(ValidationConstants.CONSTRAINTS)) {
			profile.put(ValidationConstants.CONSTRAINTS, new JSONObject());
		}
		JSONObject cons = profile
				.getJSONObject(ValidationConstants.CONSTRAINTS);
		
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(context
				.getLocale());
		
		accumulateProperty(cons, field.getClientId(), new JSONLiteral(
				"[corner.validate.isPictureSize,{" + "\"width\":"+ picSize[0] + "," 
				+ "\"height\":"+ picSize[1] + "," + "decimal:" 
				+ JSONObject.quote(symbols.getDecimalSeparator())
						+ "}]"));
		
		accumulateProfileProperty(field, profile,
				ValidationConstants.CONSTRAINTS, buildMessage(context,field));
	}
	
	/**
	 * @param pictureSize
	 */
	public void setPictureSize(String pictureSize) {
		//去掉大括号
		pictureSize = pictureSize.substring(1,
				pictureSize.length() - 1);
		
		picSize = pictureSize.split(":");
	}
	
	/**
	 * 构建message
	 * 
	 * @return 返回显示信息
	 */
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		return messages.formatValidationMessage("图像尺寸：图像尺寸过大！你只能上传尺寸为 {0}×{1}的图像，请重新浏览图片！.", null,
				new Object[] { picSize[0],picSize[1] });
	}
	
	
}
