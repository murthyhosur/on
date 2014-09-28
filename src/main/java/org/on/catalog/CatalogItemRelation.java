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
@Table(name="PC_ItemRelation")
@IdClass(CatalogItemRelation.PrimaryClass.class)
@SuppressWarnings("unused")
public class CatalogItemRelation {
	@Id
	@ManyToOne
	@JoinColumn(name="itemRelatedFrom")
	private CatalogItem itemRelatedFrom;
	@Id
	@ManyToOne
	@JoinColumn(name="itemRelatedTo")
	private CatalogItem itemRelatedTo;
	private Date fromDate;
	private Date toDate;
	private Date createdDate;
	private String createdBy;
	private Integer minCard;
	private Integer maxCard;
	private Integer defaultCard;
	private Integer sequence;
	
	CatalogItemRelation() {
		
	}
	
	public CatalogItemRelation(CatalogItem itemRelatedFrom,CatalogItem itemRelatedTo,Date fromDate) {
		this.itemRelatedFrom = itemRelatedFrom;
		this.itemRelatedTo = itemRelatedTo;
		this.fromDate = fromDate;
	}
	
	public CatalogItem getItemRelatedTo() {
		return itemRelatedTo;
	}

	public void setMinCard(Integer minCard) {
		this.minCard = minCard;
	}
	
	public void setMaxCard(Integer maxCard) {
		this.maxCard = maxCard;
	}
	
	public void setDefaultCard(Integer defaultCard) {
		this.defaultCard = defaultCard;
	}
	
	public Integer getMinCard() {
		return minCard;
	}

	public Integer getMaxCard() {
		return maxCard;
	}

	public Integer getDefaultCard() {
		return defaultCard;
	}

	public static class PrimaryClass implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private CatalogItem itemRelatedFrom;
		private CatalogItem itemRelatedTo;
		
		public PrimaryClass() {
			
		}

		public CatalogItem getItemRelatedFrom() {
			return itemRelatedFrom;
		}

		public void setItemRelatedFrom(CatalogItem itemRelatedFrom) {
			this.itemRelatedFrom = itemRelatedFrom;
		}

		public CatalogItem getItemRelatedTo() {
			return itemRelatedTo;
		}

		public void setItemRelatedTo(CatalogItem itemRelatedTo) {
			this.itemRelatedTo = itemRelatedTo;
		}
		
		@Override
		public int hashCode() {
			int hashCode = 0;
			if(this.itemRelatedFrom != null)
				hashCode ^= this.itemRelatedFrom.getCode().hashCode();
			if(this.itemRelatedTo != null)
				hashCode ^= this.itemRelatedTo.getCode().hashCode();
			return hashCode;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof PrimaryClass))
				return false;
			PrimaryClass target = (PrimaryClass)obj;
			return this.itemRelatedFrom.getCode() == target.itemRelatedFrom.getCode() &&
			this.itemRelatedTo.getCode() == this.itemRelatedTo.getCode();
		}
	}

}
