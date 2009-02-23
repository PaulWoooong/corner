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
package lichen.internal.services;

import static org.apache.tapestry5.ioc.internal.util.CollectionFactory.newList;
import static org.apache.tapestry5.ioc.internal.util.Defense.notBlank;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.tapestry5.internal.services.CookieSource;
import org.apache.tapestry5.internal.services.PersistentFieldChangeImpl;
import org.apache.tapestry5.internal.util.Base64ObjectInputStream;
import org.apache.tapestry5.internal.util.Base64ObjectOutputStream;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.PersistentFieldChange;
import org.apache.tapestry5.services.PersistentFieldStrategy;
/**
 * 用来保存数据到客户端的cooki中的处理
 * @author <a href="mailto:jun.tsai@ganshane.net">Jun Tsai</a>
 * @version $Revision: 158 $
 * @since 0.0.1
 */
public class CookiePersistentFieldStrategy implements PersistentFieldStrategy {

	private String prefix="cookie:";
	private Cookies cookies;
	private CookieSource cookieSource;

	public CookiePersistentFieldStrategy(Cookies cookies,CookieSource cookieSource){
		this.cookies = cookies;
		this.cookieSource = cookieSource;
	}
	/**
	 * @see org.apache.tapestry5.services.PersistentFieldStrategy#discardChanges(java.lang.String)
	 */
	public void discardChanges(String pageName) {
		String fullPrefix = prefix + pageName + ":";
        
        Cookie[] cookies = cookieSource.getCookies();

        if (cookies == null) return;

        for (Cookie cooky : cookies)
        {
            if (cooky.getName().startsWith(fullPrefix)) {
            	
            	this.cookies.readCookieValue(cooky.getName());
            }
        }
	}

	/**
	 * @see org.apache.tapestry5.services.PersistentFieldStrategy#gatherFieldChanges(java.lang.String)
	 */
	public Collection<PersistentFieldChange> gatherFieldChanges(String pageName) {
		List<PersistentFieldChange> result = newList();

        String fullPrefix = prefix + pageName + ":";
        
        Cookie[] cookies = cookieSource.getCookies();

        if (cookies == null) return result;

        for (Cookie cooky : cookies)
        {
            if (cooky.getName().startsWith(fullPrefix)) {
            	
            	result.add(buildChange(cooky.getName(),cooky.getValue()));
            }
        }
        
		return result;
	}
    private PersistentFieldChange buildChange(String name, String attribute)
    {
    	Base64ObjectInputStream is = null;
		
		Object r=null;
		//解压缩
		try{
	     is= new Base64ObjectInputStream(attribute);
	     r=is.readObject();
		}catch(Exception e){
			return null;
		}finally{
			InternalUtils.close(is);
		}
        
      

        String[] chunks = name.split(":");

        // Will be empty string for the root component
        String componentId = chunks[2];
        String fieldName = chunks[3];
        
        return new PersistentFieldChangeImpl(componentId, fieldName, r);
    }
	/**
	 * @see org.apache.tapestry5.services.PersistentFieldStrategy#postChange(java.lang.String, java.lang.String, java.lang.String, java.lang.Object)
	 */
	public void postChange(String pageName, String componentId,
			String fieldName, Object newValue) {
        notBlank(pageName, "pageName");
        notBlank(fieldName, "fieldName");

        StringBuilder builder = new StringBuilder(prefix);
        builder.append(pageName);
        builder.append(':');

        if (componentId != null) builder.append(componentId);

        builder.append(':');
        builder.append(fieldName);

      //写入cookie
		//用base64outputstream算法
		Base64ObjectOutputStream os = null;
		
		if(newValue == null){
			this.cookies.removeCookieValue(builder.toString());
			return;
		}
        try
        {
            os = new Base64ObjectOutputStream();
            os.writeObject(newValue);

        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex.getMessage(), ex);
        }
        finally
        {
            InternalUtils.close(os);
        }
        
        cookies.writeCookieValue(builder.toString(), os.toBase64(),30*60);

        
	}
	
	

}
