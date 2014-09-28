package org.on.catalog;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.on.global.Catalog;
import org.on.global.DAO;
import org.on.global.selector.Selectable;

@Entity
@Table(name="PC_Item")
@SuppressWarnings("unused")
public class CatalogItem {
	@Id
	@Column(length=16)
	private String itemCode;
	private String label;
	@OneToOne
	@JoinColumn(name="baseItemCode")
	private CatalogItem baseItem;
	@Column(length=16)
	private String itemType;
	private Date fromDate;
	private Date toDate;
	private String createdBy;
	private Date createdDate;
	
	CatalogItem() throws CatalogException{
		//throw new CatalogException("Mandatory fields missing");
	}
	
	public CatalogItem(String itemCode,String label,CatalogItem baseItem,String itemType,Date fromDate) {
		this.itemCode = itemCode;
		this.label = label;
		this.baseItem = baseItem;
		this.itemType = itemType;
		this.fromDate = fromDate;
		this.createdDate = new Date();
	}
	
	public void save() {
		DAO.saveObject(this);
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public String getType() {
		return this.itemType;
	}
	
	public String getCode() {
		return this.itemCode;
	}
	
	public void addAttribute(String attrName,Date fromDate,String defaultValue,boolean addByDefault) {
		CatalogItemAttribute itemAttr = new CatalogItemAttribute(attrName,this,fromDate,defaultValue);
		itemAttr.setAddByDefault(addByDefault);
		DAO.saveObject(itemAttr);
	}
	
	public void addRelation(String itemRelatedTo,Date fromDate,Integer... cards) {
		CatalogItem relatedTo = Catalog.getItem(itemRelatedTo);
		Integer minCard = null;
		Integer maxCard = null;
		Integer defaultCard = null;
		if(cards.length > 0) minCard = cards[0];
		if(cards.length > 1) maxCard = cards[1];
		if(cards.length > 2) defaultCard = cards[2];
		if(cards.length > 3)
			throw new CatalogException("Too many parameters");
		CatalogItemRelation relation = new CatalogItemRelation(this,relatedTo,fromDate);
		if(minCard != null) relation.setMinCard(minCard);
		if(maxCard != null) relation.setMaxCard(maxCard);
		if(defaultCard != null) relation.setDefaultCard(defaultCard);
		DAO.saveObject(relation);
	}
	
	public void addIdentifier(String systemId,String name,String value,Date fromDate) {
		Identifier ident = new Identifier(systemId,this,name,value,fromDate);
		DAO.saveObject(ident);
	}
	
	public void addGroup(String groupName,Date fromDate,Integer... cards) {
		Integer minCard = null;
		Integer maxCard = null;
		if(cards.length > 0) minCard = cards[0];
		if(cards.length > 1) maxCard = cards[1];
		if(cards.length > 2) 
			throw new CatalogException("Too many Parameters");
		CatalogGroup group = new CatalogGroup(groupName,this,fromDate);
		if(minCard != null) group.setMinCard(minCard);
		if(maxCard != null) group.setMaxCard(maxCard);
		DAO.saveObject(group);
	}
}
