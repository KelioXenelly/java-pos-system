/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Penjualan;

/**
 *
 * @author ROG
 */
public class ReceiptGenerator {
    public static String generateReceiptText(Penjualan penjualan) {
        StringBuilder receipt = new StringBuilder();
        
        double subtotal = penjualan.getHargaJual() * penjualan.getQty();
        double diskon = penjualan.getDiskon(); // Dalam Rp
        double grandTotal = penjualan.getTotal(); // Sudah terhitung diskon

        receipt.append("===== STRUK PENJUALAN =====\n");
        receipt.append("Tanggal: ").append(penjualan.getTanggalJual()).append("\n\n");

        receipt.append("Barang: ").append(penjualan.getBarang().getNamaBarang()).append("\n");
        receipt.append("Harga Jual: Rp").append(formatCurrency(penjualan.getHargaJual())).append("\n");
        receipt.append("Qty: ").append(penjualan.getQty()).append("\n");

        receipt.append("----------------------------\n");
        receipt.append("Subtotal: Rp").append(formatCurrency(subtotal)).append("\n");

        if (diskon > 0) {
            receipt.append("Diskon: -Rp").append(formatCurrency(diskon)).append("\n");
        }

        receipt.append("Grand Total: Rp").append(formatCurrency(grandTotal)).append("\n");
        receipt.append("Bayar: Rp").append(formatCurrency(penjualan.getBayar())).append("\n");
        receipt.append("Kembalian: Rp").append(formatCurrency(penjualan.getKembalian())).append("\n");
        receipt.append("----------------------------\n");
        receipt.append("Terima kasih atas pembelian!\n");
        receipt.append("Semoga harimu menyenangkan.");

        return receipt.toString();
    }
    
        // Helper untuk format angka jadi format mata uang
        private static String formatCurrency(double amount) {
            return String.format("%,.0f", amount);
        }
}
