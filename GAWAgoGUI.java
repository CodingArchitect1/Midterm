import java.awt.*;
import java.awt.event.ItemEvent;
import javax.swing.*;
import javax.swing.border.*;

public class GAWAgoGUI extends JFrame {
    private Cart cart;
    private JTextArea cartArea;
    private JPanel itemPanel;
    private JScrollPane scrollPane;
    private boolean isDarkMode = false;

    public GAWAgoGUI() {
        cart = new Cart();
        setTitle("GAWAgo");
        setSize(1300, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

// Items
        itemPanel = new JPanel(new GridLayout(2, 4, 20, 20));
        itemPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

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
            panel.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 2, true));  // Rounded border effect

            ImageIcon icon = new ImageIcon(item.getImagePath());
            Image scaledImage = icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
            JButton itemButton = new JButton(new ImageIcon(scaledImage));
            itemButton.setPreferredSize(new Dimension(160, 160));
            itemButton.setBorder(BorderFactory.createEmptyBorder());
            itemButton.setToolTipText("<html><b>" + item.getName() + "</b><br>₱" + item.getPrice() + "</html>");
            itemButton.setContentAreaFilled(false);

            itemButton.setRolloverEnabled(true);
            itemButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    itemButton.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    itemButton.setBorder(BorderFactory.createEmptyBorder());
                }
            });
            JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 99, 1));
            quantitySpinner.setFont(new Font("SansSerif", Font.PLAIN, 14));

            panel.add(itemButton, BorderLayout.CENTER);
            panel.add(quantitySpinner, BorderLayout.SOUTH);

            itemButton.addActionListener(e -> {
                int quantity = (int) quantitySpinner.getValue();
                cart.addItem(item, quantity);
                updateCartDisplay();
            });
            itemPanel.add(panel);
        }
        add(itemPanel, BorderLayout.CENTER);

// Cart Area
        cartArea = new JTextArea();
        cartArea.setEditable(false);
        cartArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        cartArea.setBorder(BorderFactory.createTitledBorder("🛒 Your Cart"));

        scrollPane = new JScrollPane(cartArea);
        scrollPane.setPreferredSize(new Dimension(300, 500));
        add(scrollPane, BorderLayout.EAST);

//Clear Cart at Theme
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton clearButton = new JButton("🗑️ Clear Cart");
        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        clearButton.setBackground(new Color(255, 100, 100));
        clearButton.setForeground(Color.WHITE);
        clearButton.setBorder(BorderFactory.createLineBorder(Color.RED));
        clearButton.setFocusPainted(false);
        clearButton.setContentAreaFilled(true);

        clearButton.addActionListener(e -> {
            cart.clearCart();
            updateCartDisplay();
        });
        JToggleButton themeToggle = new JToggleButton("🌙 Dark Mode");
        themeToggle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        themeToggle.setBorder(new EmptyBorder(10, 15, 10, 15));
        themeToggle.setBackground(new Color(50, 50, 255));
        themeToggle.setForeground(Color.WHITE);

        themeToggle.addItemListener(e -> {
            isDarkMode = e.getStateChange() == ItemEvent.SELECTED;
            themeToggle.setText(isDarkMode ? "☀️ Light Mode" : "🌙 Dark Mode");
            applyTheme();
        });
        bottomPanel.add(clearButton);
        bottomPanel.add(themeToggle);
        add(bottomPanel, BorderLayout.SOUTH);

        applyTheme();
    }
//Ito ay sa Light at Dark mode
    private void applyTheme() {
        Color bgColor = isDarkMode ? new Color(30, 30, 30) : Color.WHITE;
        Color fgColor = isDarkMode ? Color.WHITE : Color.BLACK;
        Color panelBg = isDarkMode ? new Color(45, 45, 45) : new Color(240, 240, 240);
        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        itemPanel.setBackground(panelBg);
        cartArea.setBackground(bgColor);
        cartArea.setForeground(fgColor);
        cartArea.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(fgColor, 1),
                "🛒 Your Cart", TitledBorder.LEFT, TitledBorder.TOP, font, fgColor)
        );
        scrollPane.setBackground(panelBg);
        scrollPane.getViewport().setBackground(bgColor);

        for (Component comp : itemPanel.getComponents()) {
            if (comp instanceof JPanel) {
                comp.setBackground(panelBg);
                for (Component c : ((JPanel) comp).getComponents()) {
                    if (c instanceof JButton || c instanceof JSpinner) {
                        c.setBackground(bgColor);
                        c.setForeground(fgColor);
                    }
                }
            }
        }
        getContentPane().setBackground(panelBg);
    }
    private void updateCartDisplay() {
        cartArea.setText(cart.getCartDetails());
    }
// Main Method para sa pag launch
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GAWAgoGUI().setVisible(true));
    }
}