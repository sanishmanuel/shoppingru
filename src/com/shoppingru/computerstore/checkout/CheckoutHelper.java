/**
 * 
 */
package com.shoppingru.computerstore.checkout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Sanish
 *
 */
public class CheckoutHelper {

	public static HashMap<String, Product> availableProducts = new HashMap<String, Product>();
	public static HashMap<String, ArrayList<OfferType>> availableOffers = new HashMap<String, ArrayList<OfferType>>();
	public static HashMap<String, ArrayList<?>> offerDetailsForOfferType = new HashMap<String, ArrayList<?>>();

	private final static String productsFile = "/resources/product.txt";
	private final static String availableOfferTypesResource = "/resources/available_offer_types.txt";

	private final static String deal_on_bulk = "/resources/deal_on_bulk.txt";
	private final static String deal_on_bundle = "/resources/deal_on_bundle.txt";
	private final static String deal_on_numbers = "/resources/deal_on_numbers.txt";

	private List<String> userInputs;
	private HashMap<String, InvoiceLineItem> invoiceItems;
	private HashMap<String, InvoiceLineItem> invoiceItemsFreeProducts = new HashMap<String, InvoiceLineItem>();

	public CheckoutHelper(List<String> userInputs) {
		this.userInputs = userInputs;
	}

	public static void loadAvailableProducts() {
		// TODO Auto-generated method stub
		availableProducts = getAvailableProductsMapped(FileHelper.loadDetailsfromFile(productsFile));
	}

	public static boolean isItemValid(String scannedItem) {
		// TODO Auto-generated method stub
		if (!availableProducts.containsKey(scannedItem)) {
			return false;
		}
		return true;
	}

	private static HashMap<String, Product> getAvailableProductsMapped(List<String> availableProductList) {
		// TODO Auto-generated method stub
		HashMap<String, Product> availableProductsTmp = new HashMap<String, Product>();
		for (String productString : availableProductList) {
			String productDetials[] = productString.split(",");
			Product product = new Product(productDetials[2], productDetials[1], Integer.parseInt(productDetials[0]),
					BigDecimal.valueOf(Double.parseDouble(productDetials[3])), productDetials[4]);
			availableProductsTmp.put(product.getProductShortName(), product);
		}
		return availableProductsTmp;
	}

	public static void loadOfferDetailsForOfferType() {
		// TODO Auto-generated method stub
		for (Map.Entry<String, ArrayList<OfferType>> entry : availableOffers.entrySet()) {
			ArrayList<OfferType> offerTypes = entry.getValue();
			for (OfferType offerType : offerTypes) {
				if ("deal_on_numbers".equalsIgnoreCase(offerType.getDiscountType())) {
					loadDealOnNumbers(deal_on_numbers, entry.getKey());
				}
				if ("deal_on_bulk".equalsIgnoreCase(offerType.getDiscountType())) {
					loadDealOnNBulk(deal_on_bulk, entry.getKey());
				}
				if ("deal_on_bundle".equalsIgnoreCase(offerType.getDiscountType())) {
					loadDealOnNBundle(deal_on_bundle, entry.getKey());
				}
			}
		}
	}

	public static void loadAvailableOffers() {
		if (availableOffers.isEmpty()) {
			List<String> availableOfferTypes = FileHelper.loadDetailsfromFile(availableOfferTypesResource);
			populateOfferTypeMap(availableOfferTypes);
		}
	}

	public void prepareInvoice() {
		// TODO Auto-generated method stub
		invoiceItemsFreeProducts.clear();
		invoiceItems = prepareInvoiceLineItem(this.userInputs);
		applySalesRules(invoiceItems);
	}

	private void applySalesRules(HashMap<String, InvoiceLineItem> invoiceItems) {
		// TODO Auto-generated method stub
		for (Map.Entry<String, InvoiceLineItem> entry : invoiceItems.entrySet()) {
			if (availableOffers.containsKey(entry.getKey()) && !entry.getValue().isOfferAlreadyApplied()) {
				findBestOfferAndApply(entry.getValue(), availableOffers.get(entry.getKey()));
			}
		}
		addFreeProductsToInvoice();
		printInvoice();
	}

