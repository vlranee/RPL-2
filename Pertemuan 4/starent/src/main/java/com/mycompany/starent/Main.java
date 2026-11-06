package com.mycompany.starent;

import javax.swing.*;

public class Main {
    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("StaRent Camp");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            showLogin();
            frame.setVisible(true);
        });
    }

    public static void showLogin() {
        switchTo(new LoginPanel(), 520, 480);
    }

    public static void showDashboard() {
        switchTo(new DashboardPanel(), 1100, 680);
    }

    private static void switchTo(JPanel panel, int w, int h) {
        frame.setContentPane(panel);
        frame.setSize(w, h);
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }
}
