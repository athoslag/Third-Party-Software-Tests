package tests.IntegrationTests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import controllers.ApplicationController;
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

	ApplicationController applicationController = new ApplicationController();
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
		String monthName = "Janeiro";
		int monthIndex = MonthsController.getMonthIndex(monthName);
		PredictionTypes predictionType1 = PredictionTypes.PREDICTION_COPYING_VALUES;
		PredictionTypes predictionType2 = PredictionTypes.PREDICTION_MULTIPLYING_VALUES;
		PredictionTypes predictionType3 = PredictionTypes.PREDICTION_WITH_CONSTANT_VALUE;
		float auxiliarValue = (float) 0.0;
		
		// isso deve testar ALGUMA coisa...
		try {
			// lado da applicationController
			applicationController.predict(predictionType1, rubricCode, auxiliarValue, monthName);
			applicationController.predict(predictionType2, rubricCode, auxiliarValue, monthName);
			applicationController.predict(predictionType3, rubricCode, auxiliarValue, monthName);
			
			// lado da predictionController
			predictionController.updatePredictionParameters(
					rubricController.findByCode(rubricController.getBudgetItemList(), rubricCode), this.year, monthIndex);
			predictionController.predict(predictionType1, auxiliarValue);
			predictionController.predict(predictionType2, auxiliarValue);
			predictionController.predict(predictionType3, auxiliarValue);
			
			// a gente deveria testar e comparar o resultado de cada uma dessas duas chamadas
		} catch (InvalidCodeException | InvalidPredictionTypeException | InvalidPredictionDateException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}