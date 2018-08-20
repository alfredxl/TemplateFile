package com.alfredxl.templatefile.dialog;

import com.alfredxl.templatefile.factory.DynamicDataFactory;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SettingConfig implements Configurable {
    private SettingJPanel settingJPanel;


    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "TemplateFile";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (settingJPanel == null) {
            settingJPanel = new SettingJPanel(null);
        }
        return settingJPanel;
    }

    @Override
    public boolean isModified() {
        return settingJPanel.isModified();
    }

    @Override
    public void apply() {
        DynamicDataFactory.setDynamicData(settingJPanel.getDynamicList());
        DynamicDataFactory.setTemplateData(settingJPanel.getTemplateList());
        settingJPanel.setModified(false);
    }
}
