package com.svarks.lapp.order.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedNativeQuery;
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
		@NamedQuery(name = "OrderInfo.getMyOrderByDate", query = "SELECT DISTINCT e FROM OrderInfo e INNER JOIN OrderLineItem ol on e.salesOrderno=ol.salesOrderno WHERE (e.createdBy =:createdBy OR e.createdBy='FTP') AND (e.createdDate >=:startDate AND e.createdDate <=:endDate) AND (ol.productionOrderStatus='Released' OR ol.productionOrderStatus='Rel')"),
		@NamedQuery(name = "OrderInfo.getMyProcessedOrderByDate", query = "SELECT DISTINCT e FROM OrderInfo e INNER JOIN OrderLineItem ol on e.salesOrderno=ol.salesOrderno WHERE (e.createdBy =:createdBy OR e.createdBy='FTP') AND (e.createdDate >=:startDate AND e.createdDate <=:endDate) AND (ol.productionOrderStatus !='Released' OR ol.productionOrderStatus !='Rel')"),
		@NamedQuery(name = "OrderInfo.getMyOrderCount", query = "SELECT DISTINCT COUNT(e) FROM OrderInfo e INNER JOIN OrderLineItem ol on e.salesOrderno=ol.salesOrderno WHERE (e.createdBy =:createdBy OR e.createdBy='FTP') AND (ol.productionOrderStatus='Released' OR ol.productionOrderStatus='Rel')"),
		@NamedQuery(name = "OrderInfo.getMyProcessedCount", query = "SELECT DISTINCT COUNT(e) FROM OrderInfo e INNER JOIN OrderLineItem ol on e.salesOrderno=ol.salesOrderno WHERE (e.createdBy =:createdBy OR e.createdBy='FTP') AND (ol.productionOrderStatus !='Released' OR ol.productionOrderStatus !='Rel')"),

		//@NamedQuery(name = "OrderInfo.getOderByUser", query = "SELECT DISTINCT e FROM OrderInfo e INNER JOIN OrderLineItem ol on e.salesOrderno=ol.salesOrderno  WHERE e.userEmailId =:userEmailId AND (ol.productionOrderStatus='Released' OR ol.productionOrderStatus='Rel')"),
		//@NamedQuery(name = "OrderInfo.getProcessedOrderByAdmin", query = "SELECT DISTINCT e FROM OrderInfo e INNER JOIN OrderLineItem ol on e.salesOrderno=ol.salesOrderno WHERE (e.createdBy =:createdBy OR e.createdBy='FTP') AND (ol.productionOrderStatus !='Released' AND ol.productionOrderStatus !='Rel')"),
		//@NamedQuery(name = "OrderInfo.getProcessedOrderByUser", query = "SELECT DISTINCT e FROM OrderInfo e INNER JOIN OrderLineItem ol on e.salesOrderno=ol.salesOrderno  WHERE e.userEmailId =:userEmailId AND (ol.productionOrderStatus !='Released' AND ol.productionOrderStatus !='Rel')"),
		@NamedQuery(name = "OrderInfo.findBySalesOrder", query = "SELECT CASE WHEN (COUNT(*) >0) THEN TRUE ELSE FALSE END FROM OrderInfo e WHERE e.salesOrderno =:salesOrderno"),
		@NamedQuery(name = "OrderInfo.getOrderBySalesOrder", query = "SELECT e FROM OrderInfo e WHERE e.salesOrderno =:salesOrderno"),
		@NamedQuery(name = "OrderInfo.getMyOrderBySales", query = "SELECT DISTINCT e FROM OrderInfo e ,OrderLineItem ol WHERE e.salesOrderno =:salesOrderno AND e.userEmailId =:userEmailId  AND (ol.productionOrderStatus='Released' OR ol.productionOrderStatus='Rel')"),
		@NamedQuery(name = "OrderInfo.getOrderBySales", query = "SELECT DISTINCT e FROM OrderInfo e ,OrderLineItem ol WHERE e.salesOrderno =:salesOrderno AND (ol.productionOrderStatus='Released' OR ol.productionOrderStatus='Rel')"),
		@NamedQuery(name = "OrderInfo.getMyOrderBySalesAndAdmin", query = "SELECT DISTINCT e FROM OrderInfo e ,OrderLineItem ol WHERE e.salesOrderno =:salesOrderno AND (e.createdBy =:createdBy OR e.createdBy='FTP') AND (ol.productionOrderStatus='Released' OR ol.productionOrderStatus='Rel')"),
		//@NamedQuery(name = "OrderInfo.getMyOrderByProdOrder", query = "SELECT DISTINCT e FROM OrderInfo e INNER JOIN OrderLineItem ol on e.salesOrderno=ol.salesOrderno WHERE e.userEmailId =:userEmailId AND ol.productionOrderno=:productionOrderno AND (ol.productionOrderStatus='Released' OR ol.productionOrderStatus='Rel')"),
		//@NamedQuery(name = "OrderInfo.getMyOrderByProdOrderAndAdmin", query = "SELECT DISTINCT e FROM OrderInfo e INNER JOIN OrderLineItem ol on e.salesOrderno=ol.salesOrderno WHERE (e.createdBy =:createdBy OR e.createdBy='FTP')AND ol.productionOrderno=:productionOrderno AND (ol.productionOrderStatus='Released' OR ol.productionOrderStatus='Rel')"),
		@NamedQuery(name = "OrderInfo.updateOrderStatus", query = "UPDATE OrderInfo e SET e.orderStatus =1 where e.salesOrderno =:salesOrderno "),
		@NamedQuery(name = "OrderInfo.updateOrder", query = "UPDATE OrderInfo e SET e.orderStatus =:orderStatus where e.salesOrderno =:salesOrderno ") })

