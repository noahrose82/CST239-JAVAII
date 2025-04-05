package app;

import java.util.Scanner;

public class StoreFront {
    private InventoryManager inventoryManager;
    private ShoppingCart cart = new ShoppingCart();

    public StoreFront(String jsonFilePath) {
        inventoryManager = new InventoryManager();

        try {
            inventoryManager.getInventory().addAll(FileService.loadInventory(jsonFilePath));
        } catch (CustomFileException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
    }

    public void runStore() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n==== Store Menu ====");
            System.out.println("1. View and Sort Inventory");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    inventoryManager.displaySortMenu(scanner);
                    break;

                case "2":
                    inventoryManager.displayInventory();
                    System.out.print("Enter the name of the product to add to cart: ");
                    String productName = scanner.nextLine();
                    SalableProduct productToAdd = findProductByName(productName);
                    if (productToAdd != null && productToAdd.getQuantity() > 0) {
                        cart.addToCart(productToAdd);
                        productToAdd.setQuantity(productToAdd.getQuantity() - 1);
                        System.out.println("Added to cart.");
                    } else {
                        System.out.println("Product not found or out of stock.");
                    }
                    break;

                case "3":
                    System.out.println("\nYour Cart:");
                    cart.displayCart();
                    System.out.println("Total: $" + cart.getTotal());
                    break;

                case "4":
                    System.out.println("Checking out...");
                    cart.displayCart();
                    System.out.println("Total: $" + cart.getTotal());
                    running = false;
                    break;

                case "5":
                    System.out.println("Exiting the store.");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    private SalableProduct findProductByName(String name) {
        for (SalableProduct product : inventoryManager.getInventory()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}
