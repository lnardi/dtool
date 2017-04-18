/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.dtool.panels;

import br.com.mavalu.dtool.DtoolJFrame;
import java.util.List;
import java.util.Random;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;

/**
 *
 * @author q1D55V6G
 */
public class LogPanel extends javax.swing.JPanel {

    SwingWorker<Void, Integer> worker = null;
    Random rand = new Random();

    /**
     * Creates new form LogPanel
     */
    public LogPanel() {

        initComponents();

        jProgressBar2.setVisible(false);

        jLabel1.setVisible(false);
        jLabel17.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jScrollPane7 = new javax.swing.JScrollPane();
      jTextPane1 = new javax.swing.JTextPane();
      jPanel10 = new javax.swing.JPanel();
      jLabel13 = new javax.swing.JLabel();
      jLabel14 = new javax.swing.JLabel();
      jLabel15 = new javax.swing.JLabel();
      jLabel16 = new javax.swing.JLabel();
      jLabel7 = new javax.swing.JLabel();
      jLabel8 = new javax.swing.JLabel();
      jLabel1 = new javax.swing.JLabel();
      jLabel17 = new javax.swing.JLabel();
      jProgressBar2 = new javax.swing.JProgressBar();

      jScrollPane7.setViewportView(jTextPane1);

      jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

      jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
      jLabel13.setText("Docbase:");

      jLabel14.setText("-------");

      jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
      jLabel15.setText("User:");

      jLabel16.setText("-------");

      jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
      jLabel7.setText("Lines:");

      jLabel8.setText("0");

      jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
      jLabel1.setText("Exportando:");

      jLabel17.setText("-------");

      javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
      jPanel10.setLayout(jPanel10Layout);
      jPanel10Layout.setHorizontalGroup(
         jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel10Layout.createSequentialGroup()
            .addGap(4, 4, 4)
            .addComponent(jLabel13)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel14)
            .addGap(31, 31, 31)
            .addComponent(jLabel15)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel16)
            .addGap(31, 31, 31)
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel17)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 143, Short.MAX_VALUE)
            .addComponent(jLabel7)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel8)
            .addContainerGap())
      );
      jPanel10Layout.setVerticalGroup(
         jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
               .addComponent(jLabel16)
               .addComponent(jLabel15)
               .addComponent(jLabel14)
               .addComponent(jLabel13)
               .addComponent(jLabel8)
               .addComponent(jLabel7)
               .addComponent(jLabel1)
               .addComponent(jLabel17)))
      );

      jProgressBar2.setToolTipText("");
      jProgressBar2.setBorderPainted(false);
      jProgressBar2.setFocusable(false);
      jProgressBar2.setString("Processando...");
      jProgressBar2.setStringPainted(true);

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
      this.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addComponent(jScrollPane7)
         .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
         .addComponent(jProgressBar2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
            .addGap(0, 0, 0)
            .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, 0)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
      );
   }// </editor-fold>//GEN-END:initComponents


   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel13;
   private javax.swing.JLabel jLabel14;
   private javax.swing.JLabel jLabel15;
   private javax.swing.JLabel jLabel16;
   private javax.swing.JLabel jLabel17;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JLabel jLabel8;
   private javax.swing.JPanel jPanel10;
   private javax.swing.JProgressBar jProgressBar2;
   private javax.swing.JScrollPane jScrollPane7;
   private javax.swing.JTextPane jTextPane1;
   // End of variables declaration//GEN-END:variables

    public JTextPane getjTextPane1() {
        return jTextPane1;
    }

    public void setLabels(String docbase, String user) {
        jLabel14.setText(docbase);
        jLabel16.setText(user);
    }

    /**
     *
     * Metodo utilizado para execução de ações entre os difernetes componentes de interface
     *
     * @param op - Operação: SERVER_CONNECTION = 0; LOGIN = 1; LOGIN_CREDENTIALS = 2; PROGRESS_BAR = 3; DUMP = 4;
     * @param status Flag que seta o status nos componentes;
     * @param obj Qualquer objeto com status, informações para processamento.
     */
    public void operationControl(int op, boolean status, Object obj) {
        String[] list;
        switch (op) {            
            case DtoolJFrame.OP_SERVER_CONNECTION: //Login
                showProgressBar(!status);
                break;
            case DtoolJFrame.OP_LOGIN: //Login
                showProgressBar(!status);
                break;
            case DtoolJFrame.OP_LOGIN_CREDENTIALS: //Progress Bar                
                if (obj != null) {
                    list = (String[]) obj;
                    setLabels(list[1], list[0]);
                }
                break;
            case DtoolJFrame.OP_PROGRESS_BAR: //Progress Bar                
                showProgressBar(status);
                break;
            case DtoolJFrame.OP_QUERY_RESULT_SIZE: //Progress Bar                
                list = (String[]) obj;
                jLabel8.setText(list[0]);
                break;

            case DtoolJFrame.OP_EXPORT: //Progress Bar                
                jLabel1.setVisible(!status);
                jLabel17.setVisible(!status);
                break;
            case DtoolJFrame.OP_EXPORT_COUNT: //Progress Bar                
                list = (String[]) obj;
                jLabel17.setText(list[0]);
                break;
            case DtoolJFrame.OP_DUMP: //Progress Bar                
                showProgressBar(status);
                break;
        }

    }

    private void showProgressBar(boolean status) {
        jProgressBar2.setVisible(status);

        if (status) {

            worker = new SwingWorker<Void, Integer>() {
                @Override
                protected Void doInBackground() throws Exception {
                    while (!isCancelled()) {

                        for (int i = 0; i < 100; i++) {
                            Thread.sleep(rand.nextInt(50));// Simulate loading
                            publish(i);// Notify progress                     
                        }
                    }
                    return null;
                }

                @Override
                protected void process(List<Integer> chunks) {
                    jProgressBar2.setValue(chunks.get(chunks.size() - 1));
                }

                @Override
                protected void done() {

                }

            };

            worker.execute();
        } else {

            if (worker != null) {
                worker.cancel(false);
                worker = null;
            }

        }
    }

}
