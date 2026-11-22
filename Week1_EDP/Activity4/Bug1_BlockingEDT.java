import javax.swing.*;
import java.awt.*;

public class Bug1_BlockingEDT extends JFrame {

    private JLabel statusLabel;
    private JButton processButton;

    public Bug1_BlockingEDT() {

        setTitle("Bug 1 - Blocking EDT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        statusLabel = new JLabel("Ready");

        processButton = new JButton("Process");

        processButton.addActionListener(e -> {

            // Run the long operation in a background thread
            new Thread(() -> {

                try {
                    Thread.sleep(5000);

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return;
                }

                // Update the GUI on the EDT
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Done!");
                });

            }).start();

        });

        add(statusLabel);
        add(processButton);

        setSize(300, 150);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        // Start the Swing application on the EDT
        SwingUtilities.invokeLater(() -> {
            new Bug1_BlockingEDT().setVisible(true);
        });
    }
}