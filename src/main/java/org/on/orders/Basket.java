/**
 * This class represents the instance of a basket. It is associated with an order to which it belongs. It is also
 * associated with basket items
 * 
 * @author Hosur Narahari
 * @since   2014-08-29
 * @version  1.0
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.Query;
import org.on.catalog.CatalogException;
import org.on.global.Catalog;
import org.on.global.DAO;

@Entity
@Table(name="BASKET")
public class Basket {
	@Id
	@GeneratedValue
	public Long basketId;
	@OneToOne
	@JoinColumn(name="orderId")
	public Order order;
	public String createdBy;
	public Date createdTime;
	public String customerId;
	public String addressId;
	@OneToMany(cascade=CascadeType.ALL,mappedBy="basket")
	public List<BasketItem> bItems;
	
	public Basket() {
		bItems = new ArrayList<BasketItem>();
	}
	
	public Basket(String customerId,String addressId) {
		this.customerId = customerId;
		this.addressId = addressId;
		bItems = new ArrayList<BasketItem>();
	}
	
	public void save() {
		DAO.begin();
		DAO.saveOrUpdate(this);
		DAO.commit();
		DAO.close();
	}
	
	public static Basket readFromDB(Long basketId) {
		return (Basket) DAO.getSession().get(Basket.class, basketId);
	}
	
	public static void removeFromDB(Long basketId) {
		DAO.begin();
		Basket basket = (Basket) DAO.getSession().get(Basket.class, basketId);
		DAO.getSession().delete(basket);
		DAO.commit();
		DAO.close();
	}
	
	public void addItem(String itemCode,Long parentBasketItemId) {
		if(Catalog.getItem(itemCode) == null)
			throw new CatalogException("Item doesn't exist");
		BasketItem basketItem = new BasketItem();
		basketItem.basket = this;
		basketItem.addressId = this.addressId;
		basketItem.customerId = this.customerId;
		basketItem.itemCode = itemCode;
		basketItem.parentBasketItemId = parentBasketItemId;
		basketItem.createdTime = new Date();
		basketItem.customerId = this.customerId;
		basketItem.addressId = this.addressId;
		basketItem.guid = basketItem.basketItemId;
		basketItem.source = "NEW";
		basketItem.state = "ADD";
		bItems.add(basketItem);
		DAO.saveOrUpdate(basketItem);
		basketItem.guid = basketItem.basketItemId;
		basketItem.addDefaultCharacteristics();
		basketItem.addDefaultRelations();
	}
	
	public List<BasketItem> getItemByCode(String itemCode) {
		Query itemQuery = DAO.getSession().createQuery("FROM BasketItem where itemCode = :itemCode");
		itemQuery.setString("itemCode", itemCode);
		@SuppressWarnings("unchecked")
		List<BasketItem> itemList = itemQuery.list();
		return itemList;
	}
	
	public BasketItem getItemById(Long id) {
		BasketItem bItem = (BasketItem) DAO.getSession().get(BasketItem.class, id);
		if(bItem != null && bItem.basket.equals(this))
			return bItem;
		else 
			return null;
	}

}
