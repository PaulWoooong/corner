//==============================================================================
// file :       $Id: CornerPageTestCase.java 3677 2007-11-14 04:36:40Z jcai $
// project:     corner
//
// last change: date:       $Date: 2007-11-14 12:36:40 +0800 (星期三, 14 十一月 2007) $
//              by:         $Author: jcai $
//              revision:   $Revision: 3677 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.page;

import org.apache.hivemind.Registry;
import org.apache.hivemind.lib.SpringBeanFactoryHolder;
import org.apache.tapestry.BaseComponentTestCase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import corner.service.EntityService;

/**
 * @author Jun Tsai
 * @version $Revision: 3677 $
 * @since 2.2.1
 */
public class CornerPageTestCase extends BaseComponentTestCase {
	protected Registry reg;
	protected EntityService entityService;
	@BeforeMethod
	public void buildFrameRegistry() throws Exception{
		reg = buildFrameworkRegistry(new String[]{});
        SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
    	
	}
	@AfterMethod
	public void closeRegistry(){
		reg.shutdown();
	}
}
