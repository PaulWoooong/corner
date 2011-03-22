//==============================================================================
// file :       $Id$
// project:     corner2.5
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.calendar.coolest2;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.InjectScript;
import org.apache.tapestry.annotations.Parameter;
import corner.orm.tapestry.component.textfield.TextField;
import org.apache.tapestry.web.WebRequest;

/**
 * 日期组件第二版
 * @author lsq
 * @version $Revision$
 * @since 2.5.2
 */
public abstract class CoolestCalendar2 extends TextField {

	@InjectScript("CoolestCalendar2.script")
	public abstract IScript getScript();
	
	@InjectObject("infrastructure:request")
	public abstract WebRequest getWebRequest();
	
	@Override
	protected void renderFormComponent(IMarkupWriter writer, IRequestCycle cycle) {
		super.renderFormComponent(writer, cycle);
		PageRenderSupport pageRenderSupport = TapestryUtils
				.getPageRenderSupport(cycle, this);

		Map<String, Object> scriptParms = new HashMap<String, Object>();

		Locale locale = this.getPage().getLocale();
		
		StringBuffer lang = new StringBuffer(locale.getLanguage());
		
		if(locale.getCountry() != null && locale.getCountry().length() != 0){
			lang.append("-").append(locale.getCountry().toLowerCase());
		}
		
		String language = "cn";
		
		if(lang.toString().equals("zh-tw")){
			language = "cn";
		}else if(lang.toString().equals("zh-cn")){
			language = "cn";
		}else {
			language = "en";
		}
		
		//增加js语言文件
		scriptParms.put("language", language);

		// 增加环境条件
		scriptParms.put("inputField",getInputField());
		scriptParms.put("dateFormat",  getDateFormat());
		scriptParms.put("showTime",  getShowTime());
		scriptParms.put("min", getMin());
		scriptParms.put("max", getMax());
		scriptParms.put("weekNumbers", getWeekNumbers());
		scriptParms.put("onSelectFunc", getOnSelectFunc());
		scriptParms.put("minuteStep", getMinuteStep());
		
		getScript().execute(this, cycle, pageRenderSupport, scriptParms);
	}
	
	/**
	 * 加入自动完成关闭 标签 autocomplete="off"
	 * @see org.apache.tapestry.AbstractComponent#renderInformalParameters(org.apache.tapestry.IMarkupWriter, org.apache.tapestry.IRequestCycle)
	 */
	protected void renderInformalParameters(IMarkupWriter writer, IRequestCycle cycle)
    {
		writer.attribute("autocomplete", "off");
        super.renderInformalParameters(writer, cycle);
    }
	
	/**
	 * 显示
	 */
	@Parameter(defaultValue = "ognl: this.getClientId()")
	public abstract String getInputField();
	
	/**
	 * 返回到DIV上的时间格式
	 */
	@Parameter(defaultValue = "literal:%Y-%m-%d")
	public abstract String getDateFormat();
	
	/**
	 * 是否显示时间
	 * false:不显示;
	 * true:显示24小时格式 如:14:00
	 * 12:显示12小时格式   如:02:00 PM
	 */
	@Parameter(defaultValue = "ognl:false")
	public abstract String getShowTime();
	
	@Parameter(defaultValue="literal:19000101")
	public abstract String getMin();
	
	@Parameter(defaultValue="literal:20991231")
	public abstract String getMax();
	
	@Parameter(defaultValue="ognl:false")
	public abstract String getWeekNumbers();
	
	@Parameter(defaultValue="literal:\"\"")
	public abstract String getOnSelectFunc();
	
	@Parameter(defaultValue="literal:1")
	public abstract String getMinuteStep();
}	
