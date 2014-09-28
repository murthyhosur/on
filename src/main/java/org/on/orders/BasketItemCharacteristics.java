/**
 * @description     Stores the characteristics of basket items
 * @author Hosur Narahari
 * @since  2014-08-29
 * @version 1.0
 */
package org.on.orders;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BasketItemCharacteristics {
	@ManyToOne
	@JoinColumn(name="basketItemId")
	public BasketItem basketItem;
	@ManyToOne
	@JoinColumn(name="basketId")
	public Basket basket;
	@Id
	@GeneratedValue
	public Long uniqueId;
	public String attribute;
	public String value;

}
