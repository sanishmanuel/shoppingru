package com.test.shoppingru.computerstore.checkout;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.shoppingru.computerstore.checkout.*;

public class CheckoutHelperTest {

	 private CheckoutHelper  checoutHelper;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CheckoutHelper.loadAvailableProducts();
		CheckoutHelper.loadAvailableOffers();
		CheckoutHelper.loadOfferDetailsForOfferType();
	}

	@Test
	public void testingForExtraFields() {
		List<String> validInputs = new ArrayList<String>();
		validInputs.add("mbp");
		validInputs.add("mbp");
		checoutHelper = new CheckoutHelper(validInputs);
		checoutHelper.prepareInvoice();
		Assert.assertEquals(2, checoutHelper.getUserInputs().size());
	}


	@Test
	/*
	 * testing valid product check .
	 */
	public void testingValidProductCheck() {
			Assert.assertTrue(CheckoutHelper.isItemValid("mbp"));
	}
	
	@Test
	/*
	 * testing valid product check .
	 */
	public void testingValidProductCheckFailScenario() {
			Assert.assertFalse(CheckoutHelper.isItemValid("mba"));
	}
	

}
