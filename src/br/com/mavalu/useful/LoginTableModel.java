/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.useful;

import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfAttr;
import com.documentum.fc.common.IDfTime;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.apache.derby.iapi.services.io.ArrayUtil;

/**
 *
 * @author q1D55V6G
 */
public class LoginTableModel extends AbstractTableModel {

    private static int dateFormat = 0;
    private List<String> columns;
    private List<String[]> rows;
    private int columnSize[] = null;
    private int page = 1;
    private int pageSize = 10;

    public List<String> getColumns() {
        return columns;
    }

    public List<String[]> getRows() {
        return rows;
    }

    public int[] getColumnSize() {
        return columnSize;
    }

    public LoginTableModel(int dtFormat) {
        dateFormat = dtFormat;
    }

    public LoginTableModel(int dtFormat, int pgSize) {
        dateFormat = dtFormat;
        pageSize = pgSize;
    }

    public LoginTableModel(IDfCollection col, int pg, int dtFormat) throws DfException {
        loadData(col, pg);
        dateFormat = dtFormat;
    }

    public LoginTableModel(IDfCollection col, int pg) throws DfException {
        loadData(col, pg);
    }

    private void loadData(IDfCollection col, int pg) throws DfException {

        columns = new <String> ArrayList();
        rows = new <String[]> ArrayList();
        String row[] = new String[0];
        int attributes = 0;
        int lenght = 0;
        String attributeName = null;
        pageSize = pg;

        String value = null;

        if (col.next()) {

            attributes = col.getAttrCount();
            columnSize = new int[attributes];

            for (int i = 0; i < attributes; i++) {
                attributeName = col.getAttr(i).getName();
                columns.add(attributeName);
                columnSize[i] = attributeName.length();
            }
            row = new String[columns.size()];

            for (int i = 0; i < attributes; i++) {

                if (col.getAttr(i).isRepeating()) {
                    row[i] = col.getAllRepeatingStrings(columns.get(i), "|");
                } else {
                    if (col.getAttr(i).getDataType() == IDfAttr.DM_DOUBLE) {
                        row[i] = String.valueOf(col.getValueAt(i).asDouble());
                    }
                    if (col.getAttr(i).getDataType() == IDfAttr.DM_TIME) {
                        if (dateFormat == 0) {
                            row[i] = col.getValueAt(i).asTime().asString(IDfTime.DF_TIME_PATTERN44);
                        } else {
                            row[i] = col.getValueAt(i).asTime().asString(IDfTime.DF_TIME_PATTERN18);
                        }
                    } else {
                        row[i] = col.getValueAt(i).asString().replaceAll("\\r|\\n", " ");
                    }
                }

                lenght = row[i].length();
                if (lenght > columnSize[i]) {
                    columnSize[i] = lenght;
                }

            }
            rows.add(row);

        }

        while (col.next()) {
            row = new String[columns.size()];

            for (int i = 0; i < attributes; i++) {

                if (col.getAttr(i).isRepeating()) {
                    value = col.getAllRepeatingStrings(columns.get(i), "|");
                } else {
                    if (col.getAttr(i).getDataType() == IDfAttr.DM_DOUBLE) {
                        value = String.valueOf(col.getValueAt(i).asDouble());

                    }
                    if (col.getAttr(i).getDataType() == IDfAttr.DM_TIME) {
                        if (dateFormat == 0) {
                            value = col.getValueAt(i).asTime().asString(IDfTime.DF_TIME_PATTERN44);
                        } else {
                            value = col.getValueAt(i).asTime().asString(IDfTime.DF_TIME_PATTERN18);
                        }
                    } else {
                        value = col.getValueAt(i).asString().replaceAll("\\r|\\n", " ");
                    }
                }

                row[i] = value;
                lenght = value.length();
                if (lenght > columnSize[i]) {
                    columnSize[i] = (columnSize[i] + lenght) / 2;
                }
            }
            rows.add(row);

        }

        if (pageSize == 0) {
            pageSize = rows.size();
        }

        col.close();
    }

    @Override
    public String getColumnName(int i) {
        return columns.get(i);
    }

    public int getColumnCount() {
        return columns.size();
    }

    public int getRowCount() {

        int initialRow = (page - 1) * pageSize;

        int diff = rows.size() - initialRow;

        return (diff > pageSize) ? pageSize : diff;

    }

    public Object getValueAt(int r, int c) {

        return (rows.get(computeRow(r)))[c];
    }

