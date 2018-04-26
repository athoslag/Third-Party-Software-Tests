package implementation;

import interfaces.BudgetItemAnnualValues;
import util.MonthsController;

/**
 * @author Joao Vitor de Camargo
 */
public class RubricPerYear implements BudgetItemAnnualValues {

	private Number predictedValues[];
	private Number resultValues[];
	private Number realValues[];

	public RubricPerYear() {
		this.predictedValues = new Number[MonthsController.NUMBER_OF_MONTHS];
		this.resultValues = new Number[MonthsController.NUMBER_OF_MONTHS];
		this.realValues = new Number[MonthsController.NUMBER_OF_MONTHS];
	}

	public Number[] getPredictedValues() {
		return predictedValues;
	}

	public Number[] getResultValues() {
		return resultValues;
	}

	public Number[] getRealValues() {
		return realValues;
	}

	public Number getMonthRealValue(int monthIndex) {
//		assert MonthsController.isAValidMonthIndex(monthIndex) : "Invalid month index";
		return realValues[monthIndex];
	}

	public Number getMonthPredictedValue(int monthIndex) {
//		assert MonthsController.isAValidMonthIndex(monthIndex) : "Invalid month index";
		return predictedValues[monthIndex];
	}

	public Number getMonthResultValue(int monthIndex) {
//		assert MonthsController.isAValidMonthIndex(monthIndex) : "Invalid month index";
		return resultValues[monthIndex];
	}

	public void setPredictedValues(Number[] predictedValues) {
		if (predictedValues.length == MonthsController.NUMBER_OF_MONTHS)
			this.predictedValues = predictedValues;
	}

	private void calculateResultValues() {
		for (int i = 0; i < this.resultValues.length; i++) 
			this.resultValues[i] = getValue(this.predictedValues[i]) - getValue(this.realValues[i]);
	}
	
	private float getValue(Number number) {
		return number != null ? number.floatValue() : 0f;
	}

	public void updateRealValueByMonth(int monthIndex, Number value) {
//		assert value != null : "Invalid value";
		this.realValues[monthIndex] = value;
		calculateResultValues();
	}

	public void updatePredictionValueByMonth(int monthIndex, Number value) {
//		assert value != null : "Invalid value";
		this.predictedValues[monthIndex] = value;
		calculateResultValues();
	}

}
