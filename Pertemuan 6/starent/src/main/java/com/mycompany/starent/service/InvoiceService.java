package com.mycompany.starent.service;

import com.mycompany.starent.Session;
import com.mycompany.starent.dao.PenyewaanDao;
import com.mycompany.starent.model.InvoiceItem;
import com.mycompany.starent.model.PenyewaanRow;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Menyusun parameter + daftar barang invoice dari data penyewaan di database. */
public class InvoiceService {

    public static final Locale ID = new Locale("id", "ID");
    private static final DateTimeFormatter TGL = DateTimeFormatter.ofPattern("dd MMMM yyyy", ID);

    private final PenyewaanDao dao = new PenyewaanDao();

    /** Hasil siap pakai untuk JasperReports. */
    public static class InvoiceData {
        public final Map<String, Object> params;
        public final List<InvoiceItem> items;
        public final String kode;

        InvoiceData(Map<String, Object> params, List<InvoiceItem> items, String kode) {
            this.params = params;
            this.items = items;
            this.kode = kode;
        }
    }

    public List<PenyewaanRow> daftarPenyewaan() throws SQLException {
        return dao.findAll();
    }

    public InvoiceData buatInvoice(int idPenyewaan) throws SQLException {
        PenyewaanRow p = dao.findById(idPenyewaan);
        if (p == null) throw new SQLException("Penyewaan id " + idPenyewaan + " tidak ditemukan");

        String durasi = p.lamaHari + " hari";
        List<InvoiceItem> items = new ArrayList<>();
        for (Object[] d : dao.findDetail(idPenyewaan)) {
            items.add(new InvoiceItem(
                (String) d[0], (String) d[1], (Integer) d[2],
                rupiah((Long) d[3]), durasi, rupiah((Long) d[4])));
        }

        Map<String, Object> m = new HashMap<>();
        m.put("KODE_TRANSAKSI", p.kode);
        m.put("NAMA_PELANGGAN", p.namaPelanggan);
        m.put("NO_HP", p.noTelepon);
        m.put("ALAMAT_PELANGGAN", p.alamat);
        m.put("JENIS_IDENTITAS", "KTP - " + p.noIdentitas);
        m.put("PERIODE_SEWA", p.tanggalSewa.format(TGL) + " s/d "
                + p.tanggalKembaliRencana.format(TGL));
        m.put("NAMA_ADMIN", Session.getAdminName());
        m.put("TOTAL_SEWA", rupiah(p.totalSewa));
        m.put("TOTAL_DEPOSIT", rupiah(p.totalDeposit));
        m.put("GRAND_TOTAL", rupiah(p.totalSewa + p.totalDeposit));
        return new InvoiceData(m, items, p.kode);
    }

    public static String rupiah(long n) {
        return "Rp" + NumberFormat.getIntegerInstance(ID).format(n);
    }

    public static String tanggal(LocalDate d) {
        return d.format(TGL);
    }
}
