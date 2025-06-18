/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View.Kasir;

import View.Kasir.*;
import Controller.BarangDAO;
import Controller.DetailBarangDAO;
import Controller.PenjualanDAO;
import Controller.PenjualanService;
import Controller.ReceiptGenerator;
import Etc.DatabaseConnection;
import Model.Barang;
import Model.DetailBarang;
import View.LoginPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ROG
 */
public class Penjualan extends javax.swing.JFrame {
    private Connection conn = DatabaseConnection.getConnection();
    private DefaultTableModel model = new DefaultTableModel();
    private DefaultTableModel model2 = new DefaultTableModel();
    private BarangDAO barangDAO;
    private DetailBarangDAO detailBarangDAO;
    private PenjualanDAO penjualanDAO;
    private PenjualanService penjualanService;
    
    /**
     * Creates new form Penjualan
     */
    public Penjualan() {
        initComponents();
        if (conn == null) {
        JOptionPane.showMessageDialog(null, "Gagal terhubung ke database.");
        } else {
            barangDAO = new BarangDAO(conn);
            detailBarangDAO = new DetailBarangDAO(conn);
            penjualanDAO = new PenjualanDAO(conn);
            this.penjualanService = new PenjualanService(detailBarangDAO, penjualanDAO);
        }
        model.addColumn("No.");
        model.addColumn("Kode");
        model.addColumn("Nama Barang");
        model.addColumn("Qty");
        model.addColumn("Harga Beli");
        model.addColumn("Harga Jual");
        model.addColumn("Profit");
        model.addColumn("ID Barang"); 
        
        // Sembunyikan kolom "ID Barang"
        tableBarang.getColumnModel().getColumn(7).setMinWidth(0);
        tableBarang.getColumnModel().getColumn(7).setMaxWidth(0);
        tableBarang.getColumnModel().getColumn(7).setWidth(0);
        tableBarang.getColumnModel().getColumn(7).setPreferredWidth(0);

        tableBarang.setModel(model);
        
        setLocationRelativeTo(null);
        currentDate();
        loadData();
        refreshTableKeranjang();
    }
    
