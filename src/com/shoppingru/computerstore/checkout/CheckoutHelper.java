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

	public void prepareInvoice() {
		// TODO Auto-generated method stub
		System.out.println("From prepare invoice ");
		invoiceItems = prepareInvoiceLineItem(this.userInputs);
		applySalesRules(invoiceItems);
	}

	private void applySalesRules(HashMap<String, InvoiceLineItem> invoiceItems) {
		// TODO Auto-generated method stub
		loadAvailableOffers();
		for (Map.Entry<String, InvoiceLineItem> entry : invoiceItems.entrySet()) {
			if (availableOffers.containsKey(entry.getKey()) && !entry.getValue().isOfferAlreadyApplied()) {
				findBestOfferAndApply(entry.getValue(), availableOffers.get(entry.getKey()));
			}
			addFreeProductsToInvoice();
		}

		//To print and see items are added properly 
		for (Map.Entry<String, InvoiceLineItem> entry : invoiceItems.entrySet()) {
			System.out.println("Pritng invoice line item >>>>>>>>" + entry.getValue());
		}
	}

	private void addFreeProductsToInvoice() {
		// TODO Auto-generated method stub
		if (!invoiceItemsFreeProducts.isEmpty()) {
			InvoiceLineItem item = null;
			for (Map.Entry<String, InvoiceLineItem> entry : invoiceItemsFreeProducts.entrySet()) {
				if (invoiceItems.containsKey(entry.getKey())) {
					item = invoiceItems.get(entry.getKey());
					item.setNoOfItems(item.getNoOfItems() + 1);
					item.setDiscountAmount(item.getDiscountAmount().add(entry.getValue().getUnitPrice()));
				} else {
					invoiceItems.put(entry.getKey(), entry.getValue());
				}
			}
		}
	}

	private void findBestOfferAndApply(InvoiceLineItem lineItem, ArrayList<OfferType> offerTypes) {
		// we can have an improvement here if we are planning to give more than
		// one type of offer for a
		// particular product
		for (OfferType offerType : offerTypes) {
			if ("Y".equalsIgnoreCase(offerType.getOfferOnProduct())) {

				if (offerDetailsForOfferType.isEmpty()
						|| !offerDetailsForOfferType.containsKey(lineItem.getItemShortName())) {
					loadOfferDetailsForOfferType(offerType.getDiscountType(), lineItem.getItemShortName());
				}
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

	private void applyDealOnBulk(InvoiceLineItem lineItem, String string) {
		// TODO Auto-generated method stub

	}

	private void applyDealOnBundle(InvoiceLineItem lineItem, String discountType) {
		// TODO Auto-generated method stub
		System.out.println("inside apply deal on bubdle ");
		@SuppressWarnings("unchecked")
		ArrayList<DealOnBundleOffer> dealsForBundle = (ArrayList<DealOnBundleOffer>) offerDetailsForOfferType
				.get(lineItem.getItemShortName());
		for (DealOnBundleOffer dealsForBundleObj : dealsForBundle) {
			if (discountType.equals(dealsForBundleObj.getDealType())
					&& lineItem.getItemShortName().equalsIgnoreCase(dealsForBundleObj.getDealOnProductShortName())
					&& lineItem.getNoOfItems() >= dealsForBundleObj.getItemsRequiredForDeal()) {

				Integer freeProducts = lineItem.getNoOfItems() / dealsForBundleObj.getItemsRequiredForDeal();
				System.out.println("priting free products >>>>>>> " + freeProducts);
				String freeProductShorTname = dealsForBundleObj.getOfferedProductShortName();
				lineItem.setOfferAlreadyApplied(true);
				addItemTofreeProductsMap(freeProductShorTname);
			}
		}

	}

	private void addItemTofreeProductsMap(String freeProductShorTname) {
		// TODO Auto-generated method stub
		Product product = (Product) availableProducts.get(freeProductShorTname);
		InvoiceLineItem item = null;
		if (invoiceItemsFreeProducts.containsKey(product.getProductShortName())) {
			item = invoiceItemsFreeProducts.get(product.getProductShortName());
			item.setNoOfItems(item.getNoOfItems() + 1);
		} else {
			item = new InvoiceLineItem(product.getProductName(), product.getProductShortName(), product.getUnitPrice(),
					product.getEligibleForDiscuont());
			item.setNoOfItems(1);
			item.setTotal(product.getUnitPrice());
			item.setTotal(new BigDecimal(0.00));
			invoiceItemsFreeProducts.put(product.getProductShortName(), item);
		}
	}

	@SuppressWarnings("unchecked")
	private void applyDealOnNumbers(InvoiceLineItem lineItem, String discountType) {
		// TODO Auto-generated method stub
		System.out.println("inside apply deal on numbers ");
		ArrayList<DealOnNumberOffer> dealsForNumbers = (ArrayList<DealOnNumberOffer>) offerDetailsForOfferType
				.get(lineItem.getItemShortName());
		for (DealOnNumberOffer dealsForNumberObj : dealsForNumbers) {
			if (discountType.equals(dealsForNumberObj.getDealType())
					&& lineItem.getNoOfItems() >= dealsForNumberObj.getItemsRequiredForDeal()) {

				Integer freeProducts = lineItem.getNoOfItems() / dealsForNumberObj.getItemsRequiredForDeal();
				System.out.println("priting free products >>>>>>> " + freeProducts);
				BigDecimal valueOfthefreeItem = lineItem.getUnitPrice().multiply(new BigDecimal(freeProducts));
				lineItem.setDiscountAmount(valueOfthefreeItem);
				lineItem.setTotal(lineItem.getTotal().subtract(valueOfthefreeItem));
				lineItem.setOfferAlreadyApplied(true);
			}
		}
	}

	private void loadAvailableOffers() {
		if (availableOffers.isEmpty()) {
			List<String> availableOfferTypes = FileHelper.loadDetailsfromFile(availableOfferTypesResource);
			populateOfferTypeMap(availableOfferTypes);
		}
	}

	private void loadOfferDetailsForOfferType(String offerType, String productShortName) {
		if (offerDetailsForOfferType.isEmpty() || !offerDetailsForOfferType.containsKey(productShortName)) {
			String resourceName;
			if ("deal_on_numbers".equalsIgnoreCase(offerType)) {
				resourceName = deal_on_numbers;
				loadDealOnNumbers(resourceName, productShortName);
			}
			if ("deal_on_bulk".equalsIgnoreCase(offerType)) {
				resourceName = deal_on_bulk;
				loadDealOnNBulk(resourceName, productShortName);
			}
			if ("deal_on_bundle".equalsIgnoreCase(offerType)) {
				resourceName = deal_on_bundle;
				loadDealOnNBundle(resourceName, productShortName);
			}
		}
	}

	private void loadDealOnNBundle(String resourceName, String productShortName) {
		// TODO Auto-generated method stub
		List<String> dealOnBundleString = FileHelper.loadDetailsfromFile(resourceName);
		DealOnBundleOffer dealOnBundleOffer = null;
		ArrayList<DealOnBundleOffer> dealsForBundle = new ArrayList<DealOnBundleOffer>();
		for (String bundleOfferString : dealOnBundleString) {
			String bundleOfferArray[] = bundleOfferString.split(",");
			dealOnBundleOffer = new DealOnBundleOffer(Integer.parseInt(bundleOfferArray[0]), bundleOfferArray[1],
					bundleOfferArray[2], Integer.parseInt(bundleOfferArray[3]), Integer.parseInt(bundleOfferArray[4]),
					bundleOfferArray[5]);
			dealsForBundle.add(dealOnBundleOffer);
			offerDetailsForOfferType.put(productShortName, dealsForBundle);
		}

	}

	private void loadDealOnNBulk(String resourceName, String productShortName) {
		// TODO Auto-generated method stub
		List<String> dealOnNumbersString = FileHelper.loadDetailsfromFile(resourceName);
		DealOnNumberOffer dealOnNumberOffer = null;
		ArrayList<DealOnNumberOffer> dealsForNumber = new ArrayList<DealOnNumberOffer>();
		for (String offerTypeString : dealOnNumbersString) {
			String offerTypesArray[] = offerTypeString.split(",");
			dealOnNumberOffer = new DealOnNumberOffer(Integer.parseInt(offerTypesArray[0]), offerTypesArray[1],
					offerTypesArray[2], Integer.parseInt(offerTypesArray[3]), Integer.parseInt(offerTypesArray[4]));
			dealsForNumber.add(dealOnNumberOffer);
			offerDetailsForOfferType.put(productShortName, dealsForNumber);
		}

	}

	private void loadDealOnNumbers(String resourceName, String productShortName) {

		List<String> dealOnNumbersString = FileHelper.loadDetailsfromFile(resourceName);
		DealOnNumberOffer dealOnNumberOffer = null;
		ArrayList<DealOnNumberOffer> dealsForNumber = new ArrayList<DealOnNumberOffer>();
		for (String offerTypeString : dealOnNumbersString) {
			String offerTypesArray[] = offerTypeString.split(",");
			dealOnNumberOffer = new DealOnNumberOffer(Integer.parseInt(offerTypesArray[0]), offerTypesArray[1],
					offerTypesArray[2], Integer.parseInt(offerTypesArray[3]), Integer.parseInt(offerTypesArray[4]));
			dealsForNumber.add(dealOnNumberOffer);
			offerDetailsForOfferType.put(productShortName, dealsForNumber);
		}

	}

	private void populateOfferTypeMap(List<String> offerTypeslst) {
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
