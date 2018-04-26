package util;

/**
 * @author Joao Vitor de Camargo
 */
public enum Months {

	JANUARY("janeiro", 0),
	FEBRUARY("fevereiro", 1),
	MARCH("março", 2), 
	APRIL("abril", 3), 
	MAY("maio", 4),
	JUNE("junho", 5),
	JULY("julho", 6),
	AUGUST("agosto", 7),
	SEPTEMBER("setembro", 8),
	OCTOBER("outubro", 9),
	NOVEMBER("novembro", 10),
	DECEMBER("dezembro", 11);

	private int index;
	private String name;

	private Months(String name, int index) {
		this.name = name;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

}
