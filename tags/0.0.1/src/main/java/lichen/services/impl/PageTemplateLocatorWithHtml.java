/* 
 * Copyright 2008 The Lichen Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lichen.services.impl;

import static java.lang.String.format;

import java.util.Locale;

import org.apache.tapestry5.internal.services.PageTemplateLocator;
import org.apache.tapestry5.ioc.Resource;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.model.ComponentModel;
import org.apache.tapestry5.services.ComponentClassResolver;

/**
 * 针对在context中能够寻找html作为模板。
 * @author <a href="jun.tsai@ouriba.com">Jun Tsai</a>
 * @version $Revision$
 * @since 0.0.1
 */
public class PageTemplateLocatorWithHtml implements PageTemplateLocator {
	/**
	 * HTML模板文件的后缀
	 */
	private static final String HTML_TEMPLATE_EXTENSION = "html";

	private final Resource contextRoot;

    private final ComponentClassResolver resolver;

    public PageTemplateLocatorWithHtml(Resource contextRoot, ComponentClassResolver resolver)
    {
        this.contextRoot = contextRoot;
        this.resolver = resolver;
    }

    /**
	 * @see org.apache.tapestry5.internal.services.PageTemplateLocator#findPageTemplateResource(org.apache.tapestry5.model.ComponentModel, java.util.Locale)
	 */
	@Override
	public Resource findPageTemplateResource(ComponentModel model, Locale locale) {
       String className = model.getComponentClassName();

        // A bit of a hack, but should work.

        if (!className.contains(".pages.")) return null;

        String logicalName = resolver.resolvePageClassNameToPageName(className);

        int slashx = logicalName.lastIndexOf('/');

        if (slashx > 0)
        {
            // However, the logical name isn't quite what we want. It may have been somewhat
            // trimmed.

            String simpleClassName = InternalUtils.lastTerm(className);

            logicalName = logicalName.substring(0, slashx + 1) + simpleClassName;
        }

        String path = format("%s.%s", logicalName, HTML_TEMPLATE_EXTENSION);

        return contextRoot.forFile(path).forLocale(locale);
    }
	
}
