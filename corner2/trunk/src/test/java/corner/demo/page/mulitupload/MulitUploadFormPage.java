//==============================================================================
// file :       $Id: MulitUploadFormPage.java 4512 2009-11-20 02:12:11Z ghostbb $
// project:     corner
//
// last change: date:       $Date: 2009-11-20 10:12:11 +0800 (星期五, 20 十一月 2009) $
//              by:         $Author: ghostbb $
//              revision:   $Revision: 4512 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.page.mulitupload;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry.request.IUploadFile;

import corner.demo.model.mulitupload.TestMany;
import corner.demo.model.mulitupload.TestOne;
import corner.model.IBlobModel;
import corner.orm.tapestry.component.mulitupload.IMulitUploadPage;
import corner.orm.tapestry.page.relative.ReflectMultiManyEntityFormPage;
import corner.orm.tapestry.service.blob.IBlobPageDelegate;
import corner.orm.tapestry.service.blob.SqueezeBlobPageDelegate;

/**
 * 对blob的处理
 * 
 * @author <a href="mailto:jun.tsai@bjmaxinfo.com">Jun Tsai</a>
 * @version $Revision: 4512 $
 * @since 2.2.1
 */
public abstract class MulitUploadFormPage extends
		ReflectMultiManyEntityFormPage implements IMulitUploadPage {
	
	public abstract IBlobModel getBlobModel();

	public abstract void setBlobModel(IBlobModel model);

	/**
	 * @see corner.orm.tapestry.page.AbstractEntityPage#saveOrUpdateEntity()
	 */
	@Override
	protected void saveOrUpdateEntity() {

		super.saveOrUpdateEntity();// 保存one端实体
		
		TestOne one = (TestOne) this.getEntity();
		List<IUploadFile> fileList = this.getUploadFiles();
		List<TestMany> list = new ArrayList<TestMany>();
		
		
		if(fileList != null && fileList.size()>0){
			for(IUploadFile f: fileList){
				System.out.println("文件名："+f.getFileName());
				System.out.println("ContentType："+f.getContentType());
				TestMany many = new TestMany();
				this.getEntityService().saveEntity(many);
				many.setTestOne(one);
				IBlobPageDelegate<TestMany> delegate = new SqueezeBlobPageDelegate<TestMany>(
						TestMany.class, f, many, this.getEntityService());

				delegate.save();
				list.add(many);
				
			}
		}
	}

}
