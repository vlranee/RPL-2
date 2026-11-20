package com.mycompany.starent.view;

import com.mycompany.starent.Theme;
import com.mycompany.starent.model.PenyewaanRow;
import com.mycompany.starent.report.InvoicePrinter;
import com.mycompany.starent.service.InvoiceService;
import java.awt.*;
import java.io.File;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperPrint;

/** Halaman Penyewaan: daftar transaksi + cetak invoice. */
public class PenyewaanPanel extends JPanel {

    private final InvoiceService service = new InvoiceService();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Kode", "Pelanggan", "Tanggal Sewa", "Kembali (Rencana)",
                         "Total Sewa", "Deposit", "Status"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);

    public PenyewaanPanel() {
        setLayout(new BorderLayout(0, 14));
        setBackground(Theme.BG);
        setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));

        JLabel title = new JLabel("Penyewaan");
        title.setFont(Theme.TITLE);
        title.setForeground(Theme.SIDEBAR);
        add(title, BorderLayout.NORTH);

        table.setFont(Theme.BODY);
        table.setRowHeight(28);
        table.getTableHeader().setFont(Theme.BOLD);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.removeColumn(table.getColumnModel().getColumn(0)); // kolom ID disembunyikan
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        bar.setOpaque(false);
        bar.add(button("Preview Invoice", e -> cetak(false)));
        bar.add(button("Simpan PDF", e -> cetak(true)));
        bar.add(button("Refresh", e -> muat()));
        add(bar, BorderLayout.SOUTH);

        muat();
    }

    private JButton button(String text, java.awt.event.ActionListener a) {
        JButton b = new JButton(text);
        b.setBackground(Theme.ACCENT);
        b.setForeground(Color.WHITE);
        b.setFont(Theme.BOLD);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setPreferredSize(new Dimension(150, 36));
        b.addActionListener(a);
        return b;
    }

    private void muat() {
        model.setRowCount(0);
        try {
            List<PenyewaanRow> rows = service.daftarPenyewaan();
            for (PenyewaanRow r : rows) {
                model.addRow(new Object[]{
                    r.id, r.kode, r.namaPelanggan,
                    InvoiceService.tanggal(r.tanggalSewa),
                    InvoiceService.tanggal(r.tanggalKembaliRencana),
                    InvoiceService.rupiah(r.totalSewa),
                    InvoiceService.rupiah(r.totalDeposit), r.status});
            }
        } catch (Exception e) {
            error("Gagal memuat data penyewaan", e);
        }
    }

    private void cetak(boolean simpanPdf) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih satu transaksi dulu.");
            return;
        }
        int id = (Integer) model.getValueAt(table.convertRowIndexToModel(row), 0);
        try {
            InvoiceService.InvoiceData d = service.buatInvoice(id);
            JasperPrint print = InvoicePrinter.fill(d.params, d.items);
            if (!simpanPdf) {
                InvoicePrinter.preview(print);
                return;
            }
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new File("Invoice-" + d.kode + ".pdf"));
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                InvoicePrinter.exportPdf(print, fc.getSelectedFile().getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Invoice disimpan:\n" + fc.getSelectedFile());
            }
        } catch (Exception e) {
            error("Gagal membuat invoice", e);
        }
    }

    private void error(String msg, Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, msg + ":\n" + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
