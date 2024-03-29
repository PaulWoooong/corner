//==============================================================================
// file :       $Id: RelativeEntityListPageTest.java 3677 2007-11-14 04:36:40Z jcai $
// project:     corner
//
// last change: date:       $Date: 2007-11-14 12:36:40 +0800 (星期三, 14 十一月 2007) $
//              by:         $Author: jcai $
//              revision:   $Revision: 3677 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.page.relative;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.contrib.table.model.IBasicTableModel;
import org.apache.tapestry.engine.BaseEngine;
import org.easymock.EasyMock;
import org.testng.annotations.Test;

import corner.demo.model.one2many.A;
import corner.demo.model.one2many.B;
import corner.orm.tapestry.page.CornerPageTestCase;
import corner.orm.tapestry.page.relative.support.RelativeObjectOperator;

/**
 * @author jcai
 * @version $Revision: 3677 $
 * @since 2.2.1
 */
public class RelativeEntityListPageTest extends CornerPageTestCase {
	@SuppressWarnings("unchecked")
	@Test
	public void testGetSource(){
		A a=new A();
		entityService.saveEntity(a);
		
		IRequestCycle cycle=newCycle();
		AbstractRelativeEntityListPage<A,B> entityListPage=(AbstractRelativeEntityListPage<A, B>) newInstance(AbstractRelativeEntityListPage.class,"entityService",entityService);
		entityListPage.setRootedObject(a);
		EasyMock.expect(cycle.isRewinding()).andReturn(false);
		
		replay();
		entityListPage.attach(new BaseEngine(),cycle);
		IBasicTableModel source=entityListPage.getSource("bs");
		
		assertEquals(0,source.getRowCount());
		verify();
		entityService.deleteEntities(a);
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testGetSourceWithoutRelativeNameExcpetion(){
		
		AbstractRelativeEntityListPage<A,B> entityListPage=(AbstractRelativeEntityListPage<A, B>) newInstance(AbstractRelativeEntityListPage.class,"entityService",entityService);
		try{
			entityListPage.getSource();
			fail("should be throw exception");
		}catch(IllegalStateException e){
			//go there
		}
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testDoEditEntityAction(){
		A a=new A();
		entityService.saveEntity(a);
		B b=new B();
		b.setA(a);
		entityService.saveEntity(b);
		
		IRequestCycle cycle=newCycle();
		RelativeObjectOperator operator=new RelativeObjectOperator();
		operator.setRequestCycle(cycle);
		AbstractRelativeEntityListPage<A,B> entityListPage=(AbstractRelativeEntityListPage<A, B>) newInstance(AbstractRelativeEntityListPage.class,"pageName","BList","entityService",entityService,"relativeObjectOperator",operator);
		entityListPage.setRootedObject(a);
		
		AbstractRelativeEntityFormPage entityFormPage=(AbstractRelativeEntityFormPage) newInstance(AbstractRelativeEntityFormPage.class);
		EasyMock.expect(cycle.getPage("BForm")).andReturn(entityFormPage);
		replay();
		entityListPage.attach(new BaseEngine(),cycle);
		
		entityFormPage=(AbstractRelativeEntityFormPage) entityListPage.doEditEntityAction(b);
		assertEquals(a,entityFormPage.getRootedObject());
		assertEquals(b,entityFormPage.getEntity());
		verify();
		entityService.deleteEntities(a);
		
	}
	@SuppressWarnings("unchecked")
	@Test
	public void testDoNewEntityAction(){
		A a=new A();
		entityService.saveEntity(a);
		B b=new B();
		b.setA(a);
		entityService.saveEntity(b);
		
		IRequestCycle cycle=newCycle();
		RelativeObjectOperator operator=new RelativeObjectOperator();
		operator.setRequestCycle(cycle);
		AbstractRelativeEntityListPage<A,B> entityListPage=(AbstractRelativeEntityListPage<A, B>) newInstance(AbstractRelativeEntityListPage.class,"pageName","BList","entityService",entityService,"relativeObjectOperator",operator);
		entityListPage.setRootedObject(a);
		
		AbstractRelativeEntityFormPage entityFormPage=(AbstractRelativeEntityFormPage) newInstance(AbstractRelativeEntityFormPage.class);
		EasyMock.expect(cycle.getPage("BForm")).andReturn(entityFormPage);
		replay();
		entityListPage.attach(new BaseEngine(),cycle);
		
		entityFormPage=(AbstractRelativeEntityFormPage) entityListPage.doNewEntityAction();
		assertEquals(a,entityFormPage.getRootedObject());
		
		verify();
		entityService.deleteEntities(a);
		
	}
}
