package controllers;

import java.util.HashMap;
import java.util.Map;

import interfaces.BudgetItemAnnualValues;
import util.MonthsController;

/**
* @author Joao Vitor de Camargo
*/
public class BudgetItemAnnualValuesController<someBIAnnualValuesClass extends BudgetItemAnnualValues> {

	private Class<someBIAnnualValuesClass> usedClass;
	private static final int VALUES_LINE_STARTING_INDEX = 3;

	public BudgetItemAnnualValuesController(Class<someBIAnnualValuesClass> usedClass) {
		this.usedClass = usedClass;
	}

	public BudgetItemAnnualValues updateRealValueByMonth(BudgetItemAnnualValues biAnnualValues, int monthIndex, float realValue) {
		assert MonthsController.isAValidMonthIndex(monthIndex) : "Invalid month index";
		biAnnualValues.updateRealValueByMonth(monthIndex, realValue);
		return biAnnualValues;
	}

	public void updatePredictionValueByMonth(BudgetItemAnnualValues biAnnualValues, int monthIndex, Number predictionValue) {
		assert MonthsController.isAValidMonthIndex(monthIndex) : "Invalid month index";
		biAnnualValues.updatePredictionValueByMonth(monthIndex, predictionValue);
	}

	public Map<Integer, BudgetItemAnnualValues> buildProjectionsHash(String[] fileLine, int year)
			throws InstantiationException, IllegalAccessException, NullPointerException {
		
		int monthIndex = 0;
		BudgetItemAnnualValues biAnnualValues = usedClass.newInstance();
		Map<Integer, BudgetItemAnnualValues> projections = new HashMap<Integer, BudgetItemAnnualValues>();
		Number[] monthValues = new Number[MonthsController.NUMBER_OF_MONTHS];

		while (monthIndex < MonthsController.NUMBER_OF_MONTHS) {
			String projection = fileLine[monthIndex + VALUES_LINE_STARTING_INDEX].replace(".", "");
			monthValues[monthIndex++] = Float.parseFloat(projection);
		}
		
		biAnnualValues.setPredictedValues(monthValues);
		projections.put(year, biAnnualValues);
		return projections;
	}

}
