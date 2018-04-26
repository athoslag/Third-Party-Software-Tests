package controllers;

import exception.InvalidCodeException;
import exception.InvalidPredictionDateException;
import exception.InvalidPredictionTypeException;
import interfaces.BudgetItem;
import interfaces.BudgetItemAnnualValues;
import util.PredictionTypes;

/**
* @author Joao Vitor de Camargo
*/
public class PredictionController<UsedBudgetClass extends BudgetItem, UsedBudgetLineClass extends BudgetItemAnnualValues> {

	private BudgetItem budgetItem;
	private int year, monthIndex;
	private Class<UsedBudgetClass> usedBudgetClass;
	private Class<UsedBudgetLineClass> usedBudgetLineClass;

	public PredictionController(Class<UsedBudgetClass> usedBudgetClass, Class<UsedBudgetLineClass> usedBudgetLineClass) {
		this.usedBudgetClass = usedBudgetClass;
		this.usedBudgetLineClass = usedBudgetLineClass;

	}

	public void updatePredictionParameters(BudgetItem budgetItem, int year, int monthIndex)
			throws InvalidCodeException {
		if (budgetItem == null)
			throw new InvalidCodeException();
		this.budgetItem = budgetItem;
		this.year = year;
		this.monthIndex = monthIndex;
	}

	public boolean predict(PredictionTypes predictionType, float value) throws InvalidPredictionTypeException,
			InstantiationException, IllegalAccessException, InvalidPredictionDateException {
		Number predictionAmount = getPredictionValue(predictionType, value);
		try {
			BudgetItemAnnualValuesController<UsedBudgetLineClass> biavController = new BudgetItemAnnualValuesController<>(
					usedBudgetLineClass);
			BudgetItemController<UsedBudgetClass, UsedBudgetLineClass> budgetItemController = new BudgetItemController<>(
					usedBudgetClass, usedBudgetLineClass);

			biavController.updatePredictionValueByMonth(
					budgetItemController.getBudgetItemAnnualValues(budgetItem, year + 1), monthIndex, predictionAmount);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private Number getPredictionValue(PredictionTypes predictionType, Float value)
			throws InvalidPredictionTypeException {
		switch (predictionType) {
		case PREDICTION_COPYING_VALUES:
			return budgetItem.getAnnualProjections().get(year).getPredictedValues()[monthIndex];
		case PREDICTION_MULTIPLYING_VALUES:
			return budgetItem.getAnnualProjections().get(year).getPredictedValues()[monthIndex].floatValue() * value;
		case PREDICTION_WITH_CONSTANT_VALUE:
			return value;
		default:
			throw new InvalidPredictionTypeException();
		}
	}

}
