package com.alfredxl.templatefile;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

public class TemplateFilePlugin extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        if (project != null) {
            // 获取当前选中目录
            VirtualFile mVirtualFile = anActionEvent.getData(PlatformDataKeys.VIRTUAL_FILE);
            if (mVirtualFile != null && mVirtualFile.isDirectory()) {
                // TODO
            } else {
                Messages.showInfoMessage("请选中包路径", "提示");
            }
        }

    }
}
