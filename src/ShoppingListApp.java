public class ShoppingListApp {
    public static void main(String[] args) {

        Item tissues = new Item("Tissues", 3.00);
        Item soap = new Item("Soap", 2.50, 2, 4.00); // Bulk: Buy 2 for $4
        Item toothpaste = new Item("Toothpaste", 4.00);


        ItemOrder order1 = new ItemOrder(tissues, 5); // 5 boxes of tissues
        ItemOrder order2 = new ItemOrder(soap, 5); // 5 soaps, with a bulk discount
        ItemOrder order3 = new ItemOrder(toothpaste, 2); // 2 toothpastes, no bulk discount


        ShoppingCart cart = new ShoppingCart();
        cart.add(order1);
        cart.add(order2);
        cart.add(order3);


        System.out.println("Total Price: $" + cart.getTotalPrice());
    }
}
