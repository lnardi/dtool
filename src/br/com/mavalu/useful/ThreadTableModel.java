/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.useful;

import br.com.mavalu.dtool.export.DocumentumExportControl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author q1D55V6G
 */
public class ThreadTableModel extends AbstractTableModel {

   private List<String> columns;
   private List<String[]> rows;
   private int columnSize[] = null;
   private int page = 1;
   private int pageSize = 1000;

   public int[] getColumnSize() {
      return columnSize;
   }

   public ThreadTableModel(List col, int pg) throws SQLException {

      columns = new <String> ArrayList();
      columns.add("Nome");
      columns.add("Status");
      columns.add("Exportados");
      columns.add("Imp/Min");
      columns.add("Imp/Hora");

      rows = new <String[]> ArrayList();
      columnSize = new int[5];
      columnSize[0] = 50;
      columnSize[1] = 40;
      columnSize[2] = 30;
      columnSize[3] = 30;
      columnSize[4] = 30;

      DocumentumExportControl pb = null;

      String value = null;
      String row[] = null;

      Iterator it = col.iterator();

      while (it.hasNext()) {
         row = new String[5];
         pb = (DocumentumExportControl) it.next();

         row[0] = pb.getName();
         row[1] = pb.getsStatus();
         row[2] = String.valueOf(pb.getFileProcessedNumber());
         row[3] = "0";
         row[4] = "0";

         rows.add(row);

      }

   }

   @Override
   public String getColumnName(int i) {
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

   public void setValueAt(String value, int l, int c) {
      if (rows.size() >= l) {

         String line[] = rows.get(l);
         if (line.length >= c) {
            line[c] = value;
         }
      }
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

   public List<String[]> getRows() {
      return rows;
   }

   public void addRow(String[] row) {
      rows.add(row);
   }
}
