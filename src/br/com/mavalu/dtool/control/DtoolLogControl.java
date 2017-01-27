/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.dtool.control;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 *
 * @author q1D55V6G
 */
public class DtoolLogControl {

    private static JTextPane logComponent;
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private static Logger logger = Logger.getLogger(DtoolLogControl.class.getName());

    public static void setLogComponet(JTextPane jTextPane1) {
        logComponent = jTextPane1;

    }

    public static void log(String log, Level l) {
        Date date = new Date();
        try {
            SimpleAttributeSet green = new SimpleAttributeSet();
            StyleConstants.setFontFamily(green, "Courier");
            if (Level.INFO == l) {

                //  StyleConstants.setForeground(green, Color.GREEN);
                logComponent.getDocument().insertString(0, dateFormat.format(date) + " ==> " + log + "\n", green);
            } else if (Level.WARNING == l) {

                  StyleConstants.setForeground(green, Color.magenta);
                logComponent.getDocument().insertString(0, dateFormat.format(date) + " ==> " + log + "\n", green);

            } else {

                StyleConstants.setForeground(green, Color.RED);
                logComponent.getDocument().insertString(0, dateFormat.format(date) + " ==> " + log + "\n", green);
            }

            logComponent.setCaretPosition(0);
        } catch (BadLocationException ex) {
            Logger.getLogger(DtoolLogControl.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }

        Logger.getLogger(DtoolLoginControl.class.getName()).log(l, log);
    }

    public static void log(Exception ex, Level l) {
        Date date = new Date();
        try {

            SimpleAttributeSet green = new SimpleAttributeSet();
            StyleConstants.setFontFamily(green, "Courier");
            StyleConstants.setForeground(green, Color.RED);
            logComponent.getDocument().insertString(0, dateFormat.format(date) + " ==> " + ex.getMessage() + "\n", green);
            logComponent.setCaretPosition(0);
        } catch (BadLocationException ex1) {
            Logger.getLogger(DtoolLogControl.class.getName()).log(Level.SEVERE, null, ex1);
        }
        Logger.getLogger(DtoolLoginControl.class.getName()).log(l, null, ex);
        System.out.println(ex);
    }

}
