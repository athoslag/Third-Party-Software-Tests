package controllers;

import java.util.ArrayList;
import java.util.List;

import interfaces.BudgetItem;
import interfaces.BudgetItemAnnualValues;
import util.MonthsController;

/**
* @author Joao Vitor de Camargo
*/
public class BudgetItemController<UsedBudgetClass extends BudgetItem, UsedBudgetLineClass extends BudgetItemAnnualValues> {

	private ArrayList<BudgetItem> budgetItemList;
	private BudgetItem foundBudgetItem = null;
	private Class<UsedBudgetClass> usedBudgetClass;
	private Class<UsedBudgetLineClass> usedBudgetLineClass;
	private Float calculatedValue = 0f;

	public BudgetItemController(Class<UsedBudgetClass> usedBudgetClass,
			Class<UsedBudgetLineClass> usedBudgetLineClass) {
		this.usedBudgetClass = usedBudgetClass;
		this.usedBudgetLineClass = usedBudgetLineClass;
		this.budgetItemList = new ArrayList<BudgetItem>();
	}

	public ArrayList<BudgetItem> getBudgetItemList() {
		return budgetItemList;
	}
	
	public float getCalculatedValue() {
		return calculatedValue;
	}

	public void setBudgetItemList(ArrayList<BudgetItem> budgetItemList) {
		this.budgetItemList = budgetItemList;
	}

	public BudgetItem getFoundBudgetItem() {
		return foundBudgetItem;
	}

	public BudgetItem findFatherBudgetItem(List<BudgetItem> budgetItemList, BudgetItem childBudgetItem) {
		int listIndex = 0;
		if(childBudgetItem == null)
			return null;
		while (listIndex < budgetItemList.size()) {
			if (childBudgetItem.isInBudgetItemSubtree(budgetItemList.get(listIndex))) {
				if (budgetItemList.get(listIndex).getLevel() == (childBudgetItem.getLevel() - 1)) {
					return budgetItemList.get(listIndex);
				} else {
					return findFatherBudgetItem(budgetItemList.get(listIndex).getChildBudgetItems(), childBudgetItem);
				}
			} else {
				listIndex++;
			}
		}
		return null;
	}

	public void manageReadBudgetItem(String[] readFields, int year, boolean hasClassification)
			throws InstantiationException, IllegalAccessException {
		BudgetItem newBudgetItem = usedBudgetClass.newInstance();
		newBudgetItem.setCode(getIntegerValueOf(readFields[1]));
		newBudgetItem.setName(readFields[2]);
		newBudgetItem.setClassification(readFields[0]);
		newBudgetItem.setFormula(readFields[16]);
		newBudgetItem.setCost(readFields[17].equals("S"));
		BudgetItemAnnualValuesController<UsedBudgetLineClass> rpyController = new BudgetItemAnnualValuesController<>(
				usedBudgetLineClass);
		newBudgetItem.setAnnualProjections(rpyController.buildProjectionsHash(readFields, year));
		if (hasClassification) {
			BudgetItem fatherBudgetItem = findFatherBudgetItem(budgetItemList, newBudgetItem);
			if (fatherBudgetItem == null)
				budgetItemList.add(newBudgetItem);
			else
				fatherBudgetItem.addChildBudgetItem(newBudgetItem);

		} else
			budgetItemList.add(newBudgetItem);
	}

	private Integer getIntegerValueOf(String stringToBeRead) {
		try {
			return Integer.valueOf(stringToBeRead);
		} catch (NumberFormatException nfe) {
			return -1;
		}
	}

	public BudgetItem findByCode(List<BudgetItem> budgetItemList, Number code) {
		for (BudgetItem budgetItem : budgetItemList) {
			if (budgetItem.getCode() == code)
				this.foundBudgetItem = budgetItem;
			else
				this.foundBudgetItem = findByCode(budgetItem.getChildBudgetItems(), code);
		}
		return this.foundBudgetItem;
	}

	public BudgetItemAnnualValues getBudgetItemAnnualValues(BudgetItem budgetItem, int year)
			throws InstantiationException, IllegalAccessException {
		if (budgetItem.getAnnualProjections().get(year) == null)
			budgetItem.getAnnualProjections().put(year, usedBudgetLineClass.newInstance());
		return budgetItem.getAnnualProjections().get(year);
	}

