//==============================================================================
//file :        BTest.java
//
//last change:	date:       $Date$
//           	by:         $Author$
//           	revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//==============================================================================

package corner.demo.model.one2many;

import org.testng.annotations.Test;

import corner.demo.model.BaseModelTestCase;

/**
 * @author <a href=mailto:ghostbb88@gmail.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class BTest extends BaseModelTestCase {

	/**
	 * one2many中B实体的单元测试，这里只测试了保存方法
	 */
	@Test(groups="models")
	public void testB(){
		A a = new A();
		a.setLoginName("Ghost");
		this.saveOrUpdate(a);
		
		B b = new B();
		b.setLoginName("Ghostbb");
		b.setA(a);
		this.saveOrUpdate(b);
		
		this.flushAndClearSession();
		
		String bId = b.getId();
		
		b = null;
		a = null;
		
		b = this.load(B.class, bId);
		
		assertEquals(b.getLoginName(), "Ghostbb");
		assertEquals(b.getA().getLoginName(), "Ghost");
		
	}
}
