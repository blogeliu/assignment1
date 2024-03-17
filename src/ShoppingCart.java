import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<ItemOrder> orders;

    public ShoppingCart() {
        orders = new ArrayList<>();
    }

    public void add(ItemOrder order) {
        // Check if the item is already in the cart to replace it
        for (int i = 0; i < orders.size(); i++) {
            ItemOrder existingOrder = orders.get(i);
            if (existingOrder.getItem().getName().equals(order.getItem().getName())) {
                orders.set(i, order); // Replace existing order
                return;
            }
        }
        // Add new order if not found
        orders.add(order);
    }

    public void remove(String itemName) {
        orders.removeIf(order -> order.getItem().getName().equals(itemName));
    }

    public ItemOrder search(String itemName) {
        for (ItemOrder order : orders) {
            if (order.getItem().getName().equals(itemName)) {
                return order;
            }
        }
        return null;
    }

    public double getTotalPrice() {
        double total = 0;
        for (ItemOrder order : orders) {
            total += order.getPrice();
        }
        return total;
    }
}
