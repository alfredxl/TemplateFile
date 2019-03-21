package com.alfredxl.templatefile;

import com.alfredxl.templatefile.dialog.ShowLibDialog;
import com.alfredxl.templatefile.factory.DynamicDataFactory;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UpdateGradleLibPlugin extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        String filePath = DynamicDataFactory.getGradleCachePath();
        if (filePath.trim().length() == 0) {
            Messages.showInfoMessage("请在设置界面配置Gradle缓存目录", "Tips");
            return;
        }
        File file = new File(filePath);
        if (!checkFile(file, true)) {
            return;
        }
        File cache = new File(filePath + File.separator + "caches");
        if (!checkFile(cache, true)) {
            return;
        }
        File modules2 = new File(cache.getAbsolutePath() + File.separator + "modules-2");
        if (checkFile(modules2, false)) {
            File files21 = new File(modules2.getAbsolutePath() + File.separator + "files-2.1");
            List<File> filesMetadata = new ArrayList<>();
            File[] allFile = modules2.listFiles();
            if (allFile != null) {
                for (File item : allFile) {
                    if (item.getName().startsWith("metadata-")) {
                        File descriptors = new File(item.getAbsolutePath() + File.separator + "descriptors");
                        if (checkFile(descriptors, false)) {
                            filesMetadata.add(descriptors);
                        }
                    }
                }
            }
            if (checkFile(files21, false) || filesMetadata.size() > 0) {
                ShowLibDialog dialog = new ShowLibDialog(files21, filesMetadata, anActionEvent.getProject());
                dialog.outShow();
            }
        }
    }

    private boolean checkFile(File file, boolean showDialog) {
        if (!file.exists() || !file.isDirectory()) {
            if (showDialog) {
                Messages.showInfoMessage("请配置正确的Gradle缓存目录", "Tips");
            }
            return false;
        }
        return true;
    }
}
