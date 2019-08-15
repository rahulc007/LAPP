package com.svarks.lapp.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Transactional
@Table(name = "SAP_file_info")
@NamedQueries({
		@NamedQuery(name = "SAPFileInfo.findByFileName", query = "SELECT CASE WHEN (COUNT(*) >0) THEN TRUE ELSE FALSE END FROM SAPFileInfo e WHERE e.fileName =:fileName "),
		@NamedQuery(name = "SAPFileInfo.getSAPDataByUser", query = "SELECT u FROM SAPFileInfo u where u.uploadedBy=:uploadedBy "),
		@NamedQuery(name = "SAPFileInfo.updateName", query = "UPDATE SAPFileInfo e SET e.fileName =:fileName where e.fileId =:fileId ") })

public class SAPFileInfo implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int fileId;
	private String fileName;
	private long fileSize;
	private int orderCount;
	private int orderItemCount;
	private String contentType;
	private int fileStatus;
	private String filePath;
	private String uploadedBy;
	private Date createdDate;
	private Date modifiedDate;
	
	
	
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public int getFileId() {
		return fileId;
	}
	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public int getOrderItemCount() {
		return orderItemCount;
	}
	public void setOrderItemCount(int orderItemCount) {
		this.orderItemCount = orderItemCount;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public int getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(int fileStatus) {
		this.fileStatus = fileStatus;
	}
	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }


}
