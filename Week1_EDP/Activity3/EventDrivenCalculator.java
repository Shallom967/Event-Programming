import javax.swing.*;
import java.awt.*;

public class EventDrivenCalculator extends JFrame {

    private JTextField display;

    private double firstOperand = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public EventDrivenCalculator() {

        // Window setup
        setTitle("Event-Driven Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Display
        display = new JTextField("0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 28));

        add(display, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(
                new GridLayout(4, 4, 5, 5)
        );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        // Row 1
        buttonPanel.add(createNumberButton("7"));
        buttonPanel.add(createNumberButton("8"));
        buttonPanel.add(createNumberButton("9"));
        buttonPanel.add(createOperatorButton("/"));

        // Row 2
        buttonPanel.add(createNumberButton("4"));
        buttonPanel.add(createNumberButton("5"));
        buttonPanel.add(createNumberButton("6"));
        buttonPanel.add(createOperatorButton("*"));

        // Row 3
        buttonPanel.add(createNumberButton("1"));
        buttonPanel.add(createNumberButton("2"));
        buttonPanel.add(createNumberButton("3"));
        buttonPanel.add(createOperatorButton("-"));

        // Row 4
        buttonPanel.add(createNumberButton("0"));
        buttonPanel.add(createClearButton("C"));
        buttonPanel.add(createEqualsButton("="));
        buttonPanel.add(createOperatorButton("+"));

        add(buttonPanel, BorderLayout.CENTER);

        // Window settings
        setSize(350, 450);
        setLocationRelativeTo(null);
    }

    // Creates number buttons
    private JButton createNumberButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        // EVENT HANDLER
        button.addActionListener(e -> {

            if (startNewNumber) {

                display.setText(text);
                startNewNumber = false;

            } else {

                display.setText(
                        display.getText() + text
                );
            }
        });

        return button;
    }

    // Creates operator buttons
    private JButton createOperatorButton(String op) {

        JButton button = new JButton(op);

        button.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        // EVENT HANDLER
        button.addActionListener(e -> {

            firstOperand =
                    Double.parseDouble(display.getText());

            operator = op;

            startNewNumber = true;
        });

        return button;
    }

    // Creates equals button
    private JButton createEqualsButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        // EVENT HANDLER
        button.addActionListener(e -> {

            double secondOperand =
                    Double.parseDouble(display.getText());

            double result = 0;

            switch (operator) {

                case "+":
                    result = firstOperand + secondOperand;
                    break;

                case "-":
                    result = firstOperand - secondOperand;
                    break;

                case "*":
                    result = firstOperand * secondOperand;
                    break;

                case "/":

                    if (secondOperand != 0) {

                        result = firstOperand / secondOperand;

                    } else {

                        display.setText("Error");
                        startNewNumber = true;
                        return;
                    }

                    break;

                default:
                    return;
            }

            display.setText(
                    String.valueOf(result)
            );

            startNewNumber = true;
        });

        return button;
    }

    // Creates clear button
    private JButton createClearButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        // EVENT HANDLER
        button.addActionListener(e -> {

            display.setText("0");

            firstOperand = 0;

            operator = "";

            startNewNumber = true;
        });

        return button;
    }

    public static void main(String[] args) {

        // Start Swing application on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {

            EventDrivenCalculator calculator =
                    new EventDrivenCalculator();

            calculator.setVisible(true);
        });
    }
}