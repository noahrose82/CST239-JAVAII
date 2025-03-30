package app;

import java.util.ArrayList;
import java.util.Scanner;

public class StoreFront {
    private InventoryManager inventoryManager = new InventoryManager();
    private ShoppingCart shoppingCart = new ShoppingCart();

    // Constructor to populate initial inventory from JSON
    public StoreFront(String jsonFilePath) {
        initializeInventory(jsonFilePath);
    }

    // Method to populate the store with initial products
    private void initializeInventory(String jsonFilePath) {
        try {
            ArrayList<SalableProduct> inventory = FileService.loadInventory(jsonFilePath);
            for (SalableProduct product : inventory) {
                inventoryManager.addProduct(product);
            }
        } catch (CustomFileException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }

    // Method to purchase a product
    public void purchase(SalableProduct product) {
        if (product.getQuantity() > 0) {
            shoppingCart.addToCart(product);
            product.setQuantity(product.getQuantity() - 1);
            System.out.println(product.getName() + " has been added to your cart.");
        } else {
            System.out.println("Product out of stock.");
        }
    }

    // Method to cancel a purchase
    public void cancelPurchase(SalableProduct product) {
        if (shoppingCart.getTotal() > 0) {
            shoppingCart.removeFromCart(product);
            product.setQuantity(product.getQuantity() + 1);
            System.out.println(product.getName() + " has been removed from your cart.");
        } else {
            System.out.println("Cart is empty.");
        }
    }

    // Show the total cart cost
    public void showCartTotal() {
        System.out.println("Total: $" + shoppingCart.getTotal());
    }

    // Show the contents of the cart
    public void showCartContents() {
        shoppingCart.displayCart();
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nAvailable Products:");
        for (SalableProduct product : inventoryManager.getInventory()) {
            System.out.println(product.getName() + " - $" + product.getPrice() + " [" + product.getQuantity() + " available]");
        }
    }

    // Handle user interaction
    public void runStore() {
        System.out.println("\nWelcome to the Game Store!");
        boolean running = true;
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Display Inventory");
            System.out.println("2. Purchase Product");
            System.out.println("3. Cancel Purchase");
            System.out.println("4. Show Cart");
            System.out.println("5. Show Total");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayInventory();
                    break;
                case 2:
                    System.out.print("Enter product name to purchase: ");
                    String productName = scanner.nextLine();
                    SalableProduct productToBuy = findProduct(productName);
                    if (productToBuy != null) {
                        purchase(productToBuy);
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter product name to cancel purchase: ");
                    String productToCancel = scanner.nextLine();
                    SalableProduct productToRemove = findProduct(productToCancel);
                    if (productToRemove != null) {
                        cancelPurchase(productToRemove);
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 4:
                    showCartContents();
                    break;
                case 5:
                    showCartTotal();
                    break;
                case 6:
                    System.out.println("Thank you for visiting the store!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    // Helper method to find a product by name
    private SalableProduct findProduct(String name) {
        for (SalableProduct product : inventoryManager.getInventory()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}
