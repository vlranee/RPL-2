package com.mycompany.kwitansi;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("StaRent Camp - Kwitansi");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new KwitansiPanel());
            f.setSize(900, 560);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}