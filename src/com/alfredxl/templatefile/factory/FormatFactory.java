package com.alfredxl.templatefile.factory;

import com.alfredxl.templatefile.bean.Template;

import java.util.List;

public class FormatFactory {
    private String packageName;
    private String currentPath;

    public FormatFactory(String sourceRootFilePath, String currentPath) {
        if (sourceRootFilePath.equals(currentPath)) {
            this.packageName = "";
        } else {
            this.packageName = currentPath.substring(sourceRootFilePath.length() + 1).replace("/", ".");
        }
        this.currentPath = currentPath;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public String formatData(List<Template> dynamicList, List<Template> defaultDynamicList, String data) {
        for (Template template : defaultDynamicList) {
            if (template.isEnabled()) {
                data = data.replace(template.getKey(), template.getValue());
            }
        }
        for (Template template : dynamicList) {
            if (template.isEnabled()) {
                data = data.replace(template.getKey(), template.getValue());
            }
        }
        return data;
    }
}
