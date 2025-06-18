/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author ROG
 */
public class DetailBarang {
    private int detail_barang_id;
    private Barang barang;
    private int qty;
    private double harga_beli;
    private double harga_jual;
    private double profit;
    // Dipakai untuk fetch data dari tabel pembelian
    private Date tanggal_beli;
    private String keterangan;
    
    public DetailBarang(Barang barang, int qty, double harga_beli, double harga_jual, double profit) {
        this.barang = barang;
        this.qty = qty;
        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
        this.profit = profit;
    }
    
    public DetailBarang(int detail_barang_id, Barang barang, int qty, double harga_beli, double harga_jual, double profit) {
        this.detail_barang_id = detail_barang_id;
        this.barang = barang;
        this.qty = qty;
        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
        this.profit = profit;
    }
    
    // Constructor dengan tanggal_beli dan keterangan (menyimpan data pembelian)
    public DetailBarang(int detail_barang_id, Barang barang, int qty, double harga_beli, double harga_jual, 
        double profit, Date tanggal_beli, String keterangan) {
        this.detail_barang_id = detail_barang_id;
        this.barang = barang;
        this.qty = qty;
        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
        this.profit = profit;
        this.tanggal_beli = tanggal_beli;
        this.keterangan = keterangan;
    }
    
    public int getDetailBarangId() {
        return detail_barang_id;
    }
    
    public Barang getBarang() {
        return barang;
    }
    
    public void setBarang(Barang barang) {
        this.barang = barang;
    }
    
    public int getQty() {
        return qty;
    }
    
    public void setQty(int qty) {
        this.qty = qty;
    }
    
    public double getHargaBeli() {
        return harga_beli;
    }
    
    public void setHargaBeli(double harga_beli) {
        this.harga_beli = harga_beli;
    }
     
    public double getHargaJual() {
        return harga_jual;
    }
    
    public void setHargaJual(double harga_jual) {
        this.harga_jual = harga_jual;
    }
    
    public double getProfit() {
        return profit;
    }
    
    public void setProfit(double profit) {
        this.profit = profit;
    }
    
    public Date getTanggalBeli() {
        return tanggal_beli;
    }
    
    public void setTanggalBeli(Date tanggal_beli) {
        this.tanggal_beli = tanggal_beli;
    }
    
    public String getKeterangan() {
        return keterangan;
    }
    
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
