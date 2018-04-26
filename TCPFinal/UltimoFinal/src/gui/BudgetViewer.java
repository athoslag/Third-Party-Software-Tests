/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 * @author Andy Ruiz
 */
public interface BudgetViewer {
    public void selectedMonthView(String month,int year);
    public void loadViewerWindowFile(String filePath);
    public void selectedYearView(String month, Integer year);
    public void closeViewer();
    
}
