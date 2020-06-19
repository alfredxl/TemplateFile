package com.alfredxl.templatefile;

import com.alfredxl.templatefile.factory.DynamicDataFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.panels.VerticalLayout;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class GradleDirectoryConfig implements Configurable {
    private JBTextField jbTextField;
    private boolean isModified;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "gradle缓存目录";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        JBPanel<?> component = new JBPanel<>(new VerticalLayout(10));
        JBPanel<?> jbPanel = new JBPanel<>(new FlowLayout(FlowLayout.LEFT));
        jbPanel.add(new JBLabel("gradle缓存路径:  "));
        jbTextField = new JBTextField(DynamicDataFactory.getGradleCachePath(), 40);
        jbPanel.add(jbTextField);
        jbTextField.getDocument().addDocumentListener(new SimpleDocumentListener() {

            @Override
            protected void change() {
                isModified = true;
            }
        });
        component.add(jbPanel);
        return component;
    }

    @Override
    public boolean isModified() {
        return isModified;
    }

    @Override
    public void apply() throws ConfigurationException {
        String value = jbTextField.getText();
        if (value.length() > 0) {
            DynamicDataFactory.setGradleCachePath(value);
            isModified = false;
        }
    }

    static abstract class SimpleDocumentListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            change();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            change();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            change();
        }

        protected abstract void change();
    }
}