    /**
     * Metodo deve ser removido
     *
     * @param p
     */
    public void setPage(int p) {
        page = p;
    }

    public void setPageSize(int ps) {
        if (ps < 1) {
            pageSize = rows.size();
        } else {
            pageSize = ps;
        }
        page = 1;
    }

    public boolean nextPage() {

        boolean canIncrese = page < getMaxPageNumber();

        page = canIncrese ? ++page : page;

        return canIncrese;

    }

    public boolean previousPage() {

        boolean canIncrese = page > 1;
        page = canIncrese ? --page : page;
        return canIncrese;

    }

    public int getMaxPageNumber() {
        int pgs = rows.size() / pageSize;
        int rst = rows.size() % pageSize;
        return (rst) > 0 ? ++pgs : pgs;
    }

    /**
     * Retonar o número de lihas existente na query
     *
     * @return
     */
    public int getMaxRowsCount() {
        return rows.size();
    }

    private int computeRow(int r) {
        return ((page - 1) * pageSize) + r;
    }

    public int getPage() {
        return page;
    }

    /**
     * Define o formado ta data que será exibido na tabela.
     *
     * @param format 1 para dd/mm/yyyy e 2 para mm/dd/yyyy
     */
    public void setDateFormat(int format) {
        dateFormat = format;
    }

    public void executeScriptTemplate(LoginTableModel queryTableModel, String scriptTemplate) throws Exception {

        columns = new <String> ArrayList();
        rows = new <String[]> ArrayList();

        //Só tem uma coluna chamada script Cria o cabeçalho
        columnSize = new int[1];
        columns.add("Script");
        columnSize[0] = scriptTemplate.length();

        String key = "@Value(";
        List<String[]> queryRows = queryTableModel.getRows();
        List<String> queryRowColumns = queryTableModel.getColumns();
        List<String> queryColumns = getColumnList(scriptTemplate);
        int[] columIndex = getColumnIndex(queryRowColumns, queryColumns);
        String query = null;
        int lenght = -1;
        //para cada linha de registro
        for (String[] queryRow : queryRows) {
            query = scriptTemplate;
            for (int i = 0; i < columIndex.length; i++) {
                query = query.replace(key + queryColumns.get(i) + ")", (queryRow[columIndex[i]] == null ? "null" : queryRow[columIndex[i]]));
            }
            rows.add(new String[]{query});
            lenght = query.length();
            if (lenght > columnSize[0]) {
                columnSize[0] = lenght;
            }
        }

        if (pageSize == 0) {
            pageSize = rows.size();
        }

    }

    private List<String> getColumnList(String sT) {
        String key = "@Value(";
        List<String> lst = new <String> ArrayList();

        int iKey = sT.indexOf(key);

        while (iKey != -1) {
            int iEndKey = sT.indexOf(")", iKey);
            String column = sT.substring(iKey + 7, iEndKey);
            lst.add(column);
            //Verifica se há uma outra coluna.
            iKey = sT.indexOf(key, iEndKey);
        }

        return lst;
    }

    private int[] getColumnIndex(List<String> queryRowColumns, List<String> queryColumns) throws Exception {
        int[] indexs = new int[queryColumns.size()];
        boolean fail = false;
        for (int i = 0; i < queryColumns.size(); i++) {
            String col = queryColumns.get(i);
            indexs[i] = queryRowColumns.indexOf(col);

            if (indexs[i] < 0) {
                throw new Exception("Coluna " + col + " não foi encontrada no grid");
            }

        }

        return indexs;
    }

    /**
     * Import a csv file
     *
     * @param fileInput - File path
     * @param fieldDelimiter - CSV delimiter, normaly ;
     * @param columnDelimiter - colunm delimiter, normaly "
     * @param hasColumns - Has
     * @throws IOException
     */
    public void importFile(BufferedReader fileInput, String fieldDelimiter, String columnDelimiter, boolean hasColumns) throws IOException {

        columns = new <String> ArrayList();
        rows = new <String[]> ArrayList();
        String attributeName = null;
        String[] row = null;

        //If no column delimiter, set " to process broken lines
        if (fieldDelimiter == null) {
            fieldDelimiter = "\"";
        }
        //        
        if (hasColumns) {
            String[] clm = ReadLine(fileInput, fieldDelimiter, columnDelimiter, null);
            //Define o tamanho do grid
            columnSize = new int[clm.length];

            for (int i = 0; i < clm.length; i++) {

                if (fieldDelimiter != null) {
                    attributeName = clm[i].replace(fieldDelimiter, "");
                } else {
                    attributeName = clm[i];
                }
                columns.add(attributeName);
                columnSize[i] = attributeName.length();
            }
        } else {
            String[] ln = ReadLine(fileInput, fieldDelimiter, columnDelimiter, null);
            //Criar colunas genericas
            for (int i = 0; i < ln.length; i++) {
                columns.add("<COLUMN_" + i + ">");
            }
            //Define o tamanho do grid
            columnSize = new int[ln.length];
            row = processRow(ln, fieldDelimiter, columnDelimiter, fileInput);
            rows.add(row);
        }
        String[] ln;
        while ((ln = ReadLine(fileInput, fieldDelimiter, columnDelimiter, null)) != null) {

            row = processRow(ln, fieldDelimiter, columnDelimiter, fileInput);
            rows.add(row);
        }
        if (pageSize == 0) {
            pageSize = rows.size();
        }

    }

