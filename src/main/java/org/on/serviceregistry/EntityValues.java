package org.on.serviceregistry;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="SR_EntityValues")
@IdClass(EntityValues.PrimaryKey.class)
public class EntityValues {
	private String entityApplicationContext;
	private String entityType;
	@Id
	private String entityDn;
	@Id
	private String characteristic;
	@Id
	private String value;
	private Date fromDate;
	private Date toDate;
	
	public String getEntityApplicationContext() {
		return entityApplicationContext;
	}
	public void setEntityApplicationContext(String entityApplicationContext) {
		this.entityApplicationContext = entityApplicationContext;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getEntityDn() {
		return entityDn;
	}
	public void setEntityDn(String entityDn) {
		this.entityDn = entityDn;
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
	
	public static class PrimaryKey implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String entityDn;
		private String characteristic;
		private String value;
		
		@Override
		public int hashCode() {
			int hashCode = 0;
			if(entityDn != null)
				hashCode ^= entityDn.hashCode();
			if(characteristic != null)
				hashCode ^= characteristic.hashCode();
			if(value != null)
				hashCode ^= value.hashCode();
			return hashCode;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof PrimaryKey))
				return false;
			PrimaryKey target = (PrimaryKey)obj;
			return (this.entityDn == null ? target.entityDn == null : this.entityDn.equals(target.entityDn)) &&
				   (this.characteristic == null ? target.characteristic == null : this.characteristic.equals(target.characteristic)) &&
				   (this.value == null ? target.value == null : this.value.equals(target.value));			
		}
	}
	
}
