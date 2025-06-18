/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Etc.DatabaseConnection;
import Model.Barang;
import Model.Penjualan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane; 

/**
 *
 * @author ROG
 */
public class PenjualanDAO {
    private Connection conn = DatabaseConnection.getConnection();
    
    public PenjualanDAO(Connection conn) {
        this.conn = conn;
    }
    
    public List<Penjualan> getAll() {
        List<Penjualan> penjualanList = new ArrayList<>();
        String sql = "SELECT penjualan_id, penjualan.tanggal_jual, barang.barang_id, barang.nama_barang, "
                   + "kode_barang, penjualan.qty, penjualan.diskon, "
                   + "penjualan.total, penjualan.harga_jual, penjualan.bayar, "
                   + "penjualan.kembalian FROM penjualan "
                   + "LEFT JOIN barang ON penjualan.barang_id = barang.barang_id "
                   + "LEFT JOIN detailbarang ON barang.barang_id = detailbarang.barang_id";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                int penjualan_id = rs.getInt("penjualan_id");
                Date tanggal_jual = rs.getDate("tanggal_jual");
                int barang_id = rs.getInt("barang_id");
                String nama_barang = rs.getString("nama_barang");
                String kode_barang = rs.getString("kode_barang");
                int qty = rs.getInt("qty");
                double diskon = rs.getDouble("diskon");
                double total = rs.getDouble("total");
                double harga_jual = rs.getDouble("harga_jual");
                double bayar = rs.getDouble("bayar");
                double kembalian = rs.getDouble("kembalian");
                
                Barang brg = new Barang(barang_id, kode_barang, nama_barang);
                Penjualan pjl = new Penjualan(penjualan_id, tanggal_jual, brg, qty, diskon, total,
                    harga_jual, bayar, kembalian);
                
                penjualanList.add(pjl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return penjualanList;
    }
    
    public void getById(int penjualan_id) {
        String sql = "SELECT penjualan_id, penjualan.tanggal_jual, barang.barang_id, barang.nama_barang, "
                   + "kode_barang, penjualan.qty, penjualan.diskon, "
                   + "penjualan.total, penjualan.harga_jual, penjualan.bayar, "
                   + "penjualan.kembalian FROM penjualan "
                   + "LEFT JOIN barang ON penjualan.barang_id = barang.barang_id "
                   + "LEFT JOIN detailbarang ON barang.barang_id = detailbarang.barang_id "
                   + "WHERE penjualan_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, penjualan_id);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
                int pjl_id = rs.getInt("penjualan_id");
                Date tanggal_jual = rs.getDate("tanggal_jual");
                int barang_id = rs.getInt("barang_id");
                String nama_barang = rs.getString("nama_barang");
                String kode_barang = rs.getString("kode_barang");
                int qty = rs.getInt("qty");
                double diskon = rs.getDouble("diskon");
                double total = rs.getDouble("total");
                double harga_jual = rs.getDouble("harga_jual");
                double bayar = rs.getDouble("bayar");
                double kembalian = rs.getDouble("kembalian");
                
                Barang brg = new Barang(barang_id, kode_barang, nama_barang);
                Penjualan pjl = new Penjualan(pjl_id, tanggal_jual, brg, qty, diskon, total,
                    harga_jual, bayar, kembalian);
            } else {
                throw new SQLException("Data penjualan tidak ditemukan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insert(Penjualan pjl) {
        String sql = "INSERT INTO penjualan (tanggal_jual, barang_id, qty, diskon, total, "
                + "harga_jual, bayar, kembalian) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            java.util.Date utilDate = pjl.getTanggalJual(); // Dari model
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Konversi
            pstmt.setDate(1, sqlDate);
            pstmt.setInt(2, pjl.getBarang().getBarangId());
            pstmt.setInt(3, pjl.getQty());
            pstmt.setDouble(4, pjl.getDiskon());
            pstmt.setDouble(5, pjl.getTotal());
            pstmt.setDouble(6, pjl.getHargaJual());
            pstmt.setDouble(7, pjl.getBayar());
            pstmt.setDouble(8, pjl.getKembalian());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menambah penjualan, data penjualan tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Penjualan Berhasil Ditambahkan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(Penjualan pjl) {
        String sql = "UPDATE penjualan SET tanggal_jual = ?, barang_id = ?, qty = ?, diskon = ?,"
                + "total = ?, harga_jual = ?, bayar = ?, kembalian = ?"
                + "WHERE penjualan_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, (java.sql.Date) pjl.getTanggalJual());
            pstmt.setInt(2, pjl.getBarang().getBarangId());
            pstmt.setInt(3, pjl.getQty());
            pstmt.setDouble(4, pjl.getDiskon());
            pstmt.setDouble(5, pjl.getTotal());
            pstmt.setDouble(6, pjl.getHargaJual());
            pstmt.setDouble(7, pjl.getBayar());
            pstmt.setDouble(8, pjl.getKembalian());
            pstmt.setInt(9, pjl.getPenjualanId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menyimpan penjualan, data penjualan tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Penjualan Berhasil Disimpan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int penjualan_id) {
        String sql = "DELETE FROM penjualan WHERE penjualan_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, penjualan_id);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menghapus penjualan, data penjualan tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Penjualan Berhasil Dihapus!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
