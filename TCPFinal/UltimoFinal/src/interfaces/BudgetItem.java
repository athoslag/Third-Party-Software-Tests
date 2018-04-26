package interfaces;

import java.util.List;
import java.util.Map;

/**
 * @author Joao Vitor de Camargo
 */
public interface BudgetItem {

	public void setAnnualProjections(Map<Integer, BudgetItemAnnualValues> annualProjections);

	public Map<Integer, BudgetItemAnnualValues> getAnnualProjections();

	public Number getCode();

	public void setCode(Number code);

	public String getName();

	public void setName(String name);

	public String getClassification();

	public void setClassification(String classification);

	public List<BudgetItem> getChildBudgetItems();

	public String getFormula();
	
	public void setFormula(String formula);
	
	public boolean isCost();
	
	public void setCost(boolean isCost);

	public void setChildBudgetItems(List<BudgetItem> childRubrics);

	public void addChildBudgetItem(BudgetItem childBudgetItem);

	public boolean isInBudgetItemSubtree(BudgetItem fatherBudgetItem);

	public int getLevel();

}
