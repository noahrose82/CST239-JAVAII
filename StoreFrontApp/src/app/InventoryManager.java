package app;

import java.util.ArrayList;

public class InventoryManager {
    private ArrayList<SalableProduct> inventory = new ArrayList<>();

    // Add a product to the inventory
    public void addProduct(SalableProduct product) {
        inventory.add(product);
    }

    // Remove a product from the inventory
    public void removeProduct(SalableProduct product) {
        inventory.remove(product);
    }

    // Get the inventory list
    public ArrayList<SalableProduct> getInventory() {
        return inventory;
    }
}
