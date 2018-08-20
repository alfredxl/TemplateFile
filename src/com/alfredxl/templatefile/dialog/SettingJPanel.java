package com.alfredxl.templatefile.dialog;

import com.alfredxl.templatefile.bean.Template;
import com.alfredxl.templatefile.constant.Constants;
import com.alfredxl.templatefile.factory.DynamicDataFactory;
import com.alfredxl.templatefile.factory.SimpleDocumentListener;
import com.intellij.ui.AnActionButtonRunnable;
import com.intellij.ui.TitledSeparator;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.panels.VerticalLayout;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class SettingJPanel extends JPanel implements ActionListener {

    private List<Template> dynamicList = DynamicDataFactory.getDynamicData();
    private TemplateTableModel dynamicModel = new TemplateTableModel(dynamicList, DynamicDataFactory.getTitle());
    private JBTable dynamicTable = new JBTable(dynamicModel);

    private List<Template> templateList = DynamicDataFactory.getTemplateData();
    private TemplateTableModel classModel = new TemplateTableModel(templateList, DynamicDataFactory.getClassTitle());
    private JBTable classTable = new JBTable(classModel);

    private JTextArea jTextAreaCode;

    private boolean isModified;



    public SettingJPanel() {
        buildRuleFilePanel();
    }

    private void buildRuleFilePanel() {
        setLayout(new VerticalLayout(10));
        setBorder(JBUI.Borders.empty(10));
        add(new JLabel(Constants.SETTING_PANEL_TIPS));

        // 动态参数配置
        add(setJBTable(dynamicTable, new TemplateAction.AddLocationAction(dynamicList, Constants.REGEX_DYNAMIC,
                        Constants.TITLE_DYNAMIC, Constants.MESSAGE_DYNAMIC, dynamicModel, dynamicTable, this),
                new TemplateAction.RemoveLocationAction(dynamicList, Constants.REGEX_DYNAMIC,
                        Constants.TITLE_DYNAMIC, Constants.MESSAGE_DYNAMIC, dynamicModel, dynamicTable, this),
                Constants.DYNAMIC_TITLE));

        // 类模板配置
        JPanel classContainer = setJBTable(classTable, new TemplateAction.AddLocationAction(templateList, Constants.REGEX_TEMPLATE,
                        Constants.TITLE_TEMPLATE, Constants.MESSAGE_TEMPLATE, classModel, classTable, this),
                new TemplateAction.RemoveLocationAction(templateList, Constants.REGEX_TEMPLATE,
                        Constants.TITLE_TEMPLATE, Constants.MESSAGE_TEMPLATE, classModel, classTable, this),
                Constants.TEMPLATE_TITLE);
        JLabel infoLabel = new JLabel(Constants.CODE_TIPS, SwingConstants.LEFT);
        infoLabel.setBorder(JBUI.Borders.empty(8, 0, 4, 0));
        classContainer.add(infoLabel, BorderLayout.SOUTH);
        add(classContainer);

        // 模板代码区域
        jTextAreaCode = new JTextArea();
        JBScrollPane jScrollPane = new JBScrollPane(jTextAreaCode);
        jScrollPane.setPreferredSize(new Dimension(800, 600));
        add(jScrollPane);

        //创建监听
        createListener();
    }

    private void createListener() {
        addTableListener(dynamicTable, dynamicModel, dynamicList);
        addTableListener(classTable, classModel, templateList);
        classTable.getSelectionModel().addListSelectionListener(e -> {
            final int selectedIndex = classTable.getSelectedRow();
            if (selectedIndex == -1) {
                jTextAreaCode.setText("");
                return;
            }
            jTextAreaCode.setText(templateList.get(selectedIndex).getData());
        });
        jTextAreaCode.getDocument().addDocumentListener(new SimpleDocumentListener() {

            protected void change() {
                final int selectedIndex = classTable.getSelectedRow();
                if (selectedIndex == -1) {
                    return;
                }
                templateList.get(selectedIndex).setData(jTextAreaCode.getText());
            }
        });
    }


    private void addTableListener(JBTable jbTable, AbstractTableModel model, List<Template> listData) {
        jbTable.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int columnIndex = jbTable.columnAtPoint(e.getPoint()); //获取点击的列
                    int rowIndex = jbTable.rowAtPoint(e.getPoint()); //获取点击的行
                    if (columnIndex == 0) {
                        Template template = listData.get(rowIndex);
                        template.setEnabled(!template.isEnabled());
                        model.fireTableDataChanged();
                        isModified = true;
                    }
                }
            }
        });
    }


    private JPanel setJBTable(JBTable jbTable, AnActionButtonRunnable addAction, AnActionButtonRunnable removeAction, String title) {
        jbTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jbTable.setStriped(true);
        jbTable.getTableHeader().setReorderingAllowed(false);
        setTableColumnWidth(jbTable);
        ToolbarDecorator toolbarDecorator = ToolbarDecorator.createDecorator(jbTable);
        toolbarDecorator.setAddAction(addAction);
        toolbarDecorator.setRemoveAction(removeAction);
        toolbarDecorator.setPreferredSize(new Dimension(500, 120));
        JPanel container = new JPanel(new BorderLayout());
        container.add(new TitledSeparator(title), BorderLayout.NORTH);
        container.add(toolbarDecorator.createPanel(), BorderLayout.CENTER);
        return container;
    }

    private void setTableColumnWidth(JBTable table) {
        TableColumn column = table.getColumnModel().getColumn(0);
        column.setMinWidth(60);
        column.setWidth(80);
        column.setPreferredWidth(80);
        column.setMaxWidth(80);
    }

    public boolean isModified() {
        return isModified;
    }

    @Override
    public void change() {
        setModified(true);
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public List<Template> getDynamicList() {
        return dynamicList;
    }

    public List<Template> getTemplateList() {
        return templateList;
    }
}
