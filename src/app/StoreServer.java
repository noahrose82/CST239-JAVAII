package app;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StoreServer implements Runnable {
    private static final int PORT = 8080;  // Port to listen on
    private InventoryManager inventoryManager;

    public StoreServer(int port, InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("✅ StoreFront server started on port " + PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("👤 Client connected: " + clientSocket.getInetAddress());
                    handleClient(clientSocket);
                } catch (IOException e) {
                    System.out.println("❌ Error handling client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Server error: " + e.getMessage());
        }
    }

    private void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String command = in.readLine();

            if (command != null) {
                switch (command) {
                    case "U": {
                        String jsonData = in.readLine();
                        updateInventory(jsonData);
                        out.println("✅ Inventory updated successfully.");
                        break;
                    }
                    case "R": {
                        String inventoryJson = inventoryManagerToJson();
                        System.out.println("📤 Sending inventory JSON:\n" + inventoryJson);

                        if (inventoryJson == null || inventoryJson.trim().isEmpty()) {
                            out.println("[]");
                        } else {
                            out.println(inventoryJson);
                        }
                        break;
                    }
                    default:
                        out.println("⚠️ Invalid command.");
                }
            }

        } catch (IOException e) {
            System.out.println("❌ Error handling client: " + e.getMessage());
        }
    }

    private void updateInventory(String jsonData) {
        try {
            ArrayList<SalableProduct> updatedInventory = (ArrayList<SalableProduct>) FileService.loadInventory(jsonData);
            inventoryManager.getInventory().clear();
            inventoryManager.getInventory().addAll(updatedInventory);
        } catch (CustomFileException e) {
            System.out.println("❌ Error updating inventory: " + e.getMessage());
        }
    }

    private String inventoryManagerToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(inventoryManager.getInventory());
    }

    // ✅ Main method to run the server
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager(); // You should have this class in your project
        StoreServer server = new StoreServer(PORT, inventoryManager);
        server.run(); // Or use new Thread(server).start(); to run in a new thread
    }
}
