package com.alfredxl.templatefile;

import com.alfredxl.templatefile.bean.Template;
import com.alfredxl.templatefile.factory.DynamicDataFactory;
import com.alfredxl.templatefile.ui.SettingJPanel;
import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

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
            settingJPanel = new SettingJPanel();
            List<Template> defaultDynamicList = DynamicDataFactory.getDefaultDynamicData(null);
            List<Template> dynamicList = DynamicDataFactory.getDynamicData();
            List<Template> templateList = DynamicDataFactory.getTemplateData();
            settingJPanel.addData(defaultDynamicList, dynamicList, templateList);
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
