// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: CaptchaService.java 3678 2007-11-14 04:43:52Z jcai $
// created at:2006-12-10
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

package corner.orm.tapestry.service.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.hivemind.ApplicationRuntimeException;
import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.engine.IEngineService;
import org.apache.tapestry.engine.ILink;
import org.apache.tapestry.services.LinkFactory;
import org.apache.tapestry.services.ServiceConstants;
import org.apache.tapestry.util.ContentType;
import org.apache.tapestry.web.WebRequest;
import org.apache.tapestry.web.WebResponse;
import org.apache.tapestry.web.WebSession;

/**
 * 针对产生验证码的服务类
 * 
 * @author jcai
 * @version $Revision: 3678 $
 * @since 2.3
 */
public class CaptchaService implements IEngineService {
	public static final String SERVICE_NAME = "captcha";
	public static final String SESSION_ATTRIBUTE_ID="corner.orm.tapestry.service.captcha";
	/** 构建动态的连接工厂类 **/
	private LinkFactory _linkFactory;

	/** Arial字体 **/
	private Font arialFont = new Font("Arial", Font.BOLD, 13);
	/** web的response **/
	private WebResponse _response;
	/**产生图片的mime类型**/
	private String mimeType = "image/jpeg";
	/**web请求**/
	private WebRequest _request;

	/**
	 * 产生连接
	 * @see org.apache.tapestry.engine.IEngineService#getLink(boolean, java.lang.Object)
	 */
	public ILink getLink(boolean post, Object parameter) {
		final Map<String, String> parameters = new TreeMap<String, String>();
		parameters.put(ServiceConstants.SERVICE, getName());
		//避免浏览器的缓存
		parameters.put("cachetime", String.valueOf(System.currentTimeMillis()));
		return _linkFactory.constructLink(this, post, parameters, false);

	}
	/**
	 * 得到服务类的名称
	 * @see org.apache.tapestry.engine.IEngineService#getName()
	 */
	public String getName() {
		return SERVICE_NAME;
	}
	/**
	 * 
	 * @see org.apache.tapestry.engine.IEngineService#service(org.apache.tapestry.IRequestCycle)
	 */
	public void service(IRequestCycle cycle) throws IOException {
		final String randomStr=createAndSaveRandomId();
		final BufferedImage image = this.createCaptch(randomStr); //产生随机图片
		//得到图片的数据
		final ImageWriter writer = getImageWriter(mimeType);
		final byte[] bytes = toImageBytes(writer, image);
		//输出图片
		_response.setContentLength(bytes.length);
		final OutputStream out = _response.getOutputStream(new ContentType(mimeType));
		out.write(bytes);
		
	}
	
	private String createAndSaveRandomId() {
		String randomStr=RandomUtil.generateUUIDString();
		recordRandomString(randomStr);
		String randomCode=RandomUtil.encodeStr(randomStr);
		return randomCode;
	}
	protected void recordRandomString(String randomStr){
		final WebSession session = _request.getSession(true);
		session.setAttribute(SESSION_ATTRIBUTE_ID, randomStr);
		
	}

	BufferedImage createCaptch(String code) {
		// 构造图像
		BufferedImage image = new BufferedImage(54, 20,
				BufferedImage.TYPE_INT_RGB);
		Graphics gra = image.getGraphics();

		// 设定一个矩形框
		gra.fillRect(1, 1, 52, 18);
		gra.setFont(arialFont);

		Random ran = new Random();
		gra.setColor(Color.darkGray);
		//产生背景图像
		for (int i = 0; i < 20; i++) {
			int x = ran.nextInt(52);
			int y = ran.nextInt(18);
			gra.drawLine(x, y, x, y);
		}
		//产生验证码的图像
		if (code != null) {
			for (int i = 0; i < code.length(); i++) {
				gra.drawString(String.valueOf(code.charAt(i)), i * 12 + 3, 15);
			}
		}
		return image;
	}

	/**得到图像的writer **/
	private ImageWriter getImageWriter(String mimeType) {
		Iterator writers = ImageIO.getImageWritersByMIMEType(mimeType);
		if (!writers.hasNext()) {
			throw new ApplicationRuntimeException(
					"Unable to find image writer for mime type " + mimeType
							+ ".");
		}
		return (ImageWriter) writers.next();
	}
	/**得到图像的数据 **/
	byte[] toImageBytes(ImageWriter writer, BufferedImage image)
			throws IOException {
		final ByteArrayOutputStream bout = new ByteArrayOutputStream();
		final ImageOutputStream stream = ImageIO.createImageOutputStream(bout);
		writer.setOutput(stream);
		writer.write(image);
		bout.close();
		return bout.toByteArray();
	}

	
	public void setLinkFactory(LinkFactory linkFactory) {
		_linkFactory = linkFactory;
	}

	
	public void setResponse(WebResponse response) {
		_response = response;
	}

	public void setRequest(WebRequest webRequest) {
		this._request = webRequest;
	}
}
