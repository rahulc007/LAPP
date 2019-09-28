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
@Table(name = "order_status_update")
@NamedQueries({
		@NamedQuery(name = "OrderStatusUpdate.getByUser", query = "SELECT e FROM OrderStatusUpdate e WHERE e.createdUser =:createdUser"),
		@NamedQuery(name = "OrderStatusUpdate.findByName", query = "SELECT CASE WHEN (COUNT(*) >0) THEN TRUE ELSE FALSE END FROM OrderStatusUpdate e WHERE e.fileName =:fname "),
		@NamedQuery(name = "OrderStatusUpdate.updateOrder", query = "UPDATE OrderStatusUpdate e SET e.createdUser =:createdUser where e.statusid =:statusid ") })


public class OrderStatusUpdate implements Serializable {

		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE)
		private int statusid;
		private String fileName;
		private long fileSize;
		private String contentType;
		private int fileStatus;
		private String filePath;
		private String createdUser;
		private Date createdDate;
		private Date modifiedDate;
		
		
		
		public int getStatusid() {
			return statusid;
		}



		public void setStatusid(int statusid) {
			this.statusid = statusid;
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



		public String getContentType() {
			return contentType;
		}



		public void setContentType(String contentType) {
			this.contentType = contentType;
		}



		public int getFileStatus() {
			return fileStatus;
		}



		public void setFileStatus(int fileStatus) {
			this.fileStatus = fileStatus;
		}



		public String getFilePath() {
			return filePath;
		}



		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}



		public Date getCreatedDate() {
			return createdDate;
		}


		public String getCreatedUser() {
			return createdUser;
		}



		public void setCreatedUser(String createdUser) {
			this.createdUser = createdUser;
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



		@Override
		public String toString() {
		     return ReflectionToStringBuilder.toString(this);
		 }

}
