package com.mycompany.starent.model;

import java.time.LocalDate;

/** Data penyewaan + pelanggan (untuk daftar dan header invoice). */
public class PenyewaanRow {
    public int id;
    public String kode;
    public String namaPelanggan;
    public String noIdentitas;
    public String noTelepon;
    public String alamat;
    public LocalDate tanggalSewa;
    public LocalDate tanggalKembaliRencana;
    public int lamaHari;
    public long totalSewa;
    public long totalDeposit;
    public String status;
}
