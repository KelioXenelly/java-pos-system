package Controller;


import Controller.DetailBarangDAO;
import Controller.PemutihanDAO;
import Model.Barang;
import Model.DetailBarang;
import Model.Pemutihan;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ROG
 */
public class PemutihanService {
    private DetailBarangDAO detailBarangDAO;
    private PemutihanDAO pemutihanDAO;
    
    public PemutihanService(DetailBarangDAO detailBarangDAO, PemutihanDAO pemutihanDAO) {
        this.detailBarangDAO = detailBarangDAO;
        this.pemutihanDAO = pemutihanDAO;
    }
    
    public void processPemutihan(Pemutihan pemutihan) throws Exception {
        Barang barang = pemutihan.getBarang();
        int barang_id = barang.getBarangId();
        double harga_beli = pemutihan.getHargaBeli();
        int qty = pemutihan.getQty();
        
        // Cari detail barang terkait
        DetailBarang detailBarang = detailBarangDAO.getByBarangIdAndHargaBeli(barang_id, harga_beli);
        
        if (detailBarang == null) {
            throw new Exception("Detail barang tidak ditemukan.");
        }

        // Hitung ulang qty dan profit
        int qtyNow = detailBarang.getQty() - qty;
        if (qtyNow < 0) {
            throw new Exception("Stok tidak mencukupi untuk diputihkan.");
        }

        double profitNow = (qtyNow * pemutihan.getHargaJual()) - (qtyNow * harga_beli);
        
        detailBarangDAO.updateQtyAndProfit(detailBarang.getDetailBarangId(), qtyNow, profitNow);
        
        pemutihanDAO.insert(pemutihan);
    }
}
