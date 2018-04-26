package tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import controllers.PredictionController;
import exception.InvalidCodeException;
import exception.InvalidPredictionDateException;
import exception.InvalidPredictionTypeException;
import implementation.Rubric;
import implementation.RubricPerYear;
import interfaces.BudgetItem;
import interfaces.BudgetItemAnnualValues;
import util.PredictionTypes;

/**
 * @author Pedro Perrone
 */
public class PredictionControllerTest {
	PredictionController<Rubric, RubricPerYear> predictionController = new PredictionController<>(Rubric.class, RubricPerYear.class);
	BudgetItem rubric = new Rubric();
	RubricPerYear rubricPerYear = new RubricPerYear();
	TestsHelper helper = new TestsHelper();
	
	@Before
	public void setUp() {
		rubric = helper.initializeRubric(1, "Test Rubric", "1");
		rubricPerYear.setPredictedValues(helper.predictionValues());
		Map<Integer, BudgetItemAnnualValues> projectionsHash = new HashMap<Integer, BudgetItemAnnualValues>();
		projectionsHash.put(2017, rubricPerYear);
		rubric.setAnnualProjections(projectionsHash);
	}
	
	@Test
	public void testUpdatePredictionParametersWithNullBI() {
	    try {
			predictionController.updatePredictionParameters(null, 2017, 10);
			fail("Exeption should be thrown");
		} catch (InvalidCodeException e) {
			assertThat(e, instanceOf(InvalidCodeException.class));
		}
	}

	@Test
	public void testUpdatePredictionParameters() {
		try {
			predictionController.updatePredictionParameters(rubric, 2017, 10);
		} catch (InvalidCodeException e) {
			fail("Should not throw exeptions");
		}
	}

	@Test
	public void testPredict() {
		try {
			try {
				predictionController.updatePredictionParameters(rubric, 2017, 0);
			} catch (InvalidCodeException e) {
				fail("Should not throw exeption ".concat(e.getMessage()));
			}
			assertTrue(predictionController.predict(PredictionTypes.PREDICTION_COPYING_VALUES, 1000f));
			assertTrue(predictionController.predict(PredictionTypes.PREDICTION_MULTIPLYING_VALUES, 1000f));
			assertTrue(predictionController.predict(PredictionTypes.PREDICTION_WITH_CONSTANT_VALUE, 1000f));
		} catch (InstantiationException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
		} catch (IllegalAccessException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
		} catch (InvalidPredictionTypeException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
		} catch (InvalidPredictionDateException e) {
			fail("Should not throw exeption ".concat(e.getMessage()));
		}
	}

}
