// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: PersonID.java 3678 2007-11-14 04:43:52Z jcai $
// created at:2006-10-31
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

import corner.util.CardIDChecker;

/**
 * 验证公民身份证号码是否正确.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author xiafei
 * @version $Revision: 3678 $
 * @since 2.3
 */
public class PersonID extends BaseValidator{

	public PersonID() {
		super();
	}

	public PersonID(String initializer) {
		super(initializer);
	}

	/**
	 * 
	 * @see org.apache.tapestry.form.validator.Validator#validate(org.apache.tapestry.form.IFormComponent, org.apache.tapestry.form.ValidationMessages, java.lang.Object)
	 */
	public void validate(IFormComponent field, ValidationMessages messages, Object object) throws ValidatorException {
		if(object==null){
			return;
		}
		if(CardIDChecker.validate(object.toString())){
			return;
		}
		
		throw new ValidatorException(buildMessage(messages,field));
		
	}
	//构建message
	private String buildMessage(ValidationMessages messages,
			IFormComponent field) {
		return messages.formatValidationMessage("{0}不正确.格式为15／18位身份证号码.",
				null, new Object[] { field
						.getDisplayName() });
	}
	/**
	 * 插入js脚本
	 * @see org.apache.tapestry.form.validator.BaseValidator#renderContribution(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle, org.apache.tapestry.form.FormComponentContributorContext, org.apache.tapestry.form.IFormComponent)
	 */
	public void renderContribution(IMarkupWriter writer, IRequestCycle cycle,
			FormComponentContributorContext context, IFormComponent field) {
		context.addInitializationScript(field, "dojo.require(\"corner.validate.web\");");
        
        JSONObject profile = context.getProfile();
        
        if (!profile.has(ValidationConstants.CONSTRAINTS)) {
            profile.put(ValidationConstants.CONSTRAINTS, new JSONObject());
        }
        JSONObject cons = profile.getJSONObject(ValidationConstants.CONSTRAINTS);
        
        accumulateProperty(cons, field.getClientId(),
                new JSONLiteral("[corner.validate.isPersonID,false,true]"));
        
        accumulateProfileProperty(field, profile, 
                ValidationConstants.CONSTRAINTS, buildMessage(context, field));
	}
	
	
}
