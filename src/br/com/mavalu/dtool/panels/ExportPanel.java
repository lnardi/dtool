/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.dtool.panels;

import br.com.mavalu.dtool.DtoolJFrame;
import br.com.mavalu.dtool.control.DtoolExportControl;
import br.com.mavalu.dtool.control.DtoolLogControl;
import br.com.mavalu.useful.LoginTableModel;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author q1D55V6G
 */
public class ExportPanel extends javax.swing.JPanel {

   private DtoolJFrame dtoolJFrame;
   private JDialog jDialog;
   private LoginTableModel loginTableModel;
   private String csvFile;
   private String query;
   private static String path = null;
   private static String file_name = null;
   
   

   /**
    * Creates new form ExportPanel
    */
   public ExportPanel() {
      initComponents();
   }

   ExportPanel(JDialog jd, DtoolJFrame dt, LoginTableModel ltm, String q) throws IOException {
      initComponents();
      jDialog = jd;
      dtoolJFrame = dt;
      loginTableModel = ltm;
      query = q;

      if (path == null){
         path = new File(".").getCanonicalPath();
      }      
      jTextField1.setText(path);
      
      if (file_name != null){
          jTextField2.setText(file_name);
      }

   }

   /**
    * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
    * content of this method is always regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      buttonGroup1 = new javax.swing.ButtonGroup();
      buttonGroup2 = new javax.swing.ButtonGroup();
      jLabel1 = new javax.swing.JLabel();
      jButton1 = new javax.swing.JButton();
      jTextField1 = new javax.swing.JTextField();
      jButton3 = new javax.swing.JButton();
      jButton4 = new javax.swing.JButton();
      jTextField2 = new javax.swing.JTextField();
      jLabel2 = new javax.swing.JLabel();
      jPanel1 = new javax.swing.JPanel();
      jRadioButton1 = new javax.swing.JRadioButton();
      jRadioButton3 = new javax.swing.JRadioButton();
      jRadioButton2 = new javax.swing.JRadioButton();
      jScrollPane1 = new javax.swing.JScrollPane();
      jTextArea1 = new javax.swing.JTextArea();
      jLabel3 = new javax.swing.JLabel();
      jComboBox1 = new javax.swing.JComboBox();
      jCheckBox2 = new javax.swing.JCheckBox();
      jSpinner1 = new javax.swing.JSpinner();
      jCheckBox1 = new javax.swing.JCheckBox();
      jPanel2 = new javax.swing.JPanel();
      jRadioButton4 = new javax.swing.JRadioButton();
      jRadioButton5 = new javax.swing.JRadioButton();

      setBorder(javax.swing.BorderFactory.createTitledBorder(""));

      jLabel1.setText("Folder:");

      jButton1.setText(" ... ");
      jButton1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
         }
      });

      jTextField1.setAutoscrolls(false);
      jTextField1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField1ActionPerformed(evt);
         }
      });

      jButton3.setText("Cancelar");
      jButton3.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
         }
      });

      jButton4.setText("OK");
      jButton4.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton4ActionPerformed(evt);
         }
      });

      jTextField2.setText("dtool_export.csv");
      jTextField2.setAutoscrolls(false);
      jTextField2.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField2ActionPerformed(evt);
         }
      });

      jLabel2.setText("Arquivo de índices:");

      jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Destino"));

      buttonGroup1.add(jRadioButton1);
      jRadioButton1.setSelected(true);
      jRadioButton1.setText("Exportar tudo para o mesmo Folder");
      jRadioButton1.setEnabled(false);

      buttonGroup1.add(jRadioButton3);
      jRadioButton3.setText("Replicar Estrutura de Folders do servidor");
      jRadioButton3.setEnabled(false);
      jRadioButton3.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(javax.swing.event.ChangeEvent evt) {
            jRadioButton3StateChanged(evt);
         }
      });

      buttonGroup1.add(jRadioButton2);
      jRadioButton2.setText("Recriar estrutura baseada nose seguintes campos");
      jRadioButton2.setEnabled(false);

      jTextArea1.setColumns(20);
      jTextArea1.setLineWrap(true);
      jTextArea1.setRows(5);
      jTextArea1.setText("<ds_nome>/<ds_id>");
      jTextArea1.setEnabled(false);
      jScrollPane1.setViewportView(jTextArea1);

      jLabel3.setText("Exportar extrutura a :");
      jLabel3.setEnabled(false);

      jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3" }));
      jComboBox1.setToolTipText("0 é default. Se não encontrar a selecionada exporta a 0.");
      jComboBox1.setEnabled(false);

      jCheckBox2.setText("Criar um novo .csv a cada: ");

      jSpinner1.setModel(new javax.swing.SpinnerNumberModel(50000, 1, 1000000, 10000));

      jCheckBox1.setText("Exportar conteúdo ( baseado no atributo r_object_id)");
      jCheckBox1.addChangeListener(new javax.swing.event.ChangeListener() {
         public void stateChanged(javax.swing.event.ChangeEvent evt) {
            jCheckBox1StateChanged(evt);
         }
      });
      jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jCheckBox1ActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE))
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addGap(21, 21, 21)
                  .addComponent(jLabel3)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
               .addComponent(jRadioButton1)
               .addComponent(jRadioButton3)
               .addComponent(jRadioButton2)
               .addComponent(jCheckBox1)
               .addGroup(jPanel1Layout.createSequentialGroup()
                  .addComponent(jCheckBox2)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jCheckBox2)
               .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(jCheckBox1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel3)
               .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jRadioButton2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
      );

      jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Origem"));

      buttonGroup2.add(jRadioButton4);
      jRadioButton4.setSelected(true);
      jRadioButton4.setText("Exportar Resulado do GRID");

      buttonGroup2.add(jRadioButton5);
      jRadioButton5.setText("Executar a query e exportar o resultado");
      jRadioButton5.setEnabled(false);

      javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
      jPanel2.setLayout(jPanel2Layout);
      jPanel2Layout.setHorizontalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jRadioButton4)
               .addComponent(jRadioButton5))
            .addGap(0, 0, Short.MAX_VALUE))
      );
      jPanel2Layout.setVerticalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addComponent(jRadioButton4)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jRadioButton5))
      );

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
      this.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                  .addComponent(jButton4)
                  .addGap(106, 106, 106)
                  .addComponent(jButton3)
                  .addGap(53, 53, 53))
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                     .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                     .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                     .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                     .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                  .addContainerGap())))
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jLabel1)
               .addComponent(jButton1)
               .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
               .addComponent(jLabel2))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, 0)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(jButton3)
               .addComponent(jButton4))
            .addContainerGap())
      );
   }// </editor-fold>//GEN-END:initComponents

   private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

      JFileChooser chooser = new JFileChooser();

      chooser.setCurrentDirectory(new java.io.File(jTextField1.getText()));
      chooser.setDialogTitle("Selecione um arquivo ou crie um novo ");
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      chooser.setAcceptAllFileFilterUsed(false);

      FileFilter filter = new FileNameExtensionFilter("CSV file", new String[]{"csv"});
      chooser.setFileFilter(filter);

      chooser.setSelectedFile(new File(jTextField2.getText()));

      if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {

          path = chooser.getSelectedFile().getParent();
          file_name = chooser.getSelectedFile().getName();
          
         jTextField1.setText(path);
         jTextField2.setText(file_name);
         

      }      
   }//GEN-LAST:event_jButton1ActionPerformed

   private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

   }//GEN-LAST:event_jTextField1ActionPerformed

   private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
   }//GEN-LAST:event_jTextField2ActionPerformed

   private void jCheckBox1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox1StateChanged
      if (jCheckBox1.isSelected()) {
         jRadioButton1.setEnabled(true);      
         jRadioButton3.setEnabled(true);
       
      } else {
         jRadioButton1.setEnabled(false);      // Exportar para Mesmo Folder
         jRadioButton3.setEnabled(false);      // Exportar para extrutura do servidor

      }

   }//GEN-LAST:event_jCheckBox1StateChanged

   private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
      jDialog.setVisible(false);      
   }//GEN-LAST:event_jButton3ActionPerformed

   private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      jDialog.setVisible(false);      
      file_name = jTextField2.getText();

      // jRadioButton5 ==> Reexecutar a query e exportar o resultado;
      // jRadioButton4 ==>Exportar resultado apresentado no grid:
      SwingWorker workerQuery = new SwingWorker() {
         @Override
         protected Object doInBackground() throws Exception {

            try {

               dtoolJFrame.operationControl(dtoolJFrame.OP_PROGRESS_BAR, true, null);
               dtoolJFrame.operationControl(dtoolJFrame.OP_EXPORT, false, null);

               //Exportar o resultado do Grid, ou reexecutar a query?
               if (jRadioButton4.isSelected()) {
                  //Pega o nome do arquivo e caminho existente na tela.
                  csvFile = jTextField1.getText() + "\\" + jTextField2.getText();

                  long value = 0;

                  if (jSpinner1.getValue() instanceof Integer) {

                     value = ((Integer) jSpinner1.getValue()).longValue();
                  } else {
                     value = (Long) jSpinner1.getValue();
                  }

                  DtoolExportControl.exportQueryGrid(csvFile,
                          loginTableModel.getRows(), loginTableModel.getColumns(), jCheckBox1.isSelected(), jRadioButton1.isSelected(), jComboBox1.getSelectedIndex(), dtoolJFrame, (jCheckBox2.isSelected() ? value : 0));
               } else {

                  DtoolExportControl.exportQuery(query, csvFile, jCheckBox1.isSelected(), jRadioButton1.isSelected());
               }

            } catch (Exception ex) {
               DtoolLogControl.log(ex, Level.SEVERE);
               dtoolJFrame.operationControl(dtoolJFrame.OP_PROGRESS_BAR, false, null);
               dtoolJFrame.operationControl(dtoolJFrame.OP_EXPORT, true, null);
            }

            return null;

         }

         protected void done() {
            dtoolJFrame.operationControl(dtoolJFrame.OP_PROGRESS_BAR, false, null);
            dtoolJFrame.operationControl(dtoolJFrame.OP_EXPORT, true, null);

         }
      };

      workerQuery.execute();

   }//GEN-LAST:event_jButton4ActionPerformed

   private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed

   }//GEN-LAST:event_jCheckBox1ActionPerformed

   private void jRadioButton3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jRadioButton3StateChanged
      if (jRadioButton3.isSelected()) {
         jLabel3.setEnabled(true);
         jComboBox1.setEnabled(true);

      } else {
         jLabel3.setEnabled(false);
         jComboBox1.setEnabled(false);
      }
   }//GEN-LAST:event_jRadioButton3StateChanged


   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.ButtonGroup buttonGroup1;
   private javax.swing.ButtonGroup buttonGroup2;
   private javax.swing.JButton jButton1;
   private javax.swing.JButton jButton3;
   private javax.swing.JButton jButton4;
   private javax.swing.JCheckBox jCheckBox1;
   private javax.swing.JCheckBox jCheckBox2;
   private javax.swing.JComboBox jComboBox1;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JRadioButton jRadioButton1;
   private javax.swing.JRadioButton jRadioButton2;
   private javax.swing.JRadioButton jRadioButton3;
   private javax.swing.JRadioButton jRadioButton4;
   private javax.swing.JRadioButton jRadioButton5;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JSpinner jSpinner1;
   private javax.swing.JTextArea jTextArea1;
   private javax.swing.JTextField jTextField1;
   private javax.swing.JTextField jTextField2;
   // End of variables declaration//GEN-END:variables
}
