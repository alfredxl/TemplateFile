package com.alfredxl.templatefile.dialog;

import com.alfredxl.templatefile.factory.FormatFactory;

import javax.swing.*;
import java.awt.*;

public class WriteDialog extends JFrame {
    private FormatFactory formatFactory;
    public WriteDialog(FormatFactory formatFactory){
        this.formatFactory = formatFactory;
        initView();
    }

    private void initView() {
        JPanel jPanel = new SettingJPanel(true, formatFactory);
        setContentPane(jPanel);
    }

    public void outShow(){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        int w = (Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2;
        int h = (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2;
        setLocation(w, h);
    }
}
