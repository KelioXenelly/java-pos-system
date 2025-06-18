/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.DetailBarang;
import Model.Pembelian;
import java.sql.SQLException;

/**
 *
 * @author ROG
 */
public class PembelianService {
    private DetailBarangDAO detailBarangDAO;
    
    public PembelianService(DetailBarangDAO detailBarangDAO) {
        this.detailBarangDAO = detailBarangDAO;
    }
    
    public void processIncomingStock(Pembelian pbl) throws SQLException {
        // Cari apakah ada detail barang dengan harga beli sama
        DetailBarang detailBarang = detailBarangDAO.getByBarangIdAndHargaBeli(
            pbl.getBarang().getBarangId(),
            pbl.getHargaBeli()
        );
        
        if(detailBarang != null) {
            // Update qty saja
            int updatedQty = detailBarang.getQty() + pbl.getQty();
            detailBarangDAO.updateQty(detailBarang.getDetailBarangId(), updatedQty);
        } else {
            // Tidak ada -> tambahkan sebagai detailbarang baru
            DetailBarang newDetailBarang = new DetailBarang(pbl.getBarang(), pbl.getQty(),
                pbl.getHargaBeli(), pbl.getHargaJual(), pbl.getProfit());
            detailBarangDAO.insert(newDetailBarang);
        }
    }
}
