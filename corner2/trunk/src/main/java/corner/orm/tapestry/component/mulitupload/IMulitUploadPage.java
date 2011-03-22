//==============================================================================
// file :       $Id$
// project:     corner2.5
//
// last change: date:       $Date$
//              by:         $Author$
//              revision:   $Revision$
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.orm.tapestry.component.mulitupload;

import java.util.List;

import org.apache.tapestry.request.IUploadFile;

/**
 * 多实体上传页面使用的接口
 * @author <a href="mailto:Ghostbb@bjmaxinfo.com">Ghostbb</a>
 * @version $Revision$
 * @since 2.5.2
 */
public interface IMulitUploadPage {
	
	/**
	 * 取得上传文件
	 * @return
	 */
	public List<IUploadFile> getUploadFiles();
	public void setUploadFiles(List<IUploadFile> files);
}
