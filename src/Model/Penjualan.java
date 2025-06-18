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
public class Penjualan {
    private int penjualan_id;
    private Date tanggal_jual;
    private Barang barang;
    private int qty;
    private double diskon;
    private double total;
    private double harga_jual;
    private double bayar;
    private double kembalian;
    
    public Penjualan(Date tanggal_jual, Barang barang, int qty, double diskon, double total,
            double harga_jual, double bayar, double kembalian) {
        this.tanggal_jual = tanggal_jual;
        this.barang = barang;
        this.qty = qty;
        this.diskon = diskon;
        this.total = total;
        this.harga_jual = harga_jual;
        this.bayar = bayar;
        this.kembalian = kembalian;
    }
    
    public Penjualan(int penjualan_id, Date tanggal_jual, Barang barang, int qty, double diskon, double total,
            double harga_jual, double bayar, double kembalian) {
        this.penjualan_id = penjualan_id;
        this.tanggal_jual = tanggal_jual;
        this.barang = barang;
        this.qty = qty;
        this.diskon = diskon;
        this.total = total;
        this.harga_jual = harga_jual;
        this.bayar = bayar;
        this.kembalian = kembalian;
    }
    
    public int getPenjualanId() {
        return penjualan_id;
    }
    
    public Date getTanggalJual() {
        return tanggal_jual;
    }
    
    public void setTanggalJual(Date tanggal_jual) {
        this.tanggal_jual = tanggal_jual;
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
    
    public double getDiskon() {
        return diskon;
    }
    
    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }
    
    public double getTotal() {
        return total;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
    
        public double getHargaJual() {
        return harga_jual;
    }
    
    public void setHargaJual(double harga_jual) {
        this.harga_jual = harga_jual;
    }
    
    public double getBayar() {
        return bayar;
    }
    
    public void setBayar(double bayar) {
        this.bayar = bayar;
    }
    
    public double getKembalian() {
        return kembalian;
    }
    
    public void setKembalian(double kembalian) {
        this.kembalian = kembalian;
    }
}
