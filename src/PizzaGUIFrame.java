import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {

    private JRadioButton thinCrustRadio, regularCrustRadio, deepDishCrustRadio;
    private JComboBox<String> sizeComboBox;
    private JCheckBox pineapple, bacon, skittles, pickles, olives, sprinkles;
    private JTextArea orderTextArea;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel crustPanel = createCrustPanel();
        JPanel sizePanel = createSizePanel();
        JPanel toppingsPanel = createToppingsPanel();
        JPanel orderPanel = createOrderPanel();
        JPanel buttonPanel = createButtonPanel();

        add(crustPanel, BorderLayout.WEST);
        add(sizePanel, BorderLayout.CENTER);
        add(toppingsPanel, BorderLayout.EAST);

        JPanel orderAndButtonPanel = new JPanel(new GridLayout(2, 1));
        orderAndButtonPanel.add(orderPanel);
        orderAndButtonPanel.add(buttonPanel);

        add(orderAndButtonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createCrustPanel() {
        JPanel crustPanel = new JPanel();
        crustPanel.setLayout(new GridLayout(3, 1));
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust"));

        thinCrustRadio = new JRadioButton("Thin");
        regularCrustRadio = new JRadioButton("Regular");
        deepDishCrustRadio = new JRadioButton("Deep-dish");

        ButtonGroup crustGroup = new ButtonGroup();
        crustGroup.add(thinCrustRadio);
        crustGroup.add(regularCrustRadio);
        crustGroup.add(deepDishCrustRadio);

        crustPanel.add(thinCrustRadio);
        crustPanel.add(regularCrustRadio);
        crustPanel.add(deepDishCrustRadio);

        return crustPanel;
    }

    private JPanel createSizePanel() {
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));

        String[] sizes = {"Small", "Medium", "Large", "Super"};
        sizeComboBox = new JComboBox<>(sizes);

        sizePanel.add(sizeComboBox);
        return sizePanel;
    }

    private JPanel createToppingsPanel() {
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setLayout(new GridLayout(6, 1));
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));

        pineapple = new JCheckBox("Pineapple");
        bacon = new JCheckBox("Bacon");
        skittles = new JCheckBox("Skittles");
        pickles = new JCheckBox("Pickles");
        olives = new JCheckBox("Olives");
        sprinkles = new JCheckBox("Sprinkles");

        toppingsPanel.add(pineapple);
        toppingsPanel.add(bacon);
        toppingsPanel.add(skittles);
        toppingsPanel.add(pickles);
        toppingsPanel.add(olives);
        toppingsPanel.add(sprinkles);

        return toppingsPanel;
    }

    private JPanel createOrderPanel() {
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));

        orderTextArea = new JTextArea();
        orderTextArea.setEditable(false);
        orderTextArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(orderTextArea);
        orderPanel.add(scrollPane, BorderLayout.CENTER);

        return orderPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JButton orderButton = new JButton("Order");
        orderButton.addActionListener(new OrderButtonListener());
        buttonPanel.add(orderButton);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearButtonListener());
        buttonPanel.add(clearButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new QuitButtonListener());
        buttonPanel.add(quitButton);

        return buttonPanel;
    }

    private class OrderButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder orderDetails = new StringBuilder();

            if (thinCrustRadio.isSelected()) {
                orderDetails.append("Crust: Thin\t\t\t$2.00\n");
            } else if (regularCrustRadio.isSelected()) {
                orderDetails.append("Crust: Regular\t\t$1.50\n");
            } else if (deepDishCrustRadio.isSelected()) {
                orderDetails.append("Crust: Deep-dish\t\t$3.00\n");
            }

            orderDetails.append("Size: ").append(sizeComboBox.getSelectedItem()).append("\n");

            orderDetails.append("Toppings:\n");
            if (pineapple.isSelected()) {
                orderDetails.append("- Pineapple\t\t\t$1.00\n");
            }
            if (bacon.isSelected()) {
                orderDetails.append("- Bacon\t\t\t$1.00\n");
            }
            if (skittles.isSelected()) {
                orderDetails.append("- Skittles\t\t\t$1.00\n");
            }
            if (pickles.isSelected()) {
                orderDetails.append("- Pickles\t\t\t$1.00\n");
            }
            if (olives.isSelected()) {
                orderDetails.append("- Olives\t\t\t$1.00\n");
            }
            if (sprinkles.isSelected()) {
                orderDetails.append("- Sprinkles\t\t\t$1.00\n");
            }

            double subTotal = calculateSubTotal();
            double tax = subTotal * 0.07;
            double total = subTotal + tax;

            String receipt = "=========================================\n"
                    + "Type of Crust & Size\t\tPrice\n"
                    + orderDetails.toString()
                    + "--------------------------------------------------------------------------\n"
                    + "Sub-total:\t\t\t$" + String.format("%.2f", subTotal) + "\n"
                    + "Tax:\t\t\t$" + String.format("%.2f", tax) + "\n"
                    + "--------------------------------------------------------------------------\n"
                    + "Total:\t\t\t$" + String.format("%.2f", total) + "\n"
                    + "=========================================\n";

            orderTextArea.setText(receipt);
        }

        private double calculateSubTotal() {
            double crustPrice = 0.0;
            double sizePrice = 0.0;
            double toppingsPrice = 0.0;

            if (thinCrustRadio.isSelected()) {
                crustPrice = 2.0;
            } else if (regularCrustRadio.isSelected()) {
                crustPrice = 1.5;
            } else if (deepDishCrustRadio.isSelected()) {
                crustPrice = 3.0;
            }

            switch (sizeComboBox.getSelectedItem().toString()) {
                case "Small":
                    sizePrice = 8.0;
                    break;
                case "Medium":
                    sizePrice = 12.0;
                    break;
                case "Large":
                    sizePrice = 16.0;
                    break;
                case "Super":
                    sizePrice = 20.0;
                    break;
            }

            if (pineapple.isSelected()) {
                toppingsPrice += 1.0;
            }
            if (bacon.isSelected()) {
                toppingsPrice += 1.0;
            }
            if (skittles.isSelected()) {
                toppingsPrice += 1.0;
            }
            if (pickles.isSelected()) {
                toppingsPrice += 1.0;
            }
            if (olives.isSelected()) {
                toppingsPrice += 1.0;
            }
            if (sprinkles.isSelected()) {
                toppingsPrice += 1.0;
            }
            return crustPrice + sizePrice + toppingsPrice;
        }
    }
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            thinCrustRadio.setSelected(false);
            regularCrustRadio.setSelected(false);
            deepDishCrustRadio.setSelected(false);
            sizeComboBox.setSelectedIndex(0);
            pineapple.setSelected(false);
            bacon.setSelected(false);
            skittles.setSelected(false);
            pickles.setSelected(false);
            olives.setSelected(false);
            sprinkles.setSelected(false);
            orderTextArea.setText("");
        }
    }
    private class QuitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(PizzaGUIFrame.this,
                    "Are you sure you want to quit?", "Quit Confirmation", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PizzaGUIFrame().setVisible(true);
        });
    }
}
