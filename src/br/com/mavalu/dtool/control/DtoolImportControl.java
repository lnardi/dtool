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
import javax.swing.JTable;
import javax.swing.table.TableColumn;

/**
 *
 * @author C0475434
 */
public class DtoolImportControl {

    public static void ImportToGrid(File fileImput, JTable jTable, DtoolJFrame dtoolJFrame, String fieldDelimiter, String columnDelimiter, boolean hasColumns, int pg, boolean isScript) throws FileNotFoundException, UnsupportedEncodingException, IOException {

        BufferedReader csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileImput), "ISO-8859-1"));
        LoginTableModel loginTableModel = new LoginTableModel(dtoolJFrame.dateFormat, pg);
        if (isScript) {
            loginTableModel.importScript(csvReader);
        } else {
            loginTableModel.importFile(csvReader, fieldDelimiter, columnDelimiter, hasColumns);
        }
        csvReader.close();
        jTable.setModel(loginTableModel);
        TableColumn column = null;
        int size[] = loginTableModel.getColumnSize();
        int columns = loginTableModel.getColumnCount();

        for (int i = 0; i < columns; i++) {
            column = jTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(size[i] * 7);
        }
        if (isScript) {
            dtoolJFrame.operationControl(dtoolJFrame.OP_IMPORT_SCRIPT, false, loginTableModel);
        } else {
            dtoolJFrame.operationControl(dtoolJFrame.OP_IMPORT_FILE, false, loginTableModel);
        }

        jTable.repaint();

    }

}
