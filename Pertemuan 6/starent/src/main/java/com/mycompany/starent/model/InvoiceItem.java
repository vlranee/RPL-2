package com.mycompany.starent.model;

/** Satu baris barang di tabel invoice. Getter dibaca JRBeanCollectionDataSource. */
public class InvoiceItem {
    private final String kode;
    private final String nama;
    private final int jumlah;
    private final String hargaPerHari; // sudah diformat, mis. "Rp50.000"
    private final String durasi;       // mis. "3 hari"
    private final String subtotal;     // sudah diformat

    public InvoiceItem(String kode, String nama, int jumlah,
                       String hargaPerHari, String durasi, String subtotal) {
        this.kode = kode;
        this.nama = nama;
        this.jumlah = jumlah;
        this.hargaPerHari = hargaPerHari;
        this.durasi = durasi;
        this.subtotal = subtotal;
    }

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public Integer getJumlah() { return jumlah; }
    public String getHargaPerHari() { return hargaPerHari; }
    public String getDurasi() { return durasi; }
    public String getSubtotal() { return subtotal; }
}
