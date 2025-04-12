package admin;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import com.google.gson.*;

public class AdminClient {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void sendUpdateCommand(String jsonFilePath) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            String response = sendRequest("U", json);
            System.out.println("Update response: " + response);
        } catch (IOException e) {
            System.out.println("Failed to read JSON file: " + e.getMessage());
        }
    }

    public static void sendRetrieveCommand() {
        String response = sendRequest("R", null);
        if (response == null || response.trim().isEmpty()) {
            System.out.println("Empty response from server. Nothing to display.");
            return;
        }

        System.out.println("Raw server response:\n" + response);  // Log what we got

        try {
            Gson gson = new Gson();
            ArrayList<?> list = gson.fromJson(response, ArrayList.class);
            System.out.println("\n-- Store Inventory --");
            for (Object item : list) {
                System.out.println(item.toString());
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Failed to parse JSON: " + e.getMessage());
        }
    }

    private static String sendRequest(String command, String jsonData) {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println(command);  // Send command

            if (jsonData != null) {
                out.println(jsonData);  // Send JSON if available
            }

            return in.readLine(); // Read one line of response
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
        return null;
    }
}
