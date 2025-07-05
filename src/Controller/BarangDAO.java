/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Barang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ROG
 */
public class BarangDAO {
    private Connection conn;
    
    public BarangDAO(Connection conn) {
        this.conn = conn;
    }
    
    public List<Barang> getAll() {
        List<Barang> barangList = new ArrayList<>();
        String sql = "SELECT barang_id, kode_barang, nama_barang FROM barang "
                + "ORDER BY kode_barang ASC";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                int barang_id = rs.getInt("barang_id");
                String kode_barang = rs.getString("kode_barang");
                String nama_barang = rs.getString("nama_barang");
                
                Barang brg = new Barang(barang_id, kode_barang, nama_barang);
                
                barangList.add(brg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return barangList;
    }
    
    public Barang getByKdBarang(String kode_barang) {
        String sql = "SELECT barang_id, kode_barang, nama_barang FROM barang "
                   + "WHERE kode_barang = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, kode_barang);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
                int barang_id = rs.getInt("barang_id");
                String nama_barang = rs.getString("nama_barang");
                
                return new Barang(barang_id, kode_barang, nama_barang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Barang getByNamaBarang(String nama_barang) {
        String sql = "SELECT barang_id, kode_barang, nama_barang FROM barang "
                   + "WHERE nama_barang = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nama_barang);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
                int barang_id = rs.getInt("barang_id");
                String kode_barang = rs.getString("kode_barang");
                
                return new Barang(barang_id, kode_barang, nama_barang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void getById(int barang_id) {
        String sql = "SELECT barang_id, kode_barang, nama_barang, FROM barang "
                   + "WHERE barang_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, barang_id);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
                int brg_id = rs.getInt("barang_id");
                String kode_barang = rs.getString("kode_barang");
                String nama_barang = rs.getString("nama_barang");
                
                Barang brg = new Barang(brg_id, kode_barang, nama_barang);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insert(Barang brg) {
        String sql = "INSERT INTO barang (kode_barang, nama_barang) "
                   + "VALUES (?, ?)";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, brg.getKodeBarang());
            pstmt.setString(2, brg.getNamaBarang());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menambah barang, data barang tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Barang Berhasil Ditambahkan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(Barang brg) {
        String sql = "UPDATE barang SET kode_barang = ?, nama_barang = ? WHERE barang_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, brg.getKodeBarang());
            pstmt.setString(2, brg.getNamaBarang());
            pstmt.setInt(3, brg.getBarangId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menyimpan barang, data barang tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Barang Berhasil Disimpan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int barang_id) {
        String sql = "DELETE FROM barang WHERE barang_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, barang_id);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menghapus barang, data barang tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Barang Berhasil Dihapus!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isKodeBarangExist(String kode_barang) {
    boolean exists = false;
    try {
        String sql = "SELECT COUNT(*) FROM barang WHERE kode_barang = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, kode_barang);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            exists = rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return exists;
}
}
