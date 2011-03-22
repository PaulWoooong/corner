//==============================================================================
// file :       $Id: TestModule.java 3418 2007-09-20 09:20:32Z jcai $
// project:     corner
//
// last change: date:       $Date: 2007-09-20 17:20:32 +0800 (星期四, 20 九月 2007) $
//              by:         $Author: jcai $
//              revision:   $Revision: 3418 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import corner.demo.model.one.A;

/**
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 3418 $
 * @since 2.3.7
 */
public class TestModule extends AbstractModule {

	/**
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		bind(A.class).in(Scopes.SINGLETON);

	}

}
