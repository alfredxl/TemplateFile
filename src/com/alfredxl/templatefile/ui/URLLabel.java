package com.alfredxl.templatefile.ui;

import javax.swing.*;
import java.awt.*;

public class URLLabel extends JLabel {
    public URLLabel(String str) {
        this.setText("<html><u>" + str + "</u></html>");
        this.setForeground(new Color(88, 157, 246));//设置链接颜色
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//设置鼠标样式
    }
}

