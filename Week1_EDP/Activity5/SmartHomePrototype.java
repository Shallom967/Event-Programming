import javax.swing.*;
import java.awt.*;

public class SmartHomePrototype extends JFrame {

    private JLabel lightStatus;
    private JLabel fanStatus;
    private JLabel temperatureStatus;

    private boolean lightsOn = false;
    private boolean fanOn = false;

    public SmartHomePrototype() {

        setTitle("Smart Home Control System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel titleLabel = new JLabel(
                "Smart Home Control System",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        add(titleLabel, BorderLayout.NORTH);

        // Status panel
        JPanel statusPanel = new JPanel(
                new GridLayout(3, 1, 10, 10)
        );

        statusPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        );

        lightStatus = new JLabel("Lights: OFF");
        fanStatus = new JLabel("Fan: OFF");
        temperatureStatus = new JLabel("Temperature: 24°C");

        statusPanel.add(lightStatus);
        statusPanel.add(fanStatus);
        statusPanel.add(temperatureStatus);

        add(statusPanel, BorderLayout.CENTER);

        // Control buttons
        JPanel buttonPanel = new JPanel(
                new FlowLayout()
        );

        JButton lightButton = new JButton("Toggle Lights");
        JButton fanButton = new JButton("Toggle Fan");
        JButton temperatureButton = new JButton("Increase Temperature");

        // Event Handler 1 - Lights
        lightButton.addActionListener(e -> {

            lightsOn = !lightsOn;

            if (lightsOn) {
                lightStatus.setText("Lights: ON");
            } else {
                lightStatus.setText("Lights: OFF");
            }
        });

        // Event Handler 2 - Fan
        fanButton.addActionListener(e -> {

            fanOn = !fanOn;

            if (fanOn) {
                fanStatus.setText("Fan: ON");
            } else {
                fanStatus.setText("Fan: OFF");
            }
        });

        // Event Handler 3 - Temperature
        temperatureButton.addActionListener(e -> {

            String currentText =
                    temperatureStatus.getText();

            int currentTemperature =
                    Integer.parseInt(
                            currentText.replace(
                                    "Temperature: ", ""
                            ).replace("°C", "")
                    );

            currentTemperature++;

            temperatureStatus.setText(
                    "Temperature: "
                            + currentTemperature
                            + "°C"
            );
        });

        buttonPanel.add(lightButton);
        buttonPanel.add(fanButton);
        buttonPanel.add(temperatureButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setSize(600, 300);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {

        // Start the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {

            SmartHomePrototype home =
                    new SmartHomePrototype();

            home.setVisible(true);
        });
    }
}