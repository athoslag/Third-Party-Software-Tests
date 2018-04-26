package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import controllers.BudgetItemAnnualValuesController;
import controllers.BudgetItemController;
import interfaces.BudgetItem;
import interfaces.BudgetItemAnnualValues;

/**
 * @author Joao Vitor de Camargo e Pedro Perrone
 */
public class FileInterpreter<UsedBudgetClass extends BudgetItem, UsedBudgetLineClass extends BudgetItemAnnualValues> {

	private static final String UTF_8 = "UTF8";

	private static final int HISTORY_FILE_CLASSIFICATION_INDEX = 0;
	private static final int HISTORY_FILE_CODE_INDEX = 1;
	private static final int HISTORY_FILE_VALUES_STARTING_INDEX = 3;

	private static final int REAL_FILE_MONTH_NAME = 0;
	private static final int REAL_FILE_CODE_INDEX = 1;
	private static final int REAL_FILE_DEBIT_INDEX = 2;
	private static final int REAL_FILE_CREDIT_INDEX = 3;

	private BudgetItemController<UsedBudgetClass, UsedBudgetLineClass> budgetItemController;
	private BudgetItemAnnualValuesController<UsedBudgetLineClass> biavController;

	public FileInterpreter(Class<UsedBudgetClass> usedBudgetClass, Class<UsedBudgetLineClass> usedBudgetLineClass) {
		this.budgetItemController = new BudgetItemController<>(usedBudgetClass, usedBudgetLineClass);
		this.biavController = new BudgetItemAnnualValuesController<>(usedBudgetLineClass);
	}

	public BudgetItemController<UsedBudgetClass, UsedBudgetLineClass> readBudgetItemHistoryFile(String fileName, int year)
			throws InstantiationException, IllegalAccessException, IOException, FileNotFoundException, ParseException {

		budgetItemController.setBudgetItemList(new ArrayList<>());
		BufferedReader fileReader = readFile(formatFileName(fileName));
		List<Number> existingCodes = new ArrayList<>();
		String fileLine = new String();

		while ((fileLine = fileReader.readLine()) != null) {
			String[] readFields = fileLine.split(";");
			if (isHistoricFileLineValid(readFields)) {
				if (lineHasCode(readFields)) {
					existingCodes.add(NumberFormat.getInstance().parse(readFields[1]));
					budgetItemController.manageReadBudgetItem(readFields, year, lineHasClassification(readFields));
				} else {
					Number generatedRandomCode = generateCode(existingCodes);
					readFields[HISTORY_FILE_CODE_INDEX] = generatedRandomCode.toString();
					existingCodes.add(generatedRandomCode);
					budgetItemController.manageReadBudgetItem(readFields, year, lineHasClassification(readFields));
				}
			}
		}
		budgetItemController.recalculateValues(budgetItemController.getBudgetItemList(), year);
		return budgetItemController;
	}

	public void readRealValuesFile(String fileName, ArrayList<BudgetItem> budgetFileList, int year)
			throws InstantiationException, IllegalAccessException, NumberFormatException, IOException,
			FileNotFoundException {

		int lineCounter = 0, monthIndex = -1;
		BufferedReader fileReader = readFile(formatFileName(fileName));
		String fileLine = new String();

		while ((fileLine = fileReader.readLine()) != null) {
			String[] fields = fileLine.split(",");
			if (lineCounter == 0) {
				monthIndex = MonthsController.getMonthIndex(fields[REAL_FILE_MONTH_NAME]);
			} else {
				if (isMonthValuesFileLineValid(fields)) {
					budgetItemController.findByCode(budgetFileList, Integer.valueOf(fields[1]));
					if (budgetItemController.getFoundBudgetItem() != null) {
						biavController.updateRealValueByMonth(budgetItemController.getBudgetItemAnnualValues(year + 1),
								monthIndex, Float.parseFloat(fields[REAL_FILE_CREDIT_INDEX].replace(".", "").replace("-", "").replace(" ", ""))
										- Float.parseFloat(fields[REAL_FILE_DEBIT_INDEX].replace(".", "").replace("-", "").replace(" ", "")));
					}
				}
			}
			lineCounter++;
		}
	}

	private Number generateCode(List<Number> existingCodes) {
		Number randomCode;
		do {
			randomCode = (int) (Math.random() * 9000);
		} while (existingCodes.contains(randomCode));
		return randomCode;
	}

	private BufferedReader readFile(String fileName) throws UnsupportedEncodingException, FileNotFoundException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName)), UTF_8));
	}

	private boolean lineHasClassification(String[] fileLine) {
		return lineHasField(fileLine, HISTORY_FILE_CLASSIFICATION_INDEX);
	}

	private boolean lineHasCode(String[] fileLine) {
		return lineHasField(fileLine, HISTORY_FILE_CODE_INDEX);
	}

	private boolean lineHasField(String[] fileLine, int index) {
		return fileLine.length > 0 && !fileLine[index].trim().isEmpty();
	}

	private boolean isHistoricFileLineValid(String[] fileLine) {
		if (fileLine != null && fileLine.length > 1) {
			try {
				for (int monthIndex = HISTORY_FILE_VALUES_STARTING_INDEX; monthIndex < MonthsController.NUMBER_OF_MONTHS
						+ HISTORY_FILE_VALUES_STARTING_INDEX; monthIndex++)
					Float.parseFloat(fileLine[monthIndex]);
				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		}
		return false;
	}

	private boolean isMonthValuesFileLineValid(String[] fileLine) {
		if (!fileLine[REAL_FILE_CODE_INDEX].isEmpty()) {
			try {
				Integer.parseInt(fileLine[REAL_FILE_CODE_INDEX]);
				Float.parseFloat(fileLine[REAL_FILE_CREDIT_INDEX]);
				Float.parseFloat(fileLine[REAL_FILE_DEBIT_INDEX]);
				return true;
			} catch (NumberFormatException nfe) {
				return false;
			}
		}
		return false;
	}

	private String formatFileName(String fileName) {
		if (!fileName.contains("."))
			fileName = fileName.concat(".csv");
		return fileName;
	}

}
