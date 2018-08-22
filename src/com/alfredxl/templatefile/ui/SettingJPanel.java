package com.alfredxl.templatefile.ui;

import com.alfredxl.templatefile.bean.Template;
import com.alfredxl.templatefile.impl.SimpleMouseListener;
import com.alfredxl.templatefile.util.HandleLocalData;
import com.intellij.openapi.fileChooser.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileWrapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jdesktop.swingx.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SettingJPanel extends JPanel {
    private ConfigJPanel configJPanel;

    public SettingJPanel() {
        super(new VerticalLayout(2));
        initView();
    }

    private void initView() {
        JPanel topJPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 2));
        JLabel jLabelInput = new URLLabel("Import configuration");
        JLabel jLabelOutput = new URLLabel("Export Configuration");
        topJPanel.add(jLabelInput);
        topJPanel.add(jLabelOutput);
        add(topJPanel);
        configJPanel = new ConfigJPanel();
        add(configJPanel);
        jLabelInput.addMouseListener(new SimpleMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFileNoJarsDescriptor()
                        .withTitle("Import")
                        .withDescription("Import configuration")
                        .withShowFileSystemRoots(true)
                        .withHideIgnored(true)
                        .withShowHiddenFiles(false);
                VirtualFile chosen = FileChooser.chooseFile(descriptor, null, null);
                if (chosen != null) {
                    try {
                        String jsonData = new String(chosen.contentsToByteArray(), StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(jsonData);
                        configJPanel.addData(null, HandleLocalData.getDynamicList(jsonObject),
                                HandleLocalData.getTemplateList(jsonObject));
                    } catch (IOException | JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        jLabelOutput.addMouseListener(new SimpleMouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String name = "templateConfiguration.txt";
                FileSaverDescriptor desc = new FileSaverDescriptor("Export", "Export configuration");
                VirtualFileWrapper file = FileChooserFactory.getInstance().createSaveFileDialog(desc, (Project) null)
                        .save(null, name);
                if (file != null) {
                    VirtualFile vf = file.getVirtualFile(true);
                    if (vf != null) {
                        try {
                            String data = HandleLocalData.handleDataToJson(getDynamicList(), getTemplateList());
                            if (data != null) {
                                vf.setBinaryContent(data.getBytes(StandardCharsets.UTF_8));
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public void addData(List<Template> defaultDynamicList, List<Template> dynamicList, List<Template> templateList) {
        if (configJPanel != null) {
            configJPanel.addData(defaultDynamicList, dynamicList, templateList);
        }
    }

    public boolean isModified() {
        return configJPanel != null && configJPanel.isModified();
    }

    public void setModified(boolean isModified) {
        if (configJPanel != null) {
            configJPanel.setModified(isModified);
        }
    }

    public List<Template> getDynamicList() {
        if (configJPanel != null) {
            return configJPanel.getDynamicList();
        }
        return null;
    }

    public List<Template> getTemplateList() {
        if (configJPanel != null) {
            return configJPanel.getTemplateList();
        }
        return null;
    }
}
