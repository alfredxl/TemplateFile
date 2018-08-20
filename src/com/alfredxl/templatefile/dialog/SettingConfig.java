package com.alfredxl.templatefile.dialog;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SettingConfig implements Configurable {
    private SettingJPanel settingJPanel;


    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "MVPConfig";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (settingJPanel == null) {
            settingJPanel = new SettingJPanel();
        }
        return settingJPanel;
    }

    @Override
    public boolean isModified() {
        return settingJPanel.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        settingJPanel.apply();
    }
}
