/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Barang;
import Model.Pembelian;
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
public class PembelianDAO {
    private Connection conn;
    
    public PembelianDAO(Connection conn) {
        this.conn = conn;
    }
    
    public List<Pembelian> getAll() {
        List<Pembelian> pembelianList = new ArrayList<>();
        String sql = "SELECT pembelian_id, pembelian.tanggal_beli, pembelian.barang_id, barang.nama_barang, "
                   + "kode_barang, pembelian.qty, pembelian.harga_beli, "
                   + "pembelian.harga_jual, pembelian.profit, pembelian.keterangan FROM pembelian "
                   + "LEFT JOIN barang ON pembelian.barang_id = barang.barang_id "
                   + "LEFT JOIN detailbarang ON barang.barang_id = detailbarang.barang_id";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                int pembelian_id = rs.getInt("pembelian_id");
                Date tanggal_beli = rs.getDate("tanggal_beli");
                int barang_id = rs.getInt("barang_id");
                String nama_barang = rs.getString("nama_barang");
                String kode_barang = rs.getString("kode_barang");
                int qty = rs.getInt("qty");
                double harga_beli = rs.getDouble("harga_beli");
                double harga_jual = rs.getDouble("harga_jual");
                double profit = rs.getDouble("profit");
                String keterangan = rs.getString("keterangan");
                
                Barang brg = new Barang(barang_id, kode_barang, nama_barang);
                Pembelian pbl = new Pembelian(pembelian_id, tanggal_beli, brg, qty, harga_beli, 
                        harga_jual, profit, keterangan);
                
                pembelianList.add(pbl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pembelianList;
    }
    
    public void getById(int pembelian_id) {
        String sql = "SELECT pembelian_id, pembelian.tanggal_beli, pembelian.barang_id, barang.nama_barang, "
                   + "kode_barang, pembelian.qty, pembelian.harga_beli, "
                   + "pembelian.harga_jual, pembelian.profit, pembelian.keterangan FROM pembelian "
                   + "LEFT JOIN barang ON pembelian.barang_id = barang.barang_id "
                   + "LEFT JOIN detailbarang ON barang.barang_id = detailbarang.barang_id "
                   + "WHERE pembelian_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pembelian_id);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
                int pbl_id = rs.getInt("pembelian_id");
                Date tanggal_beli = rs.getDate("tanggal_beli");
                int barang_id = rs.getInt("barang_id");
                String nama_barang = rs.getString("nama_barang");
                String kode_barang = rs.getString("kode_barang");
                int qty = rs.getInt("qty");
                double harga_beli = rs.getDouble("harga_beli");
                double harga_jual = rs.getDouble("harga_jual");
                double profit = rs.getDouble("profit");
                String keterangan = rs.getString("keterangan");
                
                Barang brg = new Barang(barang_id, kode_barang, nama_barang);
                Pembelian pbl = new Pembelian(pbl_id, tanggal_beli, brg, qty, harga_beli, 
                        harga_jual, profit, keterangan);
            } else {
                throw new SQLException("Data pembelian tidak ditemukan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insert(Pembelian pbl) {
        String sql = "INSERT INTO pembelian (tanggal_beli, barang_id, qty, harga_beli,"
                + "harga_jual, profit, keterangan) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            // Konversi java.util.Date -> java.sql.Date
            java.util.Date utilDate = pbl.getTanggalBeli();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, sqlDate);
            pstmt.setInt(2, pbl.getBarang().getBarangId());
            pstmt.setInt(3, pbl.getQty());
            pstmt.setDouble(4, pbl.getHargaBeli());
            pstmt.setDouble(5, pbl.getHargaJual());
            pstmt.setDouble(6, pbl.getProfit());
            pstmt.setString(7, pbl.getKeterangan());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menambah pembelian, data pembelian tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Pembelian Berhasil Ditambahkan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(Pembelian pbl) {
        String sql = "UPDATE pembelian SET tanggal_beli = ?, barang_id = ?, qty = ?, harga_beli = ?,"
                + "harga_jual = ?, profit = ?, keterangan = ? WHERE pembelian_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, (java.sql.Date) pbl.getTanggalBeli());
            pstmt.setInt(2, pbl.getBarang().getBarangId());
            pstmt.setInt(3, pbl.getQty());
            pstmt.setDouble(4, pbl.getHargaBeli());
            pstmt.setDouble(5, pbl.getHargaJual());
            pstmt.setDouble(6, pbl.getProfit());
            pstmt.setString(7, pbl.getKeterangan());
            pstmt.setInt(8, pbl.getPembelianId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menyimpan pembelian, data user tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Pembelian Berhasil Disimpan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int pembelian_id) {
        String sql = "DELETE FROM pembelian WHERE pembelian_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pembelian_id);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menghapus pembelian, data pembelian tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Pembelian Berhasil Dihapus!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updatePembelian(int pembelian_id, Date tanggal_beli, String keterangan) {
        String sql = "UPDATE pembelian SET tanggal_beli = ?, keterangan = ? WHERE pembelian_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            java.sql.Date sqlDate = new java.sql.Date(tanggal_beli.getTime());
            pstmt.setDate(1, sqlDate);
            pstmt.setString(2, keterangan);
            pstmt.setInt(3, pembelian_id);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menyimpan pembelian, data pembelian tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Pembelian Berhasil Dihapus!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
