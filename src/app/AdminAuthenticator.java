package app;

import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class AdminAuthenticator {
    private static final Map<String, String> adminCredentials = new HashMap<>();

    static {
        adminCredentials.put("admin", hashPassword("password123"));
        adminCredentials.put("noah", hashPassword("hunter2"));
        adminCredentials.put("doreen", hashPassword("s3cret"));
    }

    public static boolean authenticate(Scanner scanner) {
        System.out.println("\n🔐 Admin Login (Optional):");
        System.out.print("Enter username (or press Enter to continue as guest): ");
        String username = scanner.nextLine();

        if (username.isEmpty()) {
            return false;
        }

        String password;

        Console console = System.console();
        if (console != null) {
            // ✅ Hides password input
            char[] passwordChars = console.readPassword("Enter password: ");
            password = new String(passwordChars);
        } else {
            // ⚠️ Fallback: visible input if console is not available (e.g., in IDE)
            System.out.print("Enter password (input will be visible): ");
            password = scanner.nextLine();
        }

        String hashedInput = hashPassword(password);

        if (adminCredentials.containsKey(username) &&
            adminCredentials.get(username).equals(hashedInput)) {
            System.out.println("✅ Admin authenticated.");
            return true;
        } else {
            System.out.println("❌ Invalid username or password.");
            return false;
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found.");
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
