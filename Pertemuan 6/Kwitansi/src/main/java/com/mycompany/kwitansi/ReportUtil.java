package com.mycompany.kwitansi;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

public class ReportUtil {

    private static JasperPrint isi(String namaFile, Map<String, Object> param) throws Exception {
        Map<String, Object> p = (param == null) ? new HashMap<>() : new HashMap<>(param);
        p.put("REPORT_LOCALE", Locale.forLanguageTag("id-ID")); // Rp 240.000, "20 November 2026"

        try (InputStream in = ReportUtil.class.getResourceAsStream("/reports/" + namaFile);
             Connection conn = DB.get()) {
            if (in == null) {
                throw new IllegalArgumentException("File report tidak ditemukan: " + namaFile);
            }
            JasperReport report = JasperCompileManager.compileReport(in);
            return JasperFillManager.fillReport(report, p, conn);
        }
    }

    public static void tampilkan(String namaFile, Map<String, Object> param, String judul) {
        try {
            JasperPrint print = isi(namaFile, param);
            JFrame f = new JFrame(judul);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // jangan EXIT_ON_CLOSE
            f.setContentPane(new JRViewer(print));
            f.setSize(1000, 700);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal membuat laporan:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void simpanPdf(String namaFile, Map<String, Object> param, File tujuan) {
        try {
            JasperPrint print = isi(namaFile, param);
            JasperExportManager.exportReportToPdfFile(print, tujuan.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "PDF tersimpan:\n" + tujuan.getAbsolutePath());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan PDF:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Map<String, Object> paramKwitansi(int idPembayaran) throws SQLException {
        long jumlah;
        try (Connection c = DB.get();
             PreparedStatement ps = c.prepareStatement(
                 "SELECT jumlah_bayar FROM pembayaran WHERE id_pembayaran = ?")) {
            ps.setInt(1, idPembayaran);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new SQLException("Pembayaran tidak ditemukan.");
                jumlah = rs.getLong(1);
            }
        }
        Map<String, Object> p = new HashMap<>();
        p.put("ID_PEMBAYARAN", idPembayaran);
        p.put("TERBILANG", Terbilang.rupiah(jumlah));
        p.put("PETUGAS", "Admin");
        return p;
    }

    public static void tampilkanKwitansi(int idPembayaran) {
        try {
            tampilkan("kwitansi.jrxml", paramKwitansi(idPembayaran), "Kwitansi Pembayaran");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal: " + e.getMessage());
        }
    }

    public static void simpanKwitansiPdf(int idPembayaran, File tujuan) {
        try {
            simpanPdf("kwitansi.jrxml", paramKwitansi(idPembayaran), tujuan);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal: " + e.getMessage());
        }
    }
}