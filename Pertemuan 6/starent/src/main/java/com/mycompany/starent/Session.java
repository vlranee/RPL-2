package com.mycompany.starent;

/** Menyimpan admin yang sedang login (dipakai untuk "Nama Admin" di invoice). */
public class Session {
    private static String adminName = "-";

    public static String getAdminName() { return adminName; }
    public static void setAdminName(String name) { adminName = name; }
    public static void clear() { adminName = "-"; }
}
