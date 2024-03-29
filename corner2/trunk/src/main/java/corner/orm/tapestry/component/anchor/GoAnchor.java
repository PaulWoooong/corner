// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: GoAnchor.java 3919 2008-01-16 04:36:52Z xf $
// created at:2006-10-13
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

package corner.orm.tapestry.component.anchor;

import java.util.HashMap;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.AbstractComponent;
import org.apache.tapestry.IMarkupWriter;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.IScript;
import org.apache.tapestry.PageRenderSupport;
import org.apache.tapestry.TapestryUtils;

/**
 * 提供了一个自动跳到Anchor的组件.
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 3919 $
 * @since 2.2.2
 */
public abstract class GoAnchor extends AbstractComponent {

	/**
     * Injected
     * 
     * 
     */
    public abstract IScript getScript();
	@Override
	protected void renderComponent(IMarkupWriter writer, IRequestCycle cycle) {
		PageRenderSupport pageRenderSupport = TapestryUtils.getPageRenderSupport(cycle, this);
		
		getScript().execute(this,cycle,pageRenderSupport,new HashMap());
		
		String element = getElement();

        if (element == null)
            throw new ApplicationRuntimeException("not defined!", this,
                    null, null);

        boolean rewinding = cycle.isRewinding();

        if (!rewinding)
        {
            writer.begin(element);
            
            
            
            StringBuffer sb=new StringBuffer();
            
            sb.append("return checkScrollNecessary(this);");
            writer.attribute("onClick",sb.toString());
            
//            writer.attribute("style","cursor=hand");
            
            renderInformalParameters(writer, cycle);
        }

        renderBody(writer, cycle);

        if (!rewinding)
        {
            writer.end(element);
        }

        
		
	}
	/**
	 * 生成的元素
	 * @return 元素的名字
	 */
	public abstract String getElement();
	/**
	 * 需要转向的锚点.
	 * @return 锚点名字.
	 */
	public abstract String getAnchorName();
	
}
