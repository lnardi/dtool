/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.test;

import br.com.mavalu.dtool.control.DtoolLogControl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author q1D55V6G
 */
public class JavaDBTest {

    private Connection conn = null;
    private Statement sttm = null;
    private static String dabaseLink = "jdbc:derby:C:\\Users\\q1d55v6g\\Documents\\NetBeansProjects\\dtools\\dtoolsdb;create=true;user=luc;password=senha";

    public Connection query(String query) {
        try {
            //Obtenemos el Driver de Derby
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection("jdbc:derby:.\\dtoolsdb;create=true");
            if (conn != null) {
                //JOptionPane.showMessageDialog(null, "Base de Datos Lista");
                try {
                    PreparedStatement pstm = conn.prepareStatement(query);
                    pstm.execute();
                    pstm.close();
                    //JOptionPane.showMessageDialog(null, "Base de Datos Creada Correctamente");
                    System.out.println("SENTENCIA SQL EFECTUADA CORRECTAMENTE");
                } catch (SQLException ex) {
                    //JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
                    System.out.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null, "NO SE PUDO EFECTUAR LA SENTENCIA SQL", "Error", JOptionPane.ERROR_MESSAGE);
                    //JOptionPane.showMessageDialog(null, "NO SE PUDO EFECTUAR LA SENTENCIA SQL");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "NO SE PUDO EFECTUAR LA SENTENCIA SQL", "Error", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(null, "TRONO LA APLICACION EN EJECUTAR LAS SENTENCIAS SQL parte 2");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "NO SE PUDO EFECTUAR LA SENTENCIA SQL", "Error", JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(null, "TRONO LA APLICACION EN EJECUTAR LAS SENTENCIAS SQL parte 3");
        }
        return conn;
    }

    public Connection AccederBD() {
        try {
            //Obtenemos el Driver de Derby
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            //Obtenemos la Conexión
            conn = DriverManager.getConnection("jdbc:derby:.\\BD\\nombrebasededatos.db");
            if (conn != null) {
                System.out.println("Base de Datos Ya Leida Correctamente");
                //JOptionPane.showMessageDialog(null, "Base de Datos Ya Leida Correctamente");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Sistema Creado por Mario José Echeverría");
            System.out.println("NO SE ENCONTRO LA BASE DE DATOS");
            System.out.println("CREANDO BASE DE DATOS EN DERBY DATABASE");
            String createTableProyecto = "Sentence to create first table";
            String createTablePrimer = "Sentence to create second table";
            String createTableTopCoat = "Sentence to create third table";
//            String createTableCotizacion = "Sentence to create fourth table"
            //          CrearBD(createTableProyecto);
            //          CrearBD(createTablePrimer);
            //          CrearBD(createTableTopCoat);
            //         CrearBD(createTableCotizacion);
            //*************PRUEBAS*****************
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("ERROR DE TIPO ClassNotFoundException");
            //JOptionPane.showMessageDialog(null, "TRONO LA APLICACION EN ACCEDER A LA BASE DE DATOS parte 2");
        }
        return conn;
    }

    public void UID(String sqlcad) {
        try {
            //Obtenemos el Driver de Derby
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conn = DriverManager.getConnection("jdbc:derby:.\\BD\\nombrebasededatos.db");
            sttm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            sttm.executeUpdate(sqlcad);
            System.out.println("Conexión Exitosa a la Base de Datos");
            //JOptionPane.showMessageDialog(null, "Conexión exitosa");
            sttm.close();
            conn.close();
            if (conn != null) {
                System.out.println("Consulta Realizada Correctamente");
                //JOptionPane.showMessageDialog(null, "Base de Datos Ya Leida Correctamente");
            }
        } catch (SQLException e) {
            System.out.println("Error= " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error= " + e.getMessage());
        }
    }

    private void connection(int resultType) throws ClassNotFoundException, SQLException {

        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        conn = DriverManager.getConnection(dabaseLink);
        sttm = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, resultType);

    }

    public ResultSet selectQuery(String sqlcad) throws ClassNotFoundException, SQLException {
        ResultSet rs = null;
        connection(ResultSet.CONCUR_READ_ONLY);
        //String sqlcad = "Select nombre, m2xgal, pregal, precub, descripcion from primer";
        rs = sttm.executeQuery(sqlcad);
        close();
        return rs;
    }

    public int updateQuery(String sqlcad) throws ClassNotFoundException, SQLException {
        connection(ResultSet.CONCUR_READ_ONLY);
        //String sqlcad = "Select nombre, m2xgal, pregal, precub, descripcion from primer";
        int rs = sttm.executeUpdate(sqlcad);
        close();
        return rs;
    }

    private void close() throws SQLException {
        if (sttm != null) {
            sttm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws SQLException {
        try {
            /* Set the Nimbus look and feel */
            
            JavaDBTest teste = new JavaDBTest();
            
            ResultSet rst = teste.selectQuery("select * from LUC.DQL_QUERY");
            
            try {
                
                ResultSetMetaData rsmd = rst.getMetaData();
                int numberCols = rsmd.getColumnCount();
                for (int i = 1; i <= numberCols; i++) {
                    //print Column Names
                    System.out.print(rsmd.getColumnLabel(i) + "\t\t\t\t");
                }
                
                System.out.println("\n-------------------------------------------------");
                
                while (rst.next()) {
                    int id = rst.getInt(1);
                    String restName = rst.getString(2);
                    String cityName = rst.getString(3);
                    System.out.println(id + "\t\t" + restName + "\t\t" + cityName);
                }
                rst.close();
                
            } catch (SQLException sqlExcept) {
                sqlExcept.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            DtoolLogControl.log(ex, Level.SEVERE);
        }
    }

}
