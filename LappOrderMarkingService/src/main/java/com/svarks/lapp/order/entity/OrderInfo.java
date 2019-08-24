package com.svarks.lapp.order.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Transactional
@Table(name = "order_info")
@NamedQueries({
		@NamedQuery(name = "OrderInfo.getOrderByAdmin", query = "SELECT e FROM OrderInfo e WHERE e.createdBy =:createdBy order by e.createdDate desc"),
		@NamedQuery(name = "OrderInfo.getOderByUser", query = "SELECT e FROM OrderInfo e WHERE e.userEmailId =:userEmailId order by e.createdDate desc"),
		@NamedQuery(name = "OrderInfo.findBySalesOrder", query = "SELECT CASE WHEN (COUNT(*) >0) THEN TRUE ELSE FALSE END FROM OrderInfo e WHERE e.salesOrderno =:salesOrderno"),
		@NamedQuery(name = "OrderInfo.updateOrderStatus", query = "UPDATE OrderInfo e SET e.orderStatus =1 where e.salesOrderno =:salesOrderno "),
		@NamedQuery(name = "OrderInfo.updateOrder", query = "UPDATE OrderInfo e SET e.orderStatus =:orderStatus where e.salesOrderno =:salesOrderno ") })


public class OrderInfo implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int oid;
	private String salesOrderno;
	private String userEmailId;
	private String countryCode;
	private Date orderDate;
	private String createdBy;
	private int orderStatus;
	private Date createdDate;
	private Date modifiedDate;
	@JoinTable
	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderLineItem> orderLineItem;
	
	
	
	
	public String getSalesOrderno() {
		return salesOrderno;
	}


	public void setSalesOrderno(String salesOrderno) {
		this.salesOrderno = salesOrderno;
	}


	public int getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}



	public int getOid() {
		return oid;
	}


	public void setOid(int oid) {
		this.oid = oid;
	}


	public String getUserEmailId() {
		return userEmailId;
	}


	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}


	public String getCountryCode() {
		return countryCode;
	}


	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}


	public Date getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public List<OrderLineItem> getOrderLineItem() {
		return orderLineItem;
	}


	public void setOrderLineItem(List<OrderLineItem> orderLineItem) {
		this.orderLineItem = orderLineItem;
	}


	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }
}
