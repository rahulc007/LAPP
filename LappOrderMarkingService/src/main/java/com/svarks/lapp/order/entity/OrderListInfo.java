package com.svarks.lapp.order.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Transactional
@Table(name = "order_info")
/*@NamedQueries({
		@NamedQuery(name = "OrderInfo.getUserByEmail", query = "SELECT e FROM OrderInfo e WHERE e.emailId =:emailId "),
		@NamedQuery(name = "OrderInfo.findByEmailId", query = "SELECT CASE WHEN (COUNT(*) >0) THEN TRUE ELSE FALSE END FROM OrderInfo e WHERE e.emailId =:emailId "),
		@NamedQuery(name = "OrderInfo.findByCustId", query = "SELECT CASE WHEN (COUNT(*) >0) THEN TRUE ELSE FALSE END FROM OrderInfo e WHERE e.customerId =:customerId "),
		@NamedQuery(name = "OrderInfo.updateIsEmailVerified", query = "UPDATE OrderInfo e SET e.isEmailConfirmed =1 where e.emailId =:emailId "),
		@NamedQuery(name = "OrderInfo.findUserByCredentials", query = "SELECT e FROM OrderInfo e WHERE e.emailId =:emailId AND e.password =:password AND e.countryCode=:countryCode AND e.isEmailConfirmed=true"),
		@NamedQuery(name = "OrderInfo.findUserByCustomerId", query = "SELECT e FROM OrderInfo e WHERE e.customerId =:customerId AND e.password =:password AND e.countryCode=:countryCode AND e.isEmailConfirmed=true"),
		@NamedQuery(name = "OrderInfo.resetNewPassword", query = "UPDATE OrderInfo e SET e.password =:password where e.emailId =:emailId ") })

*/
public class OrderListInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int orderlistid;
	private String salesOrderno;
	private String productionOrderno;
	private String lineItem;
	
	
	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }

}
