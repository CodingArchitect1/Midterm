
import java.util.*;

public class Cart {

    private Map<Item, Integer> items;

    public Cart() {
        items = new LinkedHashMap<>();
    }

    public void addItem(Item item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public void clearCart() {
        items.clear();
    }

    public String getCartDetails() {
        if (items.isEmpty()) {
            return "🛒 Your cart is empty.";
        }
        StringBuilder sb = new StringBuilder();
        double total = 0.0;

        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            Item item = entry.getKey();
            int qty = entry.getValue();
            double subtotal = item.getPrice() * qty;
            sb.append(String.format("%s x%d - ₱%.2f%n", item.getName(), qty, subtotal));
            total += subtotal;
        }
        sb.append(String.format("\nTotal: ₱%.2f", total));
        return sb.toString();
    }
}
