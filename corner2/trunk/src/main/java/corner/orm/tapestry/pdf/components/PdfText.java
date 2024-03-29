// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: PdfText.java 3678 2007-11-14 04:43:52Z jcai $
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

package corner.orm.tapestry.pdf.components;

import java.io.IOException;

import org.apache.hivemind.util.Defense;
import org.apache.tapestry.annotations.Parameter;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.TextField;

import corner.orm.tapestry.pdf.PdfSystemException;
import corner.orm.tapestry.pdf.PdfUtils;
import corner.orm.tapestry.pdf.PdfWriterDelegate;

/**
 * pdf的一个TextField组件
 * 
 * @author jcai
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision: 3678 $
 * @since 2.3.7
 */
public abstract class PdfText extends AbstractPdfComponent {

	/** 是否多行* */
	@Parameter
	public abstract boolean isMultiline();

	@Parameter(required = true)
	public abstract String getValue();

	@Parameter(defaultValue = "0")
	public abstract float getFontSize();
	
	@Parameter(defaultValue ="false")
	public abstract boolean isAlignCenter();


	
	
	/**
	 * @see corner.orm.tapestry.pdf.components.AbstractPdfComponent#renderPdf(corner.orm.tapestry.pdf.PdfWriterDelegate, com.lowagie.text.Document)
	 */
	@Override
	public void renderPdf(PdfWriterDelegate writer,Document doc)  {
		Defense.notNull(getRectangle(), "TextField的位置");
		String[] p = getRectangle().split(",");
		Rectangle r = new Rectangle(Float.valueOf(p[0]), Float.valueOf(p[1]),
				Float.valueOf(p[2]), Float.valueOf(p[3]));
		
		
		TextField tf = getFieldCreator().createTextField(writer.getPdfWriter(), r,this.getId());

		tf.setOptions(TextField.READ_ONLY);
		
		if (isMultiline()) {
			tf.setOptions(TextField.MULTILINE);
		}
		
		if(isAlignCenter()){
			tf.setAlignment(Element.ALIGN_CENTER);
		}
		if (getValue() != null) {// 设定文字
			tf.setFont(PdfUtils.createSongLightBaseFont());
			if (getFontSize() > 0)
				tf.setFontSize(getFontSize());
			tf.setText(getValue());
		}
		try {
			writer.getPdfWriter().addAnnotation(tf.getTextField());
		} catch (IOException e) {
			throw new PdfSystemException(e);
		} catch (DocumentException e) {
			throw new PdfSystemException(e);
		}
	}
}
