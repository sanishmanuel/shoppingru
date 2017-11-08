/**
 * 
 */
package com.shoppingru.computerstore.checkout;

/**
 * @author Sanish
 *
 */
public class OfferType {

	 Integer id;
	 String discountType;
	 String offerOnProduct;
	 String productShortName;
	 
	/**
	 * @param discountType
	 * @param offerOnProduct
	 * @param productShortName
	 */
	public OfferType(Integer id, String productShortName,String discountType, String offerOnProduct) {
		this.id= id;
		this.discountType = discountType;
		this.offerOnProduct = offerOnProduct;
		this.productShortName = productShortName;
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
	 * @return the discountType
	 */
	public String getDiscountType() {
		return discountType;
	}
	/**
	 * @param discountType the discountType to set
	 */
	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}
	/**
	 * @return the offerOnProduct
	 */
	public String getOfferOnProduct() {
		return offerOnProduct;
	}
	/**
	 * @param offerOnProduct the offerOnProduct to set
	 */
	public void setOfferOnProduct(String offerOnProduct) {
		this.offerOnProduct = offerOnProduct;
	}
	
	 
}
