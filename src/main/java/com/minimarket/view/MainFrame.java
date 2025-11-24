package com.minimarket.view;

import com.minimarket.controller.MainController;
import com.minimarket.controller.CartItem;
import com.minimarket.model.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private MainController controller;
    private JTabbedPane tabbedPane;

    private JComboBox<Product> cbProducts;
    private JTable cartTable;
    private DefaultTableModel cartModel;
    private JLabel lblTotalAmt;
    private JComboBox<String> cbDocType;
    private JComboBox<String> cbDiscount;
    private JCheckBox chkDelivery;
    private JCheckBox chkGiftWrap;

    private InventoryPanel inventoryPanel;
    private HistoryPanel historyPanel;

    private final Color PRIMARY_GREEN = new Color(39, 174, 96);
    private final Color WHITE_BG      = new Color(255, 255, 255);

    public MainFrame() {
        controller = new MainController();
        inventoryPanel = new InventoryPanel(controller);
        historyPanel = new HistoryPanel(controller);

        setTitle("🥬 La Esquina | Sistema con Patrones UML");
        setSize(1000, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        UIManager.put("TabbedPane.selected", PRIMARY_GREEN);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel pnlSale = createSalePanel();

        tabbedPane.addTab("🛒 PUNTO DE VENTA", null, pnlSale, "Ventas");
        tabbedPane.addTab("📦 INVENTARIO", null, inventoryPanel, "Stock");
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

        JPanel pnlCenter = new JPanel(new BorderLayout(0, 10));

        JPanel pnlSelection = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlSelection.setBorder(BorderFactory.createTitledBorder("Productos"));
        cbProducts = new JComboBox<>();
        loadProductsCombo();
        JButton btnAdd = new JButton("AGREGAR");
        JButton btnCombo = new JButton("PACK OFERTA");

        pnlSelection.add(cbProducts);
        pnlSelection.add(btnAdd);
        pnlSelection.add(btnCombo);

        String[] colNames = {"Producto", "Precio", "Cant.", "Subtotal"};
        cartModel = new DefaultTableModel(colNames, 0);
        cartTable = new JTable(cartModel);

        pnlCenter.add(pnlSelection, BorderLayout.NORTH);
        pnlCenter.add(new JScrollPane(cartTable), BorderLayout.CENTER);
        panel.add(pnlCenter, BorderLayout.CENTER);

        JPanel pnlRight = new JPanel();
        pnlRight.setLayout(new BoxLayout(pnlRight, BoxLayout.Y_AXIS));
        pnlRight.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        pnlRight.setPreferredSize(new Dimension(250, 0));

        String[] docs = {"BOLETA", "FACTURA", "NOTA"};
        cbDocType = new JComboBox<>(docs);

        String[] discounts = {"Regular", "VIP", "SEASONAL"};
        cbDiscount = new JComboBox<>(discounts);

        chkDelivery = new JCheckBox("Delivery (+S/10)");
        chkGiftWrap = new JCheckBox("Regalo (+S/5)");

        lblTotalAmt = new JLabel("S/. 0.00");
        lblTotalAmt.setFont(new Font("Segoe UI", Font.BOLD, 30));

        JButton btnProcess = new JButton("COBRAR");
        btnProcess.setBackground(PRIMARY_GREEN);
        btnProcess.setForeground(Color.WHITE);

        JButton btnClear = new JButton("LIMPIAR");

        pnlRight.add(new JLabel("Documento:"));
        pnlRight.add(cbDocType);
        pnlRight.add(Box.createVerticalStrut(10));
        pnlRight.add(new JLabel("Descuento:"));
        pnlRight.add(cbDiscount);
        pnlRight.add(Box.createVerticalStrut(10));
        pnlRight.add(chkDelivery);
        pnlRight.add(chkGiftWrap);
        pnlRight.add(Box.createVerticalStrut(20));
        pnlRight.add(new JLabel("Total:"));
        pnlRight.add(lblTotalAmt);
        pnlRight.add(Box.createVerticalStrut(20));
        pnlRight.add(btnProcess);
        pnlRight.add(Box.createVerticalStrut(10));
        pnlRight.add(btnClear);

        panel.add(pnlRight, BorderLayout.EAST);

        btnAdd.addActionListener(e -> {
            try {
                controller.addToCart((Product)cbProducts.getSelectedItem());
                refreshCart();
            } catch(Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
        });

        btnCombo.addActionListener(e -> {
            try {
                controller.addToCart(controller.createSampleBundle());
                refreshCart();
            } catch(Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
        });

        btnProcess.addActionListener(e -> {
            try {
                String ticket = controller.processSale(
                    (String) cbDocType.getSelectedItem(),
                    (String) cbDiscount.getSelectedItem(),
                    chkDelivery.isSelected(),
                    chkGiftWrap.isSelected()
                );
                JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(ticket)), "Comprobante", JOptionPane.PLAIN_MESSAGE);
                clearScreen();
            } catch(Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
        });

        btnClear.addActionListener(e -> clearScreen());

        return panel;
    }

    private void refreshCart() {
        cartModel.setRowCount(0);
        for(CartItem i : controller.getCartItems()) {
            cartModel.addRow(new Object[]{i.getItem().getName(), i.getItem().getPrice(), i.getQuantity(), i.getSubtotal()});
        }
        lblTotalAmt.setText("S/. " + controller.calculateCurrentTotal());
    }

    private void clearScreen() {
        controller.clearCart();
        refreshCart();
    }

    private void loadProductsCombo() {
        cbProducts.removeAllItems();
        for(Product p : controller.getProducts()) cbProducts.addItem(p);
    }
}
