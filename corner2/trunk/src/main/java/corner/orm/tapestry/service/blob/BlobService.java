// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: BlobService.java 3919 2008-01-16 04:36:52Z xf $
// created at:2006-08-26
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

package corner.orm.tapestry.service.blob;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.engine.IEngineService;

import corner.model.IBlobModel;

/**
 * 
 * 扩展tapestry的EngineService,提供了对blob的处理.
 * <p>
 * 提供了对blob的显示服务.
 * 
 * @author <a href="http://wiki.java.net/bin/view/People/JunTsai">Jun Tsai</a>
 * @version $Revision: 3919 $
 * @since 2006-1-19
 * @see IEngineService
 */
public class BlobService extends AbstractBlobService{
	private Map<String, IBlobProvider> LOB_PROVIDERS = new HashMap<String, IBlobProvider>();
	private Map<String,Class<? extends IBlobModel>> LOB_MODEL_CLASSES=new HashMap<String,Class<? extends IBlobModel>>();
//	static {
//		LOB_PROVIDER_CLAZZS.put("MiImageBlob", MiImageBlobProvider.class);
//		LOB_PROVIDER_CLAZZS.put("MiDocumentBlob", MiDocumentBlobProvider.class);
//		LOB_PROVIDER_CLAZZS.put("MiStructureBlob",
//				MiStructureBlobProvider.class);
//		LOB_PROVIDER_CLAZZS.put("MiAgentCtrlExpertPhoto",
//				MiAgentCtrlExpertPhotoProvider.class);
//		
//	}
	protected Map<String, IBlobProvider> getBlobProviderMap(){
		return LOB_PROVIDERS;
	}
	protected Map<String, Class<? extends IBlobModel>> getBlobModelClassesMap(){
		return LOB_MODEL_CLASSES;
	}
//	 Set from tapestry.props.PersistenceStrategy
    private List _contributions;

    private List nameProviderContributions;
	

    /**
	 * @param nameProviderContributions The nameProviderContributions to set.
	 */
	public void setNameProviderContributions(List nameProviderContributions) {
		this.nameProviderContributions = nameProviderContributions;
	}
	@SuppressWarnings("unchecked")
	public void initializeService()
    {
        Iterator i = _contributions.iterator();
        while(i.hasNext())
        {
        	BlobProviderContribution c = (BlobProviderContribution) i
                    .next();

        	LOB_PROVIDERS.put(c.getName(), c.getProvider());
        }
        i = nameProviderContributions.iterator();
        while(i.hasNext())
        {
        	NameBlobProviderContribution c = (NameBlobProviderContribution) i
                    .next();
        	String className=c.getProviderClassName();
        	Class clazz;
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				throw new ApplicationRuntimeException(e);
			}
        	
			LOB_MODEL_CLASSES.put(c.getName(),clazz);
        }
    }
    public void setContributions(List contributions)
    {
        _contributions = contributions;
    }

}
