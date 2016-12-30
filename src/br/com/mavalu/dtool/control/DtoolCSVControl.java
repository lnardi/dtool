/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.dtool.control;

import br.com.mavalu.useful.QueryTableModel;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author q1D55V6G
 */
public class DtoolCSVControl {

   public static void processCSV(File file, List<String[]> rowList, List<String> collumList) throws IOException {

      BufferedWriter csv = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "ISO-8859-1"));
//new java.io.BufferedWriter(new java.io.FileWriter(file));

      Iterator<String> it = collumList.iterator();

      String value = "";
      while (it.hasNext()) {
         value = value + it.next();
         if (it.hasNext()) {
            value = value + ";";
         }
      };

      csv.write(value);
      csv.newLine();

      Iterator<String[]> rows = rowList.iterator();
      String[] row = null;

      while (rows.hasNext()) {
         row = rows.next();
         value = "";
         int checkLastCollun = row.length - 1;
         for (int i = 0; i < row.length; i++) {

            value = value + row[i];

            if (i < checkLastCollun) {
               value = value + ";";
            }
         }
         csv.write(value);
         csv.newLine();

      }

      csv.flush();
      csv.close();
   }

   public static void processCSV(String query, File csvFile) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }

}
