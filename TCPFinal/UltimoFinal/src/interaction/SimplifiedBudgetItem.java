package interaction;

/**
 * @author Andy Ruiz e Joao Vitor de Camargo
 */
public class SimplifiedBudgetItem {

	private Number code;
	private String name;
	private Number[] lastYearValues;
	private Boolean cost;

	public Boolean isCost() {
		return cost;
	}

	public void setCost(Boolean cost) {
		this.cost = cost;
	}

	public Number getCode() {
		return code;
	}

	public void setCode(Number code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Number[] getLastYearValues() {
		return lastYearValues;
	}

	public void setLastYearValues(Number[] lastYearValues) {
		this.lastYearValues = lastYearValues;
	}

	@Override
	public String toString() {
		String string = code + " | " + name;
		for (int i = 0; i < 12; i++)
			string += "\n\tLY Value: " + lastYearValues[i] + "\n";
		return string;
	}

}
