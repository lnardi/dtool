/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.dtool.control;

import br.com.mavalu.useful.DbUseful;
import br.com.mavalu.useful.DocumentumUseful;
import br.com.mavalu.useful.Login;
import com.documentum.fc.common.DfException;
import java.awt.Desktop;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author q1D55V6G
 */
public class DtoolLoginControl {

    public static void loadDfc() {
        try {
            File file = new File(".\\documentum\\config\\dfc.properties");

            Desktop.getDesktop().open(file);

        } catch (Exception ex) {
            DtoolLogControl.log(ex, Level.SEVERE);
        }

    }

    public static boolean login(String docbase, String user, String password) {

        try {
            //Faz o login no documentum
            DocumentumUseful.login(docbase, user, password);
        } catch (DfException ex) {
            DtoolLogControl.log(ex, Level.SEVERE);
            return false;
        } catch (Exception ex) {
            DtoolLogControl.log(ex, Level.SEVERE);
            return false;
        }

        return true;
    }

    public static List<String> loadDocbases() {

        List<String> list = null;
        try {
            list = DocumentumUseful.getDobaseList();

            DtoolLogControl.log("Conexão com servidor (" + DocumentumUseful.getHostName() + ") ativada e lista de docbases disponíveis retornada", Level.INFO);

        } catch (DfException ex) {
            DtoolLogControl.log("Erro de conexão. Ajuste a conexão e clique no botão \"Relaoad\"", Level.WARNING);
            DtoolLogControl.log(ex, Level.SEVERE);
        }

        return list;
    }

    public static List<Login> loadDocbaseLogins(String docbase) {
        //Pegar o Host e montar a query

        List<Login> list = new ArrayList<Login>();
        try {

            String query = "Select \"login\", \"password\" from \"logins\" where "
                    + "\"docbase_host\" = '" + DocumentumUseful.getHostName()
                    + "' and \"docbase\" = '" + docbase + "'";

            Login lg;

            //FAzer um select na tabela de logins utilizando a docbase e o Host
            ResultSet rs = DbUseful.selectQuery(query);

            while (rs.next()) {
                lg = new Login();
                lg.login = rs.getString(1);
                lg.senha = rs.getString(2);
                list.add(lg);
            }

            rs.close();
            DbUseful.close();

        } catch (ClassNotFoundException ex) {

            DtoolLogControl.log(ex, Level.SEVERE);
        } catch (SQLException ex) {
            DtoolLogControl.log(ex, Level.SEVERE);
        } catch (DfException ex) {
            DtoolLogControl.log(ex, Level.SEVERE);
        }
        //retornar os valores para popular a lista de logins
        return list;
    }

    public static void storeLogin(String docbase, String user, String password) {
//Pegar o Host e montar a query

        Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

        try {

            String hostName = DocumentumUseful.getHostName();

            String insertQuery = "INSERT INTO LUC.\"logins\" (\"docbase_host\", \"docbase\", \"login\", "
                    + "\"password\", \"creation_time\") VALUES ('" + hostName + "', '" + docbase + "', '" + user + "', '" + password + "','" + currentTimestamp + "')";

            String updateQuery = "update \"logins\" set \"password\" = '"
                    + password + "' where \"docbase_host\" = '"
                    + hostName + "' and \"docbase\" = '"
                    + docbase + "' and \"login\" = '"
                    + user + "'";

            //FAzer um select na tabela de logins utilizando a docbase e o Host            
            int result = DbUseful.updateQuery(updateQuery);

            if (result == 0) {
                DbUseful.updateQuery(insertQuery);
            }

            //DtoolLogControl.log("Linhas afetadas: " + result, Level.SEVERE);
        } catch (ClassNotFoundException ex) {

            DtoolLogControl.log(ex, Level.SEVERE);
        } catch (SQLException ex) {
            DtoolLogControl.log(ex, Level.SEVERE);
        } catch (DfException ex) {
            DtoolLogControl.log(ex, Level.SEVERE);

        }
    }

    public static String getDocbrockerDomain() {

        try {
            return DocumentumUseful.getHostName();
        } catch (DfException ex) {
            DtoolLogControl.log("Erro de conexão. Ajuste a conexão e clique no botão \"Relaoad\"", Level.WARNING);
            DtoolLogControl.log(ex, Level.SEVERE);
            return "<<FALHA>>";
        }

        
    }

}
