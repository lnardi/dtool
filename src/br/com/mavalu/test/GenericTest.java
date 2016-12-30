/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.test;

import java.awt.Desktop;
import java.io.File;
import java.util.regex.Pattern;

/**
 *
 * @author q1D55V6G
 */
public class GenericTest {

   public static void main(String args[]) {
      /* Set the Nimbus look and feel */

      Pattern PATTERN = Pattern.compile("[^A-Za-z0-9çÇ   ã\t\nÃõÕ_\\-\\.]");

      //String a = "::CONCEI?AO DA PAIXAO MACIEL DE SA DOS|| SANTOç:ÇS* - 3484146.pdf -|";
      String a = "TAÃ??S CA   R\t\nLA MA\\GA//LH>Ã<ass?Æ?E\"S";
      System.out.println(a);
      // String a = "TAÃiS CARLA MAnGALHÃES";

      //System.out.println("String: " + a.replaceAll("[^A-Za-z0-9Ç\\ÃÕÔ//ÁÉÊÛ_\\s_\\-\\.]", "#"));
      //System.out.println("String: " + a.replaceAll("[\\?]", "#"));
      System.out.println("String: " + a.replaceAll("[\\t\\n\\\\\\/\\:\\*\\?\\\"\\<\\>\\|]", "@"));
      

      /**
       * try {
       *
       * //Desktop desktop = Desktop.getDesktop();
       *
       * File file = new File(".\\documentum\\config\\dfc.properties");
       *
       * Desktop.getDesktop().open(file);
       *
       *
       * }
       * catch (Exception ex) { ex.printStackTrace();
        }*
       */
   }

}
