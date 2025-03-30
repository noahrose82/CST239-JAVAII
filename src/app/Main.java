package app;

public class Main {
    public static void main(String[] args) {
        // Replace with your JSON file path
        String jsonFilePath = "C:/Users/james/CST239 workspace/StoreFrontApp/inventory/inventory.json";

        // Initialize the store with the JSON file for inventory
        StoreFront store = new StoreFront(jsonFilePath);
        store.runStore(); // Start the store interaction
    }
}
