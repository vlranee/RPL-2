package com.mycompany.starent;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class DashboardPanel extends JPanel {

    private final CardLayout cards = new CardLayout();
    private final JPanel content = new JPanel(cards);

    public DashboardPanel() {
        setLayout(new BorderLayout());

        // ===== Sidebar =====
        JPanel side = new JPanel();
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBackground(Theme.SIDEBAR);
        side.setPreferredSize(new Dimension(210, 0));
        side.setBorder(BorderFactory.createEmptyBorder(24, 0, 16, 0));

        JLabel brand = new JLabel("  StaRent Camp");
        brand.setFont(Theme.TITLE.deriveFont(18f));
        brand.setForeground(Color.WHITE);
        brand.setAlignmentX(LEFT_ALIGNMENT);
        brand.setBorder(BorderFactory.createEmptyBorder(0, 12, 20, 0));
        side.add(brand);

        String[] menu = {"Dashboard", "Penyewaan", "Pengembalian",
                         "Inventaris", "Pelanggan", "Laporan"};
        for (String m : menu) {
            side.add(menuButton(m, () -> cards.show(content, m)));
        }

        side.add(Box.createVerticalGlue());
        side.add(menuButton("Logout", () -> {
            int ok = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?",
                    "Logout", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) Main.showLogin();
        }));

        // ===== Konten =====
        content.setBackground(Theme.BG);
        for (String m : menu) content.add(placeholder(m), m);

        add(side, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);
    }

    private JButton menuButton(String text, Runnable action) {
        JButton b = new JButton("  " + text);
        b.setFont(Theme.BODY);
        b.setForeground(Color.WHITE);
        b.setBackground(Theme.SIDEBAR);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setAlignmentX(LEFT_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        b.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(Theme.SIDE_HOVER); }
            public void mouseExited(MouseEvent e)  { b.setBackground(Theme.SIDEBAR); }
        });
        b.addActionListener(e -> action.run());
        return b;
    }

    private JPanel placeholder(String name) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.BG);
        p.setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));
        JLabel l = new JLabel(name);
        l.setFont(Theme.TITLE);
        l.setForeground(Theme.SIDEBAR);
        p.add(l, BorderLayout.NORTH);
        JLabel soon = new JLabel("Halaman " + name + " (belum dibuat)");
        soon.setFont(Theme.BODY);
        soon.setForeground(Theme.MUTED);
        p.add(soon, BorderLayout.CENTER);
        return p;
    }
}