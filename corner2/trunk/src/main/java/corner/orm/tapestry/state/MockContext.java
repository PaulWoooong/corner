// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: MockContext.java 3678 2007-11-14 04:43:52Z jcai $
// created at:2007-02-02
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package corner.orm.tapestry.state;

import org.apache.tapestry.services.DataSqueezer;

/**
 * 一个mock的context
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 3678 $
 * @since 2.3.7
 */
public class MockContext implements IContext {

	public void setDataSqueezer(DataSqueezer squeezer) {
		//do nothing
		System.out.println(this);
	}
}
