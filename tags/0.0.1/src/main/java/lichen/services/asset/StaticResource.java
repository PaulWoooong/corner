/*		
 * Copyright 2008 The OurIBA Develope Team.
 * site: http://ouriba.com
 * file: $Id: StaticResource.java 626 2008-09-22 06:10:00Z jcai $
 * created at:2008-09-03
 */

package lichen.services.asset;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import org.apache.tapestry5.ioc.Resource;

/**
 * 静态资源类型,可供以类似context的方式引用在T5之外的静态资源
 * @author dong
 * @version $Revision: 626 $
 * @since 0.0.1
 */
public class StaticResource implements Resource {

	private final String path;
	private URL url;

	protected StaticResource(String path) {
		this.path = path;
	}

	/**
	 * @see org.apache.tapestry5.ioc.Resource#toURL()
	 */
	public synchronized URL toURL() {
		if(this.url!=null){
			return this.url;
		}
		try {
			this.url = new java.net.URL(this.path);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		return this.url;
	}

	public boolean exists() {
		return toURL()!=null;
	}

	public Resource forFile(String relativePath) {
		String prefix = "";
		if(!relativePath.startsWith("/")){
			prefix = "/";
		}
		return new StaticResource(this.path+prefix+relativePath);
	}

	/**
	 * @see org.apache.tapestry5.ioc.Resource#forLocale(java.util.Locale)
	 */
	public Resource forLocale(Locale locale) {
		/*
		 * 暂时不处理locale
		 */
		return this;
	}

	public String getFile() {
		return toURL().getFile();
	}

	public String getFolder() {
		throw new UnsupportedOperationException();
	}

	public String getPath() {
		return this.path;
	}

	public InputStream openStream() throws IOException {
		throw new UnsupportedOperationException();
	}

	public Resource withExtension(String extension) {
		throw new UnsupportedOperationException();
	}
}
