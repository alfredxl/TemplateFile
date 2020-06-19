package com.alfredxl.templatefile.util;

import com.alfredxl.templatefile.bean.Template;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WriteFile {
    public static void writeToFile(Project project, List<Template> dynamicList, List<Template> defaultDynamicList, List<Template> templateList) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            try {
                List<Template> formatList = new ArrayList<>();
                formatList.addAll(dynamicList);
                formatList.addAll(defaultDynamicList);
                for (Template writeItem : templateList) {
                    if (!writeItem.isEnabled()) {
                        continue;
                    }
                    for (Template formatItem : formatList) {
                        if (!formatItem.isEnabled()) {
                            continue;
                        }
                        writeItem.setKey(writeItem.getKey().replace(formatItem.getKey(), formatItem.getValue()));
                        writeItem.setValue(writeItem.getValue().replace(formatItem.getKey(), formatItem.getValue()));
                        writeItem.setData(writeItem.getData().replace(formatItem.getKey(), formatItem.getValue()));
                    }
                    VirtualFile folder = createFolderIfNotExist(project, writeItem.getValue());
                    if (folder != null && folder.findChild(writeItem.getKey()) == null) {
                        VirtualFile createdFile = folder.findOrCreateChildData(project, writeItem.getKey());
                        createdFile.setBinaryContent(writeItem.getData().getBytes(StandardCharsets.UTF_8));
                        FileEditorManager.getInstance(project).openFile(createdFile, true);
                    }
                }
            } catch (final Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }


    private static VirtualFile createFolderIfNotExist(Project project, String folder) throws IOException {
        VirtualFile directory = project.getProjectFile();
        if (directory != null && folder.startsWith(directory.getPath())) {
            folder = folder.substring(directory.getPath().length() + 1);
            String[] folders = folder.split("/");
            for (String childFolder : folders) {
                VirtualFile childDirectory = directory.findChild(childFolder);
                if (childDirectory != null && childDirectory.isDirectory()) {
                    directory = childDirectory;
                } else {
                    directory = directory.createChildDirectory(project, childFolder);
                }
            }
            return directory;
        }
        return null;
    }
}
