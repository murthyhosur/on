/**
 * @description    Represents a single item in basket
 * @author Hosur Narahari
 * @since  2014-08-29
 * @version    1.0
 */
package org.on.orders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.Query;
import org.on.catalog.CatalogException;
import org.on.catalog.CatalogItem;
import org.on.catalog.CatalogItemAttribute;
import org.on.catalog.CatalogItemRelation;
import org.on.global.Catalog;
import org.on.global.DAO;

@Entity
public class BasketItem {
	@Id
	@GeneratedValue
	public Long basketItemId;
	@ManyToOne
	@JoinColumn(name="basketId")
	public Basket basket;
	public String itemCode;
	public Long parentBasketItemId;
	//public String relation;
	public String source;
	public Long guid;
	public String state;
	public String createdBy;
	public Date createdTime;
	public String orderItemState;
	public String srStatus;
	public String customerId;
	public String addressId;
	@OneToMany(cascade=CascadeType.ALL,mappedBy="basketItem")
	public List<BasketItemCharacteristics> characteristics;
	
	public BasketItem() {
		characteristics = new ArrayList<BasketItemCharacteristics>();
	}
	
	public void save() {
		DAO.begin();
		DAO.saveOrUpdate(this);
		DAO.commit();
		DAO.close();
	}
	
	public CatalogItem getCatalogItem() {
		return Catalog.getItem(this.itemCode);
	}
	
	public void setCharacteristic(String attribute,String value) throws CatalogException {
		List<CatalogItemAttribute> attributes = Catalog.getItemAttributes(this.getCatalogItem());
		boolean attributeExists = false;
		for(CatalogItemAttribute attr : attributes) {
			if(attr.getAttributeName().equals(attribute)) {
				attributeExists = true;
				break;
			}
				
		}
		if(!attributeExists)
			throw new CatalogException("Attribtue doesn't exist");
		BasketItemCharacteristics bItemChar = new BasketItemCharacteristics();
		bItemChar.basketItem = this;
		bItemChar.basket = this.basket;
		bItemChar.attribute = attribute;
		bItemChar.value = value;
		characteristics.add(bItemChar);
	}
	
	public void addDefaultCharacteristics() {
		List<CatalogItemAttribute> attributes = Catalog.getItemAttributes(this.getCatalogItem());
		for(CatalogItemAttribute attr : attributes) {
			if(attr.isAddByDefault() && (attr.getDefaultValue() != null)) {
				BasketItemCharacteristics bChar = new BasketItemCharacteristics();
				bChar.basketItem = this;
				bChar.basket = this.basket;
				bChar.attribute = attr.getAttributeName();
				bChar.value = attr.getDefaultValue();
				characteristics.add(bChar);
			}
		}
	}
	
	public void addDefaultRelations() {
		List<CatalogItemRelation> relations = Catalog.getItemRelations(this.getCatalogItem());
		for(CatalogItemRelation relation : relations) {
			if(relation.getDefaultCard() != null) {
				for(int i = 0 ; i < relation.getDefaultCard() ; i++) {
					this.basket.addItem(relation.getItemRelatedTo().getCode(), this.basketItemId);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<BasketItem> getAllChildren(boolean recursive) {
		Query queryChildren = DAO.getSession().createQuery("FROM BasketItem where basket = :basket and parentBasketItemId = :basketItemId");
		queryChildren.setParameter("basket", this.basket);
		queryChildren.setLong("basketItemId", this.basketItemId);
		List<BasketItem> children = queryChildren.list();
		if(recursive) {
			List<BasketItem> tempChildren = new ArrayList<BasketItem>();
			for(BasketItem child : children) {
				tempChildren.addAll(child.getAllChildren(recursive));
			}
			if(!tempChildren.isEmpty())
				children.addAll(tempChildren);
		}
		return children;
	}
}
