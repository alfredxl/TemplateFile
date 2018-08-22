package com.alfredxl.templatefile.util;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

public class PathUtil {
    public static String getModulePath(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project != null) {
            return project.getBasePath();
        }
        return null;
    }
}
