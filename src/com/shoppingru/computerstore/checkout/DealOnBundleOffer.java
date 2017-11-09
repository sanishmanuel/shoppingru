/**
 * 
 */
package com.shoppingru.computerstore.checkout;

/**
 * @author Sanish
 *
 */
public class DealOnBundleOffer {
	  Integer id ;
	  String dealType;
	  String dealOnProductShortName;
	  Integer itemsRequiredForDeal ;
	  Integer freeItems;
	  String offeredProductShortName;
	  
	  
	/**
	 * @param id
	 * @param dealType
	 * @param dealOnProductShortName
	 * @param itemsRequiredForDeal
	 * @param freeItems
	 * @param offeredProductShortName
	 */
	public DealOnBundleOffer(Integer id, String dealType, String dealOnProductShortName, Integer itemsRequiredForDeal,
			Integer freeItems, String offeredProductShortName) {
		this.id = id;
		this.dealType = dealType;
		this.dealOnProductShortName = dealOnProductShortName;
		this.itemsRequiredForDeal = itemsRequiredForDeal;
		this.freeItems = freeItems;
		this.offeredProductShortName = offeredProductShortName;
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
	 * @return the freeItems
	 */
	public Integer getFreeItems() {
		return freeItems;
	}
	/**
	 * @param freeItems the freeItems to set
	 */
	public void setFreeItems(Integer freeItems) {
		this.freeItems = freeItems;
	}
	/**
	 * @return the offeredProductShortName
	 */
	public String getOfferedProductShortName() {
		return offeredProductShortName;
	}
	/**
	 * @param offeredProductShortName the offeredProductShortName to set
	 */
	public void setOfferedProductShortName(String offeredProductShortName) {
		this.offeredProductShortName = offeredProductShortName;
	}
	  
}
