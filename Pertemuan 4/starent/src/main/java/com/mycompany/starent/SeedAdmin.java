package com.mycompany.starent;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class SeedAdmin {
    public static void main(String[] args) throws Exception {
        String hash = BCrypt.withDefaults().hashToString(12, "admin123".toCharArray());
        try (Connection c = DB.get();
             PreparedStatement ps = c.prepareStatement(
                 "INSERT INTO admin(username, password) VALUES (?, ?)")) {
            ps.setString(1, "admin");
            ps.setString(2, hash);
            ps.executeUpdate();
            System.out.println("Admin dibuat: admin / admin123");
        }
    }
}
