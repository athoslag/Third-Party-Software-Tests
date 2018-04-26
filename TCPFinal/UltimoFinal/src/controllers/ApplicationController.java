package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import exception.InvalidCodeException;
import exception.InvalidPredictionDateException;
import exception.InvalidPredictionTypeException;
import implementation.PredictionExpirationDate;
import implementation.Rubric;
import implementation.RubricPerYear;
import interfaces.BudgetItem;
import interfaces.BudgetItemAnnualValues;
import util.FileInterpreter;
import util.MonthsController;
import util.PredictionTypes;

/**
 * @author Joao Vitor de Camargo
 */
public class ApplicationController {

	protected FileInterpreter<Rubric, RubricPerYear> fileInterpreter;
	protected BudgetItemController<Rubric, RubricPerYear> rubricController;
	protected PredictionController<Rubric, RubricPerYear> predictionController;
	protected ExpirationDateController<PredictionExpirationDate> expirationDateController;
	protected int year;

	public ApplicationController() {
		this.fileInterpreter = new FileInterpreter<>(Rubric.class, RubricPerYear.class);
		this.predictionController = new PredictionController<>(Rubric.class, RubricPerYear.class);
		this.expirationDateController = new ExpirationDateController<>(PredictionExpirationDate.class);
	}

	/**
	 * Set the current year Prediction are valid for the next year
	 */
	public void setUpYear(int year) {
		this.year = year;
	}

	/**
	 * Set the limit date
	 */
	public void setUpExpirationDate(Date expirationDate) {
		try {
			this.expirationDateController.setExpirationDateForYear(this.year, expirationDate);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void readHistoryFile(String fileName) throws FileNotFoundException, IOException {
		try {
			rubricController = fileInterpreter.readBudgetItemHistoryFile(fileName, this.year);
		} catch (InstantiationException | IllegalAccessException | ParseException e) {
			e.printStackTrace();
		}
	}

	public void readRealValuesFile(String fileName) throws FileNotFoundException, IOException {
		try {
			fileInterpreter.readRealValuesFile(fileName, rubricController.getBudgetItemList(), this.year);
		} catch (NumberFormatException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void predict(PredictionTypes predictionType, int rubricCode, float auxiliarValue, String monthName)
			throws InvalidCodeException, InvalidPredictionTypeException, InvalidPredictionDateException {
		if (!this.expirationDateController.isValidDatePerYear(this.year))
			throw new InvalidPredictionDateException();
		int monthIndex = MonthsController.getMonthIndex(monthName);
		predictionController.updatePredictionParameters(
				rubricController.findByCode(rubricController.getBudgetItemList(), rubricCode), this.year, monthIndex);
		try {
			predictionController.predict(predictionType, auxiliarValue);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public List<BudgetItem> getBudgetItemList() {
		return rubricController.getBudgetItemList();
	}

	public boolean showRubric(int code) {
		rubricController.findByCode(rubricController.getBudgetItemList(), code);
		if (rubricController.getFoundBudgetItem() != null) {
			System.out.println(rubricController.getFoundBudgetItem().getName().trim() + " (.Cód: "
					+ rubricController.getFoundBudgetItem().getCode() + ")");
			BudgetItemAnnualValues rubricPerYear;
			try {
				rubricPerYear = rubricController.getBudgetItemAnnualValues(rubricController.getFoundBudgetItem(),
						this.year + 1);

				for (int monthIndex = 0; monthIndex < 12; monthIndex++) {
					System.out.print(MonthsController.getMonthName(monthIndex) + ": Pred: "
							+ (rubricPerYear.getMonthPredictedValue(monthIndex) == null ? 0
									: rubricPerYear.getMonthPredictedValue(monthIndex))
							+ " | Real: "
							+ (rubricPerYear.getMonthRealValue(monthIndex) == null ? 0
									: rubricPerYear.getMonthRealValue(monthIndex))
							+ " | Result: " + getAvaliation(rubricController.getFoundBudgetItem().isCost(),
									rubricPerYear.getMonthResultValue(monthIndex).floatValue())
							+ "\n");
				}
				return true;
			} catch (InstantiationException | IllegalAccessException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public String getAvaliation(boolean isCost, float value) {
		if (isCost)
			if (value > 0)
				return "Prejuizo D:";
			else if (value == 0)
				return "OK :|";
			else
				return "Lucro :D";
		else if (value > 0)
			return "Lucro :D";
		else if (value == 0)
			return "OK :|";
		else
			return "Prejuizo D:";
	}

}
