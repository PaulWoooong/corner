// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: MatrixRowField.java 4520 2009-11-26 02:39:12Z lsq $
// created at:2006-10-19
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

package corner.orm.tapestry.component.matrix;

import org.apache.tapestry.BaseComponent;
import org.apache.tapestry.IForm;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.Component;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.components.Any;
import org.apache.tapestry.components.ForBean;
import org.apache.tapestry.form.IFormComponent;
import org.apache.tapestry.form.ValidatableField;
import org.apache.tapestry.form.ValidatableFieldSupport;
import org.apache.tapestry.form.ValidationMessages;
import org.apache.tapestry.form.ValidationMessagesImpl;
import org.apache.tapestry.form.translator.Translator;
import org.apache.tapestry.valid.IValidationDelegate;
import org.apache.tapestry.valid.ValidatorException;

import corner.orm.hibernate.v3.MatrixRow;

/**
 * 行组件，来产生组件.
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 4520 $
 * @since 2.2.2
 */
public abstract class MatrixRowField extends BaseComponent implements
		IFormComponent, ValidatableField {

	@Parameter(required = true)
	public abstract MatrixRow<Object> getValue();

	public abstract void setValue(MatrixRow<Object> value);

	@Parameter(required = true)
	public abstract MatrixRow<Object> getRefVector();

	@Parameter
	public abstract Object getDefaultValue();
	
	@Parameter(defaultValue="false")
	public abstract Boolean getOnlyRead();
	
	@Parameter(defaultValue = "translator:string")
	public abstract Translator getTranslator();
	
	/**
	 * 取得当前for循环的位置的索引
	 * @return int
	 */
	public abstract int getIndex();

	@Component(type = "MatrixRowTextField", bindings = { "displayName=displayName",
			"class=inputClass", "value=elementValue", "translator=translator","defaultValue=defaultValue","onlyRead=onlyRead" })
	public abstract MatrixRowTextField getElementTextField();
	
	@Component(type="For",bindings = {"source=refVector","value=tmpObj","index=index"})
	public abstract ForBean getForComponentField();
	
	@Component(type="Any",bindings={"class=tdClass"})
	public abstract Any getAnyComponentField();

	public abstract IForm getForm();

	public abstract void setForm(IForm form);

	public abstract String getName();

	public abstract void setName(String name);

//	@InjectScript("MatrixRowField.script")
//	public abstract IScript getScript();
	/**
	 * 得到循环元素的值.
	 * 
	 * @return
	 * @throws ValidatorException
	 *             当错误验证的时候.
	 */
	public Object getElementValue() throws ValidatorException {
		
		if (getValue().size() > getIndex()) {
			ValidationMessages messages = new ValidationMessagesImpl(this, this
					.getPage().getLocale());
			Object obj=getValue().get(getIndex());
			
			return obj==null?null:this.getTranslator().parse(this, messages,obj.toString());
			// return getValue().get(star++);
		} else {
			return null;
		}
	}
	
	public void setElementValue(Object value) {
		if (this.getPage().getRequestCycle().isRewinding()) {
			if (getIndex() == 0) {
				setValue(new MatrixRow<Object>());
			}
			getValue().add(getIndex(), value);
		}
	}

	/**
	 * 是否为第一次增加。
	 * 
	 * @return
	 */
	public boolean isFirstNew() {
		return this.getRefVector() == null || this.getRefVector().size() == 0;
	}

	/**
	 * 
	 * @see org.apache.tapestry.AbstractComponent#prepareForRender(org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void prepareForRender(IRequestCycle arg0) {
		if (this.getValue() == null) {
			setValue(new MatrixRow<Object>());
		}
	}

	/**
	 * @see org.apache.tapestry.BaseComponent#renderComponent(org.apache.tapestry.IMarkupWriter,
	 *      org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		IForm form = TapestryUtils.getForm(cycle, this);
		this.setForm(form);
		
		if (form.wasPrerendered(writer, this))
			return;

		IValidationDelegate delegate = form.getDelegate();

		delegate.setFormComponent(this);
		
		form.getElementId(this);
		
		form.getDelegate().writePrefix(writer, cycle, this, null);
		super.renderComponent(writer, cycle);
		
		if (form.isRewinding()) {

			try {

				getValidatableFieldSupport().validate(this, writer, cycle,
						((MatrixRow<Object>)this.getValue()).getRowSum());

			} catch (ValidatorException e) {
				getForm().getDelegate().record(e);
			}
		} else {
			
//			getValidatableFieldSupport().renderContributions(this, writer,
//					cycle);
//			
//			PageRenderSupport prs = TapestryUtils.getPageRenderSupport(cycle, this);
//			HashMap parms = new HashMap();
//			parms.put("len",this.getRefVector().size());
//			parms.put("fieldName",this.getClientId());
//			getScript().execute(this, cycle, prs, parms);

		}
		
	}

	/**
	 * Injected.
	 */
	public abstract ValidatableFieldSupport getValidatableFieldSupport();

}
