package interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import controllers.ApplicationController;
import interfaces.BudgetItem;
import interfaces.BudgetItemAnnualValues;
import util.MonthsController;
import util.Quintuple;
import util.Triple;

/**
 * @author Andy Ruiz e Joao Vitor de Camargo
 */
@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
public class DesktopClientController extends ApplicationController {
	
	private int temporaryYear = this.year;
	protected BudgetItemAnnualValues rubricPerYear;
	
	private List<SimplifiedBudgetItem> getBudgetItemInformationRecursive(List<BudgetItem> budgetItemList, boolean checkRealValues) {
		ArrayList<SimplifiedBudgetItem> simplifiedBudgetItems = new ArrayList<>();
            budgetItemList.forEach((budgetItem) -> {
                SimplifiedBudgetItem simplifiedBudgetItem = new SimplifiedBudgetItem();
                simplifiedBudgetItem.setCode(budgetItem.getCode());
                simplifiedBudgetItem.setName(budgetItem.getName());
                simplifiedBudgetItem.setLastYearValues(budgetItem.getAnnualProjections().get(this.temporaryYear).getPredictedValues());
                simplifiedBudgetItem.setCost(budgetItem.isCost());
                simplifiedBudgetItems.add(simplifiedBudgetItem);
            if (budgetItem.getChildBudgetItems() != null) {
                simplifiedBudgetItems.addAll(getBudgetItemInformationRecursive(budgetItem.getChildBudgetItems(), checkRealValues));
            }
	        });
		return simplifiedBudgetItems;
	}
	
	private List<BudgetItem> getAllBudgetItems(List<BudgetItem> budgetItemList) {
		List<BudgetItem> items = new ArrayList<>();
		for (BudgetItem budgetItem : budgetItemList) {
			items.add(budgetItem);
			if (budgetItem.getChildBudgetItems() != null)
				items.addAll(getAllBudgetItems(budgetItem.getChildBudgetItems()));
		}
		return items;
	}

	public List<SimplifiedBudgetItem> getBudgetItemInformation(int year, boolean checkRealValues) {
		this.temporaryYear = year;
		return getBudgetItemInformationRecursive(super.getBudgetItemList(), checkRealValues);
	}
	
	public List<SimplifiedBudgetItem> getBudgetItemInformation(boolean checkRealValues) {
		this.temporaryYear = this.year;
		return getBudgetItemInformationRecursive(super.getBudgetItemList(), checkRealValues);
	}

	private Boolean[] isBudgetItemPredicted(BudgetItem budgetItem, Integer year, boolean checkRealValues) {
		Boolean[] isPredictedValues = new Boolean[MonthsController.NUMBER_OF_MONTHS];
		for (int monthIndex = 0; monthIndex < MonthsController.NUMBER_OF_MONTHS; monthIndex++) {
			if (budgetItem.getAnnualProjections().get(year) != null) {
				if (!checkRealValues)
					isPredictedValues[monthIndex] = (budgetItem.getAnnualProjections().get(year).getMonthPredictedValue(monthIndex) != null);
				else
					isPredictedValues[monthIndex] = (budgetItem.getAnnualProjections().get(year).getMonthRealValue(monthIndex) != null);
			} else {
				isPredictedValues[monthIndex] = false;
			}
		}
		return isPredictedValues;
	}
	
	public Set<Integer> getYears() {
		Integer index = 0, greatest = 0, counter = 0;
		List<BudgetItem> allBudgetItems = getAllBudgetItems(super.getBudgetItemList());
		for (BudgetItem budgetItem : allBudgetItems) {
			if (budgetItem.getAnnualProjections().size() > greatest) {
				greatest = budgetItem.getAnnualProjections().size();
				index = counter;
			}
			counter++;
		}
		return allBudgetItems.get(index).getAnnualProjections().keySet();
	}
	
	private Number getValuePrevision(int code,int year,String month){
        int monthIndex = MonthsController.getMonthIndex(month);
        rubricController.findByCode(rubricController.getBudgetItemList(), code);
            try {   
                    rubricPerYear = rubricController.getBudgetItemAnnualValues(rubricController.getFoundBudgetItem(), this.year+1 );
                            return rubricPerYear.getMonthPredictedValue(monthIndex);
                                            //+ rubricPerYear.getMonthRealValue(monthIndex) + " | Result: ");

                    
            } catch (InstantiationException | IllegalAccessException e) {
                    return 0.0f;
            }   
    }
	
	private Number getValueRealized(int code,int year,String month){
        int monthIndex = MonthsController.getMonthIndex(month);
        rubricController.findByCode(rubricController.getBudgetItemList(), code);
            try {   
                    rubricPerYear = rubricController.getBudgetItemAnnualValues(rubricController.getFoundBudgetItem(), this.year+1 );
                    Number realizedValue = rubricPerYear.getMonthRealValue(monthIndex);
                    if(realizedValue == null){
                        return 0.0f;
                    }
                    else{
                        return realizedValue;
                    }
                    
            } catch (InstantiationException | IllegalAccessException e) {
                    return 0.0f;
            }
    }
	
	public List<Quintuple> getMonthlyBudgetDetails(List<SimplifiedBudgetItem> budgetItems,String month, int year) {
        List <Quintuple> itemDetails = new ArrayList<>();
        Quintuple actualItem;
        int code;
        float budgeted,realized,result;
        String name,visualResult;
        for (SimplifiedBudgetItem item : budgetItems) {
            code = (int) item.getCode();
            name =  item.getName();
            budgeted = (float) getValuePrevision(code,year, month);
            realized = (float) getValueRealized(code,year, month);
            result = budgeted - realized;
            
            if (item.isCost()){
                if (result >=0){
                    visualResult = "Lucrou! :D";
                }
                else{
                    visualResult = "Prejuizo :(";
                }
            }
            else{
                if (result <=0){
                    visualResult = "Lucrou! :D";
                }
                else{
                    visualResult = "Prejuizo :(";
                }
            }
            actualItem = new Quintuple(name, String.valueOf(budgeted), String.valueOf(realized), String.valueOf(result),visualResult);
            itemDetails.add(actualItem);
        }
        return itemDetails;
    }
	
	
	public List<Triple> extractItemsDescription(List<SimplifiedBudgetItem> budgetItems, int monthIndex) {
        List <Triple> itemDetails = new ArrayList<>();
        Number code,lastValue;
        String name;
        for (int i = 0; i < budgetItems.size(); i++) {
            code =  budgetItems.get(i).getCode();
            name = budgetItems.get(i).getName();
            lastValue = budgetItems.get(i).getLastYearValues()[monthIndex];

            itemDetails.add(new Triple(code,name,lastValue));
        }  

        return itemDetails;
    }
	

}
