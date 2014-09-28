package org.on.catalog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name="PC_GroupItem")
@IdClass(CatalogGroupItems.PrimaryClass.class)
public class CatalogGroupItems {

	@Id
	@ManyToOne
	@JoinColumns({
	@JoinColumn(name="groupName",referencedColumnName="groupName"),
	@JoinColumn(name="itemCode",referencedColumnName="itemCode")})
	private CatalogGroup group;
	@Id
	@ManyToOne
	@JoinColumn(name="groupItemCode")
	private CatalogItem item;
	private boolean addByDefault;
	private Date fromDate;
	private Date toDate;
	private Integer sequence;
	
	CatalogGroupItems() {
		
	}
	
	public CatalogGroupItems(CatalogGroup group,CatalogItem item,Date fromDate) {
		this.group = group;
		this.item = item;
		this.fromDate = fromDate;
	}

	public boolean isAddByDefault() {
		return addByDefault;
	}

	public void setAddByDefault(boolean addByDefault) {
		this.addByDefault = addByDefault;
	}
	
	public static class PrimaryClass implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private CatalogGroup group;
		private CatalogItem item;
		
		@Override
		public int hashCode() {
			int hashCode = 0;
			if(group != null)
				hashCode ^= group.getGroupName().hashCode();
			if(item != null)
				hashCode ^= item.getCode().hashCode();
			return hashCode;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof PrimaryClass))
				return false;
			PrimaryClass target = (PrimaryClass)obj;
			return this.group == null ? target.group == null : this.group.getGroupName().equals(target.group.getGroupName())
					&& this.group.getItem().getCode().equals(target.group.getItem().getCode())
					&& this.item == null ? target.item == null : this.item.getCode().equals(target.item.getCode());
		}
	}

}
