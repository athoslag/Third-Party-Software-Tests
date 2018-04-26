package tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import implementation.RubricPerYear;
import tests.TestsHelper;

/**
 * @author Pedro Perrone
 */
public class RubricPerYearTest {
	
	RubricPerYear rubricPerYear = new RubricPerYear();
	TestsHelper helper = new TestsHelper();
	
	@Before
	public void setUp() {
		rubricPerYear.setPredictedValues(helper.predictionValues());
	}

	@Test
	public void testGetResultValues() {
		assertEquals(12, rubricPerYear.getResultValues().length);
	}
	
	@Test
	public void testSetPredictedValues() {
		rubricPerYear.setPredictedValues(invalidSizePrediction());
		assertArrayEquals("Array with invalid size, predictionValues don't change",
					 	  helper.predictionValues(), rubricPerYear.getPredictedValues());
		
		rubricPerYear.setPredictedValues(alternativePredictionValues());
		assertArrayEquals("Array with valid size, predictionValues change",
								alternativePredictionValues(), rubricPerYear.getPredictedValues());
	}

	@Test
	public void testUpdateRealValueByMonth() {
		Number realValues[] = rubricPerYear.getRealValues();
		realValues[0] = 999f;
		rubricPerYear.updateRealValueByMonth(0, 999f);
		assertArrayEquals("Update only the correct value", realValues, rubricPerYear.getRealValues());
	}

	@Test
	public void testGetMonthPredictedValue() {
		assertEquals("Get value from first month", helper.predictionValues()[0],
					 rubricPerYear.getMonthPredictedValue(0));
		assertEquals("Get value from last month", helper.predictionValues()[11],
				 	 rubricPerYear.getMonthPredictedValue(11));
		assertEquals("Get value from middle term position", helper.predictionValues()[7],
				 	 rubricPerYear.getMonthPredictedValue(7));
	}

	@Test
	public void testGetMonthResultValue() {
		Number predictedValues[] = rubricPerYear.getPredictedValues();
		rubricPerYear.updateRealValueByMonth(0, 800f);
		float result = predictedValues[0].floatValue() - 800f;
		assertEquals("Get value", rubricPerYear.getMonthResultValue(0), result);
	}

	@Test
	public void testUpdatePredictionValueByMonth() {
		Number oldList[] = rubricPerYear.getPredictedValues();
		float newValue = oldList[5].floatValue() * 3;
		Number newList[] = oldList.clone();
		newList[5] = newValue;
		rubricPerYear.updatePredictionValueByMonth(5, newValue);
		assertArrayEquals("Updates only the correct value", newList,
						  rubricPerYear.getPredictedValues());
	}

	@Test
	public void testGetMonthRealValue() {
		rubricPerYear.updateRealValueByMonth(4, 555f);
		Number realValues[] = rubricPerYear.getRealValues();
		assertEquals("Get the value from parameter index", realValues[4],
					 rubricPerYear.getMonthRealValue(4));
	}
	
	private Number[] invalidSizePrediction() {
		Number predictionList[] = new Number[] {123f, 456f};
		return predictionList;
	}

	private Number[] alternativePredictionValues() {
		Number predictionList[] = helper.predictionValues();
		for(Number monthValue : predictionList)
			monthValue = monthValue.floatValue() + 2f;
		return predictionList;
	}

}
