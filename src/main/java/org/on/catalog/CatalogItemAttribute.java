package org.on.catalog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.on.global.selector.Selectable;

@Entity
@Table(name="PC_ItemAttribute")
@IdClass(CatalogItemAttribute.PrimaryClass.class)
@SuppressWarnings("unused")
public class CatalogItemAttribute {
	@Id
	private String attributeName;
	@Id
	@ManyToOne
	@JoinColumn(name="itemCode")
	private CatalogItem item;
	private Date fromDate;
	private Date toDate;
	private String defaultValue;
	private boolean addByDefault;
	private Integer sequence;
	
	public static class PrimaryClass implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String attributeName;
		private CatalogItem item;
		
		public PrimaryClass() {
			
		}
		public String getAttributeName() {
			return attributeName;
		}
		public void setAttributeName(String attributeName) {
			this.attributeName = attributeName;
		}
		public CatalogItem getItem() {
			return item;
		}
		public void setItem(CatalogItem item) {
			this.item = item;
		}
		@Override
		public int hashCode() {
			int hashCode = 0;
			if(attributeName != null) 
				hashCode ^= attributeName.hashCode();
			if(item != null) 
				hashCode ^= item.getCode().hashCode();
			return hashCode;
		}
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof PrimaryClass))
				return false;
			PrimaryClass target = (PrimaryClass) obj;
			return ((this.attributeName == null) ? target.attributeName == null : 
				this.attributeName.equals(target.attributeName)) && 
				((this.item.getCode() == null) ? target.getItem().getCode() == null : 
					this.item.getCode().equals(target.getItem().getCode()));
		}
		
	}
	
	CatalogItemAttribute() throws CatalogException {
		//throw new CatalogException("Mandatory fields missing");
	}
	
	public CatalogItemAttribute(String attributeName,CatalogItem item,Date fromDate,String defaultValue) {
		this.attributeName = attributeName;
		this.item = item;
		this.fromDate = fromDate;
		this.defaultValue = defaultValue;
	}
	
	public void setAddByDefault(boolean defaultAdd) {
		this.addByDefault = defaultAdd;
	}
	
	public String getDefaultValue() {
		return this.defaultValue;
	}

	public String getAttributeName() {
		return this.attributeName;
	}
	
	public boolean isAddByDefault() {
		return addByDefault;
	}
}
