package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import implementation.PredictionExpirationDate;

/**
 * @author Pedro Perrone
 */
public class PredictionExpirationDateTest {
	PredictionExpirationDate limitDates = new PredictionExpirationDate();
	TestsHelper helper = new TestsHelper();
	
	@Before
	public void setUp() {
		limitDates.newMap();
		limitDates.addExpirationDate(2017, helper.parseDate("12/12/2017"));
		limitDates.addExpirationDate(2018, helper.parseDate("06/09/2018"));
	}

	@Test
	public void testGetExpirationDateByYear() {
		assertEquals("Get the correct expiration date", helper.parseDate("12/12/2017"),
					 limitDates.getExpirationDateByYear(2017));
		assertEquals("Get the correct expiration date", helper.parseDate("06/09/2018"),
					 limitDates.getExpirationDateByYear(2018));
		assertNotNull(limitDates);
	}

	@Test
	public void testIsValidDatePerYear() {
		assertTrue("Date is before limit date", limitDates.isValidDatePerYear(2017, helper.parseDate("11/12/2017")));
		assertTrue("Date is the limit date", limitDates.isValidDatePerYear(2017, helper.parseDate("12/12/2017")));
		assertFalse("Date after limit date", limitDates.isValidDatePerYear(2017, helper.parseDate("13/12/2017")));
	}

	@Test
	public void testAddExpirationDate() {
		Map<Integer, Date> expirationDates = limitDates.getExpirationDates();
		expirationDates.put(2019, helper.parseDate("03/04/2019"));
		limitDates.addExpirationDate(2019, helper.parseDate("03/04/2019"));
		assertTrue("Adds date in the correct year", expirationDates.equals(limitDates.getExpirationDates()));
	}	
}
