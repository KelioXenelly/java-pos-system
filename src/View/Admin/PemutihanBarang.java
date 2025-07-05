package View.Admin;


import Controller.PemutihanService;
import Controller.BarangDAO;
import Controller.DetailBarangDAO;
import Controller.PemutihanDAO;
import Etc.DatabaseConnection;
import Model.Barang;
import Model.DetailBarang;
import Model.Pemutihan;
import View.LoginPanel;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author ROG
 */
public class PemutihanBarang extends javax.swing.JFrame {
    private Connection conn = DatabaseConnection.getConnection();
    private DefaultTableModel model = new DefaultTableModel();
    private BarangDAO barangDAO;
    private DetailBarangDAO detailBarangDAO;
    private PemutihanDAO pemutihanDAO;
    private PemutihanService pemutihanService;
    
    /**
     * Creates new form PemutihanBarang
     */
    public PemutihanBarang() {
        initComponents();
        if (conn == null) {
        JOptionPane.showMessageDialog(null, "Gagal terhubung ke database.");
        } else {
            barangDAO = new BarangDAO(conn);
            detailBarangDAO = new DetailBarangDAO(conn);
            pemutihanDAO = new PemutihanDAO(conn);
            pemutihanService = new PemutihanService(detailBarangDAO, pemutihanDAO);
        }
        model.addColumn("No.");
        model.addColumn("Kode");
        model.addColumn("Nama Barang");
        model.addColumn("Qty");
        model.addColumn("Harga Beli");
        model.addColumn("Harga Jual");
        model.addColumn("Profit");
        model.addColumn("ID Barang"); 
        model.addColumn("ID Detail Barang");
        model.addColumn("Tanggal Beli");
        model.addColumn("Keterangan");
        
        tableBarang.setModel(model);
        
        // Sembunyikan kolom "ID Barang"
        tableBarang.getColumnModel().getColumn(7).setMinWidth(0);
        tableBarang.getColumnModel().getColumn(7).setMaxWidth(0);
        tableBarang.getColumnModel().getColumn(7).setWidth(0);
        tableBarang.getColumnModel().getColumn(7).setPreferredWidth(0);
        
        // Sembunyikan kolom "ID Detail Barang"
        tableBarang.getColumnModel().getColumn(8).setMinWidth(0);
        tableBarang.getColumnModel().getColumn(8).setMaxWidth(0);
        tableBarang.getColumnModel().getColumn(8).setWidth(0);
        tableBarang.getColumnModel().getColumn(8).setPreferredWidth(0);
        
        // Sembunyikan kolom "Tanggal Beli"
        tableBarang.getColumnModel().getColumn(9).setMinWidth(0);
        tableBarang.getColumnModel().getColumn(9).setMaxWidth(0);
        tableBarang.getColumnModel().getColumn(9).setWidth(0);
        tableBarang.getColumnModel().getColumn(9).setPreferredWidth(0);
        
        // Sembunyikan kolom "Keterangan"
        tableBarang.getColumnModel().getColumn(10).setMinWidth(0);
        tableBarang.getColumnModel().getColumn(10).setMaxWidth(0);
        tableBarang.getColumnModel().getColumn(10).setWidth(0);
        tableBarang.getColumnModel().getColumn(10).setPreferredWidth(0);
        
        setLocationRelativeTo(null);
        
        loadData();
    }
    
