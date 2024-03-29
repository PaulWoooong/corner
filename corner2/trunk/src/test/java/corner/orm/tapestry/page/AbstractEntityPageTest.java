//==============================================================================
// file :       $Id: AbstractEntityPageTest.java 3677 2007-11-14 04:36:40Z jcai $
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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.tapestry.BaseComponentTestCase;
import org.apache.tapestry.request.IUploadFile;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.demo.model.one.A;
import corner.orm.spring.SpringContainer;
import corner.service.EntityService;

/**
 * @author jcai
 * @version $Revision: 3677 $
 * @since 2.2.2
 */
public class AbstractEntityPageTest extends BaseComponentTestCase{

	@Test
	public void test_saveLob(){
		IUploadFile file=newMock(IUploadFile.class);
		EntityService entityService=(EntityService) SpringContainer.getInstance().getApplicationContext().getBean("entityService");
		AbstractEntityPage page=(AbstractEntityPage) newInstance(AbstractEntityPage.class,new Object[]{"uploadFile",file,"entityService",entityService});
		InputStream stream=new ByteArrayInputStream("test".getBytes());
		
		EasyMock.expect(file.getStream()).andReturn(stream);
		EasyMock.expect(file.getContentType()).andReturn("image/jpeg");
		EasyMock.expect(file.getFileName()).andReturn("filename");
		replay();
		A a=new A();
		page.saveBlobData(a, true);
		verify();
	}
	@Test
	public void test_deleteLobUsingIfNullDelete(){
		EntityService entityService=(EntityService) SpringContainer.getInstance().getApplicationContext().getBean("entityService");
		AbstractEntityPage page=(AbstractEntityPage) newInstance(AbstractEntityPage.class,new Object[]{"entityService",entityService});
		
		
		replay();
		
		A a=new A();
		entityService.saveEntity(a);
		page.saveBlobData(a, true);
		
		assertNull(entityService.getEntity(A.class,a.getId()),null);
		verify();
	}
	@Test
	public void test_saveLobWithNullBlob(){
		EntityService entityService=(EntityService) SpringContainer.getInstance().getApplicationContext().getBean("entityService");
		AbstractEntityPage page=(AbstractEntityPage) newInstance(AbstractEntityPage.class,new Object[]{"entityService",entityService});
		
		
		replay();
		
		A a=new A();
		entityService.saveEntity(a);
		page.saveBlobData(a, false);
		
		assertNotNull(entityService.getEntity(A.class,a.getId()),null);
		verify();
	}
	@Test
	public void test_updateLobNullUsingIfNullDelete(){
		IUploadFile file=newMock(IUploadFile.class);
		EntityService entityService=(EntityService) SpringContainer.getInstance().getApplicationContext().getBean("entityService");
		AbstractEntityPage page=(AbstractEntityPage) newInstance(AbstractEntityPage.class,new Object[]{"uploadFile",file,"entityService",entityService});
		InputStream stream=new ByteArrayInputStream("test".getBytes());
		
		EasyMock.expect(file.getStream()).andReturn(stream);
		EasyMock.expect(file.getContentType()).andReturn("image/jpeg");
		EasyMock.expect(file.getFileName()).andReturn("filename");
		replay();
		A a=new A();
		page.saveBlobData(a, false);
		assertNotNull(entityService.getEntity(A.class,a.getId()),null);
		assertNotNull(entityService.getEntity(A.class,a.getId()).getBlobData(),null);
		
		verify();
		
		page=(AbstractEntityPage) newInstance(AbstractEntityPage.class,"entityService",entityService);
		replay();
		page.saveBlobData(a, false);
		assertNotNull(entityService.getEntity(A.class,a.getId()),null);
		assertNull(entityService.getEntity(A.class,a.getId()).getBlobData(),null);
	}
}
