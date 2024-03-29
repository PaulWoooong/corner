package corner.demo.model.one;

import org.testng.annotations.Test;

import corner.demo.model.BaseModelTestCase;


/**
 * 单表尸体A的单元测试
 * 
 * @author <a href=mailto:ghostbb88@gmail.com>Ghostbb</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class ATest extends BaseModelTestCase {
	
	/**
	 * 这里之测试了保存
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
