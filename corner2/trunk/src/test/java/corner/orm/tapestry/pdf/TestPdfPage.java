/*		
 * Copyright 2006-2007 The Beijing Maxinfo Technology Ltd. 
 * site:http://www.bjmaxinfo.com
 *	file : $Id: TestPdfPage.java 3677 2007-11-14 04:36:40Z jcai $
 *	created at:2007-4-12
 */



package corner.orm.tapestry.pdf;

import org.apache.tapestry.IPage;

import corner.demo.model.pdf.A;
import corner.orm.tapestry.page.AbstractEntityFormPage;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision: 3677 $
 * @since 2.3.7
 */
public abstract class TestPdfPage extends AbstractEntityFormPage<A>{

	private A c=null;
	public Object getEntityObject(){
		if(c == null ){
			c=new A();
			c.setCnName("中文名称");
			c.setName("JunTsai");
			this.getEntityService().saveEntity(c);
		}
		return c;
	}
	
	public IPage viewPdf(Object entity){
		IPage page=this.getRequestCycle().getPage("pdf:classpath:test.pdf");
		((PdfEntityPage) page).setEntity(entity);
		return page;
	}
}
