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
	@NamedQuery(name = "OrderLineItem.getSalesOrderItem", query = "SELECT e FROM OrderLineItem e WHERE e.salesOrderno =:salesOrderno and (e.productionOrderStatus = 'Released' or e.productionOrderStatus='Rel') "),
	@NamedQuery(name = "OrderLineItem.getLineItemBySales", query = "SELECT e FROM OrderLineItem e WHERE e.salesOrderno =:salesOrderno"),
	@NamedQuery(name = "OrderLineItem.getLineItemByItemno", query = "SELECT e FROM OrderLineItem e WHERE e.lineItemId =:lineItemId"),
	@NamedQuery(name = "OrderLineItem.getProcessedSalesOrderItem", query = "SELECT e FROM OrderLineItem e WHERE e.salesOrderno =:salesOrderno and (e.productionOrderStatus != 'Released' or e.productionOrderStatus != 'Rel') "),
	@NamedQuery(name = "OrderLineItem.findByLineItemNo", query = "SELECT CASE WHEN (COUNT(*) >0) THEN TRUE ELSE FALSE END FROM OrderLineItem e WHERE e.lineItemno =:lineItemno"),
	@NamedQuery(name = "OrderLineItem.updateOrderStatus", query = "UPDATE OrderLineItem e SET e.productionOrderStatus =:productionOrderStatus WHERE e.salesOrderno =:salesOrderno AND e.lineItemno=:lineItemno"),
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((articleNo == null) ? 0 : articleNo.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((customerNo == null) ? 0 : customerNo.hashCode());
		result = prime * result + ((customerPartNo == null) ? 0 : customerPartNo.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (isSubmit ? 1231 : 1237);
		result = prime * result + legsCount;
		result = prime * result + ((length == null) ? 0 : length.hashCode());
		result = prime * result + lineItemId;
		result = prime * result + ((lineItemno == null) ? 0 : lineItemno.hashCode());
		result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + ((productionOrderStatus == null) ? 0 : productionOrderStatus.hashCode());
		result = prime * result + ((productionOrderno == null) ? 0 : productionOrderno.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((salesOrderno == null) ? 0 : salesOrderno.hashCode());
		result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLineItem other = (OrderLineItem) obj;
		if (articleNo == null) {
			if (other.articleNo != null)
				return false;
		} else if (!articleNo.equals(other.articleNo))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (customerNo == null) {
			if (other.customerNo != null)
				return false;
		} else if (!customerNo.equals(other.customerNo))
			return false;
		if (customerPartNo == null) {
			if (other.customerPartNo != null)
				return false;
		} else if (!customerPartNo.equals(other.customerPartNo))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (isSubmit != other.isSubmit)
			return false;
		if (legsCount != other.legsCount)
			return false;
		if (length == null) {
			if (other.length != null)
				return false;
		} else if (!length.equals(other.length))
			return false;
		if (lineItemId != other.lineItemId)
			return false;
		if (lineItemno == null) {
			if (other.lineItemno != null)
				return false;
		} else if (!lineItemno.equals(other.lineItemno))
			return false;
		if (modifiedDate == null) {
			if (other.modifiedDate != null)
				return false;
		} else if (!modifiedDate.equals(other.modifiedDate))
			return false;
		if (productionOrderStatus == null) {
			if (other.productionOrderStatus != null)
				return false;
		} else if (!productionOrderStatus.equals(other.productionOrderStatus))
			return false;
		if (productionOrderno == null) {
			if (other.productionOrderno != null)
				return false;
		} else if (!productionOrderno.equals(other.productionOrderno))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (salesOrderno == null) {
			if (other.salesOrderno != null)
				return false;
		} else if (!salesOrderno.equals(other.salesOrderno))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		return true;
	}

	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }
	

}
