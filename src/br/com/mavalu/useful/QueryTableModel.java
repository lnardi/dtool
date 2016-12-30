/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.mavalu.useful;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author q1D55V6G
 */
public class QueryTableModel extends AbstractTableModel {

    private List<String> columns;
    private List<String[]> rows;
    private int columnSize[] = null;
    private int page = 1;
    private int pageSize = 1000;

    public int[] getColumnSize() {
        return columnSize;
    }

    public QueryTableModel(ResultSet col, int pg) throws SQLException {

        columns = new <String> ArrayList();
        columns.add("ID");
        columns.add("Query");
        columns.add("Data");

        rows = new <String[]> ArrayList();
        columnSize = new int[3];
        columnSize[0] = 10;
        columnSize[1] = 450;
        columnSize[2] = 30;

        String row[] = null;

        pageSize = pg;

        String value = null;

        while (col.next()) {
            row = new String[3];

            row[0] = col.getString(1);
            row[1] = col.getString(2);
            row[2] = col.getString(3);

            rows.add(row);

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

        // return rows.size();
        //return pageSize;
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
}
