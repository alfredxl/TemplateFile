package com.alfredxl.templatefile.dialog;

import com.alfredxl.templatefile.impl.SimpleDocumentListener;
import com.alfredxl.templatefile.util.FileUtils;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ShowLibDialog extends JFrame {
    private File files21;
    private List<File> filesMetadata;
    private List<String> listLib = new ArrayList<>();
    private String filePathName;
    private Project project;

    public ShowLibDialog(File files21, List<File> filesMetadata, Project project) {
        super("清除依赖缓存");
        this.files21 = files21;
        this.filesMetadata = filesMetadata;
        this.project = project;
        initView();
        if (files21.exists() && files21.isDirectory()) {
            File[] fileList = files21.listFiles();
            if (fileList != null) {
                for (File item : fileList) {
                    if (item.isDirectory()) {
                        File[] subListFile = item.listFiles();
                        String itemName = item.getName();
                        if (subListFile != null) {
                            for (File itemSub : subListFile) {
                                if (itemSub.isDirectory()) {
                                    listLib.add(itemName + File.separator + itemSub.getName());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void initView() {
        JPanel jPanelMain = new JPanel(new VerticalLayout(2));
        JBPanel jbPanelE = new JBPanel(new FlowLayout(FlowLayout.LEFT));
        jPanelMain.add(jbPanelE);
        JBTextField jbTextField = new JBTextField(40);
        jbPanelE.add(jbTextField);
        JBList<String> jList = new JBList<>();
        Vector<String> value = new Vector<>();
        jList.setListData(value);
        JBScrollPane jScrollPane = new JBScrollPane(jList);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setLayoutOrientation(JList.VERTICAL);
        jScrollPane.setPreferredSize(new Dimension(500, 300));
        jScrollPane.setBorder(JBUI.Borders.empty(0, 10));
        jPanelMain.add(jScrollPane);
        JBPanel jbPanelBT = new JBPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton jButtonOK = new JButton("确认清除");
        jbPanelBT.add(jButtonOK);
        jPanelMain.add(jbPanelBT);
        jbTextField.getDocument().addDocumentListener(new SimpleDocumentListener() {
            @Override
            protected void change() {
                filePathName = null;
                jButtonOK.setEnabled(false);
                changeInput(jbTextField.getText(), value);
                jList.updateUI();
            }
        });
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                filePathName = jList.getSelectedValue();
                if (filePathName == null || filePathName.length() == 0) {
                    jButtonOK.setEnabled(false);
                } else {
                    jButtonOK.setEnabled(true);
                }
            }
        });
        jButtonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filePathName != null && filePathName.length() > 0) {
                    List<File> deleteList = new ArrayList<>();
                    deleteList.add(new File(files21.getAbsoluteFile() + File.separator + filePathName));
                    for (File item : filesMetadata) {
                        deleteList.add(new File(item.getAbsoluteFile() + File.separator + filePathName));
                    }
                    FileUtils.deleteFileByFiles(deleteList);
                    Messages.showMessageDialog("清除成功", "提示", null);
                    ShowLibDialog.this.dispose();
                }
            }
        });
        jButtonOK.setEnabled(false);
        setContentPane(jPanelMain);
    }

    public void outShow() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        int h = (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2;
        int w = (Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2;
        setLocation(w, h - 20);
    }

    private void changeInput(String value, Vector<String> list) {
        list.clear();
        if (value != null && value.length() != 0) {
            for (String item : listLib) {
                if (item.contains(value)) {
                    list.add(item);
                }
            }
        }
    }
}
