// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: PdfDirectLink.java 3919 2008-01-16 04:36:52Z xf $
// created at:2007-07-31
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

package corner.orm.tapestry.pdf.link;

import org.apache.tapestry.IActionListener;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.Tapestry;
import org.apache.tapestry.annotations.InjectObject;
import org.apache.tapestry.annotations.Parameter;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.link.AbstractLinkComponent;
import org.apache.tapestry.link.DirectLink;
import org.apache.tapestry.listener.ListenerInvoker;

import corner.orm.tapestry.pdf.IPdfDirect;

/**
 * pdf一个可以跳转的连接
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision: 3919 $
 * @since 2.3.7
 */
public abstract class PdfDirectLink extends AbstractLinkComponent implements
		IPdfDirect, IPdfLinkParameters {

	// 响应的参数
	@Parameter
	public abstract Object getParameters();

	// 监听器
	@Parameter(required = true)
	public abstract IActionListener getListener();

	// 执行Listener
	@InjectObject("infrastructure:listenerInvoker")
	public abstract ListenerInvoker getListenerInvoker();

	// 得到pdf direct link 服务
	@InjectObject("engine-service:pdfd")
	public abstract IEngineService getPdfService();

	/**
	 * 构造pdf连接
	 * 
	 * @see org.apache.tapestry.link.AbstractLinkComponent#getLink(org.apache.tapestry.IRequestCycle)
	 */
	@Override
	public ILink getLink(IRequestCycle cycle) {
		Object[] serviceParameters = DirectLink
				.constructServiceParameters(getParameters());
		
//		
//		Object[] parameters = new Object[serviceParameters.length + 2];
//		for (int i = 0; i < serviceParameters.length; i++) {
//			parameters[i] = serviceParameters[i];
//		}
//		parameters[serviceParameters.length] = this.isSaveAsFile();
//		parameters[serviceParameters.length + 1] = this.getDownloadFileName();

		PdfDirectServiceParameter pdsp = new PdfDirectServiceParameter(this,
				serviceParameters);
//		增加两个参数 1.是否保存为文件 2.文件名称
		pdsp.setSaveAsFile(this.isSaveAsFile());
		pdsp.setDownLoadFileName(this.getDownloadFileName());
		return getPdfService().getLink(false, pdsp);
	}

	/**
	 * @see corner.orm.tapestry.pdf.IPdfDirect#trigger(org.apache.tapestry.IRequestCycle)
	 */
	public void trigger(IRequestCycle cycle) {
		IActionListener listener = getListener();

		if (listener == null)
			throw Tapestry.createRequiredParameterException(this, "listener");

		getListenerInvoker().invokeListener(listener, this, cycle);
	}
}
