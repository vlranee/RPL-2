package com.mycompany.signup;

import com.mycompany.login.LoginForm;   

public class Main {
    private static JFrameHolder holder = new JFrameHolder();

    private static class JFrameHolder {
        javax.swing.JFrame frame;
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            holder.frame = new javax.swing.JFrame("Aplikasi");
            holder.frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            showSignUp();
            holder.frame.setVisible(true);
        });
    }

    public static void showSignUp() {
        switchTo(new SignUpForm(), "Sign Up");
    }

    public static void showLogin() {
        switchTo(new LoginForm(), "Login");
    }

    private static void switchTo(javax.swing.JPanel panel, String title) {
        holder.frame.setTitle(title);
        holder.frame.setContentPane(panel);
        holder.frame.pack();
        holder.frame.setLocationRelativeTo(null);
        holder.frame.revalidate();
        holder.frame.repaint();
    }
}
