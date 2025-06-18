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
public class Pembelian {
    private int pembelian_id;
    private Date tanggal_beli;
    private Barang barang;
    private int qty;
    private double harga_beli;
    private double harga_jual;
    private double profit;
    private String keterangan;
    
    public Pembelian(Date tanggal_beli, Barang barang, int qty, double harga_beli,
            double harga_jual, double profit, String keterangan) {
        this.tanggal_beli = tanggal_beli;
        this.barang = barang;
        this.qty = qty;
        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
        this.profit = profit;
        this.keterangan = keterangan;
    }
    
    public Pembelian(int pembelian_id, Date tanggal_beli, Barang barang, int qty, double harga_beli,
            double harga_jual, double profit, String keterangan) {
        this.pembelian_id = pembelian_id;
        this.tanggal_beli = tanggal_beli;
        this.barang = barang;
        this.qty = qty;
        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
        this.profit = profit;
        this.keterangan = keterangan;
    }
    
    public int getPembelianId() {
        return pembelian_id;
    }
    
    public Date getTanggalBeli() {
        return tanggal_beli;
    }
    
    public void setTanggalBeli(Date tanggal_beli) {
        this.tanggal_beli = tanggal_beli;
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
    
    public String getKeterangan() {
        return keterangan;
    }
    
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
