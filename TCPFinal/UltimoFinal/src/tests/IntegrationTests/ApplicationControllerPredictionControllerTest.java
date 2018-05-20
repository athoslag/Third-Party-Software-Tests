package tests.IntegrationTests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import controllers.BudgetItemController;
import controllers.ExpirationDateController;
import controllers.PredictionController;
import exception.InvalidCodeException;
import exception.InvalidPredictionDateException;
import exception.InvalidPredictionTypeException;
import implementation.PredictionExpirationDate;
import implementation.Rubric;
import implementation.RubricPerYear;
import interfaces.BudgetItem;
import interfaces.BudgetItemAnnualValues;
import util.MonthsController;
import util.PredictionTypes;

public class ApplicationControllerPredictionControllerTest {

	PredictionController<Rubric, RubricPerYear> predictionController = new PredictionController<>(Rubric.class, RubricPerYear.class);
	BudgetItemController<Rubric, RubricPerYear> rubricController;
	ExpirationDateController<PredictionExpirationDate> expirationDateController = new ExpirationDateController<>(PredictionExpirationDate.class);
	int year = 2017; 
	//ApplicationController usa as seguintes funções:
		//	predictionController.updatePredictionParameters
		//	predictionController.predict
	
	@Before
	public void setUp() {
		 
	}
	
	@Test
	public void testUpdatePredictionParameters() {
		int rubricCode = 12;
		int monthIndex = MonthsController.getMonthIndex("Janeiro");
		
		// isso deve testar ALGUMA coisa...
		try {
			predictionController.updatePredictionParameters(
					rubricController.findByCode(rubricController.getBudgetItemList(), rubricCode), this.year, monthIndex);
		} catch (InvalidCodeException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPredict() {
//		PredictionTypes:
//			PREDICTION_COPYING_VALUES,
//			PREDICTION_MULTIPLYING_VALUES,
//			PREDICTION_WITH_CONSTANT_VALUE;
		
		PredictionTypes predictionType = PredictionTypes.PREDICTION_MULTIPLYING_VALUES;
		float auxiliarValue = (float) 0.0;
		
		try {
			predictionController.predict(predictionType, auxiliarValue);
		} catch (InstantiationException | IllegalAccessException | InvalidPredictionTypeException | InvalidPredictionDateException e) {
			e.printStackTrace();
		}
	}

}