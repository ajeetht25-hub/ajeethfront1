
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BankingApp extends JFrame {

    private double balance = 10000.00;
    private JLabel balanceLabel;
    private JTextArea historyArea;

    public BankingApp() {
        setTitle("Banking Management System");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
        mainPanel.setBackground(new Color(240, 245, 250));

        // Heading
        JLabel heading = new JLabel("My Banking Dashboard");
        heading.setFont(new Font("Arial", Font.BOLD, 25));
        heading.setForeground(new Color(25, 65, 120));

        // Balance display
        balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 22));
        updateBalance();

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );
        topPanel.add(heading);
        topPanel.add(balanceLabel);

        // Buttons
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton historyButton = new JButton("Transaction History");

        JPanel buttonPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER, 10, 10)
        );
        buttonPanel.setBackground(new Color(240, 245, 250));

        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(historyButton);

        // Transaction history
        historyArea = new JTextArea(10, 40);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(historyArea);

        // Button actions
        depositButton.addActionListener(e -> deposit());
        withdrawButton.addActionListener(e -> withdraw());
        historyButton.addActionListener(e ->
            JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Transaction History",
                JOptionPane.INFORMATION_MESSAGE
            )
        );

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void updateBalance() {
        balanceLabel = balanceLabel == null
            ? new JLabel() : balanceLabel;

        balanceLabel.setText(
            String.format("Available Balance: Rs. %.2f", balance)
        );
    }

    private void deposit() {
        String input = JOptionPane.showInputDialog(
            this, "Enter deposit amount:"
        );

        if (input == null) return;

        try {
            double amount = Double.parseDouble(input);

            if (amount <= 0 || !Double.isFinite(amount)) {
                throw new IllegalArgumentException();
            }

            balance += amount;
            updateBalance();

            historyArea.append(
                String.format(
                    "Deposited: Rs. %.2f%n", amount
                )
            );

            JOptionPane.showMessageDialog(
                this, "Deposit successful!"
            );

        } catch (NumberFormatException |
                 IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this, "Please enter a valid positive amount."
            );
        }
    }

    private void withdraw() {
        String input = JOptionPane.showInputDialog(
            this, "Enter withdrawal amount:"
        );

        if (input == null) return;

        try {
            double amount = Double.parseDouble(input);

            if (amount <= 0 || !Double.isFinite(amount)) {
                throw new IllegalArgumentException();
            }

            if (amount > balance) {
                JOptionPane.showMessageDialog(
                    this, "Insufficient balance!"
                );
                return;
            }

            balance -= amount;
            updateBalance();

            historyArea.append(
                String.format(
                    "Withdrawn: Rs. %.2f%n", amount
                )
            );

            JOptionPane.showMessageDialog(
                this, "Withdrawal successful!"
            );

        } catch (NumberFormatException |
                 IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(
                this, "Please enter a valid positive amount."
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BankingApp app = new BankingApp();
            app.setVisible(true);
        });
    }
}
