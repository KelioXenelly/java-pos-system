/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Etc.DatabaseConnection;
import Model.Barang;
import Model.Pemutihan;
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
public class PemutihanDAO {
    private Connection conn;
    
    public PemutihanDAO(Connection conn) {
        this.conn = conn;
    }
    
    public List<Pemutihan> getAll() {
        List<Pemutihan> pemutihanList = new ArrayList<>();
        String sql = "SELECT pemutihan_id, tanggal_pemutihan, barang.barang_id, barang.nama_barang, "
                   + "kode_barang, pemutihan.qty, pemutihan.keterangan, confirm, pemutihan.harga_beli, "
                   + "pemutihan.harga_jual FROM pemutihan "
                   + "LEFT JOIN barang ON pemutihan.barang_id = barang.barang_id "
                   + "LEFT JOIN detailbarang ON barang.barang_id = detailbarang.barang_id";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                int pemutihan_id = rs.getInt("pemutihan_id");
                Date tanggal_pemutihan = rs.getDate("tanggal_pemutihan");
                int barang_id = rs.getInt("barang_id");
                String nama_barang = rs.getString("nama_barang");
                String kode_barang = rs.getString("kode_barang");
                int qty = rs.getInt("qty");
                String keterangan = rs.getString("keterangan");
                boolean confirm = rs.getBoolean("confirm");
                double harga_beli = rs.getDouble("harga_beli");
                double harga_jual = rs.getDouble("harga_jual");
                
                Barang brg = new Barang(barang_id, kode_barang, nama_barang);
                Pemutihan pth = new Pemutihan(pemutihan_id, tanggal_pemutihan, brg, qty, keterangan, 
                    confirm, harga_beli, harga_jual);
                
                pemutihanList.add(pth);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pemutihanList;
    }
    
    public void getById(int penjualan_id) {
        String sql = "SELECT pemutihan_id, tanggal_pemutihan, barang.barang_id, barang.nama_barang, "
                   + "kode_barang, pemutihan.qty, pemutihan.keterangan, confirm, pemutihan.harga_beli, "
                   + "pemutihan.harga_jual FROM pemutihan "
                   + "LEFT JOIN barang ON pemutihan.barang_id = barang.barang_id "
                   + "LEFT JOIN detailbarang ON barang.barang_id = detailbarang.barang_id "
                   + "WHERE penjualan_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, penjualan_id);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
                int pemutihan_id = rs.getInt("pemutihan_id");
                Date tanggal_pemutihan = rs.getDate("tanggal_pemutihan");
                int barang_id = rs.getInt("barang_id");
                String nama_barang = rs.getString("nama_barang");
                String kode_barang = rs.getString("kode_barang");
                int qty = rs.getInt("qty");
                String keterangan = rs.getString("keterangan");
                boolean confirm = rs.getBoolean("confirm");
                double harga_beli = rs.getDouble("harga_beli");
                double harga_jual = rs.getDouble("harga_jual");
                
                Barang brg = new Barang(barang_id, kode_barang, nama_barang);
                Pemutihan pth = new Pemutihan(pemutihan_id, tanggal_pemutihan, brg, qty, keterangan, 
                    confirm, harga_beli, harga_jual);
            } else {
                throw new SQLException("Data Pemutihan tidak ditemukan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insert(Pemutihan pth) {
        String sql = "INSERT INTO pemutihan (tanggal_pemutihan, barang_id, qty, keterangan, confirm,"
                + "harga_beli, harga_jual) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            java.util.Date utilDate = pth.getTanggalPemutihan(); // Dari model
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); // Konversi
            pstmt.setDate(1, sqlDate);
            pstmt.setInt(2, pth.getBarang().getBarangId());
            pstmt.setInt(3, pth.getQty());
            pstmt.setString(4, pth.getKeterangan());
            pstmt.setBoolean(5, pth.getConfirm());
            pstmt.setDouble(6, pth.getHargaBeli());
            pstmt.setDouble(7, pth.getHargaJual());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menambah pemutihan, data pemutihan tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Pemutihan Berhasil Ditambahkan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(Pemutihan pth) {
        String sql = "UPDATE pemutihan SET tanggal_pemutihan = ?, barang_id = ?, qty = ?, keterangan = ?,"
                + "confirm = ?, harga_beli = ?, harga_jual = ? WHERE pemutihan_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, (java.sql.Date) pth.getTanggalPemutihan());
            pstmt.setInt(2, pth.getBarang().getBarangId());
            pstmt.setInt(3, pth.getQty());
            pstmt.setString(4, pth.getKeterangan());
            pstmt.setBoolean(5, pth.getConfirm());
            pstmt.setDouble(6, pth.getHargaBeli());
            pstmt.setDouble(7, pth.getHargaJual());
            pstmt.setInt(8, pth.getPemutihanId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menyimpan pemutihan, data pemutihan tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Pemutihan Berhasil Disimpan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int pemutihan_id) {
        String sql = "DELETE FROM pemutihan WHERE pemutihan_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pemutihan_id);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menghapus pemutihan, data pemutihan tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Pemutihan Berhasil Dihapus!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
