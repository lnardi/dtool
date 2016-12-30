/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.dtool.control;

import br.com.mavalu.useful.DbUseful;
import br.com.mavalu.useful.DocumentumUseful;
import br.com.mavalu.useful.Login;
import br.com.mavalu.useful.QueryTableModel;
import com.documentum.fc.common.DfException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author q1D55V6G
 */
public class DtoolQueryControl {

   public static void loadQueries(JTable jTable1) {

      
      //Pegar o Host e montar a query
      String query = "Select \"id\", \"query\", \"creation_time\" from luc.\"dql_queries\" order by \"creation_time\" DESC";
      TableColumn column = null;

      List<Login> list = list = new ArrayList<Login>();

      try {
         //FAzer um select na tabela de logins utilizando a docbase e o Host
         ResultSet rs = DbUseful.selectQuery(query);

         QueryTableModel queryTM = new QueryTableModel(rs, 1000);

         jTable1.setModel(queryTM);

         int size[] = queryTM.getColumnSize();
         int columns = queryTM.getColumnCount();

         for (int i = 0; i < columns; i++) {
            column = jTable1.getColumnModel().getColumn(i);

            if (i != 1) {
               column.setPreferredWidth(size[i] * 5);
               //column.setResizable(false);
               column.setMaxWidth(size[i] * 7);
            } else {
               column.setPreferredWidth(size[i]);
            }
         }

         // jLabel8.setText(String.valueOf(queryTM.getRowCount()));
         jTable1.repaint();

         DbUseful.close();

      } catch (ClassNotFoundException ex) {

         DtoolLogControl.log(ex, Level.SEVERE);
      } catch (SQLException ex) {
         DtoolLogControl.log(ex, Level.SEVERE);
      }
      //retornar os valores para popular a lista de logins

   }

   public static void updateQueries(String query) {

   }

   public static int storeQuery(String query) {
//Pegar o Host e montar a query

      Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
      try {
         String hostName = DocumentumUseful.getHostName();
         
         query = query.trim().replaceAll("(\\r|\\n)", "");

         String insertQuery = "INSERT INTO LUC.\"dql_queries\" (\"query\", \"creation_time\", \"query_hash\") VALUES ('" + query.replace("'", "''") + "','" + currentTimestamp + "', " + query.hashCode()
                 + " ) ";

         //FAzer um select na tabela de logins utilizando a docbase e o Host            
         return DbUseful.updateQuery(insertQuery);
         //DtoolLogControl.log("Linhas afetadas: " + result, Level.SEVERE);
      } catch (ClassNotFoundException ex) {

         DtoolLogControl.log(ex, Level.SEVERE);
      } catch (SQLException ex) {
         if (!ex.getMessage().contains("QUERY_HASH")) {
            DtoolLogControl.log(ex, Level.SEVERE);
         }
      } catch (DfException ex) {
         DtoolLogControl.log(ex, Level.SEVERE);
      }

      return 0;
   }

   public static int removeQuerys(int[] rows, QueryTableModel model) {

      String rowsId = "";
      int control = rows.length - 1;

      for (int i = 0; i < rows.length; i++) {

         rowsId = rowsId + model.getValueAt(rows[i], 0);
         if (i < control) {
            rowsId = rowsId + ",";
         }
      }

      String query = "delete from LUC.\"dql_queries\" where \"id\" in (" + rowsId + ")";
      try {
         //FAzer um select na tabela de logins utilizando a docbase e o Host            

         return DbUseful.updateQuery(query);
         //DtoolLogControl.log("Linhas afetadas: " + result, Level.SEVERE);
      } catch (ClassNotFoundException ex) {

         DtoolLogControl.log(ex, Level.SEVERE);
      } catch (SQLException ex) {
         DtoolLogControl.log(ex, Level.SEVERE);
      }
      return 0;
   }

}
