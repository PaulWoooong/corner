//==============================================================================
// file :       $Id: A.java 4470 2009-09-08 05:07:04Z jcai $
// project:     corner
//
// last change: date:       $Date: 2009-09-08 13:07:04 +0800 (星期二, 08 9月 2009) $
//              by:         $Author: jcai $
//              revision:   $Revision: 4470 $
//------------------------------------------------------------------------------
//copyright:	Beijing Maxinfo Technology Ltd. http://www.bjmaxinfo.com
//License:      the Apache License, Version 2.0 (the "License")
//==============================================================================

package corner.demo.model.mulitable;

import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.Lob;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import corner.demo.model.AbstractModel;
import corner.model.IBlobModel;

/**
 * @author Ghost
 * @version $Revision: 4470 $
 * @since 2.2.1
 */
@Entity(name="mulitableA")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class A extends AbstractModel implements IBlobModel {

	/**
	 *
	 */
	private static final long serialVersionUID = -1296703401706863951L;

	/**
	 * @hibernate.property type="corner.orm.hibernate.v3.VectorType"
	 */
	
	private Vector<String> colors;
	private String password;
	private double num;
	/**
	 * @return Returns the num.
	 */
	public double getNum() {
		return num;
	}

	/**
	 * @param num The num to set.
	 */
	public void setNum(double num) {
		this.num = num;
	}

	/**
	 * @return Returns the colors.
	 */
	@Type(type="corner.orm.hibernate.v3.VectorType")
	public Vector<String> getColors() {
		return colors;
	}

	/**
	 * @param colors
	 *            The colors to set.
	 */
	public void setColors(Vector<String> colors) {
		this.colors = colors;
	}

	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * blob数据.
	 * @hibernate.property column="BlobData" length="2147483647"
	 *                     type="org.springframework.orm.hibernate3.support.BlobByteArrayType"
	 * 
	 */
	private byte[] blobData;
	/**
	 * blob数据的类型,此类型用来web页面的显示,可能的结果为:image/jpeg,image/gif,application/pdf 等.
	 * @hibernate.property column="ContentType" length="30"
	 */
	private String contentType;
	
	/**
	 * @see corner.model.IBlobModel#getBlobData()
	 */
	@Lob
	@Type(type="org.springframework.orm.hibernate3.support.BlobByteArrayType")
	public byte[] getBlobData() {
		return blobData;
	}

	/**
	 * @see corner.model.IBlobModel#setBlobData(byte[])
	 */
	public void setBlobData(byte[] blobData) {
		this.blobData = blobData;
	}

	/**
	 * @see corner.model.IBlobModel#getContentType()
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @see corner.model.IBlobModel#setContentType(java.lang.String)
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	/**
	 * 文件名称
	 */
	private String blobName;
	/**
	 * 文件路径
	 */
	private String filePath;

	/**
	 * @return Returns the blobName.
	 */
	public String getBlobName() {
		return blobName;
	}

	/**
	 * @param blobName The blobName to set.
	 */
	public void setBlobName(String blobName) {
		this.blobName = blobName;
	}

	/**
	 * @return Returns the filePath.
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath The filePath to set.
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
}