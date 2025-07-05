package View.Admin;

import Etc.DatabaseConnection;
import java.sql.Connection;
import View.LoginPanel;
import com.toedter.calendar.JDateChooser;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author ROG
 */
public class Laporan extends javax.swing.JFrame {
    private String jenisLaporan;

    /**
     * Creates new form Admin
     */
    public Laporan() {
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void generateLaporanPembelian(Date dariTanggal, Date sampaiTanggal) throws Exception {
        Connection conn = DatabaseConnection.getConnection();

        String reportPath = "D:\\Personal\\ITBSS\\SEMESTER 4\\PBO 2\\pos_system\\src\\View\\Report\\PembelianReport.jrxml";
        JasperDesign jd = JRXmlLoader.load(reportPath);

        String sql = "SELECT pembelian.pembelian_id, pembelian.tanggal_beli, pembelian.barang_id, barang.nama_barang, barang.kode_barang, "
                   + "pembelian.qty, pembelian.harga_beli, pembelian.harga_jual, pembelian.profit, "
                   + "(pembelian.qty * pembelian.harga_beli) AS harga_pokok FROM pembelian "
                   + "LEFT JOIN barang ON pembelian.barang_id = barang.barang_id "
                   + "LEFT JOIN detailbarang ON barang.barang_id = detailbarang.barang_id "
                   + "WHERE pembelian.tanggal_beli BETWEEN $P{DARI_TANGGAL} AND $P{SAMPAI_TANGGAL}";

        JRDesignQuery newQuery = new JRDesignQuery();
        newQuery.setText(sql);
        jd.setQuery(newQuery);
        
        // Set Bahasa ke Java (untuk menghindari error GroovyScript di Java 17+)
        jd.setLanguage("java");

        JasperReport jr = JasperCompileManager.compileReport(jd);

        // Siapkan parameter
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("DARI_TANGGAL", new java.sql.Date(dariTanggal.getTime()));
        parameters.put("SAMPAI_TANGGAL", new java.sql.Date(sampaiTanggal.getTime()));

        try {
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengisi laporan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void generateLaporanPenjualan(Date dariTanggal, Date sampaiTanggal) throws Exception {
        Connection conn = DatabaseConnection.getConnection();

        String reportPath = "D:\\Personal\\ITBSS\\SEMESTER 4\\PBO 2\\pos_system\\src\\View\\Report\\PenjualanReport.jrxml";
        JasperDesign jd = JRXmlLoader.load(reportPath);

        String sql = "SELECT penjualan.penjualan_id, penjualan.tanggal_jual, barang.kode_barang, barang.nama_barang, penjualan.qty, "
                   + "penjualan.harga_jual, (penjualan.qty * penjualan.harga_jual) AS total, penjualan.diskon, "
                   + "penjualan.total AS grand_total, penjualan.bayar, penjualan.kembalian FROM penjualan "
                   + "LEFT JOIN barang ON penjualan.barang_id = barang.barang_id "
                   + "LEFT JOIN detailbarang ON barang.barang_id = detailbarang.barang_id "
                   + "WHERE penjualan.tanggal_jual BETWEEN $P{DARI_TANGGAL} AND $P{SAMPAI_TANGGAL}";

        JRDesignQuery newQuery = new JRDesignQuery();
        newQuery.setText(sql);
        jd.setQuery(newQuery);
        
        // Set Bahasa ke Java (untuk menghindari error GroovyScript di Java 17+)
        jd.setLanguage("java");

        JasperReport jr = JasperCompileManager.compileReport(jd);

        // Siapkan parameter
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("DARI_TANGGAL", new java.sql.Date(dariTanggal.getTime()));
        parameters.put("SAMPAI_TANGGAL", new java.sql.Date(sampaiTanggal.getTime()));

        try {
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengisi laporan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void generateLaporanPemutihan(Date dariTanggal, Date sampaiTanggal) throws Exception {
        Connection conn = DatabaseConnection.getConnection();

        String reportPath = "D:\\Personal\\ITBSS\\SEMESTER 4\\PBO 2\\pos_system\\src\\View\\Report\\PemutihanReport.jrxml";
        JasperDesign jd = JRXmlLoader.load(reportPath);

        String sql = "SELECT pemutihan.pemutihan_id, pemutihan.tanggal_pemutihan, barang.nama_barang, barang.kode_barang, "
                   + "pemutihan.qty, pemutihan.keterangan, pemutihan.harga_beli, pemutihan.harga_jual, "
                   + "(pemutihan.qty * pemutihan.harga_beli) AS total_HPP, "
                   + "(pemutihan.qty * pemutihan.harga_jual)  AS total_harga_jual FROM pemutihan "
                   + "LEFT JOIN barang ON pemutihan.barang_id = barang.barang_id "
                   + "LEFT JOIN detailbarang ON barang.barang_id = detailbarang.barang_id "
                   + "WHERE pemutihan.tanggal_pemutihan BETWEEN $P{DARI_TANGGAL} AND $P{SAMPAI_TANGGAL} "
                   + "GROUP BY pemutihan.pemutihan_id, barang.nama_barang, barang.kode_barang";

        JRDesignQuery newQuery = new JRDesignQuery();
        newQuery.setText(sql);
        jd.setQuery(newQuery);
        
        // Set Bahasa ke Java (untuk menghindari error GroovyScript di Java 17+)
        jd.setLanguage("java");

        JasperReport jr = JasperCompileManager.compileReport(jd);

        // Siapkan parameter
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("DARI_TANGGAL", new java.sql.Date(dariTanggal.getTime()));
        parameters.put("SAMPAI_TANGGAL", new java.sql.Date(sampaiTanggal.getTime()));

        try {
            JasperPrint jp = JasperFillManager.fillReport(jr, parameters, conn);
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengisi laporan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void showTanggalDialog() {
        // Buat panel custom untuk input tanggal
        JPanel panel = new JPanel(new GridLayout(2, 2));

        JLabel lblDari = new JLabel("Dari Tanggal:");
        JLabel lblSampai = new JLabel("Sampai Tanggal:");

        JDateChooser dateFrom = new JDateChooser();
        dateFrom.setPreferredSize(new Dimension(150, 25));
        
        JDateChooser dateTo = new JDateChooser();
        dateTo.setPreferredSize(new Dimension(150, 25));

        panel.add(lblDari);
        panel.add(dateFrom);
        panel.add(lblSampai);
        panel.add(dateTo);

        // Tambah tombol OK & Cancel
        int option = JOptionPane.showConfirmDialog(this, panel, "Pilih Rentang Tanggal", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            Date dariTanggal = dateFrom.getDate();
            Date sampaiTanggal = dateTo.getDate();

            if (dariTanggal == null || sampaiTanggal == null) {
                JOptionPane.showMessageDialog(this, "Harap pilih kedua tanggal.");
                return;
            }

            if (dariTanggal.after(sampaiTanggal)) {
                JOptionPane.showMessageDialog(this, "Tanggal awal tidak boleh lebih besar dari tanggal akhir");
                return;
            }

            try {
                // Panggil method generate report
                switch (jenisLaporan) {
                    case "pembelian":
                        generateLaporanPembelian(dariTanggal, sampaiTanggal);
                        break;
                    case "penjualan":
                        generateLaporanPenjualan(dariTanggal, sampaiTanggal);
                        break;
                    case "pemutihan":
                        generateLaporanPemutihan(dariTanggal, sampaiTanggal);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Jenis laporan tidak diketahui");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal generate laporan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
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

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu7 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel13 = new javax.swing.JLabel();
        salesBtn5 = new javax.swing.JButton();
        kelolaBarangBtn = new javax.swing.JButton();
        pemutihanBtn = new javax.swing.JButton();
        kelolaPenggunaBtn = new javax.swing.JButton();
        logoutBtn = new javax.swing.JButton();
        laporanBtn = new javax.swing.JButton();
        btnStokBarangReport = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnPembelianReport = new javax.swing.JButton();
        btnPenjualanReport = new javax.swing.JButton();
        btnPemutihanReport = new javax.swing.JButton();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jMenu1.setText("jMenu1");

        jMenu2.setText("File");
        jMenuBar1.add(jMenu2);

        jMenu5.setText("Edit");
        jMenuBar1.add(jMenu5);

        jMenu8.setText("jMenu8");

        jMenu6.setText("jMenu6");

        jMenu7.setText("File");
        jMenuBar3.add(jMenu7);

        jMenu9.setText("Edit");
        jMenuBar3.add(jMenu9);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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

        logoutBtn.setText("Logout");
        logoutBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutBtnMouseClicked(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
                .addComponent(logoutBtn)
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
                            .addComponent(logoutBtn)
                            .addComponent(laporanBtn))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        btnStokBarangReport.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnStokBarangReport.setText("Cetak Stok Barang");
        btnStokBarangReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStokBarangReportMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setText("Laporan Real-Time");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel15.setText("Laporan Filtered by Date");

        btnPembelianReport.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPembelianReport.setText("Cetak Transaksi Pembelian");
        btnPembelianReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPembelianReportMouseClicked(evt);
            }
        });

        btnPenjualanReport.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPenjualanReport.setText("Cetak Transaksi Penjualan");
        btnPenjualanReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPenjualanReportMouseClicked(evt);
            }
        });

        btnPemutihanReport.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnPemutihanReport.setText("Cetak Transaksi Pemutihan");
        btnPemutihanReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPemutihanReportMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(btnStokBarangReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPembelianReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(btnPenjualanReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnPemutihanReport, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(421, 421, 421))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnStokBarangReport, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPembelianReport, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPenjualanReport, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPemutihanReport, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 421, Short.MAX_VALUE))
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
    
    private void salesBtn5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesBtn5MouseClicked
        Penjualan penjualanPage = new Penjualan();
        penjualanPage.setVisible(true);
        penjualanPage.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_salesBtn5MouseClicked

    private void logoutBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBtnMouseClicked
        JOptionPane.showMessageDialog(null, "Anda berhasil keluar.");
        LoginPanel login = new LoginPanel();
        login.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_logoutBtnMouseClicked

    private void kelolaBarangBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelolaBarangBtnMouseClicked
        KelolaBarang kelolaBarang = new KelolaBarang();
        kelolaBarang.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kelolaBarangBtnMouseClicked

    private void kelolaPenggunaBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelolaPenggunaBtnMouseClicked
        KelolaPengguna kelolaPengguna = new KelolaPengguna();
        kelolaPengguna.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kelolaPenggunaBtnMouseClicked

    private void pemutihanBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pemutihanBtnMouseClicked
        PemutihanBarang pemutihanPage = new PemutihanBarang();
        pemutihanPage.setVisible(true);
        pemutihanPage.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_pemutihanBtnMouseClicked

    private void laporanBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_laporanBtnMouseClicked
        Laporan laporanPage = new Laporan();
        laporanPage.setVisible(true);
        laporanPage.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_laporanBtnMouseClicked

    private void btnStokBarangReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStokBarangReportMouseClicked
        try {
            JasperDesign jd = JRXmlLoader.load("D:\\Personal\\ITBSS\\SEMESTER 4\\PBO 2\\pos_system\\src\\View\\Report\\StokBarangReport.jrxml");
            
            jd.setLanguage("java");
            
            String sql = "SELECT barang.barang_id, kode_barang, nama_barang, "
                       + "IFNULL(detailbarang.qty, 0) AS qty, "
                       + "IFNULL(detailbarang.harga_beli, 0) AS harga_beli, "
                       + "IFNULL(detailbarang.harga_jual, 0) AS harga_jual, "
                       + "IFNULL(detailbarang.profit, 0) AS profit "
                       + "FROM barang "
                       + "LEFT JOIN detailbarang ON barang.barang_id = detailbarang.barang_id "
                       + "ORDER BY kode_barang ASC";
            
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jd.setQuery(newQuery);
            
            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, DatabaseConnection.getConnection());
            JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan laporan: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnStokBarangReportMouseClicked

    private void btnPembelianReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPembelianReportMouseClicked
        jenisLaporan = "pembelian";
        showTanggalDialog();
    }//GEN-LAST:event_btnPembelianReportMouseClicked

    private void btnPenjualanReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPenjualanReportMouseClicked
        jenisLaporan = "penjualan";
        showTanggalDialog();
    }//GEN-LAST:event_btnPenjualanReportMouseClicked

    private void btnPemutihanReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPemutihanReportMouseClicked
        jenisLaporan = "pemutihan";
        showTanggalDialog();
    }//GEN-LAST:event_btnPemutihanReportMouseClicked
         
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
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Laporan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Laporan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPembelianReport;
    private javax.swing.JButton btnPemutihanReport;
    private javax.swing.JButton btnPenjualanReport;
    private javax.swing.JButton btnStokBarangReport;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JButton kelolaBarangBtn;
    private javax.swing.JButton kelolaPenggunaBtn;
    private javax.swing.JButton laporanBtn;
    private javax.swing.JButton logoutBtn;
    private javax.swing.JButton pemutihanBtn;
    private javax.swing.JButton salesBtn5;
    // End of variables declaration//GEN-END:variables
}
