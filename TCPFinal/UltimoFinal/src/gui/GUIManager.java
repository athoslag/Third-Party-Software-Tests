/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import exception.InvalidCodeException;
import exception.InvalidPredictionDateException;
import exception.InvalidPredictionTypeException;
import interaction.DesktopClientController;
import interaction.SimplifiedBudgetItem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.MonthsController;
import util.PredictionTypes;
import util.Triple;
import util.Quintuple;

/**
 * @author Andy Ruiz
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class GUIManager implements UserMenu, BudgetViewer, BudgetCalculator {

	private final GUIMenu menuWindow;
	private final GUIPrevisionViewer viewerWindow;
	private final GUICalculateBudget calculatorWindow;
	private final DesktopClientController uiController;
	private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");;
	private boolean validHistoricFile = false;
	private List<SimplifiedBudgetItem> budgetItems;
	private boolean completedMonths[];
	private int lastYearPredicted = 0;
	private String lastLimitDateBudget = "0000/01/01";
	private boolean modifyng = false;

	public GUIManager() {
		uiController = new DesktopClientController();
		menuWindow = new GUIMenu();
		viewerWindow = new GUIPrevisionViewer();
		calculatorWindow = new GUICalculateBudget();
	}

	public void start() {

		menuWindow.setup(this);
		calculatorWindow.setup(this);
		viewerWindow.setup(this);
		menuWindow.setVisible(true);
		this.completedMonths = new boolean[12];
	}

	/*
	 *
	 * UserMenu implementation methods
	 * 
	 *
	 */

	/*
	 * Given a filePath string, tryes to open this Budget Items Historic File
	 * 
	 * @param filePath
	 */
	@Override
	public void loadMenuFile(String filePath) {
		try {
			uiController.setUpYear(2017);
			uiController.readHistoryFile(filePath);
			validHistoricFile = true;
			menuWindow.setAviso(filePath + "  foi carregado com sucesso!");
			menuWindow.disableFileLoader();
		} catch (FileNotFoundException e) {
			menuWindow.setAviso("O arquivo selecionado nao foi encontrado.");
		} catch (IOException e) {
			menuWindow.setAviso("O arquivo selecionado nao é valido como arquivo de histórico");
		}
	}

	/**
	 * Request to calculate Previsions
	 */
	@Override
	public void openPrevisionWindow() {
		int actualYear = Calendar.getInstance().get(Calendar.YEAR);
		if (validHistoricFile) {
			if (actualYear != lastYearPredicted) {
				menuWindow.setVisible(false);
				calculatorWindow.openWindow();
				budgetItems = uiController.getBudgetItemInformation(actualYear, false);
				calculatorWindow.showTextUser(
						"Selecione a data limite para fazer modificações no ano que vem inserindo o mês e dia no formato mm/dd");
			} else {
				menuWindow.setAviso("Você já fez previsão nesse ano para o ano que vem");
			}

		} else {
			menuWindow.setAviso(
					"Nenhum arquivo histórico foi selecionado. Por favor selecione um arquivo válido para poder prosseguir");
		}
	}

	/**
	 * Request to calculate Prevision
	 */
	@Override
	public void viewCalculatedPrevisions() {
		if (lastYearPredicted != 0) {
			menuWindow.setVisible(false);
			viewerWindow.openWindow();
			viewerWindow.setYears(uiController.getYears());
		} else {
			menuWindow.setAviso("Não há previsões a serem vistas ainda");
		}
	}

	/**
	 * Request to modify Prevision
	 */
	@Override
	public void modifyActualPrevision() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = 1 + Calendar.getInstance().get(Calendar.MONTH);
		int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		String todayString = year + "/" + month + "/" + day;
		try {
			Date today = dateFormatter.parse(todayString);
			Date lastLimitDate = dateFormatter.parse(lastLimitDateBudget);
			if (today.before(lastLimitDate)) {
				openModifyPrevisionWindow();
			} else {
				menuWindow.setAviso("Não há previsão atual disponivel para modificar");
				modifyng = false;
			}
		} catch (ParseException ex) {
			Logger.getLogger(GUIManager.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void openModifyPrevisionWindow() {
		Arrays.fill(completedMonths, Boolean.FALSE);
		int year = Calendar.getInstance().get(Calendar.YEAR);
		menuWindow.closeWindow();
		budgetItems = uiController.getBudgetItemInformation(year, false);
		calculatorWindow.openWindow();
		calculatorWindow.showTextUser("Selecione um mês");
		modifyng = true;
	}

	/*
	 *
	 *
	 * End of UserMenu implementation methods
	 *
	 */

	/**
	 *
	 * BudgetViewer Interface implementation below
	 * 
	 * 
	 */

	/**
	 * Close Viewer window
	 */
	@Override
	public void closeViewer() {
		viewerWindow.closeWindow();
		menuWindow.setVisible(true);

	}

	/**
	 * Collect all the budget items informations of the given year and month to
	 * send it to the viewer
	 * 
	 * @param month
	 * @param year
	 */
	
	@Override
	public void selectedMonthView(String month, int year) {
		int yearIndex = year - 1;
		budgetItems = uiController.getBudgetItemInformation(yearIndex, true);
		List<Quintuple> actualMonthDetails = uiController.getMonthlyBudgetDetails(budgetItems, month, yearIndex + 1);
		viewerWindow.showTextUser("Vá escolhendo entre os meses que quiser ou troque de ano");
		viewerWindow.fillViewerWindowList(actualMonthDetails);
	}

	/**
	 * Prepares to load all the budget items of these year and month
	 * 
	 * @param month
	 * @param year
	 */
	@Override
	public void selectedYearView(String month, Integer year) {
		int yearIndex = year - 1;
		budgetItems = uiController.getBudgetItemInformation(yearIndex, true);
		viewerWindow.showTextUser("Escolha um mês");

		if (year - 1 == lastYearPredicted) {
			viewerWindow.enableLoaderFile();
		} else {
			viewerWindow.disableLoaderFile();
		}
	}

	/**
	 * Uses the file path given to load and read the possible file
	 * 
	 * @param filePath
	 */
	@Override
	public void loadViewerWindowFile(String filePath) {
		try {
			uiController.readRealValuesFile(filePath);
			viewerWindow.showTextUser("Realizado Mensal carregado! Selecione os meses para recarregar");
			viewerWindow.clearTable();
		} catch (FileNotFoundException e) {
			System.out.println("O arquivo selecionado não foi encontrado.");
		} catch (IOException e) {
			System.out.println("O arquivo selecionado não é válido como arquivo de realizado mensal");
		}
	}

	/*
	 *
	 * End of BudgetViever Implementation
	 *
	 *
	 */

	/*
	 *
	 * Budget Calulator Implentation methods
	 *
	 */

	/*
	 * Calculates a percentage prevision for a list of codes for a specific
	 * month
	 * 
	 * @param percentValue
	 * 
	 * @param codes
	 * 
	 * @param month
	 */
	@Override
	public void calculatePercentagePrevision(float percentValue, List<Integer> codes, String month) {
		int monthIndex = MonthsController.getMonthIndex(month);
		float value = 1 + percentValue / 100;
		makePrevision(codes, PredictionTypes.PREDICTION_MULTIPLYING_VALUES, value, month);
	}

	/**
	 * Calculates a fixed-value prevision for a list of codes for a specific
	 * month
	 * 
	 * @param value
	 * @param codes
	 * @param month
	 */
	@Override
	public void calculateConstantPrevision(float value, List<Integer> codes, String month) {
		int monthIndex = MonthsController.getMonthIndex(month);
		makePrevision(codes, PredictionTypes.PREDICTION_WITH_CONSTANT_VALUE, value, month);
	}

	/**
	 * Calculates a mantain-values prevision for a list of codes for a specific
	 * month
	 * 
	 * @param value
	 * @param codes
	 * @param month
	 */
	@Override
	public void calculateCopyValuesPrevision(float value, List<Integer> codes, String month) {
		int monthIndex = MonthsController.getMonthIndex(month);
		makePrevision(codes, PredictionTypes.PREDICTION_COPYING_VALUES, 0, month);
	}

	/**
	 * Finish calculating budget of all remainings budget Items for all them
	 * remaining months
	 * 
	 * @param remainingCodes
	 * @param month
	 */
	@Override
	public void saveCalculatedPrevision(List<Integer> remainingCodes, String month) {
		int actualYear = Calendar.getInstance().get(Calendar.YEAR);
		if (lastYearPredicted != actualYear) {
			if (remainingCodes.isEmpty() == false) {
				calculateCopyValuesPrevision(0, remainingCodes, month);
				int monthIndex = MonthsController.getMonthIndex(month);
				completedMonths[monthIndex] = true;
			}
			List<Integer> codes = new ArrayList<>();
			String remainingMonth;
			for (int i = 0; i < completedMonths.length; i++) {
				if (completedMonths[i] == false) {
					budgetItems.forEach((item) -> {
						codes.add((Integer) item.getCode());
					});
					remainingMonth = MonthsController.getMonthName(i);
					calculateCopyValuesPrevision(0, codes, remainingMonth);
					codes.clear();
				}
			}
			lastYearPredicted = Calendar.getInstance().get(Calendar.YEAR);
		}
		calculatorWindow.closeWindow();
		menuWindow.OpenWindow();

	}

	/**
	 * Set a month prevision as completed
	 * 
	 * @param month
	 */
	@Override
	public void monthPrevisionCompleted(String month) {
		int monthIndex = MonthsController.getMonthIndex(month);
		completedMonths[monthIndex] = true;

	}

	/*
	 * Given a month get all the items and values from the given month
	 */
	private void loadItemsCalculatorWindow(int monthIndex) {
		if (completedMonths[monthIndex] == false) {
			List<Triple> itemDetails = uiController.extractItemsDescription(budgetItems, monthIndex);
			calculatorWindow.fillRubricLists(itemDetails, modifyng);
		} else {
			calculatorWindow.showTextUser("Não há nada que mostrar esse mês, todas as rubricas de "
					+ MonthsController.getMonthName(monthIndex)
					+ " ja foram alteradas\nSe quiser modificar o mes de novo salve as alteracoes e modifique novamente");
		}

	}

	/*
	 * Does a budget prevision
	 */
	private void makePrevision(List<Integer> codes, PredictionTypes type, float value, String month) {
		for (int i = 0; i < codes.size(); i++) {
			try {
				uiController.predict(type, (int) codes.get(i), value, month);
			} catch (InvalidCodeException ex) {
				System.out.println("Codigo de rubrica" + codes.get(i) + " invalido");
			} catch (InvalidPredictionTypeException ex) {
				System.out.println("Tipo de predicao invalido");
			} catch (InvalidPredictionDateException ex) {
				System.out.println("Ja passou da hora de fazer previsao");
			}
		}
	}

	/**
	 * When user changes months is needed to load all the items again
	 * 
	 * @param month
	 */
	@Override
	public void changeCalculatorMonthItems(String month) {
		int monthIndex = MonthsController.getMonthIndex(month);
		loadItemsCalculatorWindow(monthIndex);
	}

	/**
	 * Validates if is a date for setting it as expiration date for this budget
	 * 
	 * @param date
	 */
	@Override
	public void validateDate(String date) {
		int actualYear = Calendar.getInstance().get(Calendar.YEAR);
		dateFormatter.setLenient(false);
		date = String.valueOf(actualYear + 1) + "/" + date;
		try {
			uiController.setUpExpirationDate(dateFormatter.parse(date));
			lastLimitDateBudget = date;
			calculatorWindow.enableButtons();
		} catch (ParseException e) {
			calculatorWindow.wrongDate();
		}

	}
}
