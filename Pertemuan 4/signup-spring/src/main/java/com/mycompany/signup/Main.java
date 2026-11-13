package com.mycompany.signup;

import com.mycompany.signup.config.AppConfig;
import com.mycompany.signup.dao.UserDao;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private static JFrame frame;
    private static AnnotationConfigApplicationContext ctx;

    public static void main(String[] args) {
        ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Aplikasi");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            showSignUp();
            frame.setVisible(true);
        });
    }

    public static UserDao getUserDao() {
        return ctx.getBean(UserDao.class);
    }

    public static void showSignUp() {
        switchTo(new SignUpForm(), "Sign Up");
    }

    public static void showLogin() {
        switchTo(new LoginForm(), "Login");
    }

    private static void switchTo(JPanel panel, String title) {
        frame.setTitle(title);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }
}