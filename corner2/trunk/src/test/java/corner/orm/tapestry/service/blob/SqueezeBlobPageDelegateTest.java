//==============================================================================
// file :       $Id: SqueezeBlobPageDelegateTest.java 3389 2007-09-13 06:11:19Z ghostbb $
// project:     corner
//
// last change: date:       $Date: 2007-09-13 14:11:19 +0800 (星期四, 13 九月 2007) $
//              by:         $Author: ghostbb $
//              revision:   $Revision: 3389 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.service.blob;

import java.io.ByteArrayInputStream;

import org.apache.hivemind.Registry;
import org.apache.hivemind.lib.SpringBeanFactoryHolder;
import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.request.IUploadFile;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.demo.model.one.A;
import corner.service.EntityService;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 3389 $
 * @since 2.2.1
 */
public class SqueezeBlobPageDelegateTest extends BaseComponentTestCase {
	@Test
	public void testSaveBlob() throws Exception{
		IUploadFile file=EasyMock.createMock(IUploadFile.class);
		EasyMock.expect(file.getStream()).andReturn(new ByteArrayInputStream("string".getBytes()));
		EasyMock.expect(file.getContentType()).andReturn("text/plain");
		EasyMock.expect(file.getFileName()).andReturn("filename");
		
		final Registry reg = buildFrameworkRegistry(new String[]{});
		SpringBeanFactoryHolder spring=(SpringBeanFactoryHolder) reg.getService("hivemind.lib.DefaultSpringBeanFactoryHolder",SpringBeanFactoryHolder.class);
        EntityService entityService=(EntityService) spring.getBeanFactory().getBean("entityService");
        EasyMock.replay(file);
        
        
		IBlobPageDelegate<A> delegate = new SqueezeBlobPageDelegate<A>(
				A.class, file, new A(),entityService);
		delegate.save();
        
        EasyMock.verify(file);
        reg.cleanupThread();
        reg.shutdown();
	}
}
