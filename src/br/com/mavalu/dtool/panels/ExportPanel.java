/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.dtool.panels;

import br.com.mavalu.dtool.DtoolJFrame;
import br.com.mavalu.dtool.control.DtoolExportControl;
import br.com.mavalu.dtool.control.DtoolLogControl;
import br.com.mavalu.dtool.export.ExportControl;
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
    private ExportControl ec = null;
    private static String file_name = null;
    private static long split_line_number = 0;

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

        if (path == null) {
            path = new File(".").getCanonicalPath();
        }
        jTextField1.setText(path);

        if (file_name != null) {
            jTextField2.setText(file_name);
        }
        
        if (split_line_number != 0){
            jSpinner1.setValue(split_line_number);
        }

    }

    public ExportControl getExportControl() {
        return ec;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
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
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jSpinner1 = new javax.swing.JSpinner();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jTextField3 = new javax.swing.JTextField();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();

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
        jTextField2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField2FocusLost(evt);
            }
        });
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

        jLabel3.setText("Exportar extrutura a :");
        jLabel3.setEnabled(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3" }));
        jComboBox1.setToolTipText("0 é default. Se não encontrar a selecionada exporta a 0.");
        jComboBox1.setEnabled(false);

        jCheckBox2.setText("Criar um novo .csv a cada: ");
        jCheckBox2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox2StateChanged(evt);
            }
        });

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(Long.valueOf(10000L), Long.valueOf(1L), Long.valueOf(1000000L), Long.valueOf(1000L)));

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

        jCheckBox3.setText("Exportar em subfolder :");
        jCheckBox3.setEnabled(false);
        jCheckBox3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox3StateChanged(evt);
            }
        });
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jTextField3.setText("dtool_export");
        jTextField3.setEnabled(false);
        jTextField3.setFocusable(false);
        jTextField3.setRequestFocusEnabled(false);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jCheckBox4.setText("Exportar o caminho no SO do servidor");
        jCheckBox4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox4StateChanged(evt);
            }
        });
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox4ActionPerformed(evt);
            }
        });

        jCheckBox5.setText("Quebrar arquivo quando  i_chronicle_id for diferente");
        jCheckBox5.setEnabled(false);
        jCheckBox5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox5StateChanged(evt);
            }
        });
        jCheckBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel3))
                            .addComponent(jRadioButton3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jCheckBox3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jRadioButton1)))
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox4)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jCheckBox5)))
                .addGap(41, 41, 41))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox2)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox5)
                .addGap(0, 0, 0)
                .addComponent(jCheckBox4)
                .addGap(0, 0, 0)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Threads"));

        jLabel4.setText("Número de Threads: ");

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(2, 1, 20, 1));
        jSpinner2.setEditor(new javax.swing.JSpinner.NumberEditor(jSpinner2, ""));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addGap(106, 106, 106)
                                .addComponent(jButton3)
                                .addGap(53, 53, 53))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addGap(18, 18, 18)
                                            .addComponent(jButton1))
                                        .addComponent(jLabel2))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                                        .addComponent(jTextField1)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
           jTextField3.setText(jTextField2.getText().substring(0, jTextField2.getText().length() - 4));
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
           jCheckBox3.setEnabled(true);
           jCheckBox4.setEnabled(false);
           

       } else {
           jRadioButton1.setEnabled(false);      // Exportar para Mesmo Folder
           jRadioButton3.setEnabled(false);      // Exportar para extrutura do servidor
           jCheckBox3.setEnabled(false);
           jCheckBox4.setEnabled(true);
       }

   }//GEN-LAST:event_jCheckBox1StateChanged

   private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       jDialog.setVisible(false);
   }//GEN-LAST:event_jButton3ActionPerformed

   private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       jDialog.setVisible(false);
       file_name = jTextField2.getText();
       path = jTextField1.getText();
       

       // jRadioButton5 ==> Reexecutar a query e exportar o resultado;
       // jRadioButton4 ==>Exportar resultado apresentado no grid:
       SwingWorker workerQuery = new SwingWorker() {
           @Override
           protected Object doInBackground() throws Exception {

               try {

                   dtoolJFrame.operationControl(dtoolJFrame.OP_PROGRESS_BAR, true, null);
                   dtoolJFrame.operationControl(dtoolJFrame.OP_EXPORT, false, null);

                   //Exportar o resultado do Grid, ou reexecutar a query?
                   //Pega o nome do arquivo e caminho existente na tela.
                   csvFile = jTextField1.getText() + "\\" + jTextField2.getText();

                   

                   if (jSpinner1.getValue() instanceof Integer) {

                       split_line_number = ((Integer) jSpinner1.getValue()).longValue();
                   } else {
                       split_line_number = (Long) jSpinner1.getValue();
                   }

                   long numberOfThreads = 0;

                   if (jSpinner2.getValue() instanceof Integer) {

                       numberOfThreads = ((Integer) jSpinner2.getValue()).longValue();
                   } else {
                       numberOfThreads = (Long) jSpinner2.getValue();
                   }

                   if (jCheckBox1.isSelected() || jCheckBox4.isSelected()) {
                       long startTime = System.currentTimeMillis();

                       ec = DtoolExportControl.exportQueryGridThreads(csvFile,
                               loginTableModel.getRows(), loginTableModel.getColumns(), jCheckBox1.isSelected(), jRadioButton1.isSelected(), jComboBox1.getSelectedIndex(), dtoolJFrame, (jCheckBox2.isSelected() ? split_line_number : 0), jCheckBox3.isSelected(), jTextField3.getText(), numberOfThreads, jCheckBox4.isSelected());

                       ExportThreadPanel etp = new ExportThreadPanel(ec, dtoolJFrame, jDialog);
                       jDialog.setContentPane(etp);
                       jDialog.pack();
                       jDialog.setVisible(true);

                       DtoolLogControl.log("Processo de Exportação finalizado com sucesso - Tempo: " + ((System.currentTimeMillis() - startTime) / 1000) + " Segundos", Level.INFO);

                   } else {

                       DtoolExportControl.exportQueryGrid(csvFile,
                               loginTableModel.getRows(), loginTableModel.getColumns(), jCheckBox1.isSelected(), jRadioButton1.isSelected(), jComboBox1.getSelectedIndex(), dtoolJFrame, (jCheckBox2.isSelected() ? split_line_number : 0), jCheckBox4.isSelected(), jCheckBox5.isSelected());
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

    private void jCheckBox3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox3StateChanged

        jTextField3.setEnabled(jCheckBox3.isSelected());

    }//GEN-LAST:event_jCheckBox3StateChanged

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextField2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField2FocusLost
        jTextField3.setText(jTextField2.getText().substring(0, jTextField2.getText().length() - 4));
    }//GEN-LAST:event_jTextField2FocusLost

    private void jCheckBox4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox4StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox4StateChanged

    private void jCheckBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox4ActionPerformed

    private void jCheckBox5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox5StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox5StateChanged

    private void jCheckBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox5ActionPerformed

    private void jCheckBox2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox2StateChanged
               if (jCheckBox2.isSelected()) {
                   jCheckBox5.setEnabled(true);                   
               } else {
                   jCheckBox5.setEnabled(false);                   
               }
    }//GEN-LAST:event_jCheckBox2StateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}