	public BudgetItemAnnualValues getBudgetItemAnnualValues(int year)
			throws InstantiationException, IllegalAccessException {
		return getBudgetItemAnnualValues(this.foundBudgetItem, year);
	}

	public void recalculateValues(List<BudgetItem> budgetItemList, Integer year) {
		for (BudgetItem budgetItem : budgetItemList) {
			for (int monthIndex = 0; monthIndex < MonthsController.NUMBER_OF_MONTHS; monthIndex++) {
				calculatedValue = 0f;
				Number realBudgetItemPredictedValue = getRecalculatedValue(budgetItem, year, monthIndex);
				budgetItem.getAnnualProjections().get(year).updatePredictionValueByMonth(monthIndex, realBudgetItemPredictedValue);
				if (budgetItem.getChildBudgetItems() != null && !budgetItem.getChildBudgetItems().isEmpty())
					recalculateValues(budgetItem.getChildBudgetItems(), year);
			}
		}
	}

	public Float getSumOfChildrenValues(BudgetItem budgetItem, Integer year, Integer monthIndex) {
		if (budgetItem.getChildBudgetItems() != null && !budgetItem.getChildBudgetItems().isEmpty())
			for (BudgetItem childBudgetItem : budgetItem.getChildBudgetItems()) {
				calculatedValue += childBudgetItem.getAnnualProjections().get(year).getMonthPredictedValue(monthIndex)
						.floatValue();
				calculatedValue += getSumOfChildrenValues(childBudgetItem, year, monthIndex);
			}
		return 0f;
	}

	public Number getRecalculatedValue(BudgetItem budgetItem, Integer year, Integer monthIndex) {
		if (budgetItem.getFormula() != null && !budgetItem.getFormula().isEmpty()) {
			if (budgetItem.getFormula().trim().equals("SUM")) {
				getSumOfChildrenValues(budgetItem, year, monthIndex);
				return calculatedValue;
			} else if (budgetItem.getFormula().startsWith("F:")) {
				return parse(budgetItem.getFormula(), year, monthIndex);
			} else {
				return budgetItem.getAnnualProjections().get(year).getMonthPredictedValue(monthIndex);
			}
		}
		return budgetItem.getAnnualProjections().get(year).getMonthPredictedValue(monthIndex);
	}

	private Number parse(String formula, Integer year, Integer monthIndex) {
		formula = formula.replace("F:", "").trim();
		if (formula.startsWith("-"))
			return calculateParsedFormula(formula, year, monthIndex);
		List<Integer> codes = new ArrayList<>();
		List<Boolean> positives = new ArrayList<>();
		positives.add(true);
		String code = "";
		for (int charIndex = 0; charIndex < formula.length(); charIndex++) {
			if (formula.charAt(charIndex) != ' ') {
				if (Character.isDigit(formula.charAt(charIndex)))
					code += formula.charAt(charIndex);
				else {
					codes.add(Integer.valueOf(code));
					code = "";
					positives.add(formula.charAt(charIndex) == '+');
				}
				if (charIndex == formula.length() - 1)
					codes.add(Integer.valueOf(code));
			}
		}
		return calculateParsedFormula(codes, positives, year, monthIndex);
	}
	
	private Number calculateParsedFormula(List<Integer> codes, List<Boolean> positives, Integer year, Integer monthIndex) {
		Float total = 0f;
		int codeCounter = 0;
		for (Integer code : codes) {
			BudgetItem budgetItem = findByCode(this.getBudgetItemList(), code);
			if (budgetItem != null) {
				if (positives.get(codeCounter))
					total = total + budgetItem.getAnnualProjections().get(year).getMonthPredictedValue(monthIndex).floatValue();
				else
					total = total - budgetItem.getAnnualProjections().get(year).getMonthPredictedValue(monthIndex).floatValue();
			}
			codeCounter++;
		}
		return total;
	}
	
	private Number calculateParsedFormula(String formula, Integer year, Integer monthIndex) {
		formula = formula.replace("-", "");
		Integer code = Integer.valueOf(formula);
		BudgetItem budgetItem = findByCode(this.getBudgetItemList(), code);
		return -1 * budgetItem.getAnnualProjections().get(year).getMonthPredictedValue(monthIndex).floatValue();
	}
}
