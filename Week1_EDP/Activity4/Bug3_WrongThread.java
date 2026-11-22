import javax.swing.*;
import java.awt.*;

public class Bug3_WrongThread extends JFrame {

    private JLabel statusLabel;
    private JButton updateButton;

    public Bug3_WrongThread() {

        setTitle("Bug 3 - Wrong Thread");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        statusLabel = new JLabel("Waiting...");

        updateButton = new JButton("Update");

        updateButton.addActionListener(e -> {

            // Perform the background task in a separate thread
            new Thread(() -> {

                try {
                    Thread.sleep(2000);

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return;
                }

                // Update Swing components on the EDT
                SwingUtilities.invokeLater(() -> {
                    statusLabel.setText("Updated Successfully!");
                });

            }).start();

        });

        add(statusLabel);
        add(updateButton);

        setSize(350, 150);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        // Create and show the GUI on the EDT
        SwingUtilities.invokeLater(() -> {
            new Bug3_WrongThread().setVisible(true);
        });
    }
}