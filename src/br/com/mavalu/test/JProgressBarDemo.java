/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class JProgressBarDemo extends JFrame {

   protected int minValue = 0;
   protected int maxValue = 100;
   protected int counter = 0;
   protected JProgressBar progressBar;

   public JProgressBarDemo() {
      super("JProgressBar Demo");
      setSize(300, 100);
      UIManager.put("ProgressBar.background", Color.BLACK); //colour of the background
      UIManager.put("ProgressBar.foreground", Color.RED);  //colour of progress bar
      UIManager.put("ProgressBar.selectionBackground", Color.YELLOW);  //colour of percentage counter on black background
      UIManager.put("ProgressBar.selectionForeground", Color.BLUE);  //colour of precentage counter on red background
      progressBar = new JProgressBar();
      progressBar.setMinimum(minValue);
      progressBar.setMaximum(maxValue);
      progressBar.setStringPainted(true);
      JButton start = new JButton("Start");
      start.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Thread runner = new Thread() {
               public void run() {
                  counter = minValue;
                  while (counter <= maxValue) {
                     Runnable runme = new Runnable() {
                        public void run() {
                           progressBar.setValue(counter);
                        }
                     };
                     SwingUtilities.invokeLater(runme);
                     counter++;
                     try {
                        Thread.sleep(100);
                     } catch (Exception ex) {
                     }
                  }
               }
            };
            runner.start();
         }
      });
      getContentPane().add(progressBar, BorderLayout.CENTER);
      getContentPane().add(start, BorderLayout.WEST);
      WindowListener wndCloser = new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            System.exit(0);
         }
      };
      addWindowListener(wndCloser);
      setVisible(true);
   }

   public static void main(String[] args) {
      new JProgressBarDemo();
   }
}
