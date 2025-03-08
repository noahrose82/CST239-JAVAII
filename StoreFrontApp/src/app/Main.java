package app;

public class Main {
    public static void main(String[] args) {
        StoreFront store = new StoreFront();

        // Create products
        SalableProduct product1 = new SalableProduct("XBox", "High-end gaming console", 500.00, 10);
        SalableProduct product2 = new SalableProduct("Samsung S25 Ultra", "Best model smartphone", 1199.99, 5);

        // Add products to inventory
        store.purchase(product1);
        store.purchase(product2);

        // Display cart contents and total
        store.showCartContents();
        store.showCartTotal();

        // Cancel purchase of product
        store.cancelPurchase(product1);
        store.showCartContents();
        store.showCartTotal();
    }
}

