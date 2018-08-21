package com.alfredxl.templatefile;

import com.alfredxl.templatefile.bean.Template;
import com.alfredxl.templatefile.dialog.WriteDialog;
import com.alfredxl.templatefile.factory.FormatFactory;
import com.alfredxl.templatefile.factory.WriteFile;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.List;

public class TemplateFilePlugin extends AnAction implements WriteDialog.Listener {
    private Project project;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        project = anActionEvent.getProject();
        if (project != null) {
            // 获取当前选中目录
            VirtualFile mVirtualFile = anActionEvent.getData(PlatformDataKeys.VIRTUAL_FILE);
            if (mVirtualFile != null && mVirtualFile.isDirectory()) {

                ProjectRootManager rootManager = ProjectRootManager.getInstance(project);
                ProjectFileIndex fileIndex = rootManager.getFileIndex();
                // 默认的代码包root路径
                VirtualFile sourceRootFile = fileIndex.getSourceRootForFile(mVirtualFile);
                if (sourceRootFile != null) {
                    FormatFactory formatFactory = new FormatFactory(sourceRootFile.getPath(), mVirtualFile.getPath());
                    new WriteDialog(formatFactory, this).outShow();
                }
            } else {
                Messages.showInfoMessage("请选中包路径", "提示");
            }
        }

    }

    @Override
    public void write(List<Template> dynamicList, List<Template> defaultDynamicList, List<Template> templateList) {
        if (project != null) {
            WriteFile.writeToFile(project, dynamicList, defaultDynamicList, templateList);
        }
    }
}