@NamedNativeQuery(name = "OrderInfo.getMyOrderByProdOrder", query = "SELECT DISTINCT * FROM order_info e INNER JOIN order_line_item ol on e.sales_orderno=ol.sales_orderno WHERE e.user_email_id =:userEmailId AND ol.production_orderno=:productionOrderno AND (ol.production_order_status='Released' OR ol.production_order_status='Rel') group by e.sales_orderno", resultClass = OrderInfo.class)
@NamedNativeQuery(name = "OrderInfo.getMyOrderByProdOrderAndAdmin", query = "SELECT DISTINCT * FROM order_info e INNER JOIN order_line_item ol on e.sales_orderno=ol.sales_orderno WHERE (e.created_by =:createdBy OR e.created_by='FTP') AND ol.production_orderno=:productionOrderno AND (ol.production_order_status='Released' OR ol.production_order_status='Rel')", resultClass = OrderInfo.class)
@NamedNativeQuery(name = "OrderInfo.getOderByUser", query = "SELECT DISTINCT * FROM order_info e INNER JOIN order_line_item ol on e.sales_orderno=ol.sales_orderno  WHERE e.user_email_id =:userEmailId AND (ol.production_order_status='Released' OR ol.production_order_status='Rel') group by e.sales_orderno limit :startLimit,:endLimit", resultClass = OrderInfo.class)
@NamedNativeQuery(name = "OrderInfo.getOrderByAdmin", query = "SELECT DISTINCT * FROM order_info e INNER JOIN order_line_item ol on e.sales_orderno=ol.sales_orderno  WHERE (e.created_by =:createdBy OR e.created_by='FTP') AND (ol.production_order_status='Released' OR ol.production_order_status='Rel') group by e.sales_orderno limit :startLimit,:endLimit", resultClass = OrderInfo.class)
@NamedNativeQuery(name = "OrderInfo.getProcessedOrderByUser", query = "SELECT DISTINCT * FROM order_info e INNER JOIN order_line_item ol on e.sales_orderno=ol.sales_orderno  WHERE e.user_email_id =:userEmailId AND (ol.production_order_status !='Released' AND ol.production_order_status !='Rel') group by e.sales_orderno limit :startLimit,:endLimit", resultClass = OrderInfo.class)
@NamedNativeQuery(name = "OrderInfo.getProcessedOrderByAdmin", query = "SELECT DISTINCT * FROM order_info e INNER JOIN order_line_item ol on e.sales_orderno=ol.sales_orderno  WHERE (e.created_by =:createdBy OR e.created_by='FTP') AND (ol.production_order_status !='Released' AND ol.production_order_status !='Rel') group by e.sales_orderno limit :startLimit,:endLimit", resultClass = OrderInfo.class)

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
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	
	@JoinColumn(name = "salesOrderno", nullable = false)
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((modifiedDate == null) ? 0 : modifiedDate.hashCode());
		result = prime * result + oid;
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + ((orderLineItem == null) ? 0 : orderLineItem.hashCode());
		result = prime * result + orderStatus;
		result = prime * result + ((salesOrderno == null) ? 0 : salesOrderno.hashCode());
		result = prime * result + ((userEmailId == null) ? 0 : userEmailId.hashCode());
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
		OrderInfo other = (OrderInfo) obj;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (modifiedDate == null) {
			if (other.modifiedDate != null)
				return false;
		} else if (!modifiedDate.equals(other.modifiedDate))
			return false;
		if (oid != other.oid)
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (orderLineItem == null) {
			if (other.orderLineItem != null)
				return false;
		} else if (!orderLineItem.equals(other.orderLineItem))
			return false;
		if (orderStatus != other.orderStatus)
			return false;
		if (salesOrderno == null) {
			if (other.salesOrderno != null)
				return false;
		} else if (!salesOrderno.equals(other.salesOrderno))
			return false;
		if (userEmailId == null) {
			if (other.userEmailId != null)
				return false;
		} else if (!userEmailId.equals(other.userEmailId))
			return false;
		return true;
	}


}
