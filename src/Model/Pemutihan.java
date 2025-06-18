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
public class Pemutihan {
    private int pemutihan_id;
    private Date tanggal_pemutihan;
    private Barang barang;
    private int qty;
    private String keterangan;
    private boolean confirm;
    private double harga_beli;
    private double harga_jual;
    
    public Pemutihan(Date tanggal_pemutihan, Barang barang, int qty, String keterangan, 
            boolean confirm, double harga_beli, double harga_jual) {
        this.tanggal_pemutihan = tanggal_pemutihan;
        this.barang = barang;
        this.qty = qty;
        this.keterangan = keterangan;
        this.confirm = confirm;
        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
    }
    
    public Pemutihan(int pemutihan_id, Date tanggal_pemutihan, Barang barang, int qty, String keterangan, 
            boolean confirm, double harga_beli, double harga_jual) {
        this.pemutihan_id = pemutihan_id;
        this.tanggal_pemutihan = tanggal_pemutihan;
        this.barang = barang;
        this.qty = qty;
        this.keterangan = keterangan;
        this.confirm = confirm;
        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
    }
    
    public int getPemutihanId() {
        return pemutihan_id;
    }
    
    public Date getTanggalPemutihan() {
        return tanggal_pemutihan;
    }
    
    public void setTanggalPemutihan(Date tanggal_pemutihan) {
        this.tanggal_pemutihan = tanggal_pemutihan;
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
    
    public String getKeterangan() {
        return keterangan;
    }
    
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    public boolean getConfirm() {
        return confirm;
    }
    
    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
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
}
