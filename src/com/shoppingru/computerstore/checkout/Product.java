/**
 * 
 */
package com.shoppingru.computerstore.checkout;

import java.math.BigDecimal;

/**
 * @author Sanish
 *
 */
public class Product {

	private String productName;

	private String productShortName;

	private Integer productId;

	private BigDecimal unitPrice;
	
	private String eligibleForDiscuont;


	/**
	 * @param productName
	 * @param productShortName
	 * @param productId
	 * @param unitPrice
	 */
	public Product(String productName, String productShortName, Integer productId, BigDecimal unitPrice,String eligibleForDiscuont) {
		this.productName = productName;
		this.productShortName = productShortName;
		this.productId = productId;
		this.unitPrice = unitPrice;
		this.eligibleForDiscuont =eligibleForDiscuont;
	}

	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice
	 *            the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName
	 *            the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the productShortName
	 */
	public String getProductShortName() {
		return productShortName;
	}

	/**
	 * @param productShortName
	 *            the productShortName to set
	 */
	public void setProductShortName(String productShortName) {
		this.productShortName = productShortName;
	}

	/**
	 * @return the productId
	 */
	public Integer getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
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


}
