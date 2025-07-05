/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Etc.DatabaseConnection;
import Model.DetailBarang;
import Model.Penjualan;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ROG
 */
public class PenjualanService {
    private Connection conn = DatabaseConnection.getConnection();
    private DetailBarangDAO detailBarangDAO;
    private PenjualanDAO penjualanDAO;
    
    public PenjualanService(DetailBarangDAO detailBarangDAO, PenjualanDAO penjualanDAO) {
        this.detailBarangDAO = detailBarangDAO;
        this.penjualanDAO = penjualanDAO;
    }
    
    public void processPenjualan(Penjualan penjualan, int qtyStok) {
        try {
            double harga_beli = detailBarangDAO.getHargaBeliByBarangIdAndQtyStok(
                penjualan.getBarang().getBarangId(),
                qtyStok
            );

            DetailBarang detailBarang = detailBarangDAO.getByBarangIdAndHargaBeli(
                penjualan.getBarang().getBarangId(),
                harga_beli
            );

            if (detailBarang == null) {
                JOptionPane.showMessageDialog(null, "Detail barang tidak ditemukan.");
                return;
            }
            
            // Hitung ulang qty dan profit
            int qtyNow = detailBarang.getQty() - penjualan.getQty();
            double profitNow = (penjualan.getHargaJual() - detailBarang.getHargaBeli()) * qtyNow;

            // Update detail barang
            detailBarangDAO.updateQtyAndProfit(detailBarang.getDetailBarangId(), qtyNow, profitNow);

            // Simpan transaksi penjualan
            penjualanDAO.insert(penjualan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
