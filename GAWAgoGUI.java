import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

public class GAWAgoGUI extends JFrame {
    private Cart cart;
    private JTextArea cartArea;
    public GAWAgoGUI() {
        cart = new Cart();
        setTitle("GAWAgo");
        setSize(1300, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel itemPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        Item[] storeItems = {
            new Item("Chipsy Cheesy Chips", 6.25, "images/Chipsy Cheesy Chips.png"),
            new Item("Grandbisco Peanut Butter Cream Filled Biscuit", 5.00, "images/Grandbisco Peanut Butter Cream Filled Biscuit.png"),
            new Item("Grandbisco Vanilla Filled Biscuits", 4.75, "images/Grandbisco Vanilla Filled Biscuits.png"),
            new Item("Healthy Cow Cheese Slices Toast", 69.00, "images/Healthy Cow Cheese Slices Toast.png"),
            new Item("malinee Gel Toothpaste", 39.00, "images/malinee Gel Toothpaste.png"),
            new Item("Nacho Crispies", 19.50, "images/Nacho Crispies.png"),
            new Item("Pinoy Cola_Fyzz_Fave_Small Bottles", 14.00, "images/Pinoy Cola_Fyzz_Fave_Small Bottles.png"),
            new Item("Seapoint Sardines Chunks", 20.00, "images/Seapoint Sardines Chunks.png")
        };
        for (Item item : storeItems) {
            JPanel panel = new JPanel(new BorderLayout());
            ImageIcon icon = new ImageIcon(item.getImagePath());
            Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            JButton itemButton = new JButton(new ImageIcon(scaledImage));
            itemButton.setPreferredSize(new Dimension(200, 200));
            itemButton.setBorder(BorderFactory.createEmptyBorder());
            itemButton.setToolTipText("<html>" + item.getName() + "<br>₱" + item.getPrice() + "</html>");

            JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
            panel.add(quantitySpinner, BorderLayout.SOUTH);
            panel.add(itemButton, BorderLayout.CENTER);
            itemButton.addActionListener(e -> {
                int quantity = (int) quantitySpinner.getValue();
                cart.addItem(item, quantity);
                updateCartDisplay();
            });
            itemPanel.add(panel);
        }
        add(itemPanel, BorderLayout.CENTER);

//Cart display area
        cartArea = new JTextArea();
        cartArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(cartArea);
        scrollPane.setPreferredSize(new Dimension(250, 500));
        add(scrollPane, BorderLayout.EAST);

//Clear cart button
        JButton clearButton = new JButton("🗑️ Clear Cart");
        clearButton.addActionListener(e -> {
            cart.clearCart();
            updateCartDisplay();
        });
        add(clearButton, BorderLayout.SOUTH);
    }
    private void updateCartDisplay() {
        cartArea.setText(cart.getCartDetails());
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GAWAgoGUI().setVisible(true));
    }
}
