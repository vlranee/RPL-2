package com.mycompany.starent.dao;

import com.mycompany.starent.DB;
import com.mycompany.starent.model.PenyewaanRow;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PenyewaanDao {

    private static final String SELECT_HEADER =
        "SELECT p.id_penyewaan, p.kode_penyewaan, p.tanggal_sewa, p.tanggal_kembali_rencana, "
      + "p.lama_hari, p.total_sewa, p.total_deposit, p.status, "
      + "c.nama, c.no_identitas, c.no_telepon, c.alamat "
      + "FROM penyewaan p JOIN pelanggan c ON c.id_pelanggan = p.id_pelanggan ";

    public List<PenyewaanRow> findAll() throws SQLException {
        List<PenyewaanRow> list = new ArrayList<>();
        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement(
                     SELECT_HEADER + "ORDER BY p.id_penyewaan DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public PenyewaanRow findById(int id) throws SQLException {
        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement(
                     SELECT_HEADER + "WHERE p.id_penyewaan = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    /** Baris detail: {kode_barang, nama_barang, jumlah, harga_per_hari, subtotal}. */
    public List<Object[]> findDetail(int idPenyewaan) throws SQLException {
        List<Object[]> list = new ArrayList<>();
        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT b.kode_barang, b.nama_barang, d.jumlah, d.harga_per_hari, d.subtotal "
               + "FROM detail_sewa d JOIN barang b ON b.id_barang = d.id_barang "
               + "WHERE d.id_penyewaan = ? ORDER BY d.id_detail")) {
            ps.setInt(1, idPenyewaan);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getString("kode_barang"), rs.getString("nama_barang"),
                        rs.getInt("jumlah"), rs.getLong("harga_per_hari"),
                        rs.getLong("subtotal")});
                }
            }
        }
        return list;
    }

    private PenyewaanRow map(ResultSet rs) throws SQLException {
        PenyewaanRow r = new PenyewaanRow();
        r.id = rs.getInt("id_penyewaan");
        r.kode = rs.getString("kode_penyewaan");
        r.tanggalSewa = rs.getDate("tanggal_sewa").toLocalDate();
        r.tanggalKembaliRencana = rs.getDate("tanggal_kembali_rencana").toLocalDate();
        r.lamaHari = rs.getInt("lama_hari");
        r.totalSewa = rs.getLong("total_sewa");
        r.totalDeposit = rs.getLong("total_deposit");
        r.status = rs.getString("status");
        r.namaPelanggan = rs.getString("nama");
        r.noIdentitas = rs.getString("no_identitas");
        r.noTelepon = rs.getString("no_telepon");
        r.alamat = rs.getString("alamat");
        return r;
    }
}
