package com.minimarket.view;

import com.minimarket.controller.MainController;
import com.minimarket.controller.CartItem;
import com.minimarket.model.Product;
import com.minimarket.patterns.composite.ProductBundle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private MainController controller;
    private JTabbedPane tabbedPane;

    // UI Components
    private JComboBox<Product> cbProducts;
    private JTable cartTable;
    private DefaultTableModel cartModel;
    private JLabel lblTotalAmt;
    private JComboBox<String> cbDocType;
    private JComboBox<String> cbDiscount;
    private JCheckBox chkDelivery;

    // Colores de Marca
    private final Color PRIMARY_GREEN = new Color(39, 174, 96);
    private final Color ACCENT_YELLOW = new Color(241, 196, 15);
    private final Color DARK_TEXT     = new Color(44, 62, 80);
    private final Color WHITE_BG      = new Color(255, 255, 255);
    private final Color DANGER_RED    = new Color(231, 76, 60);

    private InventoryPanel inventoryPanel;
    private HistoryPanel historyPanel;

    public MainFrame() {
        controller = new MainController();
        inventoryPanel = new InventoryPanel(controller);
        historyPanel = new HistoryPanel(controller);

        setTitle("🥬 La Esquina | Sistema de Ventas");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        UIManager.put("TabbedPane.selected", PRIMARY_GREEN);
        UIManager.put("TabbedPane.contentAreaColor", WHITE_BG);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(new Color(236, 240, 241));

        JPanel pnlSale = createSalePanel();

        tabbedPane.addTab("🛒 PUNTO DE VENTA", null, pnlSale, "Realizar Ventas");
        tabbedPane.addTab("📦 INVENTARIO", null, inventoryPanel, "Ver Stock");
        tabbedPane.addTab("📅 HISTORIAL", null, historyPanel, "Reportes");

        tabbedPane.addChangeListener(e -> inventoryPanel.refreshData());

        add(tabbedPane);
    }

    private JPanel createSalePanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(WHITE_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlHeader.setBackground(WHITE_BG);
        JLabel lblBrand = new JLabel("Minimarket La Esquina");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblBrand.setForeground(PRIMARY_GREEN);
        pnlHeader.add(lblBrand);
        panel.add(pnlHeader, BorderLayout.NORTH);

        JPanel pnlSelection = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlSelection.setBackground(new Color(236, 240, 241));
        pnlSelection.setBorder(BorderFactory.createTitledBorder("Seleccionar Producto"));

        cbProducts = new JComboBox<>();
        cbProducts.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbProducts.setPreferredSize(new Dimension(300, 35));
        loadProductsCombo();

        JButton btnAdd = new JButton("AGREGAR");
        styleButton(btnAdd, PRIMARY_GREEN, Color.WHITE);

        JButton btnCombo = new JButton("PACK OFERTA ⭐");
        styleButton(btnCombo, ACCENT_YELLOW, DARK_TEXT);

        pnlSelection.add(cbProducts);
        pnlSelection.add(btnAdd);
        pnlSelection.add(btnCombo);

        String[] colNames = {"Producto", "Precio Unit.", "Cant.", "Subtotal"};
        cartModel = new DefaultTableModel(colNames, 0) {
             public boolean isCellEditable(int row, int column) { return false; }
        };
        cartTable = new JTable(cartModel);
        styleTable(cartTable);

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_GREEN, 1));

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 10));
        pnlCenter.setBackground(WHITE_BG);
        pnlCenter.add(pnlSelection, BorderLayout.NORTH);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);

        panel.add(pnlCenter, BorderLayout.CENTER);

        JPanel pnlRight = new JPanel();
        pnlRight.setLayout(new BoxLayout(pnlRight, BoxLayout.Y_AXIS));
        pnlRight.setPreferredSize(new Dimension(280, 0));
        pnlRight.setBackground(new Color(248, 250, 250));
        pnlRight.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblOptions = new JLabel("Opciones de Venta");
        lblOptions.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblOptions.setForeground(DARK_TEXT);
        lblOptions.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] docs = {"BOLETA", "FACTURA"};
        cbDocType = new JComboBox<>(docs);
        styleComboBox(cbDocType);

        String[] discounts = {"NINGUNO", "VIP (10%)", "SEASONAL (20%)"};
        cbDiscount = new JComboBox<>(discounts);
        styleComboBox(cbDiscount);

        chkDelivery = new JCheckBox("Servicio Delivery (+S/10)");
        chkDelivery.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chkDelivery.setBackground(new Color(248, 250, 250));
        chkDelivery.setForeground(DARK_TEXT);

        JLabel lblTotalTitle = new JLabel("TOTAL A PAGAR");
        lblTotalTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTotalTitle.setForeground(Color.GRAY);
        lblTotalTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblTotalAmt = new JLabel("S/. 0.00");
        lblTotalAmt.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTotalAmt.setForeground(PRIMARY_GREEN);
        lblTotalAmt.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton btnProcess = new JButton("COBRAR 💵");
        styleButton(btnProcess, PRIMARY_GREEN, Color.WHITE);
        btnProcess.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnProcess.setMaximumSize(new Dimension(300, 50));

        JButton btnClear = new JButton("CANCELAR ❌");
        styleButton(btnClear, DANGER_RED, Color.WHITE);
        btnClear.setMaximumSize(new Dimension(300, 40));

        pnlRight.add(lblOptions);
        pnlRight.add(Box.createVerticalStrut(20));
        pnlRight.add(new JLabel("Documento:"));
        pnlRight.add(cbDocType);
        pnlRight.add(Box.createVerticalStrut(15));
        pnlRight.add(new JLabel("Descuento:"));
        pnlRight.add(cbDiscount);
        pnlRight.add(Box.createVerticalStrut(15));
        pnlRight.add(chkDelivery);
        pnlRight.add(Box.createVerticalGlue());
        pnlRight.add(new JSeparator());
        pnlRight.add(Box.createVerticalStrut(10));
        pnlRight.add(lblTotalTitle);
        pnlRight.add(lblTotalAmt);
        pnlRight.add(Box.createVerticalStrut(20));
        pnlRight.add(btnProcess);
        pnlRight.add(Box.createVerticalStrut(10));
        pnlRight.add(btnClear);

        panel.add(pnlRight, BorderLayout.EAST);

        btnAdd.addActionListener(e -> {
            try {
                Product p = (Product) cbProducts.getSelectedItem();
                controller.addToCart(p);
                refreshCartTable();
            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        btnCombo.addActionListener(e -> {
            try {
                controller.addToCart(controller.createSampleBundle());
                refreshCartTable();
            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
            }
        });

        cbDiscount.addActionListener(e -> refreshTotalLabel());
        chkDelivery.addActionListener(e -> refreshTotalLabel());

        btnProcess.addActionListener(e -> {
            try {
                String descRaw = (String) cbDiscount.getSelectedItem();
                String descKey = "NINGUNO";
                if(descRaw.contains("VIP")) descKey = "VIP";
                if(descRaw.contains("SEASONAL")) descKey = "SEASONAL";

                String ticket = controller.processSale(
                    (String) cbDocType.getSelectedItem(),
                    descKey,
                    chkDelivery.isSelected()
                );

                showReceiptModal(ticket);
                clearSaleScreen();

            } catch (Exception ex) {
                showError(ex.getMessage());
            }
        });

        btnClear.addActionListener(e -> clearSaleScreen());

        return panel;
    }

    private void styleButton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleComboBox(JComboBox box) {
        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        box.setBackground(Color.WHITE);
        box.setMaximumSize(new Dimension(300, 35));
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(213, 245, 227));
        table.setSelectionForeground(DARK_TEXT);

        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY_GREEN);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(0, 40));
    }

    private void showReceiptModal(String text) {
        JTextArea area = new JTextArea(text);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setEditable(false);
        area.setMargin(new Insets(10,10,10,10));

        JScrollPane scroll = new JScrollPane(area);
        scroll.setPreferredSize(new Dimension(350, 450));

        JOptionPane.showMessageDialog(this, scroll, "✅ Venta Exitosa", JOptionPane.PLAIN_MESSAGE);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "⚠️ Alerta", JOptionPane.WARNING_MESSAGE);
    }

    private void refreshCartTable() {
        cartModel.setRowCount(0);
        List<CartItem> items = controller.getCartItems();
        for (CartItem ci : items) {
            Object[] row = {
                ci.getItem().getName(),
                String.format("S/. %.2f", ci.getItem().getPrice()),
                ci.getQuantity(),
                String.format("S/. %.2f", ci.getSubtotal())
            };
            cartModel.addRow(row);
        }
        refreshTotalLabel();
    }

    private void refreshTotalLabel() {
        double subtotal = controller.calculateCurrentTotal();
        String descRaw = (String) cbDiscount.getSelectedItem();
        double multiplier = 1.0;

        if (descRaw != null) {
            if (descRaw.contains("VIP")) multiplier = 0.9;
            else if (descRaw.contains("SEASONAL")) multiplier = 0.8;
        }

        double total = subtotal * multiplier;
        if (chkDelivery.isSelected()) total += 10.0;

        lblTotalAmt.setText(String.format("S/. %.2f", total));
    }

    private void clearSaleScreen() {
        controller.clearCart();
        refreshCartTable();
        cbDiscount.setSelectedIndex(0);
        chkDelivery.setSelected(false);
        loadProductsCombo();
    }

    private void loadProductsCombo() {
        cbProducts.removeAllItems();
        for (Product p : controller.getProducts()) {
            cbProducts.addItem(p);
        }
    }
}
