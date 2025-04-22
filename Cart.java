
import java.util.*;

public class Cart {

    private Map<String, CartItem> items;

    public Cart() {
        items = new HashMap<>();
    }

    public void addItem(Item item, int quantity) {
        String key = item.getName();
        if (items.containsKey(key)) {
            items.get(key).addQuantity(quantity);
        } else {
            items.put(key, new CartItem(item, quantity));
        }
    }

    public void clearCart() {
        items.clear();
    }

    public double getTotal() {
        return items.values().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    public String getCartDetails() {
        StringBuilder sb = new StringBuilder();
        for (CartItem cartItem : items.values()) {
            sb.append(cartItem.toString()).append("\n");
        }
        sb.append("\nTotal: ₱").append(String.format("%.2f", getTotal()));
        return sb.toString();
    }
}
