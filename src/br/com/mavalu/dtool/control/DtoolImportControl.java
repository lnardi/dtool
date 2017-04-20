/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.dtool.control;

import br.com.mavalu.dtool.DtoolJFrame;
import br.com.mavalu.useful.LoginTableModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author C0475434
 */
public class DtoolImportControl {

    public static void ImportToGrid(File fileImput, LoginTableModel loginTableModel, DtoolJFrame dtoolJFrame, String fieldDelimiter, String columnDelimiter) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileImput), "ISO-8859-1"));
        loginTableModel.importFile(csvReader,fieldDelimiter,columnDelimiter);        
        csvReader.close();
    }
    
}
