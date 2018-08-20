package com.alfredxl.templatefile.dialog;

import com.alfredxl.templatefile.bean.Template;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.AnActionButtonRunnable;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

abstract class TemplateAction extends AbstractAction implements AnActionButtonRunnable {
    List<Template> data;
    TemplateTableModel model;
    JBTable table;
    ActionListener listener;

    TemplateAction(List<Template> data, TemplateTableModel model, JBTable table, ActionListener listener) {
        this.data = data;
        this.model = model;
        this.table = table;
        this.listener = listener;
    }

    @Override
    public void run(final AnActionButton anActionButton) {
        actionPerformed(null);
    }


    static final class AddLocationAction extends TemplateAction {

        AddLocationAction(List<Template> data, TemplateTableModel model, JBTable table, ActionListener listener) {
            super(data, model, table, listener);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            Messages.showInputDialog("classType与classPath请使用\"=\"隔开", "请输入classType和classPath", null, null, new InputValidator() {
                @Override
                public boolean checkInput(String s) {
                    if (s == null) {
                        return false;
                    }
                    String[] values = s.split("=");
                    return values.length == 2 && values[0].trim().length() > 0
                            && values[1].trim().length() > 0;
                }

                @Override
                public boolean canClose(String s) {
                    String[] values = s.split("=");
                    Vector<Object> vector = new Vector<>();
                    vector.add(true);
                    vector.add(values[0].trim());
                    vector.add(values[1].trim());
                    listener.change();
                    data.add(new Template(vector, ""));
                    model.fireTableDataChanged();
                    return true;
                }
            });


        }
    }


    static final class RemoveLocationAction extends TemplateAction {

        RemoveLocationAction(List<Template> data, TemplateTableModel model, JBTable table, ActionListener listener) {
            super(data, model, table, listener);
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
