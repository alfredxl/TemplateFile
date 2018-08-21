package com.alfredxl.templatefile.dialog;

import com.alfredxl.templatefile.bean.Template;
import com.alfredxl.templatefile.constant.Constants;
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

    static final class AddOrEditLocationAction extends TemplateAction {
        private boolean isEdit;

        AddOrEditLocationAction(List<Template> data, TemplateTableModel model, JBTable table, ActionListener listener,
                                boolean isDynamic, boolean dynamicHasValue, boolean isEdit) {
            super(data, isDynamic ? (dynamicHasValue ? Constants.REGEX_DYNAMIC_HAS_VALUE : Constants.REGEX_DYNAMIC) : Constants.REGEX_TEMPLATE,
                    isDynamic ? (dynamicHasValue ? Constants.TITLE_DYNAMIC_HAS_VALUE : Constants.TITLE_DYNAMIC) : Constants.TITLE_TEMPLATE,
                    isDynamic ? (dynamicHasValue ? Constants.MESSAGE_DYNAMIC_HAS_VALUE : Constants.MESSAGE_DYNAMIC) : Constants.MESSAGE_TEMPLATE,
                    model, table, listener);
            this.isEdit = isEdit;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            String initialValue = null;
            if (isEdit) {
                int selectedIndex = table.getSelectedRow();
                if (selectedIndex >= 0) {
                    Template template = data.get(selectedIndex);
                    initialValue = template.getKey();
                    if (template.getValue() != null && template.getValue().trim().length() > 0) {
                        initialValue = initialValue + "=" + template.getValue();
                    }
                }
            }
            Messages.showInputDialog(message, title, null, initialValue, new InputValidator() {
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
                    if (isEdit) {
                        int selectedIndex = table.getSelectedRow();
                        if (selectedIndex >= 0 && selectedIndex < data.size()) {
                            Template template = data.get(selectedIndex);
                            template.setKey(key);
                            template.setValue(value);
                        }
                    } else {
                        data.add(new Template(true, key, value));
                    }
                    listener.change();
                    model.fireTableDataChanged();
                    return true;
                }
            });
        }
    }

    static final class RemoveLocationAction extends TemplateAction {

        RemoveLocationAction(List<Template> data, TemplateTableModel model, JBTable table, ActionListener listener) {
            super(data, null, null, null, model, table, listener);
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            int selectedIndex = table.getSelectedRow();
            if (selectedIndex >= 0) {
                listener.change();
                data.remove(selectedIndex);
                model.fireTableDataChanged();
            }
        }
    }
}
