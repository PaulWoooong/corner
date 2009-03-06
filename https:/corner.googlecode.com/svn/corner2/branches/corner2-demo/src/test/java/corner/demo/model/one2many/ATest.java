//==============================================================================
//file :        ATest.java
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
public class ATest extends BaseModelTestCase{

	/**
	 * one2many中A实体的单元测试，这里只测试了保存方法
	 */
	@Test(groups="models")
	public void testA(){
		A a = new A();
		a.setLoginName("Ghost");
		this.saveOrUpdate(a);
		this.flushAndClearSession();
		
		String aId = a.getId();
		a = null;
		
		a = this.load(A.class, aId);
		assertEquals(a.getLoginName(), "Ghost");
	}
}
