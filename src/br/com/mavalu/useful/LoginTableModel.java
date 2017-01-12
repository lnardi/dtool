/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.useful;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfAttr;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author q1D55V6G
 */
public class LoginTableModel extends AbstractTableModel {

   private List<String> columns;

   public List<String> getColumns() {
      return columns;
   }

   public List<String[]> getRows() {
      return rows;
   }
   private List<String[]> rows;
   private int columnSize[] = null;
   private int page = 1;
   private int pageSize = 10;

   public int[] getColumnSize() {
      return columnSize;
   }

   public LoginTableModel(IDfCollection col, int pg) throws DfException {

      columns = new <String> ArrayList();
      rows = new <String[]> ArrayList();
      String row[] = new String[0];
      int attributes = 0;
      int lenght = 0;
      String attributeName = null;
      pageSize = pg;

      String value = null;

      if (col.next()) {

         attributes = col.getAttrCount();
         columnSize = new int[attributes];

         for (int i = 0; i < attributes; i++) {
            attributeName = col.getAttr(i).getName();
            columns.add(attributeName);
            columnSize[i] = attributeName.length();
         }
         row = new String[columns.size()];

         for (int i = 0; i < attributes; i++) {

            if (col.getAttr(i).isRepeating()) {
               row[i] = col.getAllRepeatingStrings(columns.get(i), "|");
            } else {
               row[i] = col.getValueAt(i).asString().replaceAll("\\r|\\n", " ");
            }

            lenght = row[i].length();
            if (lenght > columnSize[i]) {
               columnSize[i] = lenght;
            }

         }
         rows.add(row);

      }

      while (col.next()) {
         row = new String[columns.size()];

         for (int i = 0; i < attributes; i++) {

            if (col.getAttr(i).isRepeating()) {
               value = col.getAllRepeatingStrings(columns.get(i), "|");
            } else {
               if (col.getAttr(i).getDataType() == IDfAttr.DM_DOUBLE) {
                  value = String.valueOf(col.getValueAt(i).asDouble());

               } else {
                  value = col.getValueAt(i).asString().replaceAll("\\r|\\n", " ");
               }
            }

            row[i] = value;
            lenght = value.length();
            if (lenght > columnSize[i]) {
               columnSize[i] = (columnSize[i] + lenght) / 2;
            }
         }
         rows.add(row);

      }

      if (pageSize == 0) {
         pageSize = rows.size();
      }

      col.close();
   }

   @Override
   public String getColumnName(int i
   ) {
      return columns.get(i);
   }

   public int getColumnCount() {
      return columns.size();
   }

   public int getRowCount() {

      int initialRow = (page - 1) * pageSize;

      int diff = rows.size() - initialRow;

      return (diff > pageSize) ? pageSize : diff;

      // return rows.size();
      //return pageSize;
   }

   public Object getValueAt(int r, int c) {

      return (rows.get(computeRow(r)))[c];
   }

   /**
    * Metodo deve ser removido
    *
    * @param p
    */
   public void setPage(int p) {
      page = p;
   }

   public void setPageSize(int ps) {
      pageSize = ps;
      page = 1;
   }

   public boolean nextPage() {

      boolean canIncrese = page < getMaxPageNumber();

      page = canIncrese ? ++page : page;

      return canIncrese;

   }

   public boolean previousPage() {

      boolean canIncrese = page > 1;

      page = canIncrese ? --page : page;

      return canIncrese;

   }

   public int getMaxPageNumber() {
      int pgs = rows.size() / pageSize;
      int rst = rows.size() % pageSize;
      return (rst) > 0 ? ++pgs : pgs;
   }

   /**
    * Retonar o número de lihas existente na query
    *
    * @return
    */
   public int getMaxRowsCount() {
      return rows.size();
   }

   private int computeRow(int r) {
      return ((page - 1) * pageSize) + r;
   }

   public int getPage() {
      return page;
   }
}
