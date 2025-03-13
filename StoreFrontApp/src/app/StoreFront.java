package app;

public class StoreFront {
    private InventoryManager inventoryManager = new InventoryManager();
    private ShoppingCart shoppingCart = new ShoppingCart();

    // Constructor to populate initial inventory
    public StoreFront() {
        initializeInventory();
    }

    // Method to populate the store with initial products
    private void initializeInventory() {
        // Adding Weapons
        Weapon sword = new Weapon("Sword", "A sharp steel sword", 100.00, 5, 15);
        Weapon axe = new Weapon("Axe", "A heavy battle axe", 120.00, 3, 20);
        
        // Adding Armors
        Armor shield = new Armor("Shield", "A sturdy wooden shield", 80.00, 4, 10);
        Armor helmet = new Armor("Helmet", "A steel helmet for protection", 60.00, 6, 5);
        
        // Adding Health Item
        Health potion = new Health("Health Potion", "Restores health", 50.00, 10, 25);

        // Adding to inventory
        inventoryManager.addProduct(sword);
        inventoryManager.addProduct(axe);
        inventoryManager.addProduct(shield);
        inventoryManager.addProduct(helmet);
        inventoryManager.addProduct(potion);
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

    // Method to cancel purchase
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

    public void showCartContents() {
        shoppingCart.displayCart();
    }

    // Display inventory
    public void displayInventory() {
        System.out.println("\nAvailable Products:");
        for (SalableProduct product : inventoryManager.getInventory()) {
            System.out.println(product);
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
