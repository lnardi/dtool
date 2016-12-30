/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.useful;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author q1D55V6G
 */
public class DbUseful {

   private static Connection conn = null;
   private static Statement sttm = null;
   private static final String dabaseLink = "jdbc:derby:.\\dtoolsdb;create=true;user=luc;password=senha";

   private static void connection(int resultType) throws ClassNotFoundException, SQLException {

      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
      conn = DriverManager.getConnection(dabaseLink);
      sttm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, resultType);

   }

   public static void open() throws ClassNotFoundException, SQLException {
      connection(ResultSet.CONCUR_READ_ONLY);
   }

   public static ResultSet selectQuery(String sqlcad) throws ClassNotFoundException, SQLException {

      connection(ResultSet.CONCUR_READ_ONLY);
      //String sqlcad = "Select nombre, m2xgal, pregal, precub, descripcion from primer";
      ResultSet rs = sttm.executeQuery(sqlcad);

      return rs;
   }

   public static int updateQuery(String sqlcad) throws ClassNotFoundException, SQLException {
      connection(ResultSet.CONCUR_READ_ONLY);
      //String sqlcad = "Select nombre, m2xgal, pregal, precub, descripcion from primer";
      int rs = sttm.executeUpdate(sqlcad);
      close();
      return rs;
   }

   public static void close() throws SQLException {
      if (sttm != null) {
         sttm.close();
      }
      if (conn != null) {
         conn.close();
      }
   }

}
