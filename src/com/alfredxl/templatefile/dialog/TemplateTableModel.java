package com.alfredxl.templatefile.dialog;


import com.alfredxl.templatefile.bean.Template;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Vector;

public class TemplateTableModel extends AbstractTableModel {
    private List<Template> templateList;
    private Vector<String> columnNames;

    public TemplateTableModel(List<Template> templateList, Vector<String> columnNames) {
        this.templateList = templateList;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return templateList.size();
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
        return templateList.get(rowIndex).getValueArrays().get(columnIndex);
    }
}
