// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: BaseAutocompleter.java 4527 2009-12-25 10:04:19Z xf $
// created at:2007-07-06
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

package corner.orm.tapestry.component.prototype.autocompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tapestry.IAsset;
import org.apache.tapestry.IDirect;
import org.apache.tapestry.IForm;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.Asset;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.DirectServiceParameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.form.AbstractFormComponent;
import org.apache.tapestry.form.TranslatedField;
import org.apache.tapestry.form.TranslatedFieldSupport;
import org.apache.tapestry.form.ValidatableFieldSupport;
import org.apache.tapestry.services.DataSqueezer;
import org.apache.tapestry.services.ResponseBuilder;
import org.apache.tapestry.valid.ValidatorException;

import corner.orm.tapestry.state.IContext;
import corner.orm.tapestry.state.IContextAccessible;
import corner.service.EntityService;
import corner.util.StringUtils;

/**
 * 基础的Autocompleter，父类
 * 
 * @author <a href=mailto:xf@bjmaxinfo.com>xiafei</a>
 * @author <a href="mailto:renais@bjmaxinfo.com">renais</a>
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision: 4527 $
 * @since 2.3.7
 */
public abstract class BaseAutocompleter extends AbstractFormComponent implements
		IDirect, TranslatedField,  IContextAccessible {
	/** logger */
	private static final Log log = LogFactory.getLog(BaseAutocompleter.class);
    /**
     * Injected response builder for doing specific XHR things.
     *
     * @return ResponseBuilder for this request. 
     */
    public abstract ResponseBuilder getResponse();
	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#renderComponent(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
//		 render search triggered response instead of normal render if
        // listener was invoked

        IForm form = TapestryUtils.getForm(cycle, this);
        setForm(form);

        if (form.wasPrerendered(writer, this))
            return;

        if (!form.isRewinding() && !cycle.isRewinding()
            && getResponse().isDynamic() && isSearchTriggered())
        {
            setName(form);

            // do nothing if it wasn't for this instance - such as in a loop

            if (cycle.getParameter(getClientId()) == null)
                return;

            renderPrototypeComponent(writer, cycle);
            return;
        }
		super.renderComponent(writer, cycle);
	}

	//对Prototype进行渲染
	void renderPrototypeComponent(IMarkupWriter writer,
			IRequestCycle cycle) {

		// initValue();
		// 对选择器进行额外赋值
		ISelectModel model = constructSelectModel();
		
		model.setComponent(this);
		
		// 查询
		List list = model.search(getEntityService(), this.getQueryClassName(), this
				.getSearchString(), ((IContext) this.getContext()), this.getDataSqueezer(),this.getDependFieldsValue());

		// 迭代器
		Iterator values = list.iterator();

		// Write values out as simple strings
		writer.begin("ul");

		while (values.hasNext()) {
			Object value = values.next();
			if (value == null)
				continue;

			writer.begin("li");
			model.renderResultRow(writer, value, this.getTemplate(), this
					.getDataSqueezer());
			writer.end("li");
		}
		writer.end();

	}

	protected ISelectModel constructSelectModel() {
		throw new UnsupportedOperationException("未能实现该方法");

	}

	/**
	 * Renders the component.
	 */
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		log.debug("renderFormComponent() cycle rewinding?:"
				+ cycle.isRewinding());
		
		String value = null;
		if (this.getDefaultValue() == null || StringUtils.blank(this.getDefaultValue().toString())) {
			value = getTranslatedFieldSupport().format(this, formatValue(getValue()));
		} else {
			if (this.getValue() != null) {
				value = getTranslatedFieldSupport().format(this, formatValue(getValue()));
			} else {
				value = getTranslatedFieldSupport().format(this, this.getDefaultValue());
			}
		}
		
		boolean isTextarea = "textarea".equalsIgnoreCase(getTemplateTag());
		boolean disabled = isDisabled();

		renderDelegatePrefix(writer, cycle);

		if (isTextarea) {
			writer.begin("textarea");
		} else {
			writer.beginEmpty("input");
			writer.attribute("type", "text");
			writer.attribute("autocomplete", "off");
			writer.attribute("value", value);
		}

		writer.attribute("name", getName());

		if (disabled)
			writer.attribute("disabled", "disabled");

		renderIdAttribute(writer, cycle);

		renderDelegateAttributes(writer, cycle);

		getTranslatedFieldSupport().renderContributions(this, writer, cycle);
		getValidatableFieldSupport().renderContributions(this, writer, cycle);

		renderInformalParameters(writer, cycle);

		renderDelegateSuffix(writer, cycle);

		if (isTextarea) {
			writer.print(value);
			writer.end();
		}

		// 增加其他的附加字段的生成

		appendField(writer, getValue());
		// Now insert our javascript
		// Setup script parameters

		Map<String, Object> scriptParms = new HashMap<String, Object>();

		ILink link = getLink();

		scriptParms.put("updateUrl", link.getURL());
		scriptParms.put("inputId", getClientId());
		scriptParms.put("updateId", getClientId() + "complete");
		scriptParms.put("elementclass", getElementClass());
		if (this.getOptions() != null) {
			scriptParms.put("options", this.getOptions());
		}
		if (this.getDependFields() != null) {
			scriptParms.put("dependFields", this.getDependFields());
		}
		scriptParms.put("indicator_pic", getIndicatorAsset().buildURL());

		PageRenderSupport pageRenderSupport = TapestryUtils
				.getPageRenderSupport(cycle, this);
		getScript().execute(this, cycle, pageRenderSupport, scriptParms);
	}

	/**
	 * @return
	 */
	public ILink getLink() {
		return getDirectService().getLink(true,
				new DirectServiceParameter(this));
	}
	protected Object formatValue(Object value) {
		return value;
	}

	protected void appendField(IMarkupWriter writer, Object value) {
		// do nothinng
	}

	/**
	 * Rewinds the component, doing translation, validation and binding.
	 */
	protected void rewindFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		String value = cycle.getParameter(getName());
		try {
			Object object = getTranslatedFieldSupport().parse(this, value);
			getValidatableFieldSupport().validate(this, writer, cycle, object);
			setValue(object);
		} catch (ValidatorException e) {
			getForm().getDelegate().record(e);
		}
	}

	/**
	 * @see org.apache.tapestry.form.AbstractFormComponent#isRequired()
	 */
	public boolean isRequired() {
		return getValidatableFieldSupport().isRequired(this);
	}

	/**
	 * @see org.apache.tapestry.IDynamicInvoker#getUpdateComponents()
	 */
	public List getUpdateComponents() {
		List<String> comps = new ArrayList<String>();
		comps.add(getId());
		return comps;
	}

	/**
	 * @see org.apache.tapestry.IDynamicInvoker#isAsync()
	 */
	public boolean isAsync() {
		return true;
	}

	/**
	 * @see org.apache.tapestry.IDynamicInvoker#isJson()
	 */
	public boolean isJson() {
		return false;
	}
	/**
	 * 
	 * @see org.apache.tapestry.AbstractComponent#isStateful()
	 */
	@Parameter(defaultValue = "literal:true")
	public abstract boolean isStateful();
	
	/**
	 * @see org.apache.tapestry.IDirect#trigger(org.apache.tapestry.IRequestCycle)
	 */
	public void trigger(IRequestCycle cycle) {
		this.setSearchString(cycle.getParameter(this.getId()));
		if (this.getDependFields() != null) {
			String[] fs = this.getDependFields().split(",");
			String[] dependFieldsValue=new String[fs.length];
			for(int i=0;i<fs.length;i++){
				dependFieldsValue[i]=cycle.getParameter(fs[i]);
			}
			this.setDependFieldsValue(dependFieldsValue);
		}
		setSearchTriggered(true);
	}

	/** 更新的值 * */
	@Parameter(required = true)
	public abstract Object getValue();

	public abstract void setValue(Object entity);
	
	/**
	 * 取得默认值
	 * 
	 * @return
	 */
	@Parameter
	public abstract Object getDefaultValue();

	/** 待查询的类名 * */
	@Parameter(required = true)
	public abstract String getQueryClassName();

	/** 弹开层的css类 * */
	@Parameter(defaultValue = "literal:auto_complete")
	public abstract String getElementClass();

	/**
	 * 依赖的字段
	 * 
	 * @return 依赖字段
	 */
	@Parameter
	public abstract String getDependFields();
	@Parameter
	public abstract Object getParameters();
	
	/**
	 * 用来展示的每行数据的模板
	 * 
	 * @return 展示每行数据的模板
	 */
	public abstract String getTemplate();

	/**
	 * options 对自动完成的一些option的定义
	 */
	public abstract String getOptions();

	/** template tag */
	@Parameter
	public abstract String getTemplateTag();

	/**
	 * Injected.
	 */
	@InjectObject("service:tapestry.form.ValidatableFieldSupport")
	public abstract ValidatableFieldSupport getValidatableFieldSupport();

	/**
	 * Injected.
	 */
	@InjectObject("service:tapestry.form.TranslatedFieldSupport")
	public abstract TranslatedFieldSupport getTranslatedFieldSupport();

	/**
	 * Injected.
	 */
	@InjectObject("service:tapestry.services.Direct")
	public abstract IEngineService getDirectService();

	/**
	 * Script
	 */
	@InjectScript("/corner/orm/tapestry/component/prototype/autocompleter/Autocompleter.script")
	public abstract IScript getScript();

	@Asset("classpath:/corner/orm/tapestry/component/prototype/autocompleter/indicator.gif")
	public abstract IAsset getIndicatorAsset();

	@InjectObject("spring:entityService")
	public abstract EntityService getEntityService();

	@InjectObject("service:tapestry.data.DataSqueezer")
	public abstract DataSqueezer getDataSqueezer();

	// 记录前端输入的搜索字符串
	public abstract String getSearchString();

	public abstract void setSearchString(String searchStr);
	
	//记录依赖字段的字符串的值
	public abstract void setDependFieldsValue(String [] values);
	public abstract String [] getDependFieldsValue();
	
    public abstract boolean isSearchTriggered();
    public abstract void setSearchTriggered(boolean value);

}