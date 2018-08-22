package com.alfredxl.templatefile.model;


import com.alfredxl.templatefile.bean.Template;
import com.alfredxl.templatefile.factory.FormatFactory;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Vector;

public class TemplateTableModel extends AbstractTableModel {
    private List<Template> templateList;
    private List<Template> dynamicList;
    private List<Template> defaultDynamicList;
    private Vector<String> columnNames;
    private boolean showFormatCode;
    private FormatFactory formatFactory;

    public TemplateTableModel(List<Template> templateList, List<Template> dynamicList, List<Template> defaultDynamicList,
                              Vector<String> columnNames, boolean showFormatCode, FormatFactory formatFactory) {
        this.templateList = templateList;
        this.dynamicList = dynamicList;
        this.defaultDynamicList = defaultDynamicList;
        this.columnNames = columnNames;
        this.showFormatCode = showFormatCode;
        this.formatFactory = formatFactory;
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
        if (columnIndex == 0) {
            return templateList.get(rowIndex).isEnabled();
        } else if (columnIndex == 1) {
            return templateList.get(rowIndex).getKey();
        } else if (columnIndex == 2) {
            return templateList.get(rowIndex).getValue();
        } else if (columnIndex == 3) {
            return formatData(templateList.get(rowIndex).getKey());
        } else if (columnIndex == 4) {
            return formatData(templateList.get(rowIndex).getValue());
        }
        return null;
    }

    private String formatData(String value) {
        if (showFormatCode && formatFactory != null) {
            return formatFactory.formatData(dynamicList, defaultDynamicList, value);
        } else {
            return value;
        }
    }
}
