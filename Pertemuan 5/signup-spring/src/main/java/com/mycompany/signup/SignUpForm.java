package com.mycompany.signup;

import com.mycompany.signup.dao.UserDao;
import java.awt.*;
import javax.swing.*;

public class SignUpForm extends JPanel {

    private final JTextField txtUsername = new JTextField(15);
    private final JPasswordField txtPassword = new JPasswordField(15);
    private final JPasswordField txtConfirm = new JPasswordField(15);
    private final JButton btnSignUp = new JButton("Sign Up");
    private final JButton btnToLogin = new JButton("Sudah punya akun? Login");

    public SignUpForm() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(400, 300));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);

        JLabel title = new JLabel("SIGN UP");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        add(title, c);

        c.gridwidth = 1;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0; c.gridy = 1; add(new JLabel("Username"), c);
        c.gridy = 2; add(new JLabel("Password"), c);
        c.gridy = 3; add(new JLabel("Konfirmasi"), c);

        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1; c.gridy = 1; add(txtUsername, c);
        c.gridy = 2; add(txtPassword, c);
        c.gridy = 3; add(txtConfirm, c);

        c.gridx = 0; c.gridy = 4; c.gridwidth = 2;
        c.anchor = GridBagConstraints.CENTER;
        add(btnSignUp, c);
        c.gridy = 5;
        add(btnToLogin, c);

        btnSignUp.addActionListener(e -> doSignUp());
        btnToLogin.addActionListener(e -> Main.showLogin());
    }

    private void doSignUp() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String confirm = new String(txtConfirm.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
            return;
        }
        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Password tidak sama!");
            return;
        }

        try {
            UserDao dao = Main.getUserDao();

            if (dao.existsByUsername(username)) {
                JOptionPane.showMessageDialog(this, "Username sudah dipakai!");
                return;
            }

            dao.save(new User(username, password));

            JOptionPane.showMessageDialog(this, "Sign up berhasil! Silakan login.");
            Main.showLogin();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sign up gagal: " + e.getMessage());
        }
    }
}