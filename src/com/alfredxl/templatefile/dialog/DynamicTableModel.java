package com.alfredxl.templatefile.dialog;


import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class DynamicTableModel extends AbstractTableModel {
    private Vector<Vector<Object>> list;
    private Vector<String> columnNames;

    public DynamicTableModel(Vector<Vector<Object>> list, Vector<String> columnNames) {
        this.list = list;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    // 使表格具有可编辑性
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return getColumnClass(columnIndex) == Boolean.class;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return list.get(rowIndex).get(columnIndex);
    }
}
