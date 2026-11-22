import javax.swing.*;
import java.awt.*;

public class Bug4_MemoryLeak extends JFrame {

    private JLabel statusLabel;
    private JButton startButton;
    private Timer timer;

    public Bug4_MemoryLeak() {

        setTitle("Bug 4 - Memory Leak");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        statusLabel = new JLabel("Timer stopped");

        startButton = new JButton("Start Timer");

        startButton.addActionListener(e -> {

            // Prevent multiple timers from being created
            if (timer == null || !timer.isRunning()) {

                timer = new Timer(1000, event -> {

                    statusLabel.setText(
                            "Timer running: " + System.currentTimeMillis()
                    );

                });

                timer.start();

                startButton.setText("Stop Timer");
            }

            else {

                timer.stop();

                statusLabel.setText("Timer stopped");

                startButton.setText("Start Timer");
            }
        });

        add(statusLabel);
        add(startButton);

        setSize(450, 150);
        setLocationRelativeTo(null);
    }

    @Override
    public void dispose() {

        // Stop the timer when the window is closed
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        super.dispose();
    }

    public static void main(String[] args) {

        // Start Swing application on the EDT
        SwingUtilities.invokeLater(() -> {
            new Bug4_MemoryLeak().setVisible(true);
        });
    }
}