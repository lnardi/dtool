/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.test;

import br.com.mavalu.useful.DocumentumUseful;
import java.util.List;

/**
 *
 * @author q1D55V6G
 */
public class DocumentumTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */

        DocumentumUseful teste = new DocumentumUseful();

        try {

            teste.login("SMB01", "dctmadmin", "Dctm@dmin65");

            List list = teste.getDobaseList();

            for (int i = 0; i < list.size(); i++) {
                //print Column Names
                System.out.print(list.get(i) + "\t\t\t\t");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
