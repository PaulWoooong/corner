//==============================================================================
// file :       $Id: IMulitUpload.java 3677 2007-11-14 04:36:40Z jcai $
// project:     corner
//
// last change: date:       $Date: 2007-11-14 12:36:40 +0800 (星期三, 14 十一月 2007) $
//              by:         $Author: jcai $
//              revision:   $Revision: 3677 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.page.mulitupload;

import java.util.List;

import org.apache.tapestry.request.IUploadFile;

import corner.model.IBlobModel;

/**
 * 文件上传页面类接口
 * <p>
 * 实现多文件上传页面必须实现该接口
 * 
 * @author <a href=mailto:Ghostbb@bjmaxinfo.com>Ghostbb</a>
 * @version $Revision: 3677 $
 * @since 2.3.7
 */
public interface IMulitUpload extends IBlobModel {

	/**
	 * 设置上传文件
	 */
	public abstract void setFiles(List<IUploadFile> files);

	/**
	 * 取得所有上传的文件
	 * 
	 * @return 一个封装了{@link IUploadFile}的{@link List}
	 */
	public abstract List<IUploadFile> getFiles();

	public abstract IBlobModel getBlobModel();

	public abstract void setBlobModel(IBlobModel model);

}
