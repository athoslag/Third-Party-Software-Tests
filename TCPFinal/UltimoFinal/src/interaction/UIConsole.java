package interaction;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import exception.InvalidCodeException;
import exception.InvalidPredictionDateException;
import exception.InvalidPredictionTypeException;
import util.PredictionTypes;

/**
 * @author Joao Vitor de Camargo
 */
public class UIConsole {

	private static final float NOT_NECESSARY = -1;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws ParseException {

		DesktopClientController uiController = new DesktopClientController();

		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		Scanner input = new Scanner(System.in);
		boolean validHistoricFile = false;
		int menuOption = -1, rubricCode;
		String monthName;

		System.out.print("Digite o ano atual: ");
		uiController.setUpYear(input.nextInt());

		System.out.print("Digite a data limite para o ano atual no format dd/mm/yyyy: ");
		uiController.setUpExpirationDate(dateFormatter.parse(input.next()));

		do {
			try {
				System.out.print("Digite o nome do arquivo de hist�rico do ano atual: ");
				uiController.readHistoryFile(input.next());
				validHistoricFile = true;
			} catch (FileNotFoundException e) {
				System.out.println("O arquivo selecionado não foi encontrado.");
			} catch (IOException e) {
				System.out.println("O arquivo selecionado não é válido como arquivo de hist�rico");
			}
		} while (!validHistoricFile);

		while (menuOption != 0) {
			System.out.println("1) Fazer previsão para o próximo ano");
			System.out.println("2) Fazer upload de arquivo de valores reais");
			System.out.println("3) Comparar previs�es");
			System.out.println("4) Mostrar");
			System.out.println("0) Sair");

			menuOption = input.nextInt();
			switch (menuOption) {
			case 0:
				break;
			case 1:
				System.out.print("Digite o código da rubrica: ");
				rubricCode = input.nextInt();
				System.out.print("Digite o mês para qual essa previs�o vale? ");
				monthName = input.next();

				System.out.println("1) Fazer previsão copiando valores do ano atual");
				System.out.println("2) Fazer previsão�o com valor constante");
				System.out.println("3) Fazer previsão�o com base nos valores atuais");

				PredictionTypes type;
				Float value;

				switch (input.nextInt()) {
				case 1:
					type = PredictionTypes.PREDICTION_COPYING_VALUES;
					value = NOT_NECESSARY;
					break;
				case 2:
					type = PredictionTypes.PREDICTION_WITH_CONSTANT_VALUE;
					System.out.print("Digite a constante: ");
					value = input.nextFloat();
					break;
				case 3:
					type = PredictionTypes.PREDICTION_MULTIPLYING_VALUES;
					System.out.print("Digite a porcentagem: ");
					value = input.nextFloat();
					break;
				default:
					type = null;
					value = null;
				}
				try {
					uiController.predict(type, rubricCode, value, monthName);
				} catch (InvalidPredictionTypeException e) {
					System.out.println("O tipo de previsão selecionado não é válido.");
				} catch (InvalidCodeException e) {
					System.out.println("O código procurado não foi encontrado.");
				} catch (InvalidPredictionDateException e) {
					System.out.println("A data limite para alterar a previsão já passou.");
				}
				break;
			case 2:
				System.out.print("Digite o nome do arquivo de valores reais: ");
				try {
					uiController.readRealValuesFile(input.next());
				} catch (FileNotFoundException e) {
					System.out.println("O arquivo selecionado não foi encontrado.");
				} catch (IOException e) {
					System.out.println("O arquivo selecionado não é válido como arquivo de realizado mensal");
				}
				break;
			case 3:
				System.out.print("Digite o código da rubrica: ");
				rubricCode = input.nextInt();
				uiController.showRubric(rubricCode);
				break;
			case 4:
				for (Integer year : uiController.getYears()) {
					System.out.println(year);
				}
			}
		}
	}

}
