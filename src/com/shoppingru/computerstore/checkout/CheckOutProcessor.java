/**
 * 
 */
package com.shoppingru.computerstore.checkout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.sun.beans.finder.PropertyEditorFinder;

/**
 * @author Sanish
 *
 */
public class CheckOutProcessor {

	// Messages

	private final static String welcomeMessage = "\n\n Please enter your iteams (type 'q' to generate the invoice) :\n";

	private final static String exitMessage = "\n***Please enter 'quit' anytime to leave from the system ***\n";

	private final static String completionLine = "\n-------------------------------------------------------------------------------------\n";

	private Scanner scanner = null;
	public List<String> userInputsErrors;
	public static HashMap availableProducts = new HashMap<String, Product>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CheckOutProcessor checkOutProcessor = new CheckOutProcessor();
		checkOutProcessor.loadAvailableProducts();
		CheckoutHelper.loadAvailableOffers();
		CheckoutHelper.loadOfferDetailsForOfferType();
		checkOutProcessor.processItems();
	}

	private void processItems() {
		// TODO Auto-generated method stub
		//Remove the below code once tested 	
		List<String> userInputs = new ArrayList<String>();
		try {
			System.out.println(welcomeMessage);
			System.out.println(exitMessage);
			scanner = new Scanner(System.in);
			while (true) {
				String scannedItem = scanner.nextLine();
				if ("quit".equalsIgnoreCase(scannedItem)) {
					exitSystem();
				} else if ("q".equalsIgnoreCase(scannedItem)) {
					processUserInputs(userInputs);
					userInputs.clear();
					System.out.println(completionLine);
					System.out.println(welcomeMessage);
				} else if (CheckoutHelper.isItemValid(scannedItem)) {
					userInputs.add(scannedItem);
				} else {
					System.out.println("Scanned item is not valid,please enter a valid item");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("\n Error in execution, please try again \n");
		}
	}

	private void exitSystem() {
		// TODO Auto-generated method stub
		scanner.close();
		System.exit(0);
	}

	private void processUserInputs(List<String> userInputs) {
		CheckoutHelper helper = new CheckoutHelper(userInputs); 
		helper.prepareInvoice();
	}

	private void loadAvailableProducts() {
		// TODO Auto-generated method stub
		CheckoutHelper.loadAvailableProducts();
	}

}
