package util;

/**
 * @author Joao Vitor de Camargo
 */
public class MonthsController {
	
	public static final int NUMBER_OF_MONTHS = Months.values().length;

	public static int getMonthIndex(String nome) {
		nome = nome.trim().toLowerCase();
		for (Months month : Months.values()) {
			if (nome.equals(month.getName()))
				return month.getIndex();
		}
		return -1;
	}
	
	public static String getMonthName(int index) {
		for (Months month : Months.values()) {
			if (index == month.getIndex())
				return month.getName();
		}
		return null;
	}
	
	public static boolean isAValidMonthIndex(int monthIndex) {
		return (monthIndex >= 0 && monthIndex < NUMBER_OF_MONTHS);
	}

}
