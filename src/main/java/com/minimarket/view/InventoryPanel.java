package com.minimarket.view;

import com.minimarket.controller.MainController;
import com.minimarket.model.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class InventoryPanel extends JPanel {
    private MainController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    private final Color PRIMARY_COLOR = new Color(39, 174, 96);
    private final Color BG_COLOR = new Color(247, 249, 249);

    public InventoryPanel(MainController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("📦 Gestión de Inventario");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(44, 62, 80));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        String[] columns = {"ID", "Producto", "Proveedor", "Descripción", "Precio", "Stock"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        styleTable(table);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnRefresh = new JButton("🔄 Actualizar Datos");
        styleButton(btnRefresh, PRIMARY_COLOR, Color.WHITE);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBottom.setBackground(BG_COLOR);
        pnlBottom.add(btnRefresh);
        add(pnlBottom, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> refreshData());
        refreshData();
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(213, 245, 227));
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    private void styleButton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void refreshData() {
        tableModel.setRowCount(0);
        List<Product> products = controller.getProducts();
        for (Product p : products) {
            Object[] row = {
                p.getId(), p.getName(), p.getSupplier(), p.getDescription(),
                "S/." + p.getPrice(), p.getStock()
            };
            tableModel.addRow(row);
        }
    }
}