	private void printInvoice() {
		// TODO Auto-generated method stub
		// To print and see items are added properly
		BigDecimal total = new BigDecimal(0.00);
		int i=1;
		for (Map.Entry<String, InvoiceLineItem> entry : invoiceItems.entrySet()) {
			System.out.println(" Item No."+i+"  "+ entry.getValue());
			total = total.add(entry.getValue().getTotal());
			i++;
		}
		System.out.println("Priting grand total = "+total);
	}

	private void addFreeProductsToInvoice() {
		// TODO Auto-generated method stub
		InvoiceLineItem item = null;
		for (Map.Entry<String, InvoiceLineItem> entry : invoiceItemsFreeProducts.entrySet()) {
			if (invoiceItems.containsKey(entry.getKey())) {
				item = invoiceItems.get(entry.getKey());
				item.setNoOfItems(item.getNoOfItems() + entry.getValue().getNoOfItems());
				if (item.getDiscountAmount() != null)
					item.setDiscountAmount(item.getDiscountAmount().add(entry.getValue().getUnitPrice()));
			} else {
				invoiceItems.put(entry.getKey(), entry.getValue());
			}
		}
	}

	private void findBestOfferAndApply(InvoiceLineItem lineItem, ArrayList<OfferType> offerTypes) {
		// we can have an improvement here if we are planning to give more than
		// one type of offer for a
		// particular product
		for (OfferType offerType : offerTypes) {
			if ("Y".equalsIgnoreCase(offerType.getOfferOnProduct())) {
				if ("deal_on_numbers".equalsIgnoreCase(offerType.getDiscountType())) {
					applyDealOnNumbers(lineItem, "deal_on_numbers");
				}
				if ("deal_on_bundle".equalsIgnoreCase(offerType.getDiscountType())) {
					applyDealOnBundle(lineItem, "deal_on_bundle");
				}
				if ("deal_on_bulk".equalsIgnoreCase(offerType.getDiscountType())) {
					applyDealOnBulk(lineItem, "deal_on_bulk");
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void applyDealOnBulk(InvoiceLineItem lineItem, String discountType) {
		// TODO Auto-generated method stub
		ArrayList dealsForBulk = (ArrayList) offerDetailsForOfferType.get(lineItem.getItemShortName());
		for (Object dealsForBulkObj : dealsForBulk) {
			if (dealsForBulkObj instanceof DealOnBulkOffer) {
				if (discountType.equals(((DealOnBulkOffer) dealsForBulkObj).getDealType())
						&& lineItem.getNoOfItems() > ((DealOnBulkOffer) dealsForBulkObj).getItemsRequiredForDeal()) {

					BigDecimal offeredValueForUnit = ((DealOnBulkOffer) dealsForBulkObj).getOfferPrice();
					BigDecimal actualAmount = lineItem.getUnitPrice().multiply(new BigDecimal(lineItem.getNoOfItems()));
					BigDecimal discountAmount = actualAmount
							.subtract(offeredValueForUnit.multiply(new BigDecimal(lineItem.getNoOfItems())));
					lineItem.setDiscountAmount(discountAmount);
					lineItem.setTotal(lineItem.getTotal().subtract(discountAmount));
					lineItem.setOfferAlreadyApplied(true);
				}
			}
		}
	}

	private void applyDealOnBundle(InvoiceLineItem lineItem, String discountType) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		ArrayList dealsForBundle = (ArrayList) offerDetailsForOfferType.get(lineItem.getItemShortName());
		Integer freeProducts = 0;
		for (Object dealsForBundleObj : dealsForBundle) {
			if (dealsForBundleObj instanceof DealOnBundleOffer) {
				if (discountType.equals(((DealOnBundleOffer) dealsForBundleObj).getDealType())
						&& lineItem.getItemShortName()
								.equalsIgnoreCase(((DealOnBundleOffer) dealsForBundleObj).getDealOnProductShortName())
						&& lineItem.getNoOfItems() >= ((DealOnBundleOffer) dealsForBundleObj)
								.getItemsRequiredForDeal()) {

					freeProducts = lineItem.getNoOfItems()
							/ ((DealOnBundleOffer) dealsForBundleObj).getItemsRequiredForDeal();
					String freeProductShorTname = ((DealOnBundleOffer) dealsForBundleObj).getOfferedProductShortName();
					lineItem.setOfferAlreadyApplied(true);
					System.out.println("Free products >>" + freeProducts);
					addItemTofreeProductsMap(freeProductShorTname, freeProducts);
				}
			}
		}

	}

	private void addItemTofreeProductsMap(String freeProductShorTname, Integer freeProducts) {
		// TODO Auto-generated method stub
		Product product = (Product) availableProducts.get(freeProductShorTname);
		InvoiceLineItem item = null;
		if (invoiceItemsFreeProducts.containsKey(product.getProductShortName())) {
			item = invoiceItemsFreeProducts.get(product.getProductShortName());
			item.setNoOfItems(item.getNoOfItems() + freeProducts);
			item.setDiscountAmount(item.getUnitPrice().multiply(new BigDecimal(freeProducts)));
		} else {
			item = new InvoiceLineItem(product.getProductName(), product.getProductShortName(), product.getUnitPrice(),
					product.getEligibleForDiscuont());
			item.setNoOfItems(freeProducts);
			item.setUnitPrice(product.getUnitPrice());
			item.setTotal(new BigDecimal(0.00));
			invoiceItemsFreeProducts.put(product.getProductShortName(), item);
		}
	}

	@SuppressWarnings("unchecked")
	private void applyDealOnNumbers(InvoiceLineItem lineItem, String discountType) {
		// TODO Auto-generated method stub
		// System.out.println("inside apply deal on numbers ");
		ArrayList dealsForNumbers = (ArrayList) offerDetailsForOfferType.get(lineItem.getItemShortName());
		for (Object dealsForNumberObj : dealsForNumbers) {
			if (dealsForNumberObj instanceof DealOnNumberOffer) {
				DealOnNumberOffer dealsForNumberObjTmp = (DealOnNumberOffer) dealsForNumberObj;
				if (discountType.equals(dealsForNumberObjTmp.getDealType())
						&& lineItem.getNoOfItems() >= dealsForNumberObjTmp.getItemsRequiredForDeal()) {

					Integer freeProducts = lineItem.getNoOfItems() / dealsForNumberObjTmp.getItemsRequiredForDeal();
					BigDecimal valueOfthefreeItem = lineItem.getUnitPrice().multiply(new BigDecimal(freeProducts));
					lineItem.setDiscountAmount(valueOfthefreeItem);
					lineItem.setTotal(lineItem.getTotal().subtract(valueOfthefreeItem));
					lineItem.setOfferAlreadyApplied(true);
				}
			}
		}
	}

	private static void loadDealOnNBundle(String resourceName, String productShortName) {
		// TODO Auto-generated method stub
		List<String> dealOnBundleString = FileHelper.loadDetailsfromFile(resourceName);
		DealOnBundleOffer dealOnBundleOffer = null;
		ArrayList dealsForBundle = new ArrayList();
		for (String bundleOfferString : dealOnBundleString) {
			String bundleOfferArray[] = bundleOfferString.split(",");
			dealOnBundleOffer = new DealOnBundleOffer(Integer.parseInt(bundleOfferArray[0]), bundleOfferArray[1],
					bundleOfferArray[2], Integer.parseInt(bundleOfferArray[3]), Integer.parseInt(bundleOfferArray[4]),
					bundleOfferArray[5]);
			if (dealOnBundleOffer.getDealOnProductShortName().equalsIgnoreCase(productShortName)) {
				dealsForBundle.add(dealOnBundleOffer);
			}
		}
		if (offerDetailsForOfferType.containsKey(productShortName)) {
			ArrayList tmpList = (ArrayList) offerDetailsForOfferType.get(productShortName);
			tmpList.addAll(dealsForBundle);
		} else {
			offerDetailsForOfferType.put(productShortName, dealsForBundle);
		}
	}

	private static void loadDealOnNBulk(String resourceName, String productShortName) {
		// TODO Auto-generated method stub
		List<String> dealOnNumbersString = FileHelper.loadDetailsfromFile(resourceName);
		DealOnBulkOffer dealOnBulkOffer = null;
		ArrayList dealsFoBulk = new ArrayList();
		for (String offerTypeString : dealOnNumbersString) {
			String offerTypesArray[] = offerTypeString.split(",");
			dealOnBulkOffer = new DealOnBulkOffer(Integer.parseInt(offerTypesArray[0]), offerTypesArray[1],
					offerTypesArray[2], Integer.parseInt(offerTypesArray[3]),
					BigDecimal.valueOf(Double.valueOf(offerTypesArray[4])));
			if (dealOnBulkOffer.getDealOnProductShortName().equalsIgnoreCase(productShortName)) {
				dealsFoBulk.add(dealOnBulkOffer);
			}
		}
		if (offerDetailsForOfferType.containsKey(productShortName)) {
			ArrayList tmpList = (ArrayList) offerDetailsForOfferType.get(productShortName);
			tmpList.addAll(dealsFoBulk);
		} else {
			offerDetailsForOfferType.put(productShortName, dealsFoBulk);
		}
	}

	private static void loadDealOnNumbers(String resourceName, String productShortName) {
		List<String> dealOnNumbersString = FileHelper.loadDetailsfromFile(resourceName);
		DealOnNumberOffer dealOnNumberOffer = null;
		ArrayList dealsForNumber = new ArrayList();
		for (String offerTypeString : dealOnNumbersString) {
			String offerTypesArray[] = offerTypeString.split(",");
			dealOnNumberOffer = new DealOnNumberOffer(Integer.parseInt(offerTypesArray[0]), offerTypesArray[1],
					offerTypesArray[2], Integer.parseInt(offerTypesArray[3]), Integer.parseInt(offerTypesArray[4]));
			if (dealOnNumberOffer.getProductShortName().equalsIgnoreCase(productShortName)) {
				dealsForNumber.add(dealOnNumberOffer);
			}
		}
		if (offerDetailsForOfferType.containsKey(productShortName)) {
			ArrayList tmpList = (ArrayList) offerDetailsForOfferType.get(productShortName);
			tmpList.addAll(dealsForNumber);
		} else {
			offerDetailsForOfferType.put(productShortName, dealsForNumber);
		}
	}

	private static void populateOfferTypeMap(List<String> offerTypeslst) {
		// TODO Auto-generated method stub
		ArrayList<OfferType> offerTypes;
		OfferType offerType;
		for (String offerTypeString : offerTypeslst) {
			String offerTypesArray[] = offerTypeString.split(",");
			offerType = new OfferType(Integer.parseInt(offerTypesArray[0]), offerTypesArray[1], offerTypesArray[2],
					offerTypesArray[3]);
			if (availableOffers.containsKey(offerType.productShortName)) {
				((ArrayList<OfferType>) availableOffers.get(offerType.productShortName)).add(offerType);
			} else {
				offerTypes = new ArrayList<OfferType>();
				offerTypes.add(offerType);
				availableOffers.put(offerType.productShortName, offerTypes);
			}
		}
	}

	/*
	 * 
	 */

	private HashMap<String, InvoiceLineItem> prepareInvoiceLineItem(List<String> userInputs) {

		HashMap<String, InvoiceLineItem> lineItemMap = new HashMap<String, InvoiceLineItem>();
		InvoiceLineItem item = null;
		for (String scannedItem : userInputs) {
			Product product = (Product) availableProducts.get(scannedItem);
			if (lineItemMap.containsKey(product.getProductShortName())) {
				item = lineItemMap.get(product.getProductShortName());
				item.setNoOfItems(item.getNoOfItems() + 1);
				item.setTotal(item.getTotal().add(product.getUnitPrice()));
			} else {
				item = new InvoiceLineItem(product.getProductName(), product.getProductShortName(),
						product.getUnitPrice(), product.getEligibleForDiscuont());
				item.setNoOfItems(1);
				item.setTotal(product.getUnitPrice());
				lineItemMap.put(product.getProductShortName(), item);
			}

		}
		return lineItemMap;
		// TODO Auto-generated method stub

	}

	/**
	 * @return the userInputs
	 */
	public List<String> getUserInputs() {
		return userInputs;
	}

	/**
	 * @param userInputs
	 *            the userInputs to set
	 */
	public void setUserInputs(List<String> userInputs) {
		this.userInputs = userInputs;
	}

}
