package com.mycompany.starent;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class LoginPanel extends JPanel {

    private final JTextField txtUser = new JTextField(18);
    private final JPasswordField txtPass = new JPasswordField(18);

    public LoginPanel() {
        setLayout(new GridBagLayout());
        setBackground(Theme.BG);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Theme.CARD);
        card.setBorder(BorderFactory.createEmptyBorder(32, 40, 32, 40));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 0, 6, 0);
        c.gridx = 0; c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("StaRent Camp");
        title.setFont(Theme.TITLE);
        title.setForeground(Theme.SIDEBAR);
        c.gridy = 0; card.add(title, c);

        JLabel sub = new JLabel("Masuk sebagai admin");
        sub.setFont(Theme.BODY);
        sub.setForeground(Theme.MUTED);
        c.gridy = 1; c.insets = new Insets(0, 0, 18, 0); card.add(sub, c);

        c.insets = new Insets(4, 0, 2, 0);
        c.gridy = 2; card.add(label("Username"), c);
        c.gridy = 3; c.insets = new Insets(0, 0, 10, 0); card.add(txtUser, c);
        c.gridy = 4; c.insets = new Insets(4, 0, 2, 0); card.add(label("Password"), c);
        c.gridy = 5; c.insets = new Insets(0, 0, 18, 0); card.add(txtPass, c);

        JButton btn = new JButton("Masuk");
        btn.setBackground(Theme.ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFont(Theme.BOLD);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(100, 36));
        btn.addActionListener(e -> doLogin());
        c.gridy = 6; card.add(btn, c);

        txtPass.addActionListener(e -> doLogin()); // Enter = login
        add(card);
    }

    private JLabel label(String t) {
        JLabel l = new JLabel(t);
        l.setFont(Theme.BODY);
        l.setForeground(Theme.TEXT);
        return l;
    }

    private void doLogin() {
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi!");
            return;
        }

        try (Connection conn = DB.get();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT password FROM admin WHERE username = ?")) {
            ps.setString(1, user);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hash = rs.getString("password");
                    if (BCrypt.verifyer().verify(pass.toCharArray(), hash).verified) {
                        Main.showDashboard();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Username atau password salah!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Koneksi database gagal: " + e.getMessage());
        }
    }
}