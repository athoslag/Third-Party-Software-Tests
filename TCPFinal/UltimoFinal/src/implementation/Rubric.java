package implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import interfaces.BudgetItemAnnualValues;
import interfaces.BudgetItem;

/**
 * @author Joao Vitor de Camargo
 */
public class Rubric implements BudgetItem {

	private Number code;
	private String name;
	private String classification;
	private List<BudgetItem> childBudgetItems;
	private boolean isCost;
	private String formula;
	private Map<Integer, BudgetItemAnnualValues> annualProjections;

	public Rubric() {
		this.childBudgetItems = new ArrayList<>();
		this.annualProjections = new HashMap<Integer, BudgetItemAnnualValues>();
	}

	public void setAnnualProjections(Map<Integer, BudgetItemAnnualValues> annualProjections) {
		this.annualProjections = annualProjections;
	}

	public Map<Integer, BudgetItemAnnualValues> getAnnualProjections() {
		return annualProjections;
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

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public List<BudgetItem> getChildBudgetItems() {
		return childBudgetItems;
	}

	public void setChildBudgetItems(List<BudgetItem> childBudgetItems) {
		this.childBudgetItems = childBudgetItems;
	}

	public void addChildBudgetItem(BudgetItem childBudgetItem) {
		assert childBudgetItem != null : "Invalid childBudgetItem (it is null)";
		this.childBudgetItems.add(childBudgetItem);
	}
	@Override
	public String getFormula() {
		return formula;
	}

	@Override
	public void setFormula(String formula) {
		this.formula = formula;
	}

	@Override
	public boolean isCost() {
		return isCost;
	}

	@Override
	public void setCost(boolean isCost) {
		this.isCost = isCost;
	}

	public boolean isInBudgetItemSubtree(BudgetItem fatherBudgetItem) {
		assert fatherBudgetItem != null : "Invalid fatherBudgetItem (it is null)";
		if (fatherBudgetItem != null)
			return this.classification.startsWith(fatherBudgetItem.getClassification());
		return false;
	}

	public int getLevel() {
		return (this.classification.split("\\.")).length;
	}


}
