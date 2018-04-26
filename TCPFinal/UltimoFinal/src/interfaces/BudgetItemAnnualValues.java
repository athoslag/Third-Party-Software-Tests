package interfaces;

/**
 * @author Joao Vitor de Camargo
 */
public interface BudgetItemAnnualValues {
	
	public Number[] getPredictedValues();

	public Number[] getResultValues();

	public Number[] getRealValues();

	public Number getMonthRealValue(int monthIndex);
	
	public Number getMonthPredictedValue(int monthIndex);
	
	public Number getMonthResultValue(int monthIndex);

	public void setPredictedValues(Number[] predictedValues);
	
	public void updateRealValueByMonth(int month, Number value);

	public void updatePredictionValueByMonth(int month, Number value);

}