    /**
     * Assure that columns with broken lines will be processed
     *
     * @param fileInput
     * @param fieldDelimiter
     * @param columnDelimiter
     * @param clmTmp
     * @return
     * @throws IOException
     */
    private String[] ReadLine(BufferedReader fileInput, String fieldDelimiter, String columnDelimiter, String[] clmTmp) throws IOException {
        String line = null;
        line = fileInput.readLine();
        //If end of file return null or return last read value in case of recursive call;
        if (line == null) {
            if (clmTmp == null) {
                return null;
            } else {
                return clmTmp;
            }
        }
        String[] clm = line.split(columnDelimiter);

        System.out.println(clm.length + " == " + clm[0]);
        ///If yues, line is broken and we have a recursive call
        if (clmTmp != null) {
            String[] clmResult = new String[clmTmp.length + clm.length - 1];

            System.arraycopy(clmTmp, 0, clmResult, 0, clmTmp.length);
            if (clm.length > 1) {
                System.arraycopy(clm, 1, clmResult, clmTmp.length, clm.length - 1);
            }
            clmResult[clmTmp.length - 1] = clmResult[clmTmp.length - 1] + " " + clm[0];
            //
            clm = clmResult;
        }

        //If las column start with delimiter verify if finish also.
        if (clm[clm.length - 1].startsWith(fieldDelimiter)) {
            //If yes, so its is ok
            if (clm[clm.length - 1].endsWith(fieldDelimiter)) {
                return clm;
            } else {
                //If no, has a line broken
                return ReadLine(fileInput, fieldDelimiter, columnDelimiter, clm);
            }
        }
        return clm;
    }

    private String[] processRow(String[] ln, String fieldDelimiter, String columnDelimiter, BufferedReader fileInput) throws IOException {
        String value = null;
        //String[] row = new String[ln.length];
        String[] row = new String[columns.size()];
        int columnsList = 0;
        for (int i = 0; i < ln.length && columnsList < columnSize.length; i++) {

            String tmp = ln[i];
            if (tmp.startsWith(fieldDelimiter)) {
                while (i < ln.length && !ln[i].endsWith(fieldDelimiter)) {
                    tmp = tmp + " " + ln[++i];
                    //If the last possition load new line
                    if (i == ln.length - 1) {
                        ln[i] ="";
                        ln = ReadLine(fileInput, fieldDelimiter, columnDelimiter, ln);
                        i--;//Process again last line.
                    }
                }
                
                ln[i] = tmp;

            }
            if (fieldDelimiter != null) {
                value = ln[i].replace(fieldDelimiter, "");
            } else {
                value = ln[i];
            }
            row[columnsList] = value;
            if (value.length() > columnSize[columnsList]) {
                columnSize[columnsList] = value.length();
            }
            columnsList++;
        }

        return row;
    }

    public void importScript(BufferedReader fileInput) throws IOException {

        columns = new <String> ArrayList();
        rows = new <String[]> ArrayList();
        String attributeName = null;
        String delimiter = null;
        String[] row = null;

        String line = null;
        line = fileInput.readLine();
        //        
        columns.add("Script");
        columnSize = new int[1];
        columnSize[0] = line.length();
        row = new String[1];
        row[0] = line;
        rows.add(row);

        String value = null;
        while ((line = fileInput.readLine()) != null) {
            //
            row = new String[1];
            row[0] = line;
            rows.add(row);
        }
        if (pageSize == 0) {
            pageSize = rows.size();
        }

    }

    public void reset() {

        columns = new <String> ArrayList();
        rows = new <String[]> ArrayList();
        pageSize = 0;
    }
}
