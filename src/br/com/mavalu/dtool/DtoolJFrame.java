/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.dtool;

import br.com.mavalu.dtool.panels.DumpPanel;
import br.com.mavalu.dtool.panels.LoginPanel;
import br.com.mavalu.dtool.panels.QueryPanel;
import br.com.mavalu.dtool.panels.LogPanel;
import br.com.mavalu.dtool.control.DtoolLogControl;
import br.com.mavalu.dtool.panels.DtoolSplashScreen;
import br.com.mavalu.dtool.panels.ScriptPanel;
import br.com.mavalu.useful.DbUseful;
import br.com.mavalu.useful.LoginTableModel;
import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 *
 * @author q1D55V6G
 */
public class DtoolJFrame extends javax.swing.JFrame {

    private final LogPanel logPanel;
    private final LoginPanel loginPanel;
    private final QueryPanel queryPanel;
    private final ScriptPanel scriptPanel;
    private final DumpPanel dumpPanel;
    public final static int OP_SERVER_CONNECTION = 0;
    public final static int OP_LOGIN = 1;
    public final static int OP_LOGIN_CREDENTIALS = 2;
    public final static int OP_PROGRESS_BAR = 3;
    public final static int OP_DUMP = 4;
    public final static int OP_QUERY_RESULT_SIZE = 5;
    public final static int OP_SET_QUERY = 6;
    public final static int OP_EXPORT = 7;
    public final static int OP_EXPORT_COUNT = 8;
    public final static int OP_SCRIPT_SHOW = 9;
    public final static int OP_IMPORT_FILE = 10;    
    public final static int OP_IMPORT_SCRIPT = 10; 
    public final static int OP_REFRESH_QUERY_RESULT_TABLE = 11;
    public static boolean STOP = false;
    
    public int dateFormat = 0;

    

    /**
     * Creates new form dtoolJFrame
     */
    public DtoolJFrame() throws IOException, ClassNotFoundException, SQLException {

        this.setVisible(false);

        DtoolSplashScreen sp = new DtoolSplashScreen();
        sp.initUI(this);
        LogManager.getLogManager().readConfiguration(new FileInputStream(".\\dtoolLog.properties"));

        initComponents();

        ImageIcon img = new ImageIcon("./img/img (1).png");

        this.setIconImage(img.getImage());

        logPanel = new LogPanel();
        DtoolLogControl.setLogComponet(logPanel.getjTextPane1());

        loginPanel = new LoginPanel(this);
        queryPanel = new QueryPanel(this);
        scriptPanel = new ScriptPanel(this);
        dumpPanel = new DumpPanel(this);

        jTabbedPane1.addTab("Login", loginPanel);
        jTabbedPane1.addTab("Query", queryPanel);
        jTabbedPane1.addTab("Dump", dumpPanel);
        jTabbedPane1.addTab("Script", scriptPanel);
        

        jSplitPane1.setRightComponent(logPanel);

        this.setPreferredSize(new Dimension(1050, 604));

        pack();

        setLocationRelativeTo(null);
        //Carrega a lista de docbases
        loginPanel.loadDocbaseList();

        sp = null;

        //Open data base
        DbUseful.open();

    }

    /**
     *
     * Metodo utilizado para execução de ações entre os difernetes componentes
     * de interface
     *
     * @param op - Operação: SERVER_CONNECTION = 0; LOGIN = 1; LOGIN_CREDENTIALS
     * = 2; PROGRESS_BAR = 3; DUMP = 4; EXPORT = 7;
     * @param status Flag que seta o status nos componentes;
     * @param obj Qualquer objeto com status, informações para processamento.
    **/
    public void operationControl(int op, boolean status, Object obj) {

        
        this.logPanel.operationControl(op, status, obj);
        this.dumpPanel.operationControl(op, status, obj);
        this.loginPanel.operationControl(op, status, obj);
        this.queryPanel.operationControl(op, status, obj);       
        this.scriptPanel.operationControl(op, status, obj);       

        switch (op) {
            case DtoolJFrame.OP_DUMP: 
                if (status) {
                    jTabbedPane1.setSelectedIndex(2);
                }
                break;
            case DtoolJFrame.OP_SCRIPT_SHOW: 
                    jTabbedPane1.setSelectedIndex(3);
                break;
                
        }

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
      jSplitPane1 = new javax.swing.JSplitPane();
      jTabbedPane1 = new javax.swing.JTabbedPane();

      setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
      setTitle("Dtool v1.0");
      setSize(new java.awt.Dimension(779, 654));

      jSplitPane1.setDividerLocation(450);
      jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
      jSplitPane1.setResizeWeight(1.0);
      jSplitPane1.setToolTipText("");
      jSplitPane1.setMaximumSize(new java.awt.Dimension(0, 0));
      jSplitPane1.setMinimumSize(new java.awt.Dimension(0, 0));
      jSplitPane1.setName(""); // NOI18N
      jSplitPane1.setTopComponent(jTabbedPane1);

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addGap(0, 0, 0)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(0, 0, 0))
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
       * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {            
            DtoolLogControl.log(ex, Level.SEVERE);
        } catch (InstantiationException ex) {
            DtoolLogControl.log(ex, Level.SEVERE);            
        } catch (IllegalAccessException ex) {            
            DtoolLogControl.log(ex, Level.SEVERE);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {            
            DtoolLogControl.log(ex, Level.SEVERE);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                    new DtoolJFrame();//.setVisible(true);
                } catch (Exception ex) {
                    DtoolLogControl.log(ex, Level.SEVERE);                    
                }
            }
        });
    }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.ButtonGroup buttonGroup1;
   private javax.swing.JSplitPane jSplitPane1;
   private javax.swing.JTabbedPane jTabbedPane1;
   // End of variables declaration//GEN-END:variables


}
