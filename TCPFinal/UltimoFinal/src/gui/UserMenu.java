/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 * @author Andy Ruiz
 */
public interface UserMenu {
    
    public void loadMenuFile(String filePath); 
    public void openPrevisionWindow();
    public void viewCalculatedPrevisions();
    public void modifyActualPrevision();
    
}
