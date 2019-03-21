package com.alfredxl.templatefile.util;

import java.io.File;
import java.util.List;

public class FileUtils {
    /**
     * Description: 删除文件
     *
     * @param fileLists 文件集合
     * @ Author:       zhangqiang
     * @ Date:        2018/9/3 11:15
     */

    public static void deleteFileByFiles(List<File> fileLists) {
        for (File file : fileLists) {
            deleteFileAbsolutePath(file.getAbsolutePath());
        }
    }

    /**
     * Description: 删除目录
     *
     * @param absPath 文件绝对路径
     * @ Author:       zhangqiang
     * @ Date:        2018/9/3 11:15backup
     */
    public static void deleteFileAbsolutePath(String absPath) {
        File file = new File(absPath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (File file1 : files) {
                        deleteFileAbsolutePath(file1.getAbsolutePath());
                    }
                }
            }
            file.delete();
        }
    }
}
