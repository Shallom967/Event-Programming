import javax.swing.*;
import java.awt.*;

public class RegistrationForm {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Student Registration Form");

            frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
            );

            frame.setSize(600, 650);
            frame.setLocationRelativeTo(null);

            // Main panel
            JPanel panel = new JPanel(
                new GridBagLayout()
            );

            panel.setBorder(
                BorderFactory.createEmptyBorder(
                    20, 20, 20, 20
                )
            );

            GridBagConstraints gbc =
                new GridBagConstraints();

            gbc.insets =
                new Insets(5, 5, 5, 5);

            gbc.fill =
                GridBagConstraints.HORIZONTAL;

            // ---------------------------------------
            // Name
            // ---------------------------------------

            JLabel nameLabel =
                new JLabel("Name:");

            JTextField nameField =
                new JTextField(20);

            gbc.gridx = 0;
            gbc.gridy = 0;
            panel.add(nameLabel, gbc);

            gbc.gridx = 1;
            panel.add(nameField, gbc);

            // ---------------------------------------
            // Email
            // ---------------------------------------

            JLabel emailLabel =
                new JLabel("Email:");

            JTextField emailField =
                new JTextField(20);

            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(emailLabel, gbc);

            gbc.gridx = 1;
            panel.add(emailField, gbc);

            // ---------------------------------------
            // Password
            // ---------------------------------------

            JLabel passwordLabel =
                new JLabel("Password:");

            JPasswordField passwordField =
                new JPasswordField(20);

            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(passwordLabel, gbc);

            gbc.gridx = 1;
            panel.add(passwordField, gbc);

            // ---------------------------------------
            // Gender
            // ---------------------------------------

            JLabel genderLabel =
                new JLabel("Gender:");

            JRadioButton maleButton =
                new JRadioButton("Male");

            JRadioButton femaleButton =
                new JRadioButton("Female");

            JRadioButton otherButton =
                new JRadioButton("Other");

            ButtonGroup genderGroup =
                new ButtonGroup();

            genderGroup.add(maleButton);
            genderGroup.add(femaleButton);
            genderGroup.add(otherButton);

            JPanel genderPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

            genderPanel.add(maleButton);
            genderPanel.add(femaleButton);
            genderPanel.add(otherButton);

            gbc.gridx = 0;
            gbc.gridy = 3;
            panel.add(genderLabel, gbc);

            gbc.gridx = 1;
            panel.add(genderPanel, gbc);

            // ---------------------------------------
            // Interests
            // ---------------------------------------

            JLabel interestsLabel =
                new JLabel("Interests:");

            JCheckBox programmingBox =
                new JCheckBox("Programming");

            JCheckBox sportsBox =
                new JCheckBox("Sports");

            JCheckBox musicBox =
                new JCheckBox("Music");

            JCheckBox travelBox =
                new JCheckBox("Travel");

            JPanel interestsPanel =
                new JPanel(
                    new FlowLayout(FlowLayout.LEFT)
                );

            interestsPanel.add(programmingBox);
            interestsPanel.add(sportsBox);
            interestsPanel.add(musicBox);
            interestsPanel.add(travelBox);

            gbc.gridx = 0;
            gbc.gridy = 4;
            panel.add(interestsLabel, gbc);

            gbc.gridx = 1;
            panel.add(interestsPanel, gbc);

            // ---------------------------------------
            // Country
            // ---------------------------------------

            JLabel countryLabel =
                new JLabel("Country:");

            String[] countries = {
                "Kenya",
                "Uganda",
                "Tanzania",
                "Rwanda",
                "Nigeria",
                "South Africa"
            };

            JComboBox<String> countryComboBox =
                new JComboBox<>(countries);

            gbc.gridx = 0;
            gbc.gridy = 5;
            panel.add(countryLabel, gbc);

            gbc.gridx = 1;
            panel.add(countryComboBox, gbc);

            // ---------------------------------------
            // Comments
            // ---------------------------------------

            JLabel commentsLabel =
                new JLabel("Comments:");

            JTextArea commentsArea =
                new JTextArea(5, 20);

            commentsArea.setLineWrap(true);
            commentsArea.setWrapStyleWord(true);

            JScrollPane commentsScrollPane =
                new JScrollPane(commentsArea);

            gbc.gridx = 0;
            gbc.gridy = 6;
            panel.add(commentsLabel, gbc);

            gbc.gridx = 1;
            panel.add(commentsScrollPane, gbc);

            // ---------------------------------------
            // Buttons
            // ---------------------------------------

            JButton submitButton =
                new JButton("Submit");

            JButton clearButton =
                new JButton("Clear");

            JButton exitButton =
                new JButton("Exit");

            JPanel buttonPanel =
                new JPanel(
                    new FlowLayout(
                        FlowLayout.CENTER,
                        10,
                        10
                    )
                );

            buttonPanel.add(submitButton);
            buttonPanel.add(clearButton);
            buttonPanel.add(exitButton);

            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.gridwidth = 2;

            panel.add(buttonPanel, gbc);

            // ---------------------------------------
            // Submit Button
            // ---------------------------------------

            submitButton.addActionListener(e -> {

                String name =
                    nameField.getText().trim();

                String email =
                    emailField.getText().trim();

                String password =
                    new String(
                        passwordField.getPassword()
                    );

                // Validate name
                if (name.isEmpty()) {

                    JOptionPane.showMessageDialog(
                        frame,
                        "Please enter your name.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                    );

                    nameField.requestFocus();

                    return;
                }

                // Validate email
                if (email.isEmpty() ||
                    !email.contains("@")) {

                    JOptionPane.showMessageDialog(
                        frame,
                        "Please enter a valid email.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                    );

                    emailField.requestFocus();

                    return;
                }

                // Validate password
                if (password.length() < 6) {

                    JOptionPane.showMessageDialog(
                        frame,
                        "Password must be at least 6 characters.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                    );

                    passwordField.requestFocus();

                    return;
                }

                // Get gender
                String gender = "";

                if (maleButton.isSelected()) {
                    gender = "Male";
                } else if (femaleButton.isSelected()) {
                    gender = "Female";
                } else if (otherButton.isSelected()) {
                    gender = "Other";
                }

                // Get interests
                StringBuilder interests =
                    new StringBuilder();

                if (programmingBox.isSelected()) {
                    interests.append("Programming, ");
                }

                if (sportsBox.isSelected()) {
                    interests.append("Sports, ");
                }

                if (musicBox.isSelected()) {
                    interests.append("Music, ");
                }

                if (travelBox.isSelected()) {
                    interests.append("Travel, ");
                }

                if (interests.length() == 0) {
                    interests.append("None");
                }

                String country =
                    countryComboBox
                        .getSelectedItem()
                        .toString();

                // Show confirmation
                String message =
                    "Registration Successful!\n\n"
                    + "Name: " + name + "\n"
                    + "Email: " + email + "\n"
                    + "Gender: " + gender + "\n"
                    + "Interests: "
                    + interests + "\n"
                    + "Country: " + country;

                JOptionPane.showMessageDialog(
                    frame,
                    message,
                    "Registration",
                    JOptionPane.INFORMATION_MESSAGE
                );
            });

            // ---------------------------------------
            // Clear Button
            // ---------------------------------------

            clearButton.addActionListener(e -> {

                nameField.setText("");
                emailField.setText("");
                passwordField.setText("");

                genderGroup.clearSelection();

                programmingBox.setSelected(false);
                sportsBox.setSelected(false);
                musicBox.setSelected(false);
                travelBox.setSelected(false);

                countryComboBox.setSelectedIndex(0);

                commentsArea.setText("");

                nameField.requestFocus();
            });

            // ---------------------------------------
            // Exit Button
            // ---------------------------------------

            exitButton.addActionListener(e -> {

                int result =
                    JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to exit?",
                        "Exit",
                        JOptionPane.YES_NO_OPTION
                    );

                if (result ==
                    JOptionPane.YES_OPTION) {

                    System.exit(0);
                }
            });

            // ---------------------------------------
            // Display Window
            // ---------------------------------------

            frame.add(panel);

            frame.setVisible(true);
        });
    }
}