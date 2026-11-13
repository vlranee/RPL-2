package com.mycompany.signup;

import com.mycompany.signup.dao.UserDao;
import java.awt.*;
import java.util.Optional;
import javax.swing.*;

public class LoginForm extends JPanel {

    private final JTextField txtUsername = new JTextField(15);
    private final JPasswordField txtPassword = new JPasswordField(15);
    private final JButton btnLogin = new JButton("Login");
    private final JButton btnToSignUp = new JButton("Belum punya akun? Sign Up");

    public LoginForm() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(400, 280));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);

        JLabel title = new JLabel("LOGIN");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        add(title, c);

        c.gridwidth = 1;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0; c.gridy = 1; add(new JLabel("Username"), c);
        c.gridy = 2; add(new JLabel("Password"), c);

        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1; c.gridy = 1; add(txtUsername, c);
        c.gridy = 2; add(txtPassword, c);

        c.gridx = 0; c.gridy = 3; c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        add(btnLogin, c);
        c.gridy = 4;
        add(btnToSignUp, c);

        btnLogin.addActionListener(e -> doLogin());
        txtPassword.addActionListener(e -> doLogin());
        btnToSignUp.addActionListener(e -> Main.showSignUp());
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password wajib diisi!");
            return;
        }

        try {
            UserDao dao = Main.getUserDao();
            Optional<User> user = dao.findByUsername(username);

            if (user.isPresent() && user.get().getPassword().equals(password)) {
                JOptionPane.showMessageDialog(this,
                        "Login berhasil! Selamat datang, " + user.get().getUsername());
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Login gagal: " + e.getMessage());
        }
    }
}