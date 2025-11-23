package com.minimarket.view;

import com.minimarket.controller.MainController;
import com.minimarket.model.SalesRecord;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoryPanel extends JPanel {
    private MainController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtDateFilter;

    private final Color ACCENT_COLOR = new Color(241, 196, 15);
    private final Color BG_COLOR = new Color(247, 249, 249);

    public HistoryPanel(MainController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("📅 Historial de Ventas");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(44, 62, 80));
        add(lblTitle, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BG_COLOR);

        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlFilter.setBackground(BG_COLOR);
        pnlFilter.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel lblFilter = new JLabel("Filtrar por Fecha (YYYY-MM-DD): ");
        lblFilter.setFont(new Font("Segoe UI", Font.BOLD, 14));

        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        txtDateFilter = new JTextField(today, 12);
        txtDateFilter.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JButton btnFilter = new JButton("🔍 Buscar");
        styleButton(btnFilter, ACCENT_COLOR, Color.BLACK);

        pnlFilter.add(lblFilter);
        pnlFilter.add(txtDateFilter);
        pnlFilter.add(btnFilter);

        centerPanel.add(pnlFilter, BorderLayout.NORTH);

        String[] columns = {"Fecha", "Tipo Doc", "Total", "Detalle"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        styleTable(table);

        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        btnFilter.addActionListener(e -> loadHistory());
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(250, 240, 200));
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(44, 62, 80));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void styleButton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void loadHistory() {
        tableModel.setRowCount(0);
        String date = txtDateFilter.getText();
        List<SalesRecord> records = controller.getHistoryByDate(date);

        for (SalesRecord r : records) {
            Object[] row = {
                r.getDate(), r.getDocType(), "S/." + String.format("%.2f", r.getTotal()), "Ver detalle"
            };
            tableModel.addRow(row);
        }
        if (records.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron ventas.");
        }
    }
}
