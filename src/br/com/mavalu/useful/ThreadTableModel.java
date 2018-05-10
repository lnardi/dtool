/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.useful;

import br.com.mavalu.dtool.export.DocumentumExportControl;
import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author q1D55V6G
 */
public class ThreadTableModel extends AbstractTableModel {

    private List<String> columns;
    private List<String[]> rows;
    private int columnSize[] = null;
    private int page = 1;
    private int pageSize = 1000;

    public int[] getColumnSize() {
        return columnSize;
    }

    public ThreadTableModel(List col, int pg) throws SQLException {

        columns = new <String> ArrayList();
        columns.add("Nome");
        columns.add("Status");
        columns.add("Item");
        columns.add("ID");
        columns.add("Exportados");
        columns.add("Files/m");
        columns.add("Files/h");
        columns.add("Size/m");
        columns.add("Size/h");

        rows = new <String[]> ArrayList();
        columnSize = new int[9];
        columnSize[0] = 15;
        columnSize[1] = 40;
        columnSize[2] = 10;
        columnSize[3] = 20;
        columnSize[4] = 10;
        columnSize[5] = 10;
        columnSize[6] = 10;
        columnSize[7] = 15;
        columnSize[8] = 15;

        DocumentumExportControl pb = null;

        String value = null;
        String row[] = null;

        Iterator it = col.iterator();

        while (it.hasNext()) {
            row = new String[9];
            pb = (DocumentumExportControl) it.next();

            row[0] = pb.getName();
            row[1] = pb.getsStatus();
            row[2] = String.valueOf(pb.getItem());
            row[3] = pb.getExportId();
            row[4] = String.valueOf(pb.getFileProcessedNumber());
            row[5] = "0";
            row[6] = "0";
            row[7] = "0";
            row[8] = "0";

            rows.add(row);

        }

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

        // return rows.size();
        //return pageSize;
    }

    public void setValueAt(String value, int l, int c) {
        if (rows.size() >= l) {

            String line[] = rows.get(l);
            if (line.length >= c) {
                line[c] = value;
            }
        }
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
        pageSize = ps;
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

    public List<String[]> getRows() {
        return rows;
    }

    public void addRow(String[] row) {
        rows.add(row);
    }
}
