package com.mycompany.kwitansi;

public class Terbilang {

    private static final String[] SATUAN = {"", "satu", "dua", "tiga", "empat", "lima",
        "enam", "tujuh", "delapan", "sembilan", "sepuluh", "sebelas"};

    public static String rupiah(long n) {
        if (n == 0) return "Nol Rupiah";
        if (n < 0) return "Minus " + rupiah(-n);
        return kapital(bilang(n)) + " Rupiah";
    }

    private static String bilang(long n) {
        if (n < 12) return SATUAN[(int) n];
        if (n < 20) return SATUAN[(int) (n - 10)] + " belas";
        if (n < 100) return SATUAN[(int) (n / 10)] + " puluh" + sisa(n % 10);
        if (n < 200) return "seratus" + sisa(n - 100);
        if (n < 1_000) return SATUAN[(int) (n / 100)] + " ratus" + sisa(n % 100);
        if (n < 2_000) return "seribu" + sisa(n - 1_000);
        if (n < 1_000_000) return bilang(n / 1_000) + " ribu" + sisa(n % 1_000);
        if (n < 1_000_000_000L) return bilang(n / 1_000_000) + " juta" + sisa(n % 1_000_000);
        if (n < 1_000_000_000_000L) return bilang(n / 1_000_000_000L) + " miliar" + sisa(n % 1_000_000_000L);
        return bilang(n / 1_000_000_000_000L) + " triliun" + sisa(n % 1_000_000_000_000L);
    }

    private static String sisa(long r) {
        return r == 0 ? "" : " " + bilang(r);
    }

    private static String kapital(String s) {
        StringBuilder sb = new StringBuilder();
        for (String w : s.split(" ")) {
            if (sb.length() > 0) sb.append(' ');
            sb.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1));
        }
        return sb.toString();
    }
}