/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.List;

/**
 * @author Andy Ruiz
 */
public interface BudgetCalculator {
    public void calculatePercentagePrevision(float percentValue,List <Integer> codes ,String month);
    public void calculateConstantPrevision(float value,List <Integer> codes ,String month);
    public void calculateCopyValuesPrevision(float value,List <Integer> codes ,String month);
    public void monthPrevisionCompleted(String month);
    public void saveCalculatedPrevision(List <Integer> remainingCodes,String month);
    public void changeCalculatorMonthItems(String month);    
    public void validateDate(String date);


    
    
}
