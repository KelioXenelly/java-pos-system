package View.Admin;

import Controller.UsersDAO;
import Etc.DatabaseConnection;
import Model.Roles;
import Model.Users;
import View.LoginPanel;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class KelolaPengguna extends javax.swing.JFrame {
    private Connection conn = DatabaseConnection.getConnection();
    private DefaultTableModel model = null;
    private UsersDAO usersDAO;
    
    public KelolaPengguna() {
        initComponents();
        if (conn == null) {
        JOptionPane.showMessageDialog(null, "Gagal terhubung ke database.");
        } else {
            usersDAO = new UsersDAO(conn);
        }
        setLocationRelativeTo(null);
        loadData();
    }
    
   private void loadData() {
        DefaultTableModel model = (DefaultTableModel) tablePengguna.getModel();
        model.setRowCount(0);
        
        UsersDAO usersDAO = new UsersDAO(conn);
        List<Users> allUsers = usersDAO.getAll();
        
        for(Users user : allUsers) {
            
            model.addRow(new Object[] {
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getRoles().getRolesName(),
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePengguna = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        id_userTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        usernameTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        passwordTxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        roles_nameTxt = new javax.swing.JComboBox<>();
        tambahBtn = new javax.swing.JButton();
        ubahBtn = new javax.swing.JButton();
        hapusBtn = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel19 = new javax.swing.JLabel();
        salesBtn5 = new javax.swing.JButton();
        kelolaBarangBtn = new javax.swing.JButton();
        pemutihanBtn = new javax.swing.JButton();
        kelolaPenggunaBtn = new javax.swing.JButton();
        logoutBtn5 = new javax.swing.JButton();
        laporanBtn1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tablePengguna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID User", "Username", "Password", "Hak Akses"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePengguna.getTableHeader().setReorderingAllowed(false);
        tablePengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePenggunaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablePengguna);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Tabel Pengguna");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("ID User:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Akun Pengguna");

        id_userTxt.setEditable(false);
        id_userTxt.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        id_userTxt.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Username:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Password");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Hak Akses:");

        roles_nameTxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Kasir" }));

        tambahBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tambahBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Plus.png"))); // NOI18N
        tambahBtn.setText("Tambah");
        tambahBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahBtnActionPerformed(evt);
            }
        });

        ubahBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ubahBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        ubahBtn.setText("Ubah");
        ubahBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahBtnActionPerformed(evt);
            }
        });

        hapusBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        hapusBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Trash.png"))); // NOI18N
        hapusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id_userTxt)
                    .addComponent(passwordTxt)
                    .addComponent(roles_nameTxt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(tambahBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ubahBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hapusBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(usernameTxt)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(id_userTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roles_nameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hapusBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tambahBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ubahBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel10.setBackground(new java.awt.Color(102, 153, 255));

        jLabel17.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Selamat datang,");

        jLabel19.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Admin");

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

        laporanBtn1.setText("Laporan");
        laporanBtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                laporanBtn1MouseClicked(evt);
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
                .addComponent(kelolaPenggunaBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(laporanBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(kelolaPenggunaBtn)
                            .addComponent(logoutBtn5)
                            .addComponent(laporanBtn1))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    
    private void ubahBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubahBtnActionPerformed
        int user_id = Integer.parseInt(id_userTxt.getText());
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        String selectedRole = (String) roles_nameTxt.getSelectedItem();
        int roles_id = selectedRole.equals("Admin") ? 1 : 2;
        
        Roles role = new Roles(roles_id, selectedRole);
        Users user = new Users(user_id, username, password, role);
        
        usersDAO.update(user);
        loadData();
    }//GEN-LAST:event_ubahBtnActionPerformed

    private void hapusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusBtnActionPerformed
        int user_id = Integer.parseInt(id_userTxt.getText());
        usersDAO.delete(user_id);
        loadData();
    }//GEN-LAST:event_hapusBtnActionPerformed

    private void tambahBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahBtnActionPerformed
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        String selectedRole = (String) roles_nameTxt.getSelectedItem();
        int roles_id = selectedRole.equals("Admin") ? 1 : 2;
        
        Roles role = new Roles(roles_id, selectedRole);
        Users user = new Users(username, password, role);
        
        usersDAO.insert(user);
        loadData();
    }//GEN-LAST:event_tambahBtnActionPerformed

    private void tablePenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePenggunaMouseClicked
        model = (DefaultTableModel) tablePengguna.getModel();
        int selectedRow = tablePengguna.getSelectedRow();
        if (selectedRow != -1) {
            id_userTxt.setText(model.getValueAt(selectedRow, 0).toString());
            usernameTxt.setText(model.getValueAt(selectedRow, 1).toString());
            passwordTxt.setText(model.getValueAt(selectedRow, 2).toString());
            String role = model.getValueAt(selectedRow, 3).toString();
            roles_nameTxt.setSelectedItem(role);
        } else {
            JOptionPane.showMessageDialog(null, "Tidak ada baris yang dipilih.");
        }
    }//GEN-LAST:event_tablePenggunaMouseClicked

    private void salesBtn5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salesBtn5MouseClicked
        Penjualan penjualanPage = new Penjualan();
        penjualanPage.setVisible(true);
        penjualanPage.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_salesBtn5MouseClicked

    private void kelolaBarangBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelolaBarangBtnMouseClicked
        KelolaBarang kelolaBarang = new KelolaBarang();
        kelolaBarang.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kelolaBarangBtnMouseClicked

    private void pemutihanBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pemutihanBtnMouseClicked
        PemutihanBarang pemutihanPage = new PemutihanBarang();
        pemutihanPage.setVisible(true);
        pemutihanPage.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_pemutihanBtnMouseClicked

    private void kelolaPenggunaBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelolaPenggunaBtnMouseClicked
        KelolaPengguna kelolaPengguna = new KelolaPengguna();
        kelolaPengguna.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_kelolaPenggunaBtnMouseClicked

    private void logoutBtn5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutBtn5MouseClicked
        JOptionPane.showMessageDialog(null, "Anda berhasil keluar.");
        LoginPanel login = new LoginPanel();
        login.setVisible(true);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_logoutBtn5MouseClicked

    private void laporanBtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_laporanBtn1MouseClicked
        Laporan laporanPage = new Laporan();
        laporanPage.setVisible(true);
        laporanPage.setLocationRelativeTo(null);

        this.setVisible(false);
    }//GEN-LAST:event_laporanBtn1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KelolaPengguna().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton hapusBtn;
    private javax.swing.JTextField id_userTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton kelolaBarangBtn;
    private javax.swing.JButton kelolaPenggunaBtn;
    private javax.swing.JButton laporanBtn;
    private javax.swing.JButton laporanBtn1;
    private javax.swing.JButton logoutBtn5;
    private javax.swing.JTextField passwordTxt;
    private javax.swing.JButton pemutihanBtn;
    private javax.swing.JComboBox<String> roles_nameTxt;
    private javax.swing.JButton salesBtn5;
    private javax.swing.JTable tablePengguna;
    private javax.swing.JButton tambahBtn;
    private javax.swing.JButton ubahBtn;
    private javax.swing.JTextField usernameTxt;
    // End of variables declaration//GEN-END:variables
}
