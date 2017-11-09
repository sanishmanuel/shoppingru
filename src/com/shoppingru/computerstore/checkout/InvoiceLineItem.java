/**
 * 
 */
package com.shoppingru.computerstore.checkout;

import java.math.BigDecimal;

/**
 * @author Sanish
 *
 */
public class InvoiceLineItem {

	private Integer serialNumber;
	private String ItemName;
	private String ItemShortName;
	private BigDecimal unitPrice;
	private Integer noOfItems;
	private String dicsountType ;
	private BigDecimal discountAmount;
	private BigDecimal total;
	private String eligibleForDiscuont;
	
	private boolean isOfferAlreadyApplied;
	
	public InvoiceLineItem(){
		
	}
	
	/**
	 * @param itemName
	 * @param itemShortName
	 * @param unitPrice
	 * @param eligibleForDiscuont
	 */
	public InvoiceLineItem(String itemName, String itemShortName, BigDecimal unitPrice, String eligibleForDiscuont) {
		super();
		ItemName = itemName;
		ItemShortName = itemShortName;
		this.unitPrice = unitPrice;
		this.eligibleForDiscuont = eligibleForDiscuont;
	}
	/**
	 * @return the serialNumber
	 */
	public Integer getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return ItemName;
	}
	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	/**
	 * @return the itemShortName
	 */
	public String getItemShortName() {
		return ItemShortName;
	}
	/**
	 * @param itemShortName the itemShortName to set
	 */
	public void setItemShortName(String itemShortName) {
		ItemShortName = itemShortName;
	}
	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	/**
	 * @return the noOfItems
	 */
	public Integer getNoOfItems() {
		return noOfItems;
	}
	/**
	 * @param noOfItems the noOfItems to set
	 */
	public void setNoOfItems(Integer noOfItems) {
		this.noOfItems = noOfItems;
	}
	/**
	 * @return the dicsountType
	 */
	public String getDicsountType() {
		return dicsountType;
	}
	/**
	 * @param dicsountType the dicsountType to set
	 */
	public void setDicsountType(String dicsountType) {
		this.dicsountType = dicsountType;
	}

	/**
	 * @return the discountAmount
	 */
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	/**
	 * @param discountAmount the discountAmount to set
	 */
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	/**
	 * @return the eligibleForDiscuont
	 */
	public String getEligibleForDiscuont() {
		return eligibleForDiscuont;
	}
	/**
	 * @param eligibleForDiscuont the eligibleForDiscuont to set
	 */
	public void setEligibleForDiscuont(String eligibleForDiscuont) {
		this.eligibleForDiscuont = eligibleForDiscuont;
	}

	/**
	 * @return the isOfferAlreadyApplied
	 */
	public boolean isOfferAlreadyApplied() {
		return isOfferAlreadyApplied;
	}

	/**
	 * @param isOfferAlreadyApplied the isOfferAlreadyApplied to set
	 */
	public void setOfferAlreadyApplied(boolean isOfferAlreadyApplied) {
		this.isOfferAlreadyApplied = isOfferAlreadyApplied;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InvoiceLineItem [ ItemName=" + ItemName + ", ItemShortName="
				+ ItemShortName + ", unitPrice=" + unitPrice + ", noOfItems=" + noOfItems + ", dicsountType="
				+ dicsountType + ", discountAmount=" + discountAmount + ", total=" + total + ", eligibleForDiscuont="
				+ eligibleForDiscuont + ", isOfferAlreadyApplied=" + isOfferAlreadyApplied + "]";
	}



	
}
