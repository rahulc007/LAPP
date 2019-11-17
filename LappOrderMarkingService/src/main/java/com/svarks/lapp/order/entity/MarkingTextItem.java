package com.svarks.lapp.order.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Transactional
@Table(name = "marking_text_item")
@NamedQueries({
	@NamedQuery(name = "MarkingTextItem.getTextByLineItem", query = "SELECT e FROM MarkingTextItem e WHERE e.textItemid =:lineItemid "),
	@NamedQuery(name = "MarkingTextItem.updateMarkingtext", query = "UPDATE MarkingTextItem e SET e.leftText =:leftText,e.rightText =:rightText,e.middleText =:middleText"
			+ ",e.rmPartnoLeft=:rmPartnoLeft,e.rmPartnoRight=:rmPartnoRight,e.rmPartnomiddle=:rmPartnomiddle where e.markingId =:markingId "),
	@NamedQuery(name = "MarkingTextItem.deleteMarkingtext", query = "DELETE from MarkingTextItem m where m.markingId =:markingId ") })


public class MarkingTextItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int markingId;
	private String leftText;
	private String rightText;
	private String middleText;
	private String rmPartnoLeft;
	private String rmPartnoRight;
	private String rmPartnomiddle;
	private String notifyUser;
	private String updatedBy;
	private int textItemid;
	@Column(nullable = false, updatable = false)
	@CreationTimestamp
	private Date lastModifiedDate;
	
	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public int getTextItemid() {
		return textItemid;
	}

	public void setTextItemid(int textItemid) {
		this.textItemid = textItemid;
	}

	public int getMarkingId() {
		return markingId;
	}

	public void setMarkingId(int markingId) {
		this.markingId = markingId;
	}

	public String getLeftText() {
		return leftText;
	}

	public void setLeftText(String leftText) {
		this.leftText = leftText;
	}

	public String getRightText() {
		return rightText;
	}

	public void setRightText(String rightText) {
		this.rightText = rightText;
	}

	public String getMiddleText() {
		return middleText;
	}

	public void setMiddleText(String middleText) {
		this.middleText = middleText;
	}

	public String getNotifyUser() {
		return notifyUser;
	}

	public void setNotifyUser(String notifyUser) {
		this.notifyUser = notifyUser;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getRmPartnoLeft() {
		return rmPartnoLeft;
	}

	public void setRmPartnoLeft(String rmPartnoLeft) {
		this.rmPartnoLeft = rmPartnoLeft;
	}

	public String getRmPartnoRight() {
		return rmPartnoRight;
	}

	public void setRmPartnoRight(String rmPartnoRight) {
		this.rmPartnoRight = rmPartnoRight;
	}

	public String getRmPartnomiddle() {
		return rmPartnomiddle;
	}

	public void setRmPartnomiddle(String rmPartnomiddle) {
		this.rmPartnomiddle = rmPartnomiddle;
	}

	@Override
	public String toString() {
	     return ReflectionToStringBuilder.toString(this);
	 }

}
