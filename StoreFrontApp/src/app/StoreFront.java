package app;

public class StoreFront {
    private InventoryManager inventoryManager = new InventoryManager();
    private ShoppingCart shoppingCart = new ShoppingCart();

    // Method to purchase a product
    public void purchase(SalableProduct product) {
        if (product.getQuantity() > 0) {
            shoppingCart.addToCart(product);
            product.setQuantity(product.getQuantity() - 1); // Decrease quantity after purchase
        } else {
            System.out.println("Product out of stock.");
        }
    }

    // Method to cancel purchase
    public void cancelPurchase(SalableProduct product) {
        shoppingCart.removeFromCart(product);
        product.setQuantity(product.getQuantity() + 1); // Restore quantity if canceled
    }

    // Show the total cart cost
    public void showCartTotal() {
        System.out.println("Total: $" + shoppingCart.getTotal());
    }

    public void showCartContents() {
        shoppingCart.displayCart();
    }

	public void cancelPurchase(SalableProduct product1, SalableProduct product2) {
		
		
	}

	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}

	public void setInventoryManager(InventoryManager inventoryManager) {
		this.inventoryManager = inventoryManager;
	}

	
}
