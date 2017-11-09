/**
 * 
 */
package com.shoppingru.computerstore.checkout;

import java.math.BigDecimal;

/**
 * @author Sanish
 *
 */
public class DealOnBulkOffer {
  
	  Integer id ;
	  String dealType;
	  String dealOnProductShortName;
	  Integer itemsRequiredForDeal ;
	  BigDecimal offerPrice;
	  
	/**
	 * @param id
	 * @param dealType
	 * @param dealOnProductShortName
	 * @param itemsRequiredForDeal
	 * @param offerPrice
	 */
	public DealOnBulkOffer(Integer id, String dealType, String dealOnProductShortName, Integer itemsRequiredForDeal,
			BigDecimal offerPrice) {
		
		this.id = id;
		this.dealType = dealType;
		this.dealOnProductShortName = dealOnProductShortName;
		this.itemsRequiredForDeal = itemsRequiredForDeal;
		this.offerPrice = offerPrice;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the dealType
	 */
	public String getDealType() {
		return dealType;
	}
	/**
	 * @param dealType the dealType to set
	 */
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	/**
	 * @return the dealOnProductShortName
	 */
	public String getDealOnProductShortName() {
		return dealOnProductShortName;
	}
	/**
	 * @param dealOnProductShortName the dealOnProductShortName to set
	 */
	public void setDealOnProductShortName(String dealOnProductShortName) {
		this.dealOnProductShortName = dealOnProductShortName;
	}
	/**
	 * @return the itemsRequiredForDeal
	 */
	public Integer getItemsRequiredForDeal() {
		return itemsRequiredForDeal;
	}
	/**
	 * @param itemsRequiredForDeal the itemsRequiredForDeal to set
	 */
	public void setItemsRequiredForDeal(Integer itemsRequiredForDeal) {
		this.itemsRequiredForDeal = itemsRequiredForDeal;
	}
	/**
	 * @return the offerPrice
	 */
	public BigDecimal getOfferPrice() {
		return offerPrice;
	}
	/**
	 * @param offerPrice the offerPrice to set
	 */
	public void setOfferPrice(BigDecimal offerPrice) {
		this.offerPrice = offerPrice;
	}
}
