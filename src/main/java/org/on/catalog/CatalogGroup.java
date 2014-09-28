package org.on.catalog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.on.global.DAO;

@SuppressWarnings("unused")
@Entity
@Table(name="PC_Group")
@IdClass(CatalogGroup.PrimaryClass.class)
public class CatalogGroup {

	@Id
	private String groupName;
	@Id
	@ManyToOne
	@JoinColumn(name="itemCode")
	private CatalogItem item;
	private Integer minCard;
	private Integer maxCard;
	private Date fromDate;
	private Date toDate;
	private Integer sequence;
	
	CatalogGroup() {
		
	}
	
	public CatalogGroup(String groupName,CatalogItem item,Date fromDate) {
		this.groupName = groupName;
		this.item = item;
		this.fromDate = fromDate;
	}

	public Integer getMinCard() {
		return minCard;
	}

	public void setMinCard(Integer minCard) {
		this.minCard = minCard;
	}

	public Integer getMaxCard() {
		return maxCard;
	}

	public void setMaxCard(Integer maxCard) {
		this.maxCard = maxCard;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public CatalogItem getItem() {
		return item;
	}

	public void addGroupItem(CatalogItem item,Date fromDate,boolean addByDefault) {
		CatalogGroupItems groupItem = new CatalogGroupItems(this,item,fromDate);
		groupItem.setAddByDefault(addByDefault);
		DAO.saveObject(groupItem);
	}

	
	public static class PrimaryClass implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String groupName;
		private CatalogItem item;
		
		@Override
		public int hashCode() {
			int hashCode = 0;
			if(groupName != null)
				hashCode ^= groupName.hashCode();
			if(item != null)
				hashCode ^= item.getCode().hashCode();
			return hashCode;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof PrimaryClass))
				return false;
			PrimaryClass target = (PrimaryClass)obj;
			return this.groupName == null ? target.groupName == null : this.groupName.equals(target.groupName) &&
					this.item == null ? target.item == null : this.item.getCode().equals(target.item.getCode());
		}
	}
}
