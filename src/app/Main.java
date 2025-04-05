package app;

public class Main {
    public static void main(String[] args) {
        String jsonFilePath = "C:/Users/james/CST239 workspace/StoreFrontApp/inventory/inventory.json";

        StoreFront store = new StoreFront(jsonFilePath);
        store.runStore(); // Sorted inventory will be displayed
    }
}
