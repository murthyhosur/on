/**
 * This class represents the instance of an order. All the general details of an order like id,state,order type
 * and subtype etc. are stored in this class. It is related with its own basket which inturn is related with the
 * items present in the basket.
 * 
 * @author Hosur Narahari
 * @since  2014-08-29
 * @version   1.0
 */
package org.on.orders;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.on.global.DAO;

@Entity
@Table(name="OCORDER")
public class Order {
	@Id
	@GeneratedValue
	public Long orderId;
	public String orderSubType;
	public String orderType;
	public Long parentOrderId;
	public String createdBy;
	public Date createdTime;
	public Date effectiveDate;
	public String submittedBy;
	public Date submittedTime;
	public String customerId;
	public String addressId;
	public String channel;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="basketId")
	public Basket basket;
	
	public Order() {
		this.basket = new Basket();
		this.basket.order = this;
	}
	
	public Order(String customerId,String addressId) {
		this.customerId = customerId;
		this.addressId = addressId;
		this.basket = new Basket(customerId,addressId);
		this.basket.order = this;
	}
	
	public void save() {
		DAO.begin();
		if(this.basket == null) {
			this.basket = new Basket();
			this.basket.order = this;
		}
		DAO.saveOrUpdate(this);
		DAO.commit();
		DAO.close();
	}
	
	public static Order getOrderById(Long orderId) {
		DAO.begin();
		Order order = (Order)DAO.getSession().get(Order.class, orderId);
		DAO.close();
		return order;
	}
	
	public static void invalidateOrder(Long orderId) {
		DAO.begin();
		Order order = (Order)DAO.getSession().get(Order.class, orderId);
		if(order != null) {
			DAO.getSession().delete(order);
			DAO.commit();
		}
		DAO.close();
	}
}
		
