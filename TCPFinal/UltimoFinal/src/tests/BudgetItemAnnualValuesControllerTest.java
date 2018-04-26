package tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import controllers.BudgetItemAnnualValuesController;
import implementation.RubricPerYear;
import interfaces.BudgetItemAnnualValues;

/**
 * @author Pedro Perrone
 */
public class BudgetItemAnnualValuesControllerTest {
	
	BudgetItemAnnualValuesController<RubricPerYear> perYearController = new BudgetItemAnnualValuesController<RubricPerYear>(RubricPerYear.class);
	RubricPerYear rubricPerYear = new RubricPerYear();
	TestsHelper helper = new TestsHelper();
	
	@Before
	public void setUp() {
		rubricPerYear.setPredictedValues(helper.predictionValues());
	}

	@Test
	public void testUpdateRealValueByMonth() {
		assertEquals("Returns RubricPerYear", rubricPerYear,
					 perYearController.updateRealValueByMonth(rubricPerYear, 0, 1500f));
		assertEquals("Value was updated", 1500f, rubricPerYear.getMonthRealValue(0));
	}

	@Test
	public void testUpdatePredictionValueByMonth() {
		perYearController.updatePredictionValueByMonth(rubricPerYear, 3, 4567f);
		assertEquals("Value was updated", 4567f, rubricPerYear.getMonthPredictedValue(3));
	}

	@Test
	public void testBuildProjectionsHash() {
		try {
			Map<Integer, BudgetItemAnnualValues> resultHash = perYearController.buildProjectionsHash(mockFileLine(), 2017);
			assertArrayEquals("Assert RubricPerYear prediction values", valuesList(),
							  resultHash.get(2017).getPredictedValues());
		} catch (InstantiationException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (NullPointerException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBuildProjectionsHashNullPointer() {
		try {
			perYearController.buildProjectionsHash(null, 2017);
			fail("Should throw exepction");
		} catch (InstantiationException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
			e.printStackTrace();
		} catch (NullPointerException e) {
			assertThat(e, instanceOf(NullPointerException.class));
		}
	}

	private String[] mockFileLine() {
		String[] fileLine = new String(";;;1;2;3;4;5;6;7;8;9;10;11;12;;N").split(";");
		return fileLine;
	}
	
	private Number[] valuesList() {
		Number[] list = { 1f, 2f, 3f, 4f, 5f, 6f, 7f, 8f, 9f, 10f, 11f, 12f };
		return list;
	}
}
