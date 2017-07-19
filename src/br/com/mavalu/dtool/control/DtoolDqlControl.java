/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.dtool.control;

import br.com.mavalu.useful.DocumentumUseful;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.common.DfException;
import java.util.logging.Level;
import javax.swing.JTable;
import br.com.mavalu.useful.LoginTableModel;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author q1D55V6G
 */
public class DtoolDqlControl {

    public static void executeQuery(String q, JTable jTable1, String top, String pg, boolean queryEdited, int dateFormat) throws DfException {

        DtoolLogControl.log("Executando query : \n" + q, Level.INFO);

        IDfCollection col = null;
        long startTime = System.currentTimeMillis();
        try {
            //Remove qualquer quebra de linha na query         
            col = DocumentumUseful.executarQuery(q.replaceAll("\\r|\\n", " "), 0, top);
            if (pg.equals("NO")) {
                pg = "0";
            }

            LoginTableModel queryTM = new LoginTableModel(col, Integer.parseInt(pg), dateFormat);
            TableColumn column = null;

            jTable1.setModel(queryTM);

            int size[] = queryTM.getColumnSize();
            int columns = queryTM.getColumnCount();

            for (int i = 0; i < columns; i++) {
                column = jTable1.getColumnModel().getColumn(i);
                column.setPreferredWidth(size[i] * 7);
            }

            // jLabel8.setText(String.valueOf(queryTM.getRowCount()));
            jTable1.repaint();

            //Insere a query na lista de queries
            DtoolQueryControl.storeQuery(q);
            DtoolLogControl.log("Query excutada com sucesso - Tempo: " + ((System.currentTimeMillis() - startTime) / 1000) + " Segundos", Level.INFO);
            if (queryTM.getRowCount() == 0) {
                DtoolLogControl.log("NEHUM REGISTRO ENCONTRADO <==", Level.INFO);
            }

        } catch (DfException ex) {
            DtoolLogControl.log(ex, Level.SEVERE);
            DtoolLogControl.log("Query executada com erro - Tempo: " + (System.currentTimeMillis() - startTime) / 1000 + " Segundos", Level.SEVERE);
        } finally {
            if (col != null) {
                DocumentumUseful.close(col);
            }
        }

    }

    public static String apiexec(String q) {

        try {
            DtoolLogControl.log("Dumping ID: " + q, Level.INFO);

            return DocumentumUseful.apiExec(q);
        } catch (DfException ex) {
            DtoolLogControl.log(ex, Level.SEVERE);
        }

        return null;

    }

    public static boolean validID(String id) {
        return DocumentumUseful.validId(id);
    }

    /**
     * Executa o processo de exportação e retorna true se ok
     *
     * @param id
     * @return
     */
    public static boolean getContent(String id) {

        boolean exportOK = true;
        try {

            if (DocumentumUseful.validId(id)) {

                String path = DocumentumUseful.exportDocument(".\\tmp", id);

                DtoolLogControl.log("Documento (" + id + ") baixado em => \"" + path + "\"", Level.INFO);

                File file = new File(path);

                Desktop.getDesktop().open(file);

            } else {
                DtoolLogControl.log("O valor selecionado não é o ID de um documento => \"" + id + "\"", Level.INFO);
                exportOK = false;
            }
        } catch (DfException ex) {
            DtoolLogControl.log(ex, Level.SEVERE);
            exportOK = false;
        } catch (IOException ex) {
            DtoolLogControl.log(ex, Level.SEVERE);
            exportOK = false;
        } catch (Exception ex) {
            DtoolLogControl.log(ex, Level.SEVERE);
            DtoolLogControl.log("O valor selecionado não é o ID de um documento => \"" + id + "\"", Level.INFO);
            exportOK = false;
        }
        return exportOK;
    }

    public static void executeScriptTemplate(LoginTableModel queryTableModel, JTable jTable1, String scriptTempalte, int dateFormat, String pg) {

        LoginTableModel scriptTM = new LoginTableModel(dateFormat);

        if (pg.equals("NO")) {
            pg = "0";
        }

        try {
            
            jTable1.setModel(new DefaultTableModel());
            jTable1.repaint();
            
            scriptTM.executeScriptTemplate(queryTableModel, scriptTempalte.replaceAll("\\r|\\n", " "));

            scriptTM.setPageSize(Integer.parseInt(pg));

            TableColumn column = null;

            jTable1.setModel(scriptTM);

            int size[] = scriptTM.getColumnSize();
            int columns = scriptTM.getColumnCount();

            for (int i = 0; i < columns; i++) {
                column = jTable1.getColumnModel().getColumn(i);
                column.setPreferredWidth(size[i] * 7);
            }

            DtoolQueryControl.storeQuery(scriptTempalte, true);

            jTable1.repaint();
        } catch (Exception ex) {
            DtoolLogControl.log(ex, Level.INFO);
        }

    }

    public static void executeScript(JTable jTable1, JTextArea jTextArea2, Boolean isDql) {
        //Carregar linhas
        LoginTableModel lTable = (LoginTableModel) jTable1.getModel();
        
        List <String[]> rows = lTable.getRows();
        
        for (String[] row : rows) {
            //Configurações
            String operation = row[1];
            //operação
            String config = row[0];
            
            if (isDql) {
                if (operation.toLowerCase().startsWith("select")) {
                    //execute query, return loginTable
                    //export content utilizando opções de config, caso haja.
                } else {
                    //Execute operationa, return result
                    //Gera Log.
                };
            } else {
                
            }

        }
        
        //Carregar primeira operação
        
        //Se houver, carregar configurações
        
        //Executar migração
        
        
        //Para cada item da lista executar a query.
    }

    public static void executeTemplate(String text, boolean jTextArea2Edited, JTable jTable1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
