package org.on.serviceregistry;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SR_AssociationValues")
public class AssociationValues {

	private String assocApplicationContext;
	private String assocType;
	@Id
	private String assocDn;
	private String characteristic;
	private String value;
	private Date fromDate;
	private Date toDate;
	public String getAssocApplicationContext() {
		return assocApplicationContext;
	}
	public void setAssocApplicationContext(String assocApplicationContext) {
		this.assocApplicationContext = assocApplicationContext;
	}
	public String getAssocType() {
		return assocType;
	}
	public void setAssocType(String assocType) {
		this.assocType = assocType;
	}
	public String getAssocDn() {
		return assocDn;
	}
	public void setAssocDn(String assocDn) {
		this.assocDn = assocDn;
	}
	public String getCharacteristic() {
		return characteristic;
	}
	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
}
