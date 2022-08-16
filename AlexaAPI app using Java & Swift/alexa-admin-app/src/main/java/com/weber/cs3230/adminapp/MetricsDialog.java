package com.weber.cs3230.adminapp;

import com.weber.cs3230.adminapp.api.ApiClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MetricsDialog extends JDialog {
    ApiClient apiClient = new ApiClient();
    //MetricDetailList metricList = new MetricDetailList();
    
    private java.util.List<MetricDetail> metrics = new ArrayList<>();
    
    private final String[] metricColumnNames = {"eventName", "count", "mostRecentDate"};
    private DefaultTableModel metricModel;
    protected JTable metricTable;
    
    
    public MetricsDialog(java.util.List<MetricDetail> metrics) {
        this.metrics = metrics;
        setPreferredSize(new Dimension(400, 350));
        pack();
        setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        setTitle("Metrics");
        add(createMetricsPanel(), BorderLayout.CENTER);
        setVisible(true);
    }
    
    private JComponent createMetricsPanel() {
        JPanel metricsPanel = new JPanel(new BorderLayout());
        metricsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        metricsPanel.add(this.createMetricsTablePanel(), "Center");
        metricsPanel.add(this.createButtonPanel(), "South");
        return metricsPanel;
    }
    
    private JComponent createMetricsTablePanel() {
        metricModel = new DefaultTableModel(getMetricTableData(), metricColumnNames);
        metricTable = new JTable(metricModel);
        metricTable.setDefaultEditor(Object.class, null);
        JScrollPane scrollPaneMetrics = new JScrollPane(metricTable);
        metricTable.setFillsViewportHeight(true);
        scrollPaneMetrics.setVisible(true);
        return scrollPaneMetrics;
    }
    
    private JComponent createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton buttonExit = new JButton("Exit");
        buttonExit.addActionListener(e -> {
            setVisible(false);
        });
        buttonPanel.add(buttonExit);
        return buttonPanel;
    }
    
    private Object[][] getMetricTableData() {
        java.util.List<Object[]> rows = new ArrayList<>();
//        for (MetricDetail metric : metricList.getMetrics()) {
//            rows.add(new Object[]{metric.getEventName(), metric.getCount(), metric.getMostRecentDate()});
//        }
        for (MetricDetail metric : metrics) {
            rows.add(new Object[]{metric.getEventName(), metric.getCount(), metric.getMostRecentDate()});
        }
        return rows.toArray(new Object[0][0]);
    }
    
}
