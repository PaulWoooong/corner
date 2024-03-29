// Copyright 2007 the original author or authors.
// site: http://www.bjmaxinfo.com
// file: $Id: PdfMessages.java 3919 2008-01-16 04:36:52Z xf $
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

package corner.orm.tapestry.pdf;

import org.apache.hivemind.impl.MessageFormatter;

/**
 * pdf包的消息文件
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision: 3919 $
 * @since 2.3.7
 */
public class PdfMessages {
	private static final MessageFormatter _formatter = new MessageFormatter(PdfMessages.class);

    /* defeat instantiation */
    private PdfMessages() { }

	public static String outputPdfProblem(Throwable e) {
		return _formatter.format("output.pdf.problem", e);
	}

	public static String invalidateEntityPageInstance(String name) {
		
		return _formatter.format("invlidate.page.instance", name);
	}
	
    

}
