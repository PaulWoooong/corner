//==============================================================================
// file :       $Id: OsivTest.java 3677 2007-11-14 04:36:40Z jcai $
// project:     corner
//
// last change: date:       $Date: 2007-11-14 12:36:40 +0800 (星期三, 14 十一月 2007) $
//              by:         $Author: jcai $
//              revision:   $Revision: 3677 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.filter;

import java.io.IOException;

import org.apache.hivemind.Registry;
import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.services.WebRequestServicer;
import org.apache.tapestry.services.WebRequestServicerFilter;
import org.apache.tapestry.web.WebRequest;
import org.apache.tapestry.web.WebResponse;
import org.easymock.EasyMock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
@Test
public class OsivTest extends BaseComponentTestCase{
	Registry reg =null;
	@BeforeMethod
	public void startHivemind() throws Exception{
		 reg = buildFrameworkRegistry(new String[]{});
	}
	@Test
	public void testOsiv() throws IOException{
		WebRequest request = newRequest();
		EasyMock.expect(request.getActivationPath()).andReturn("/");
		
		WebResponse response = newResponse();
        WebRequestServicer servicer = newServicer();
        

        servicer.service(request, response);
        

        replay();

        WebRequestServicerFilter f = (WebRequestServicerFilter) reg.getService("corner.osiv.CornerOneSessionPerQuestFilter", org.apache.tapestry.services.WebRequestServicerFilter.class);
        
        
        f.service(request, response, servicer);

        verify();
        reg.cleanupThread();
        reg.shutdown();
	}
	private WebResponse newResponse()
    {
        return newMock(WebResponse.class);
    }

    private WebRequestServicer newServicer()
    {
        return newMock(WebRequestServicer.class);
    }

   
}
