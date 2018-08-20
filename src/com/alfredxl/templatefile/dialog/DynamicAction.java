package com.alfredxl.templatefile.dialog;

import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.AnActionButtonRunnable;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

abstract class DynamicAction extends AbstractAction implements AnActionButtonRunnable {
    Vector<Vector<Object>> data;
    DynamicTableModel model;
    JBTable table;
    ActionListener listener;
    boolean isStatic;

    DynamicAction(Vector<Vector<Object>> data, DynamicTableModel model, JBTable table, ActionListener listener, boolean isStatic) {
        this.data = data;
        this.model = model;
        this.table = table;
        this.listener = listener;
        this.isStatic = isStatic;
    }

    @Override
    public void run(final AnActionButton anActionButton) {
        actionPerformed(null);
    }


    static final class AddLocationAction extends DynamicAction {

        AddLocationAction(Vector<Vector<Object>> data, DynamicTableModel model, JBTable table, ActionListener listener, boolean isStatic) {
            super(data, model, table, listener, isStatic);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            if (isStatic) {
                Messages.showInputDialog("key与value请使用\"=\"隔开,key请使用\"$\"开始和结束", "请输入key和value", null, null, new InputValidator() {
                    @Override
                    public boolean checkInput(String s) {
                        if (s == null) {
                            return false;
                        }
                        String[] values = s.split("=");
                        return values.length == 2 && values[0].trim().length() > 0
                                && values[1].trim().length() > 0
                                && values[0].trim().startsWith("$")
                                && values[0].trim().endsWith("$");
                    }

                    @Override
                    public boolean canClose(String s) {
                        String[] values = s.split("=");
                        Vector<Object> vector = new Vector<>();
                        vector.add(true);
                        vector.add(values[0].trim());
                        vector.add(values[1].trim());
                        listener.change();
                        data.add(vector);
                        model.fireTableDataChanged();
                        return true;
                    }
                });
            } else {
                Messages.showInputDialog("只输入key即可,key请使用\"$\"开始和结束", "请输入key", null, null, new InputValidator() {
                    @Override
                    public boolean checkInput(String s) {
                        return s != null && s.trim().length() > 0 && s.trim().startsWith("$") && s.trim().endsWith("$");
                    }

                    @Override
                    public boolean canClose(String s) {
                        Vector<Object> vector = new Vector<>();
                        vector.add(true);
                        vector.add(s.trim());
                        vector.add("");
                        listener.change();
                        data.add(vector);
                        model.fireTableDataChanged();
                        return true;
                    }
                });
            }
        }
    }


    static final class RemoveLocationAction extends DynamicAction {

        RemoveLocationAction(Vector<Vector<Object>> data, DynamicTableModel model, JBTable table, ActionListener listener, boolean isStatic) {
            super(data, model, table, listener, isStatic);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            final int selectedIndex = table.getSelectedRow();
            if (selectedIndex == -1) {
                return;
            }
            listener.change();
            data.remove(selectedIndex);
            model.fireTableDataChanged();
        }
    }
}
