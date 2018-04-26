package tests;

import org.junit.*;
import static org.junit.Assert.*; 

import controllers.ExpirationDateController;
import implementation.PredictionExpirationDate;

/**
 * @author Pedro Perrone
 */
public class ExpirationDateControllerTest {
	ExpirationDateController<PredictionExpirationDate> expirationController = new ExpirationDateController<>(PredictionExpirationDate.class);
	TestsHelper helper = new TestsHelper();

	@Test
	public void testSetExpirationDateForYear() {
		try {
			PredictionExpirationDate predictionDate = (PredictionExpirationDate) expirationController.setExpirationDateForYear(2017, helper.parseDate("12/12/2017"));
			assertEquals("Handles PredictionExpirationDate correctly", helper.parseDate("12/12/2017"), predictionDate.getExpirationDateByYear(2017));
		} catch (InstantiationException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		}
	}

	@Test
	public void testIsValidDatePerYearIntDate() {
		try {
			expirationController.setExpirationDateForYear(2017, helper.parseDate("12/12/2017"));
			assertTrue("Before limit date", expirationController.isValidDatePerYear(2017, helper.parseDate("11/12/2017")));
			assertTrue("In the limit date", expirationController.isValidDatePerYear(2017, helper.parseDate("12/12/2017")));
			assertFalse("After limit date", expirationController.isValidDatePerYear(2017, helper.parseDate("13/12/2017")));
		} catch (InstantiationException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		}
	}

}
