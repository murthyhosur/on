package org.on.catalog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name="PC_Identifier")
@IdClass(Identifier.PrimaryClass.class)
public class Identifier {
	
	@Id
	private String systemId;
	@Id
	@ManyToOne
	@JoinColumn(name="itemCode")
	private CatalogItem item;
	private String name;
	private String value;
	private Date fromDate;
	private Date toDate;
	private Integer sequence;
	
	Identifier() {
		
	}
	
	public Identifier(String systemId,CatalogItem item,String name,String value,Date fromDate) {
		this.systemId = systemId;
		this.item = item;
		this.name = name;
		this.value = value;
		this.fromDate = fromDate;
	}

	public String getSystemId() {
		return systemId;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
	
	public static class PrimaryClass implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String systemId;
		private CatalogItem item;
		
		@Override
		public int hashCode() {
			int hashCode = 0;
			if(systemId != null)
				hashCode ^= systemId.hashCode();
			if(item != null)
				hashCode ^= item.getCode().hashCode();
			return hashCode;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof PrimaryClass))
				return false;
			PrimaryClass target = (PrimaryClass)obj;
			return this.systemId == null ? target.systemId == null : this.systemId.equals(target.systemId) &&
					this.item == null ? target.item == null : this.item.getCode().equals(target.item.getCode());
		}
	}
}
