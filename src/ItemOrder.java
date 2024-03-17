public class ItemOrder {
    private Item item;
    private int quantity;

    public ItemOrder(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public double getPrice() {
        return item.getPrice(quantity);
    }

    public int getQuantity() {
        return quantity;
    }
}
