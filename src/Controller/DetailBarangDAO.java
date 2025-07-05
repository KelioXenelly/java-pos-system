/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Barang;
import Model.DetailBarang;
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
public class DetailBarangDAO {
    private Connection conn;
    
    public DetailBarangDAO(Connection conn) {
        this.conn = conn;
    }
    
    public List<DetailBarang> getAll() {
        List<DetailBarang> barangList = new ArrayList<>();
        String sql = "SELECT barang.barang_id, kode_barang, nama_barang, detailbarang.detail_barang_id, "
                   + "detailbarang.qty, detailbarang.harga_beli, detailbarang.harga_jual, detailbarang.profit, "
                   + "    (SELECT tanggal_beli FROM pembelian WHERE barang.barang_id = pembelian.barang_id "
                   + "     ORDER BY tanggal_beli DESC LIMIT 1) AS tanggal_beli, "
                   + "    (SELECT keterangan FROM pembelian WHERE barang.barang_id = pembelian.barang_id "
                   + "     ORDER BY tanggal_beli DESC LIMIT 1) AS keterangan "
                   + "FROM barang "
                   + "LEFT JOIN detailbarang ON barang.barang_id = detailbarang.barang_id "
                   + "WHERE detailbarang.active = 1 "
                   + "ORDER BY kode_barang ASC";
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while(rs.next()) {
                int barang_id = rs.getInt("barang_id");
                String kode_barang = rs.getString("kode_barang");
                String nama_barang = rs.getString("nama_barang");
                int qty = rs.getInt("qty");
                double hargaBeli = rs.getDouble("harga_beli");
                double hargaJual = rs.getDouble("harga_jual");
                double profit = rs.getDouble("profit");
                int detail_barang_id = rs.getInt("detail_barang_id");
                Date tanggal_beli = rs.getDate("tanggal_beli");
                String keterangan = rs.getString("keterangan");
                
                Barang brg = new Barang(barang_id, kode_barang, nama_barang);
                
                barangList.add(new DetailBarang(detail_barang_id, brg, qty, hargaBeli, hargaJual, 
                        profit, tanggal_beli, keterangan));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return barangList;
    }
    
    public void getById(int detail_barang_id) {
        String sql = "SELECT detail_barang_id, detailbarang.barang_id, barang.nama_barang, qty, "
                   + "harga_beli, harga_jual, profit, kodebarang.kode_barang"
                   + "FROM detailbarang "
                   + "LEFT JOIN barang ON detailbarang.barang_id = barang.barang_id "
                   + "WHERE detail_barang_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, detail_barang_id);
            ResultSet rs = pstmt.executeQuery();
            
            if(rs.next()) {
                int detail_brg_id = rs.getInt("detail_barang_id");
                int barang_id = rs.getInt("barang_id");
                String nama_barang = rs.getString("nama_barang");
                int qty = rs.getInt("qty");
                double harga_beli = rs.getDouble("harga_beli");
                double harga_jual = rs.getDouble("harga_jual");
                double profit = rs.getDouble("profit");
                String kode_barang = rs.getString("kode_barang");
                
                Barang brg = new Barang(barang_id, kode_barang, nama_barang);
                DetailBarang detailBrg = new DetailBarang(detail_brg_id, brg, qty, 
                        harga_beli, harga_jual, profit);
            } else {
                throw new SQLException("Data detail barang tidak ditemukan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void insert(DetailBarang detailBrg) {
        String sql = "INSERT INTO detailbarang (barang_id, qty, harga_beli, harga_jual, profit)"
                   + "VALUES (?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, detailBrg.getBarang().getBarangId());
            pstmt.setInt(2, detailBrg.getQty());
            pstmt.setDouble(3, detailBrg.getHargaBeli());
            pstmt.setDouble(4, detailBrg.getHargaJual());
            pstmt.setDouble(5, detailBrg.getProfit());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menambah detail barang, data detail barang tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Detail Barang Berhasil Ditambahkan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void update(DetailBarang detailBrg) {
        String sql = "UPDATE detailbarang SET barang_id = ?, qty = ?, harga_beli = ?, harga_jual = ?,"
                   + "profit = ? WHERE detail_barang_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, detailBrg.getBarang().getBarangId());
            pstmt.setInt(2, detailBrg.getQty());
            pstmt.setDouble(3, detailBrg.getHargaBeli());
            pstmt.setDouble(4, detailBrg.getHargaJual());
            pstmt.setDouble(5, detailBrg.getProfit());
            pstmt.setInt(6, detailBrg.getDetailBarangId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menyimpan detail barang, data detail barang tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Detail Barang Berhasil Disimpan!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int detail_barang_id) {
        String sql = "DELETE FROM detailbarang WHERE detail_barang_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, detail_barang_id);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menghapus detail barang, data detail barang tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Detail Barang Berhasil Dihapus!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void softDelete(int detail_barang_id) {
        String sql = "UPDATE detailbarang SET active = ? WHERE detail_barang_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setBoolean(1, false);
            pstmt.setInt(2, detail_barang_id);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Gagal menghapus detail barang, data detail barang tidak ditemukan.");
            } else {
                JOptionPane.showMessageDialog(null, "Data Detail Barang Berhasil Dihapus!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Cari detail barang berdasarkan barang_id dan harga_beli
    public DetailBarang getByBarangIdAndHargaBeli(int barang_id, double harga_beli) {
        String sql = "SELECT detail_barang_id, barang.barang_id, barang.kode_barang, barang.nama_barang, "
                   + "qty, detailbarang.harga_beli, harga_jual, profit FROM detailbarang "
                   + "LEFT JOIN barang ON detailbarang.barang_id = barang.barang_id "
                   + "WHERE barang.barang_id = ? AND detailbarang.harga_beli = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, barang_id);
            pstmt.setDouble(2, harga_beli);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int detail_barang_id = rs.getInt("detail_barang_id");
                    int brg_id = rs.getInt("barang_id");
                    String kode_barang = rs.getString("kode_barang");
                    String nama_barang = rs.getString("nama_barang");
                    int qty = rs.getInt("qty");
                    double hrg_beli = rs.getDouble("harga_beli");
                    double harga_jual = rs.getDouble("harga_jual");
                    double profit = rs.getDouble("profit");
                    
                    Barang brg = new Barang(brg_id, kode_barang, nama_barang);
                    DetailBarang detailBrg = new DetailBarang(detail_barang_id, brg, qty, hrg_beli, 
                            harga_jual, profit);
                    return detailBrg;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public double getHargaBeliByBarangIdAndQtyStok(int barang_id, int qtyStok) throws SQLException {
        String sql = "SELECT harga_beli FROM detailbarang WHERE barang_id = ? AND qty = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, barang_id);
            pstmt.setDouble(2, qtyStok);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("harga_beli");
                }
            }
        }
        throw new SQLException("Barang tidak ditemukan.");
    }
    
    public void updateHargaJual(int barang_id, double new_harga_jual) {
        String sql = "UPDATE detailbarang SET harga_jual = ? WHERE barang_id = ?";
        
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, new_harga_jual);
            pstmt.setInt(2, barang_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateQtyAndProfit(int detail_barang_id, int newQty, double newProfit) {
        String sql = "UPDATE detailbarang SET qty = ?, profit = ? WHERE detail_barang_id = ?";
        if (newQty < 0) {
            System.out.println("Qty tidak boleh negatif.");
            return;
        }
        if (newQty > Integer.MAX_VALUE) {
            System.out.println("Qty terlalu besar.");
            return;
        }
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newQty);
            pstmt.setDouble(2, newProfit);
            pstmt.setInt(3, detail_barang_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
