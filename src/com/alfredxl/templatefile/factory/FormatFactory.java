package com.alfredxl.templatefile.factory;

import com.alfredxl.templatefile.bean.Template;

import java.util.List;

public class FormatFactory {
    private String packageName;
    private String currentPath;
    // 当前路径
    private static final String CDS = "$CDS$";
    // 当前包名
    private static final String CDP = "$CDP$";

    public FormatFactory(String sourceRootFilePath, String currentPath) {
        if (sourceRootFilePath.equals(currentPath)) {
            this.packageName = "";
        } else {
            this.packageName = currentPath.substring(sourceRootFilePath.length() + 1).replace("/", ".").replace("\\", ".");
        }
        this.currentPath = currentPath;
    }

    public String formatData(List<Template> dynamicList, String data) {
        data = data.replace(CDS, currentPath);
        data = data.replace(CDP, packageName);
        for (Template template : dynamicList) {
            if (template.isEnabled()) {
                data = data.replace(template.getKey(), template.getValue());
            }
        }
        return data;
    }
}