    public void loadData() {
        kodeBarangComboBox.removeAllItems();
        kodeBarangComboBox.addItem("Pilih Kode Barang");
        for(Barang kdBrg : barangDAO.getAll()) {
            kodeBarangComboBox.addItem(kdBrg.getKodeBarang());
        }
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
                String.format("%,.1f", barang.getHargaBeli()),
                String.format("%,.1f", barang.getHargaJual()),
                String.format("%,.1f", barang.getProfit()),
                barang.getBarang().getBarangId(),
                barang.getDetailBarangId(),
                barang.getTanggalBeli(),
                barang.getKeterangan(),
            });
            i++;
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
    
    public void addPlaceHolderStyle(JTextArea TextArea) {
        Font font = TextArea.getFont();
        font = font.deriveFont(Font.ITALIC);
        TextArea.setFont(font);
        TextArea.setForeground(Color.gray);
    }
    
    public void removePlaceHolderStyle(JTextArea TextArea) {
        Font font = TextArea.getFont();
        font = font.deriveFont(Font.PLAIN|Font.BOLD);
        TextArea.setFont(font);
        TextArea.setForeground(Color.black);
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
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableBarang = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        transaksiPemutihanTxt = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        namaBarangTxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        qtyTxt = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        kodeBarangComboBox = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        keteranganTextBox = new javax.swing.JTextArea();
        tanggalUbahChooser = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        prosesPemutihanBtn = new javax.swing.JButton();
        confirmCheckBox = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        hargaBeliTxt = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel13 = new javax.swing.JLabel();
        salesBtn5 = new javax.swing.JButton();
        kelolaBarangBtn = new javax.swing.JButton();
        pemutihanBtn = new javax.swing.JButton();
        kelolaPenggunaBtn = new javax.swing.JButton();
        logoutBtn5 = new javax.swing.JButton();
        laporanBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Tabel Barang");

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
                "No", "Kode", "Nama Barang", "Qty", "Harga Beli", "Harga Jual", "Profit", "ID Pembelian"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Integer.class
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
        jScrollPane5.setViewportView(tableBarang);
        if (tableBarang.getColumnModel().getColumnCount() > 0) {
            tableBarang.getColumnModel().getColumn(7).setMinWidth(0);
            tableBarang.getColumnModel().getColumn(7).setPreferredWidth(0);
            tableBarang.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 614, Short.MAX_VALUE))
                    .addComponent(jScrollPane5))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        transaksiPemutihanTxt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        transaksiPemutihanTxt.setText("Transaksi Pemutihan Barang");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Kode Barang:");

        namaBarangTxt.setToolTipText("");
        namaBarangTxt.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Nama Barang:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Qty:");

        qtyTxt.setToolTipText("");
        qtyTxt.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        qtyTxt.setOpaque(false);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Keterangan:");

        kodeBarangComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                kodeBarangComboBoxItemStateChanged(evt);
            }
        });

        keteranganTextBox.setColumns(20);
        keteranganTextBox.setRows(5);
        keteranganTextBox.setText("Tulis Keterangan");
        keteranganTextBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                keteranganTextBoxFocusGained(evt);
            }
        });
        jScrollPane4.setViewportView(keteranganTextBox);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Tanggal Pemutihan:");

        prosesPemutihanBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        prosesPemutihanBtn.setText("Proses");
        prosesPemutihanBtn.setAlignmentY(0.0F);
        prosesPemutihanBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prosesPemutihanBtnMouseClicked(evt);
            }
        });

        confirmCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        confirmCheckBox.setText("Saya yakin ingin memutihkan barang terkait");
        confirmCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmCheckBoxActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Harga Beli:");

        hargaBeliTxt.setEditable(false);
        hargaBeliTxt.setToolTipText("");
        hargaBeliTxt.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        hargaBeliTxt.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(namaBarangTxt)
                    .addComponent(qtyTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4)
                    .addComponent(hargaBeliTxt, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confirmCheckBox)
                            .addComponent(jLabel7)
                            .addComponent(transaksiPemutihanTxt)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(kodeBarangComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel16)
                                    .addComponent(tanggalUbahChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel8)
                            .addComponent(jLabel15)
                            .addComponent(jLabel9))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(prosesPemutihanBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(transaksiPemutihanTxt)
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tanggalUbahChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(kodeBarangComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(namaBarangTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(qtyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hargaBeliTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confirmCheckBox)
                .addGap(27, 27, 27)
                .addComponent(prosesPemutihanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(102, 153, 255));

        jLabel12.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Selamat datang,");

        jLabel13.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Admin");

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

        kelolaPenggunaBtn.setText("Kelola Pengguna");
        kelolaPenggunaBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kelolaPenggunaBtnMouseClicked(evt);
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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addComponent(salesBtn5, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kelolaBarangBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pemutihanBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(kelolaPenggunaBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(laporanBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutBtn5)
                .addGap(34, 34, 34))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(salesBtn5)
                            .addComponent(kelolaBarangBtn)
                            .addComponent(pemutihanBtn)
                            .addComponent(kelolaPenggunaBtn)
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
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kodeBarangComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_kodeBarangComboBoxItemStateChanged
        Object selectedItem = kodeBarangComboBox.getSelectedItem();

        if (selectedItem == null) {
            // Jika belum ada item dipilih, reset field
            namaBarangTxt.setText("");
            tableBarang.clearSelection();
            return;
        }
        
        String kode_barang = selectedItem.toString().trim();
        if (kode_barang != null) {
            if(!kode_barang.equals("Pilih Kode Barang")) {
                Barang barang = barangDAO.getByKdBarang(kode_barang);

                if(barang != null) {
                    namaBarangTxt.setText(barang.getNamaBarang());

                    for (int i = 0; i < model.getRowCount(); i++) {
                        String kodeBarangTabel = model.getValueAt(i, 1).toString(); // Kolom kode_barang di tabel
                        if (kodeBarangTabel.equals(kode_barang)) {
                            tableBarang.setRowSelectionInterval(i, i); // Pilih baris pertama yang cocok
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Kode barang tidak ditemukan.");
                }
            } else {
                namaBarangTxt.setText("");
                tableBarang.clearSelection();
            }
        } else {
            // Tidak ada item dipilih → abaikan atau tampilkan pesan
            System.out.print("");
        }
    }//GEN-LAST:event_kodeBarangComboBoxItemStateChanged

    private void keteranganTextBoxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_keteranganTextBoxFocusGained
        if(keteranganTextBox.getText().equals("Tulis Keterangan")) {
            keteranganTextBox.setText(null);
            keteranganTextBox.requestFocus();
            // Remove placeholder style
            removePlaceHolderStyle(keteranganTextBox);
        }
    }//GEN-LAST:event_keteranganTextBoxFocusGained

    private void prosesPemutihanBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_prosesPemutihanBtnMouseClicked
        int selectedRow = tableBarang.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Tidak ada baris yang dipilih.");
            return;
        }
        
        Object qtyObj = model.getValueAt(selectedRow, 3);
        Object hargaBeliObj = model.getValueAt(selectedRow, 4);
        Object hargaJualObj = model.getValueAt(selectedRow, 5);

        if (qtyObj == null || hargaBeliObj == null || hargaJualObj == null) {
            JOptionPane.showMessageDialog(null, "Data tidak lengkap di tabel.");
            return;
        }

        int qtyTable = Integer.parseInt(qtyObj.toString());
        String beliStr = hargaBeliObj.toString();
        String jualStr = hargaJualObj.toString();
        double hargaBeliTable = Double.parseDouble(beliStr.replace(".", "").replace(",", "."));
        double hargaJualTable = Double.parseDouble(jualStr.replace(".", "").replace(",", "."));

        // Ambil data dari form input
        String nama_barang = namaBarangTxt.getText();
        int qtyPemutihan = Integer.parseInt(qtyTxt.getText());
        String keterangan = keteranganTextBox.getText();
        boolean confirm = confirmCheckBox.isSelected();
        Date tanggal = tanggalUbahChooser.getDate();

        // Validasi input
        if (nama_barang.isEmpty() || qtyPemutihan <= 0 || !confirm) {
            JOptionPane.showMessageDialog(null, "Harap lengkapi semua data sebelum memproses pemutihan.");
            return;
        }

        // Ambil barang berdasarkan nama
        Barang barang = barangDAO.getByNamaBarang(nama_barang);
        if (barang == null) {
            try {
                throw new Exception("Barang tidak ditemukan");
            } catch (Exception ex) {
                Logger.getLogger(PemutihanBarang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Pemutihan pemutihan = new Pemutihan(tanggal, barang, qtyPemutihan, keterangan, confirm, 
            hargaBeliTable, hargaJualTable);
        
        try {
            pemutihanService.processPemutihan(pemutihan);
            
            Thread.sleep(1000);
            
            loadData();
        } catch (Exception ex) {
            Logger.getLogger(PemutihanBarang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_prosesPemutihanBtnMouseClicked

    private void tableBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableBarangMouseClicked
        try {
            int selectedRow = tableBarang.getSelectedRow();
            if (selectedRow != -1) {
                Object kodeBarang = model.getValueAt(selectedRow, 1);
                Object namaBarang = model.getValueAt(selectedRow, 2);
                Object qty = model.getValueAt(selectedRow, 3);
                Object hargaBeli = model.getValueAt(selectedRow, 4);
                Date tanggal_beli = (Date) model.getValueAt(selectedRow, 9);
                Object keterangan = model.getValueAt(selectedRow, 10);
                
                kodeBarangComboBox.setSelectedItem(kodeBarang != null ? kodeBarang.toString() : "");
                namaBarangTxt.setText(namaBarang != null ? namaBarang.toString() : "");
                qtyTxt.setText(qty != null ? qty.toString() : "");
                hargaBeliTxt.setText(hargaBeli != null ? hargaBeli.toString() : "");
                tanggalUbahChooser.setDate(tanggal_beli != null ? tanggal_beli : null);
                keteranganTextBox.setText(keterangan != null ? keterangan.toString() : "");
            } else {
                JOptionPane.showMessageDialog(null, "Tidak ada baris yang dipilih.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_tableBarangMouseClicked

    private void confirmCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_confirmCheckBoxActionPerformed

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

    private void kelolaPenggunaBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelolaPenggunaBtnMouseClicked
        KelolaPengguna kelolaPenggunaPage = new KelolaPengguna();
        kelolaPenggunaPage.setVisible(true);
        kelolaPenggunaPage.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_kelolaPenggunaBtnMouseClicked

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
            java.util.logging.Logger.getLogger(PemutihanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PemutihanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PemutihanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PemutihanBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PemutihanBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox confirmCheckBox;
    private javax.swing.JTextField hargaBeliTxt;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JButton kelolaBarangBtn;
    private javax.swing.JButton kelolaPenggunaBtn;
    private javax.swing.JTextArea keteranganTextBox;
    private javax.swing.JComboBox<String> kodeBarangComboBox;
    private javax.swing.JButton laporanBtn;
    private javax.swing.JButton laporanBtn1;
    private javax.swing.JButton logoutBtn5;
    private javax.swing.JTextField namaBarangTxt;
    private javax.swing.JButton pemutihanBtn;
    private javax.swing.JButton prosesPemutihanBtn;
    private javax.swing.JTextField qtyTxt;
    private javax.swing.JButton salesBtn5;
    private javax.swing.JTable tableBarang;
    private com.toedter.calendar.JDateChooser tanggalUbahChooser;
    private javax.swing.JLabel transaksiPemutihanTxt;
    // End of variables declaration//GEN-END:variables
}
