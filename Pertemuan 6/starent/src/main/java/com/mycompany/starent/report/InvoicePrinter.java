package com.mycompany.starent.report;

import com.mycompany.starent.model.InvoiceItem;
import com.mycompany.starent.service.InvoiceService;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.view.JasperViewer;

/** Memanggil report Invoice.jrxml (di resources/reports) dan menampilkan / menyimpan hasilnya. */
public class InvoicePrinter {

    private static final String JRXML = "/reports/Invoice.jrxml";
    private static final String[] LOGO_CANDIDATES = {"/reports/logo.png", "/reports/logo.jpg"};

    private static JasperReport compiled; // compile sekali saja

    public static JasperPrint fill(Map<String, Object> params, List<InvoiceItem> items)
            throws JRException {
        Map<String, Object> p = new HashMap<>(params);
        p.put(JRParameter.REPORT_LOCALE, InvoiceService.ID);
        p.put("LOGO", loadLogo());
        return JasperFillManager.fillReport(report(), p, new JRBeanCollectionDataSource(items));
    }

    public static void preview(JasperPrint print) {
        JasperViewer.viewReport(print, false); // false = tutup viewer tidak menutup aplikasi
    }

    public static void exportPdf(JasperPrint print, String path) throws JRException {
        JasperExportManager.exportReportToPdfFile(print, path);
    }

    private static synchronized JasperReport report() throws JRException {
        if (compiled == null) {
            try (InputStream in = InvoicePrinter.class.getResourceAsStream(JRXML)) {
                if (in == null) throw new JRException("File " + JRXML + " tidak ditemukan di resources");
                compiled = JasperCompileManager.compileReport(in);
            } catch (IOException e) {
                throw new JRException(e);
            }
        }
        return compiled;
    }

    /** Logo opsional: taruh logo.png / logo.jpg di src/main/resources/reports. */
    private static Image loadLogo() {
        for (String path : LOGO_CANDIDATES) {
            try (InputStream in = InvoicePrinter.class.getResourceAsStream(path)) {
                if (in != null) return ImageIO.read(in);
            } catch (IOException ignored) { }
        }
        return null;
    }
}
