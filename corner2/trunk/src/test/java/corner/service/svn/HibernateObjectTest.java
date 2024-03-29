//==============================================================================
// file :       $Id: HibernateObjectTest.java 3792 2007-12-17 06:16:25Z jetty $
// project:     corner
//
// last change: date:       $Date: 2007-12-17 14:16:25 +0800 (星期一, 17 十二月 2007) $
//              by:         $Author: jetty $
//              revision:   $Revision: 3792 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.service.svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.testng.annotations.Test;

import com.thoughtworks.xstream.core.util.Base64Encoder;

import corner.test.AbstractTestCase;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 3792 $
 * @since 2.5
 */
public class HibernateObjectTest extends AbstractTestCase{
	private static final Log logger = LogFactory.getLog(HibernateObjectTest.class);
	private static final Base64Encoder base64 = new Base64Encoder();
	
	@Test
	public void testDecodeBase64(){
		byte[] bytes = base64.decode("dGVzdA==");
		
		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		int ch = 0;
		
		try{
            while((ch = input.read()) != -1){
                out.write((char)ch);
            }
            input.close();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        assertEquals("test", new String(out.toByteArray()));
	}
	
	@Test
	public void testEncodeBase64(){
		this.startTransaction();
		Session session=this.getCurrentSession();
		
		final corner.demo.model.one.A a1=new corner.demo.model.one.A();
		a1.setName("Base 64 测试");
		a1.setBlobData("test".getBytes());
		session.saveOrUpdate(a1);
		this.commitTransaction();
		System.out.println(XStreamDelegate.toJSON(a1));
	}
	
	@Test
	public void testA(){
		logger.debug("----------------------对A的操作--------------");
		//保存A
		this.startTransaction();
		Session session=this.getCurrentSession();
		final corner.demo.model.one2many.A a1=new corner.demo.model.one2many.A();
		a1.setName("哈哈");
		System.out.println(XStreamDelegate.toJSON(a1));
		session.save(a1);
		this.commitTransaction();
		
		
		//保存B,同时增加关系
		this.startTransaction();
		session=this.getCurrentSession();
		corner.demo.model.one2many.A a = (corner.demo.model.one2many.A) session.load(corner.demo.model.one2many.A.class, a1.getId());
		
		 System.out.println(XStreamDelegate.toJSON(a));
        
		this.commitTransaction();
	}
}
