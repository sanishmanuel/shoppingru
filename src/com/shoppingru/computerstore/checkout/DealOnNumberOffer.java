/**
 * 
 */
package com.shoppingru.computerstore.checkout;

/**
 * @author Sanish
 *
 */
public class DealOnNumberOffer {

	  Integer id ;
	  String dealType;
	  String productShortName;
	  Integer itemsRequiredForDeal ;
	  Integer freeItems;
	  
	/**
	 * @param id
	 * @param dealType
	 * @param productShortName
	 * @param itemsRequiredForDeal
	 * @param freeItems
	 */
	public DealOnNumberOffer(Integer id, String dealType, String productShortName, Integer itemsRequiredForDeal,
			Integer freeItems) {
		this.id = id;
		this.dealType = dealType;
		this.productShortName = productShortName;
		this.itemsRequiredForDeal = itemsRequiredForDeal;
		this.freeItems = freeItems;
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
	 * @return the productShortName
	 */
	public String getProductShortName() {
		return productShortName;
	}
	/**
	 * @param productShortName the productShortName to set
	 */
	public void setProductShortName(String productShortName) {
		this.productShortName = productShortName;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DealOnNumberOffer [id=" + id + ", dealType=" + dealType + ", productShortName=" + productShortName
				+ ", itemsRequiredForDeal=" + itemsRequiredForDeal + ", freeItems=" + freeItems + "]";
	}
}
