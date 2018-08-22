package com.alfredxl.templatefile.dialog;

import com.alfredxl.templatefile.bean.Template;
import com.alfredxl.templatefile.factory.FormatFactory;
import com.alfredxl.templatefile.ui.ConfigJPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.panels.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WriteDialog extends JFrame implements ActionListener {
    private FormatFactory formatFactory;
    private ConfigJPanel jPanelContent;
    private Listener listener;

    public WriteDialog(FormatFactory formatFactory, Listener listener) {
        super("WriteTemplate");
        this.formatFactory = formatFactory;
        this.listener = listener;
        initView();
    }

    private void initView() {
        JPanel jPanelMain = new JPanel(new VerticalLayout(2));
        jPanelContent = new ConfigJPanel(true, formatFactory);
        JBScrollPane jScrollPane = new JBScrollPane(jPanelContent);
        jScrollPane.setPreferredSize(new Dimension(1550, 900));
        jPanelMain.add(jScrollPane);
        JPanel bottomJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 2, 2));
        JButton jButtonOK = new JButton("OK");
        bottomJPanel.add(jButtonOK);
        jButtonOK.addActionListener(this);
        jPanelMain.add(bottomJPanel);
        setContentPane(jPanelMain);
    }

    public void addData(List<Template> defaultDynamicList, List<Template> dynamicList, List<Template> templateList) {
        if (jPanelContent != null) {
            jPanelContent.addData(defaultDynamicList, dynamicList, templateList);
        }
    }

    public void outShow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        int w = (Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2;
        int h = (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2;
        setLocation(w, h - 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (listener != null) {
            listener.write(jPanelContent.getDynamicList(), jPanelContent.getDefaultDynamicList(), jPanelContent.getTemplateList());
        }
        dispose();
    }

    public static interface Listener {
        void write(java.util.List<Template> dynamicList, java.util.List<Template> defaultDynamicList, List<Template> templateList);
    }
}
