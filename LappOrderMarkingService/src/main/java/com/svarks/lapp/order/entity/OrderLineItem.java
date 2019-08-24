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
@Table(name = "order_line_item")
@NamedQueries({
	@NamedQuery(name = "OrderLineItem.getSalesOrderItem", query = "SELECT e FROM OrderLineItem e WHERE e.salesOrderno =:salesOrderno "),
	@NamedQuery(name = "OrderLineItem.updateLineItem", query = "UPDATE OrderLineItem e SET e.isSubmit =:isSubmit,e.legsCount=:legsCount WHERE e.lineItemId =:lineItemId ") })



public class OrderLineItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int lineItemId;
	private String lineItemno;
	private String articleNo;
	private String description;
	private String length;
	private String customerPartNo;
	private String customerNo;
	private String quantity;
	private String productionOrderStatus;
	private String productionOrderno;
	private String updatedBy;
	private Date createdDate;
	private Date modifiedDate;
	private String salesOrderno;
	private boolean isSubmit;
	private int legsCount;
	
	
	
	
	public int getLegsCount() {
		return legsCount;
	}

	public void setLegsCount(int legsCount) {
		this.legsCount = legsCount;
	}

	public String getProductionOrderno() {
		return productionOrderno;
	}

	public void setProductionOrderno(String productionOrderno) {
		this.productionOrderno = productionOrderno;
	}

	public String getProductionOrderStatus() {
		return productionOrderStatus;
	}

	public void setProductionOrderStatus(String productionOrderStatus) {
		this.productionOrderStatus = productionOrderStatus;
	}

	public String getSalesOrderno() {
		return salesOrderno;
	}

	public void setSalesOrderno(String salesOrderno) {
		this.salesOrderno = salesOrderno;
	}

	public int getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(int lineItemId) {
		this.lineItemId = lineItemId;
	}

	public String getLineItemno() {
		return lineItemno;
	}

	public void setLineItemno(String lineItemno) {
		this.lineItemno = lineItemno;
	}

	public String getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(String articleNo) {
		this.articleNo = articleNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getCustomerPartNo() {
		return customerPartNo;
	}

	public void setCustomerPartNo(String customerPartNo) {
		this.customerPartNo = customerPartNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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

	public boolean isSubmit() {
		return isSubmit;
	}

	public void setSubmit(boolean isSubmit) {
		this.isSubmit = isSubmit;
	}

	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }

}
