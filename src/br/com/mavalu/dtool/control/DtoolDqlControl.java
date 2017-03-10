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
import javax.swing.table.TableColumn;

/**
 *
 * @author q1D55V6G
 */
public class DtoolDqlControl {

   public static void executeQuery(String q, JTable jTable1, String top, String pg, boolean queryEdited, int dateFormat) throws DfException {

      DtoolLogControl.log("Executando query : " + q, Level.INFO);

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

   
   public static boolean validID(String id ){
      return DocumentumUseful.validId(id);
   }
   
   
   /**
    * Executa o processo de exportação e retorna true se ok
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

    public static void executeScript(String text, boolean jTextArea2Edited) {
        //Executa uma query em todos os registros listados no grid
    }

}
