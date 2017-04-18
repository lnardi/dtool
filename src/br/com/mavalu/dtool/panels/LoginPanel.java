/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.dtool.panels;

import br.com.mavalu.dtool.DtoolJFrame;
import br.com.mavalu.dtool.control.DtoolLogControl;
import br.com.mavalu.dtool.control.DtoolLoginControl;
import br.com.mavalu.useful.Login;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.SwingWorker;

/**
 *
 * @author q1D55V6G
 */
public class LoginPanel extends javax.swing.JPanel {

    private Object selectecStoredPassword;
    private final DtoolJFrame dtoolJFrame;
    private SwingWorker workerDocbases;
    private boolean runningLoadLogin;

    /**
     * Creates new form LoginPanel
     */
    public LoginPanel(DtoolJFrame dtf) {
        initComponents();
        dtoolJFrame = dtf;
        DtoolLogControl.log("Conectando no servidor Documentum", Level.INFO);

        //Associa o textfield para log;
        //  DefaultListModel model = new DefaultListModel();
        //  jList2.setModel(model);
        jList2.setListData(new ArrayList().toArray());
        jList1.setListData(new ArrayList().toArray());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton3 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel6 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<String>();
        jLabel5 = new javax.swing.JLabel();

        jMenuItem1.setText("Excluir");
        jPopupMenu1.add(jMenuItem1);

        setBorder(javax.swing.BorderFactory.createTitledBorder("    "));

        jLabel1.setText("Docbase List:");

        jLabel2.setText("Login:");

        jLabel3.setText("Password:");

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Dfc.Properties:");

        jButton3.setText("Edit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jList2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList2MouseClicked(evt);
            }
        });
        jList2.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList2ValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(jList2);

        jLabel6.setText("Last Logins:");

        jButton4.setText("Reload...");
        jButton4.setEnabled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel11.setText("Docbase:");

        jTextField4.setEditable(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "MM/dd/yyyy hh:mi:ss", "dd/MM/yyyy hh:mi:ss"}));

        jLabel5.setText("Date Format: ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(10, 10, 10)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(10, 10, 10)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(288, 288, 288))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton4)
                                        .addComponent(jLabel6))
                                    .addComponent(jButton3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel4)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(38, 38, 38))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25)
                .addComponent(jButton1)
                .addGap(0, 240, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Seleciona a docbase
     *
     * @param evt
     */
    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked

        if (workerDocbases != null && !workerDocbases.isDone()) {
            //DtoolLogControl.log("Processo já em execução", Level.WARNING);
            return;
        }

        workerDocbases = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {

                dtoolJFrame.operationControl(dtoolJFrame.OP_PROGRESS_BAR, true, null);
                List list = DtoolLoginControl.loadDocbaseLogins((String) jList1.getSelectedValue());
                jList2.setListData(list.toArray());

                if (list.size() == 1) {
                    Login lg = (Login) list.get(0);
                    jTextField1.setText(lg.getLogin());
                    jPasswordField1.setText(lg.getSenha());
                    selectecStoredPassword = lg.getSenha();
                } else {
                    jTextField1.setText("");
                    jPasswordField1.setText("");
                }

                return null;
            }

            @Override
            protected void done() {
                dtoolJFrame.operationControl(dtoolJFrame.OP_PROGRESS_BAR, false, null);
                jTextField4.setText((String) jList1.getSelectedValue());
            }
        };

        workerDocbases.execute();
        selectecStoredPassword = null;
    }//GEN-LAST:event_jList1MouseClicked

    /**
     * Login
     *
     * @param evt
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        final String user = jTextField1.getText();
        final String password = String.copyValueOf(jPasswordField1.getPassword());
        final String docbase = (String) jList1.getSelectedValue();
        //Somente armazenará o login se houve a mudança de uma senha para um login armazenado ou se é um login novo.
        final boolean storeLogin = selectecStoredPassword == null || !password.equals(selectecStoredPassword);
        selectecStoredPassword = null;
        
        //Armazena o formato padrão de data que foi escolhido pelo usuário.
        dtoolJFrame.dateFormat = jComboBox1.getSelectedIndex();

        SwingWorker worker;
        worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {

                dtoolJFrame.operationControl(dtoolJFrame.OP_LOGIN, false, null);

                if (jList1.getSelectedValue() == null) {
                    DtoolLogControl.log("Uma das docbases deve ser selecionada", Level.WARNING);
                    dtoolJFrame.operationControl(dtoolJFrame.OP_SERVER_CONNECTION, true, null);
                } else {
                    boolean loginResult = DtoolLoginControl.login(docbase, user, password);
                    dtoolJFrame.operationControl(dtoolJFrame.OP_LOGIN, true, null);
                    dtoolJFrame.operationControl(dtoolJFrame.OP_LOGIN_CREDENTIALS, true, new String[]{user, docbase});
                    if (loginResult) {
                        DtoolLogControl.log("Logando com usuário ( " + user + " )", Level.INFO);
                        //Registra o novo login na tabela de logins;
                        if (storeLogin) {
                            DtoolLoginControl.storeLogin(docbase, user, password);
                        }
                    }

                }

                return null;
            }

            @Override
            protected void done() {

            }

        };

        worker.execute();

        /**
         * if (jList1.getSelectedValue() == null) { DtoolLogControl.log("Uma das
         * docbases deve ser selecionada", Level.INFO); } else {
         * DtoolLoginControl.login((String) jList1.getSelectedValue(),
         * jTextField1.getText(),
         * String.copyValueOf(jPasswordField1.getPassword()));
         * DtoolLogControl.log("Logado com sucesso!!! ", Level.INFO); }
         *
         */
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        DtoolLoginControl.loadDfc();
        jButton4.setEnabled(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jList2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList2MouseClicked
        LoadingLogin();


    }//GEN-LAST:event_jList2MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        loadDocbaseList();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged

    }//GEN-LAST:event_jList1ValueChanged

    private void jList2ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList2ValueChanged

    }//GEN-LAST:event_jList2ValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables

    public void loadDocbaseList() {

        SwingWorker worker;
        worker = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {

                dtoolJFrame.operationControl(dtoolJFrame.OP_SERVER_CONNECTION, false, null);

                List list = DtoolLoginControl.loadDocbases();

                if (list == null) {
                    list = new <String> ArrayList();
                    list.add("<<<Error>>>");
                    jButton4.setEnabled(true);
                } else {
                    jButton4.setEnabled(false);
                }

                jList1.setListData(list.toArray());
                return null;
            }

            @Override
            protected void done() {
                //controla os botões no login
                dtoolJFrame.operationControl(dtoolJFrame.OP_SERVER_CONNECTION, true, null);
            }
        };

        worker.execute();

    }

    /**
     *
     * Metodo utilizado para execução de ações entre os difernetes componentes
     * de interface
     *
     * @param op - Operação: SERVER_CONNECTION = 0; LOGIN = 1; LOGIN_CREDENTIALS
     * = 2; PROGRESS_BAR = 3; DUMP = 4;
     * @param status Flag que seta o status nos componentes;
     * @param obj Qualquer objeto com status, informações para processamento.
     */
    public void operationControl(int op, boolean status, Object obj) {
        switch (op) {
            case DtoolJFrame.OP_SERVER_CONNECTION:
                jButton3.setEnabled(status);
                jButton1.setEnabled(status);
                break;
            case DtoolJFrame.OP_LOGIN:
                jButton3.setEnabled(status);
                jButton1.setEnabled(status);
                break;

            case 2:
                break;

        }

    }

    private synchronized void LoadingLogin() {
        Login lg = (Login) jList2.getSelectedValue();
        jTextField1.setText(lg.getLogin());
        jPasswordField1.setText(lg.getSenha());
        selectecStoredPassword = lg.getSenha();
    }

}
