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

abstract class TemplateAction extends AbstractAction implements AnActionButtonRunnable {
    List<Template> data;
    // 匹配输入的正则表达式
    String regex;
    // 提示文本
    String title;
    // 提示文本
    String message;
    TemplateTableModel model;
    JBTable table;
    ActionListener listener;

    TemplateAction(List<Template> data, String regex, String title, String message, TemplateTableModel model, JBTable table, ActionListener listener) {
        this.data = data;
        this.regex = regex;
        this.title = title;
        this.message = message;
        this.model = model;
        this.table = table;
        this.listener = listener;
    }

    @Override
    public void run(final AnActionButton anActionButton) {
        actionPerformed(null);
    }


    static final class AddLocationAction extends TemplateAction {

        AddLocationAction(List<Template> data, String regex, String title, String message, TemplateTableModel model, JBTable table, ActionListener listener) {
            super(data, regex, title, message, model, table, listener);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            Messages.showInputDialog(message, title, null, null, new InputValidator() {
                @Override
                public boolean checkInput(String s) {
                    if (s == null) {
                        return false;
                    }
                    return s.matches(regex);
                }

                @Override
                public boolean canClose(String s) {
                    String[] values = s.split("=");
                    String key = values[0].trim();
                    String value = "";
                    if (values.length == 2) {
                        value = values[1].trim();
                    }
                    listener.change();
                    data.add(new Template(true, key, value));
                    model.fireTableDataChanged();
                    return true;
                }
            });


        }
    }


    static final class RemoveLocationAction extends TemplateAction {

        RemoveLocationAction(List<Template> data, String regex, String title, String message, TemplateTableModel model, JBTable table, ActionListener listener) {
            super(data, regex, title, message, model, table, listener);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            int selectedIndex = table.getSelectedRow();
            if (selectedIndex <= 0) {
                return;
            }
            listener.change();
            data.remove(selectedIndex);
            model.fireTableDataChanged();
        }
    }
}
