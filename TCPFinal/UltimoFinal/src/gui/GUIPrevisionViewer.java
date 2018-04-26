/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import util.Quintuple;

/**
 * @author Andy Ruiz
 */
@SuppressWarnings("rawtypes")
public class GUIPrevisionViewer extends javax.swing.JFrame {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2673795244058058406L;
	private DefaultTableModel model = new DefaultTableModel();
    private BudgetViewer windowManager;
    protected boolean started = false;
    /**
     * Creates new form Interface
     */
    public GUIPrevisionViewer() {
        initComponents();    
    }

    public void setup(BudgetViewer manager){
        this.windowManager = manager;
        this.setVisible(false);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({ "unchecked", "serial" })
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileInput = new javax.swing.JTextField();
        mesesPrevisao = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableRubricas = new javax.swing.JTable();
        openButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAvisos = new javax.swing.JTextPane();
        yearsJBox = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fileInput.setEditable(false);
        fileInput.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        fileInput.setText("Upload resultados do ano vigente");
        fileInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileInputActionPerformed(evt);
            }
        });

        mesesPrevisao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Janeiro", "Fevereiro", "Mar�o", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" }));
        mesesPrevisao.setEnabled(false);
        mesesPrevisao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mesesPrevisaoActionPerformed(evt);
            }
        });

        tableRubricas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Rubrica", "Or�ado", "Realizado", "Resultado", "Saldo"
            }
        ) {
			Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableRubricas);

        openButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Open16.gif"))); // NOI18N
        openButton.setText("Open File...");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        textAvisos.setEditable(false);
        textAvisos.setBackground(new java.awt.Color(204, 204, 204));
        textAvisos.setBorder(null);
        textAvisos.setText("Selecione um dos anos que tem previs�o\n");
        textAvisos.setAlignmentY(1.0F);
        textAvisos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        textAvisos.setDisabledTextColor(new java.awt.Color(0, 102, 153));
        textAvisos.setEnabled(false);
        jScrollPane2.setViewportView(textAvisos);

        yearsJBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearsJBoxActionPerformed(evt);
            }
        });

        jButton1.setText("Sair");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(153, 153, 153)
                        .addComponent(fileInput, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(openButton))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(yearsJBox, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(mesesPrevisao, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(openButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mesesPrevisao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearsJBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        fileInput.getAccessibleContext().setAccessibleName("");
        fileInput.getAccessibleContext().setAccessibleDescription("");
        fileInput.setVisible(false);
        openButton.setVisible(false);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    * Ao se selecionar um mes, avisa o windowManager que se quer trocar de mes
    *
    */
    private void mesesPrevisaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mesesPrevisaoActionPerformed
        if(started){
            String month = (String) mesesPrevisao.getSelectedItem();
            int year = (int) yearsJBox.getSelectedItem();
            windowManager.selectedMonthView(month,year);
        }
        
    }//GEN-LAST:event_mesesPrevisaoActionPerformed

    private void fileInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileInputActionPerformed

    /*
    * Passa o path do arquivo selecionado pelo navegador do Swing como possivel caminho
    * para um arquivo valido ao windowManager
    *
    */
    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos csv", "csv");
        File f;
        fc.setFileFilter(filter);
        fc.showOpenDialog(null);
        try {
            f = fc.getSelectedFile();
            if(f.exists())
            {
                String filePath = f.getAbsolutePath();
                windowManager.loadViewerWindowFile(filePath);
            }
        } catch (Exception NullPointerException) {
            textAvisos.setText("Por favor selecione um arquivo contendo as Rubricas para poder prosseguir com a aplica��o.");
        }
    }//GEN-LAST:event_openButtonActionPerformed

    /*
    * Ao se selecionar um ano, avisa o windowManager que se quer trocar de ano
    *
    */
    private void yearsJBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearsJBoxActionPerformed
        if (started){
            Integer year = (Integer) yearsJBox.getSelectedItem();
            String month = (String) mesesPrevisao.getSelectedItem();
            windowManager.selectedYearView(month,year);
            showTextUser("Selecione o m�s que desejar ver");
            mesesPrevisao.setEnabled(true);
        }
    }//GEN-LAST:event_yearsJBoxActionPerformed
    /*
    * Método para mostrar mensagens para o usuário
    *
    */
    protected void showTextUser(String message){
        textAvisos.setText(message);
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        windowManager.closeViewer();
    }//GEN-LAST:event_jButton1ActionPerformed
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField fileInput;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JComboBox<String> mesesPrevisao;
    private javax.swing.JButton openButton;
    private javax.swing.JTable tableRubricas;
    private javax.swing.JTextPane textAvisos;
    private javax.swing.JComboBox<String> yearsJBox;
    // End of variables declaration//GEN-END:variables
    
    /*
    * Preenche os anos que teve previsoes dada uma lista de anos
    *
    */
    @SuppressWarnings("unchecked")
	public void setYears(Set<Integer> years) {
        List yearsJList = new ArrayList();
        yearsJList.addAll(years);
        yearsJList.remove(0);
        yearsJBox.setModel(new DefaultComboBoxModel(yearsJList.toArray()));
    }
    /*
    * Recebe uma lista de Quintuplas e preenche a tabela com cada tupla
    *
    */
    public void fillViewerWindowList(List<Quintuple> monthResults) {
        model = new DefaultTableModel(new String[] { "Rubrica", "Or�ado","Realizado","Saldo","Resultado"},0); 
        monthResults.forEach((tuple) -> {
            model.addRow(new Object[]{ tuple.x,tuple.y,tuple.z,tuple.v,tuple.w});
        });
        tableRubricas.setModel(model);
    }

    public void openWindow() {
        this.setVisible(true);
        started = true;
        clearTable();
    }
    
    public void clearTable(){
        model = new DefaultTableModel();
        tableRubricas.setModel(model);
    }
    /*
    * Habilita botao para carregar arquivo de realizado mensal
    *
    */
    public void enableLoaderFile() {
        openButton.setVisible(true);
        fileInput.setVisible(true);
    }
    /*
    * Deshabilita botao para carregar arquivo de realizado mensal
    *
    */
    public void disableLoaderFile() {
        openButton.setVisible(false);
        fileInput.setVisible(false);
    }

    public void closeWindow() {
        this.setVisible(false);
        disableLoaderFile();

    }
    
}