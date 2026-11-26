// ============================================================
// FIRST SWING WINDOW
// ============================================================
// This is the simplest possible Swing application.
// It creates a JFrame (main window) and displays it.
// ============================================================

import javax.swing.*; // Swing components (JFrame, JButton, etc.)

public class FirstWindow {

    public static void main(String[] args) {

        // CRITICAL: All Swing code must run on the EDT.
        // This schedules the GUI creation on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {

            createAndShowWindow();

        });
    }

    private static void createAndShowWindow() {

        // 1. Create the JFrame (the main window)
        JFrame frame = new JFrame("My First Swing App");

        // 2. Set what happens when the user clicks the X button
        // EXIT_ON_CLOSE terminates the JVM when the window closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 3. Set the window size
        // Width = 500 pixels
        // Height = 400 pixels
        frame.setSize(500, 400);

        // 4. Center the window on the screen
        frame.setLocationRelativeTo(null);

        // 5. Make the window visible
        frame.setVisible(true);

        // Log the thread being used
        System.out.println(
                "Window created on thread: "
                        + Thread.currentThread().getName()
        );
    }
}