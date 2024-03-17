public class Item {
    private String name;
    private double price;
    private double bulkPrice; // Price for bulk purchases
    private int bulkQuantity; // Quantity needed for bulk price

    // Constructor for non-bulk items
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
        this.bulkPrice = -1; // Indicates no bulk pricing
        this.bulkQuantity = -1;
    }

    // Constructor for bulk items
    public Item(String name, double price, int bulkQuantity, double bulkPrice) {
        this.name = name;
        this.price = price;
        this.bulkQuantity = bulkQuantity;
        this.bulkPrice = bulkPrice;
    }

    public String getName() {
        return name;
    }

    public double getPrice(int quantity) {
        if (bulkPrice > 0 && quantity >= bulkQuantity) {
            int bulkSets = quantity / bulkQuantity;
            int remainingQuantity = quantity % bulkQuantity;
            return bulkSets * bulkPrice + remainingQuantity * price;
        } else {
            return quantity * price;
        }
    }
}