    public void currentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        currentDatelbl.setText(dtf.format(now));
    }
    
    public void loadData() {
        pilihBarangDropDown.removeAllItems();
        pilihBarangDropDown.addItem("Pilih Barang");
        for(Barang barang : barangDAO.getAll()) {
            pilihBarangDropDown.addItem(barang.getNamaBarang());
        }
        jumlahTxt.setText("Masukkan Jumlah Barang");
        diskonTxt.setText("Masukkan Diskon (%)");
        bayarTxt.setText("");
        kembalianLbl.setText("Rp0.0");
        
        // Kosongkan tabel
        model.setRowCount(0);
        
        barangDAO = new BarangDAO(conn);
        detailBarangDAO = new DetailBarangDAO(conn);
        
        List<DetailBarang> allBarang = detailBarangDAO.getAll();
        int i = 1;
        for(DetailBarang barang : allBarang) {
            model.addRow(new Object[] {
                i,
                barang.getBarang().getKodeBarang(),
                barang.getBarang().getNamaBarang(),
                barang.getQty(),
                barang.getHargaBeli(),
                barang.getHargaJual(),
                barang.getProfit(),
                barang.getBarang().getBarangId(),
            });
            i++;
        }
    }
    
    public void refreshTableKeranjang() {
        model2 = new DefaultTableModel();
        model2.addColumn("Barang");
        model2.addColumn("Qty");
        model2.addColumn("Harga");
        model2.addColumn("Jumlah");
        tableKeranjang.setModel(model2);
        
        totalLbl.setText("Rp0.0");
        persentaseDiskonLbl.setText("");
        angkaDiskonTxt.setText("Rp0.0");
        grandTotalLbl.setText("Rp0.0");
    }
    
    private boolean isValidPrice(String value) {
        return !value.isEmpty() && value.matches("\\d+(\\.\\d+)?");
    }
    
    public void refreshPrice() {
        ArrayList<Double> hargaJual = new ArrayList<>();
        double total = 0;

        try {
            for (int i = 0; i < model2.getRowCount(); i++) {
                String value = model2.getValueAt(i, 3) != null ? model2.getValueAt(i, 3).toString().trim() : "";

                if (!isValidPrice(value)) {
                    JOptionPane.showMessageDialog(null, "Error: Harga tidak valid di baris " + (i + 1));
                    return;
                }

                double harga = Double.parseDouble(value);
                hargaJual.add(harga);
            }

            // Hitung total harga
            for (double harga : hargaJual) {
                total += harga;
            }

            // Update UI
            grandTotalLbl.setText("Rp" + String.valueOf(total));
            totalLbl.setText("Rp" + String.valueOf(total));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: Format harga tidak valid.");
        }
    }

    private double parseTotalHarga() throws NumberFormatException {
            String totalText = totalLbl.getText().replace("Rp", "").trim();
            return Double.parseDouble(totalText);
        }

    private double parsePersentaseDiskon() {
        String diskonText = diskonTxt.getText().trim();

        if (diskonText.isEmpty()) {
            return 0.0;
        }

        if (!diskonText.matches("\\d+(\\.\\d+)?")) {
            throw new NumberFormatException("Format persentase diskon salah");
        }

        return Double.parseDouble(diskonText) / 100;
    }
    
    private double calculateDiskon(double total, double persentaseDiskon) {
        return total * persentaseDiskon;
    }
    
    public void addDiskon() {
        try {
            double total = parseTotalHarga();

            if (total <= 0) {
                JOptionPane.showMessageDialog(null, "Total harga harus lebih besar dari nol.");
                return;
            }

            double persentaseDiskon = parsePersentaseDiskon();

            double diskon = calculateDiskon(total, persentaseDiskon);
            double grandTotal = total - diskon;

            persentaseDiskonLbl.setText(String.format("%.0f %%", persentaseDiskon * 100));
            angkaDiskonTxt.setText("Rp" + String.valueOf(diskon));
            grandTotalLbl.setText("Rp" + String.valueOf(grandTotal));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Input tidak valid: " + e.getMessage());
        }
    }
    
    public void addPlaceHolderStyle(JTextField textField) {
        Font font = textField.getFont();
        font = font.deriveFont(Font.ITALIC);
        textField.setFont(font);
        textField.setForeground(Color.gray);
    }
    
    public void removePlaceHolderStyle(JTextField textField) {
        Font font = textField.getFont();
        font = font.deriveFont(Font.PLAIN|Font.BOLD);
        textField.setFont(font);
        textField.setForeground(Color.black);
    }
    
    private void showReceiptInDialog(String receiptText) {
        JTextArea textArea = new JTextArea(receiptText);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300)); // Ukuran default

        JOptionPane.showMessageDialog(
            null,
            scrollPane,
            "Struk Penjualan",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    // Method untuk menyimpan struk ke file
    public void saveReceiptToFile(String receiptText) {
        try {
            // Pilih lokasi dan nama file
            String fileName = "struk_penjualan_" + System.currentTimeMillis() + ".txt";
            FileWriter writer = new FileWriter(fileName);
            writer.write(receiptText);
            writer.close();

            JOptionPane.showMessageDialog(null, "Struk berhasil disimpan sebagai:\n" + fileName);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan struk: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method untuk mencetak struk ke printer
    public void printReceipt(String receiptText) {
        try {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPrintable((graphics, pageFormat, pageIndex) -> {
                if (pageIndex > 0) return Printable.NO_SUCH_PAGE;

                int margin = 30;
                graphics.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

                Font font = new Font("Monospaced", Font.PLAIN, 10);
                graphics.setFont(font);

                BufferedReader reader = new BufferedReader(new StringReader(receiptText));
                String line;
                int y = margin;

                try {
                    while ((line = reader.readLine()) != null) {
                        graphics.drawString(line, margin / 2, y);
                        y += 15;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Penjualan.class.getName()).log(Level.SEVERE, null, ex);
                }

                return Printable.PAGE_EXISTS;
            });

            if (job.printDialog()) {
                job.print();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mencetak struk: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        currentDatelbl = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pilihBarangDropDown = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jumlahTxt = new javax.swing.JTextField();
        minusBtn = new javax.swing.JButton();
        plusBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBarang = new javax.swing.JTable();
        okBtn = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        kembalianLbl = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableKeranjang = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        angkaDiskonTxt = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        totalLbl = new javax.swing.JLabel();
        grandTotalLbl = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        persentaseDiskonLbl = new javax.swing.JLabel();
        bayarTxt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        diskonTxt = new javax.swing.JTextField();
        prosesBtn = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel19 = new javax.swing.JLabel();
        salesBtn5 = new javax.swing.JButton();
        kelolaBarangBtn = new javax.swing.JButton();
        pemutihanBtn = new javax.swing.JButton();
        logoutBtn5 = new javax.swing.JButton();
        laporanBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        currentDatelbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        currentDatelbl.setText("Current Date");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Pilih Barang:");

        pilihBarangDropDown.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                pilihBarangDropDownItemStateChanged(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Jumlah:");

        jumlahTxt.setText("Masukkan Jumlah Barang");
        jumlahTxt.setToolTipText("");
        jumlahTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jumlahTxtFocusGained(evt);
            }
        });

        minusBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        minusBtn.setText("-");
        minusBtn.setAlignmentY(0.0F);
        minusBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minusBtnMouseClicked(evt);
            }
        });

        plusBtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        plusBtn.setText("+");
        plusBtn.setAlignmentY(0.0F);
        plusBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                plusBtnMouseClicked(evt);
            }
        });

        tableBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode", "Nama Barang", "Qty", "Harga Beli", "Harga Jual", "Profit", "ID Barang"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableBarang.setShowGrid(true);
        tableBarang.getTableHeader().setReorderingAllowed(false);
        tableBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableBarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableBarang);

        okBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        okBtn.setText("OK");
        okBtn.setAlignmentY(0.0F);
        okBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(currentDatelbl, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(150, 150, 150))
                            .addComponent(pilihBarangDropDown, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel12))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jumlahTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(minusBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(plusBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(okBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                            .addGap(65, 65, 65)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(plusBtn)
                                .addComponent(minusBtn)
                                .addComponent(jumlahTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(pilihBarangDropDown, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(okBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(currentDatelbl)
                        .addGap(14, 14, 14)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel12))))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(103, 136, 201));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Kembalian");

        kembalianLbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        kembalianLbl.setForeground(new java.awt.Color(255, 255, 255));
        kembalianLbl.setText("Rp500.000");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(kembalianLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kembalianLbl)
                .addGap(28, 28, 28))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setText("Keranjang:");

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        tableKeranjang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"", null, null, null},
                {"", null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Barang", "Qty", "Harga", "Jumlah"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableKeranjang.setRowSelectionAllowed(false);
        tableKeranjang.setShowGrid(true);
        tableKeranjang.getTableHeader().setResizingAllowed(false);
        tableKeranjang.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tableKeranjang);

        jLabel16.setText("Diskon:");

        angkaDiskonTxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        angkaDiskonTxt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        angkaDiskonTxt.setText("Rpxxx");

        jLabel18.setText("Total:");

        totalLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        totalLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalLbl.setText("Rp5.150.000");

        grandTotalLbl.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        grandTotalLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        grandTotalLbl.setText("Rp5.150.000");

        jLabel21.setText("Grand Total:");

        persentaseDiskonLbl.setText("2%");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                        .addComponent(grandTotalLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(persentaseDiskonLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(totalLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(angkaDiskonTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(totalLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(angkaDiskonTxt)
                    .addComponent(persentaseDiskonLbl))
                .addGap(8, 8, 8)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(grandTotalLbl))
                .addContainerGap())
        );

        bayarTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bayarTxtActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Bayar:");

        diskonTxt.setText("Masukkan Diskon (%)");
        diskonTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                diskonTxtFocusGained(evt);
            }
        });
        diskonTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diskonTxtActionPerformed(evt);
            }
        });

        prosesBtn.setText("Proses");
        prosesBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prosesBtnMouseClicked(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(102, 153, 255));

        jLabel17.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Selamat datang,");

        jLabel19.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Kasir");

        salesBtn5.setText("Penjualan");
        salesBtn5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salesBtn5MouseClicked(evt);
            }
        });

        kelolaBarangBtn.setText("Kelola Barang");
        kelolaBarangBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kelolaBarangBtnMouseClicked(evt);
            }
        });

        pemutihanBtn.setText("Pemutihan Barang");
        pemutihanBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pemutihanBtnMouseClicked(evt);
            }
        });

        logoutBtn5.setText("Logout");
        logoutBtn5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutBtn5MouseClicked(evt);
            }
        });

        laporanBtn.setText("Laporan");
        laporanBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                laporanBtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addComponent(salesBtn5, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kelolaBarangBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pemutihanBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(laporanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutBtn5)
                .addGap(34, 34, 34))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(salesBtn5)
                            .addComponent(kelolaBarangBtn)
                            .addComponent(pemutihanBtn)
                            .addComponent(logoutBtn5)
                            .addComponent(laporanBtn))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(bayarTxt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(prosesBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(diskonTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(diskonTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bayarTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(prosesBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pilihBarangDropDownItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_pilihBarangDropDownItemStateChanged
        Object selectedItem = pilihBarangDropDown.getSelectedItem();

        if (selectedItem == null) {
            tableBarang.clearSelection();
            return;
        }
        
        if (selectedItem != null) {
            String nama_barang = selectedItem.toString().trim();
            
            if(!nama_barang.equals("Pilih Barang")) {
                Barang barang = barangDAO.getByNamaBarang(nama_barang);

                if(barang != null) {
                    for (int i = 0; i < model.getRowCount(); i++) {
                        String kodeBarangTabel = model.getValueAt(i, 2).toString(); // Kolom nama_barang di tabel
                        if (kodeBarangTabel.equals(nama_barang)) {
                            tableBarang.setRowSelectionInterval(i, i); // Pilih baris pertama yang cocok
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Nama barang tidak ditemukan.");
                }
            } else {
                tableBarang.clearSelection();
            }
        } else {
        // Tidak ada item dipilih → abaikan atau tampilkan pesan
        System.out.print("");
    }
        
    }//GEN-LAST:event_pilihBarangDropDownItemStateChanged

    private void jumlahTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jumlahTxtFocusGained
        if(jumlahTxt.getText().equals("Masukkan Jumlah Barang")) {
            jumlahTxt.setText(null);
            jumlahTxt.requestFocus();
            // Remove placeholder style
            removePlaceHolderStyle(jumlahTxt);
        }
    }//GEN-LAST:event_jumlahTxtFocusGained

    private void minusBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minusBtnMouseClicked
        try {
            String inputQty = jumlahTxt.getText().trim();
            if (inputQty.isEmpty() || inputQty.equals("Masukkan Jumlah Barang")) {
                jumlahTxt.setText("1");
                return;
            }

            int num = Integer.parseInt(jumlahTxt.getText().trim());
            if(num > 1) {
                num--;
                jumlahTxt.setText(String.valueOf(num));
            } else {
                JOptionPane.showMessageDialog(null, "Angka kuantitas tidak boleh < 1", "Error", JOptionPane.ERROR_MESSAGE);

            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Tolong masukkan angka yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
            jumlahTxt.setText("0");
        }
    }//GEN-LAST:event_minusBtnMouseClicked

    private void plusBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_plusBtnMouseClicked
        try {
            String inputQty = jumlahTxt.getText().trim();
            if (inputQty.isEmpty() || inputQty.equals("Masukkan Jumlah Barang")) {
                jumlahTxt.setText("1");
                return;
            }

            int num = Integer.parseInt(jumlahTxt.getText().trim());
            num++;
            jumlahTxt.setText(String.valueOf(num));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Tolong masukkan angka yang valid!", "Error", JOptionPane.ERROR_MESSAGE);
            jumlahTxt.setText("1");
        }
    }//GEN-LAST:event_plusBtnMouseClicked

    private void tableBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBarangMouseClicked
        try {
            int selectedRow = tableBarang.getSelectedRow();
            if (selectedRow != -1) {
                Object namaBarang = model.getValueAt(selectedRow, 2);

                pilihBarangDropDown.setSelectedItem(namaBarang != null ? namaBarang.toString() : "");
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada baris yang dipilih.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_tableBarangMouseClicked

    private void okBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_okBtnMouseClicked
        try {
            int selectedRow = tableBarang.getSelectedRow();
            if (selectedRow != -1) {
                int qty = Integer.parseInt(jumlahTxt.getText());
                double harga_jual = Double.parseDouble(model.getValueAt(selectedRow, 5).toString());
                double jumlah_qty = Integer.parseInt(model.getValueAt(selectedRow, 3).toString());

                if(qty <= jumlah_qty) {
                    Object[] data = {
                        model.getValueAt(selectedRow, 2), // Nama Barang
                        jumlahTxt.getText(), // Qty Jual
                        model.getValueAt(selectedRow, 5), // Harga Jual
                        qty * harga_jual // Jumlah Qty x Harga Jual
                    };
                    model2.addRow(data);

                    refreshPrice();
                } else {
                    JOptionPane.showMessageDialog(null, "Jumlah stok barang tersebut tidak cukup");

                }
            } else {
                JOptionPane.showMessageDialog(null, "Harap pilih baris dalam tabel");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_okBtnMouseClicked

    private void bayarTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bayarTxtActionPerformed
        try {
            int selectedRow = tableBarang.getSelectedRow();
            if (selectedRow != -1) {
                Object namaBarangObj = model.getValueAt(selectedRow, 2); // Nama barang
                Object hargaJualObj = model.getValueAt(selectedRow, 5);   // Harga jual
                Object qtyStokObj = model.getValueAt(selectedRow, 3);     // Qty stok
                
                if (namaBarangObj == null || hargaJualObj == null || qtyStokObj == null) {
                    JOptionPane.showMessageDialog(null, "Data tidak lengkap.");
                    return;
                }
                
                double hargaJual = Double.parseDouble(hargaJualObj.toString());
                int qtyStok = Integer.parseInt(qtyStokObj.toString());
                int qtyJual = Integer.parseInt(jumlahTxt.getText());
                
                if (qtyJual <= 0 || qtyJual > qtyStok) {
                    JOptionPane.showMessageDialog(null, "Jumlah jual tidak valid atau melebihi stok");
                    return;
                }
                
                double diskon = 0;
                try {
                    diskon = Double.parseDouble(angkaDiskonTxt.getText().replace("Rp", "").trim());
                } catch (NumberFormatException e) {
                    diskon = 0; // Jika kosong, diskon 0%
                }
                
                double grandTotal = Double.parseDouble(grandTotalLbl.getText().replace("Rp", "").trim());
                double bayar = Double.parseDouble(bayarTxt.getText().replace("Rp", "").trim());
                double kembalian = bayar - grandTotal;

                if (bayar < grandTotal) {
                    JOptionPane.showMessageDialog(null, "Pembayaran kurang!");
                    return;
                }
                
                int barang_id = Integer.parseInt(model.getValueAt(selectedRow, 7).toString()); 
                Barang barang = new Barang(barang_id, null, namaBarangObj.toString());
                
                Model.Penjualan pjl = new Model.Penjualan(new Date(), barang, qtyJual, diskon, grandTotal, 
                    hargaJual, bayar, kembalian);
                
                kembalianLbl.setText("Rp" + kembalian);
                
                penjualanService.processPenjualan(pjl);
                
                int confirm = JOptionPane.showConfirmDialog(null, "Ingin mencetak struk?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String receiptText = ReceiptGenerator.generateReceiptText(pjl);

                    // Tampilkan struk
                    showReceiptInDialog(receiptText);
                    
                    // Save ke File dan Cetak ke Printer
                    saveReceiptToFile(receiptText); // Jika ingin simpan ke file
                    printReceipt(receiptText);      // Jika ingin cetak ke printer
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Selesai.");
                }
                
                Thread.sleep(1000);
                refreshTableKeranjang();
                loadData();
            } else {
                JOptionPane.showMessageDialog(null, "Harap pilih baris dalam tabel");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_bayarTxtActionPerformed

    private void diskonTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_diskonTxtFocusGained
        if(diskonTxt.getText().equals("Masukkan Diskon (%)")) {
            diskonTxt.setText(null);
            diskonTxt.requestFocus();
            // Remove placeholder style
            removePlaceHolderStyle(diskonTxt);
        }
    }//GEN-LAST:event_diskonTxtFocusGained

    private void diskonTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diskonTxtActionPerformed
        addDiskon();
    }//GEN-LAST:event_diskonTxtActionPerformed

    private void prosesBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prosesBtnMouseClicked
        try {
            int selectedRow = tableBarang.getSelectedRow();
            if (selectedRow != -1) {
                Object namaBarangObj = model.getValueAt(selectedRow, 2); // Nama barang
                Object hargaJualObj = model.getValueAt(selectedRow, 5);   // Harga jual
                Object qtyStokObj = model.getValueAt(selectedRow, 3);     // Qty stok
                
                if (namaBarangObj == null || hargaJualObj == null || qtyStokObj == null) {
                    JOptionPane.showMessageDialog(null, "Data tidak lengkap.");
                    return;
                }
                
                double hargaJual = Double.parseDouble(hargaJualObj.toString());
                int qtyStok = Integer.parseInt(qtyStokObj.toString());
                int qtyJual = Integer.parseInt(jumlahTxt.getText());
                
                if (qtyJual <= 0 || qtyJual > qtyStok) {
                    JOptionPane.showMessageDialog(null, "Jumlah jual tidak valid atau melebihi stok");
                    return;
                }
                
                double diskon = 0;
                try {
                    diskon = Double.parseDouble(angkaDiskonTxt.getText().replace("Rp", "").trim());
                } catch (NumberFormatException e) {
                    diskon = 0; // Jika kosong, diskon 0%
                }
                
                double grandTotal = Double.parseDouble(grandTotalLbl.getText().replace("Rp", "").trim());
                double bayar = Double.parseDouble(bayarTxt.getText().replace("Rp", "").trim());
                double kembalian = bayar - grandTotal;

                if (bayar < grandTotal) {
                    JOptionPane.showMessageDialog(null, "Pembayaran kurang!");
                    return;
                }
                
                int barang_id = Integer.parseInt(model.getValueAt(selectedRow, 7).toString()); 
                Barang barang = new Barang(barang_id, null, namaBarangObj.toString());
                
                Model.Penjualan pjl = new Model.Penjualan(new Date(), barang, qtyJual, diskon, grandTotal, 
                    hargaJual, bayar, kembalian);
                
                kembalianLbl.setText("Rp" + kembalian);
                
                penjualanService.processPenjualan(pjl);
                
                int confirm = JOptionPane.showConfirmDialog(null, "Ingin mencetak struk?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String receiptText = ReceiptGenerator.generateReceiptText(pjl);

                    // Tampilkan struk
                    showReceiptInDialog(receiptText);
                    
                    // Save ke File dan Cetak ke Printer
                    saveReceiptToFile(receiptText); // Jika ingin simpan ke file
                    printReceipt(receiptText);      // Jika ingin cetak ke printer
                } else {
                    JOptionPane.showMessageDialog(null, "Transaksi Selesai.");
                }
                
                Thread.sleep(1000);
                refreshTableKeranjang();
                loadData();
            } else {
                JOptionPane.showMessageDialog(null, "Harap pilih baris dalam tabel");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_prosesBtnMouseClicked

    private void salesBtn5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesBtn5MouseClicked
        Penjualan penjualanPage = new Penjualan();
        penjualanPage.setVisible(true);
        penjualanPage.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_salesBtn5MouseClicked

    private void kelolaBarangBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelolaBarangBtnMouseClicked
        KelolaBarang kelolaBarangPage = new KelolaBarang();
        kelolaBarangPage.setVisible(true);
        kelolaBarangPage.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_kelolaBarangBtnMouseClicked

    private void pemutihanBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pemutihanBtnMouseClicked
        PemutihanBarang pemutihanPage = new PemutihanBarang();
        pemutihanPage.setVisible(true);
        pemutihanPage.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_pemutihanBtnMouseClicked

    private void logoutBtn5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBtn5MouseClicked
        JOptionPane.showMessageDialog(null, "Anda berhasil keluar.");
        LoginPanel login = new LoginPanel();
        login.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_logoutBtn5MouseClicked

    private void laporanBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_laporanBtnMouseClicked
        Laporan laporanPage = new Laporan();
        laporanPage.setVisible(true);
        laporanPage.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_laporanBtnMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Penjualan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Penjualan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel angkaDiskonTxt;
    private javax.swing.JTextField bayarTxt;
    private javax.swing.JLabel currentDatelbl;
    private javax.swing.JTextField diskonTxt;
    private javax.swing.JLabel grandTotalLbl;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jumlahTxt;
    private javax.swing.JButton kelolaBarangBtn;
    private javax.swing.JLabel kembalianLbl;
    private javax.swing.JButton laporanBtn;
    private javax.swing.JButton logoutBtn5;
    private javax.swing.JButton minusBtn;
    private javax.swing.JButton okBtn;
    private javax.swing.JButton pemutihanBtn;
    private javax.swing.JLabel persentaseDiskonLbl;
    private javax.swing.JComboBox<String> pilihBarangDropDown;
    private javax.swing.JButton plusBtn;
    private javax.swing.JButton prosesBtn;
    private javax.swing.JButton salesBtn5;
    private javax.swing.JTable tableBarang;
    private javax.swing.JTable tableKeranjang;
    private javax.swing.JLabel totalLbl;
    // End of variables declaration//GEN-END:variables
}
