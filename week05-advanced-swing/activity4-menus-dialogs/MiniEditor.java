import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MiniEditor {

    private JFrame frame;
    private JTextArea textArea;
    private JLabel wordCountLabel;
    private Path currentFile;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MiniEditor().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {

        frame = new JFrame("Mini Text Editor");

        frame.setDefaultCloseOperation(
            JFrame.DO_NOTHING_ON_CLOSE
        );

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        // -----------------------------------------
        // Text Area
        // -----------------------------------------

        textArea = new JTextArea();

        textArea.setFont(
            new Font("Arial", Font.PLAIN, 15)
        );

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane =
            new JScrollPane(textArea);

        // -----------------------------------------
        // Menu Bar
        // -----------------------------------------

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu helpMenu = new JMenu("Help");

        // File menu
        JMenuItem newItem =
            new JMenuItem("New");

        JMenuItem openItem =
            new JMenuItem("Open");

        JMenuItem saveItem =
            new JMenuItem("Save");

        JMenuItem saveAsItem =
            new JMenuItem("Save As");

        JMenuItem exitItem =
            new JMenuItem("Exit");

        // Edit menu
        JMenuItem cutItem =
            new JMenuItem("Cut");

        JMenuItem copyItem =
            new JMenuItem("Copy");

        JMenuItem pasteItem =
            new JMenuItem("Paste");

        // Help menu
        JMenuItem aboutItem =
            new JMenuItem("About");

        // -----------------------------------------
        // File Menu Actions
        // -----------------------------------------

        newItem.addActionListener(e -> newDocument());

        openItem.addActionListener(e -> openFile());

        saveItem.addActionListener(e -> saveFile());

        saveAsItem.addActionListener(e -> saveFileAs());

        exitItem.addActionListener(e -> exitApplication());

        // -----------------------------------------
        // Edit Menu Actions
        // -----------------------------------------

        cutItem.addActionListener(e ->
            textArea.cut()
        );

        copyItem.addActionListener(e ->
            textArea.copy()
        );

        pasteItem.addActionListener(e ->
            textArea.paste()
        );

        // -----------------------------------------
        // Help Menu
        // -----------------------------------------

        aboutItem.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                frame,
                "Mini Text Editor\n\n"
                + "Built using Java Swing.\n"
                + "Demonstrates menus, toolbar,\n"
                + "dialogs, file chooser and\n"
                + "document listeners.",
                "About",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Add items to File menu
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Add items to Edit menu
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);

        // Add item to Help menu
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);

        // -----------------------------------------
        // Tool Bar
        // -----------------------------------------

        JToolBar toolBar =
            new JToolBar();

        JButton newButton =
            new JButton("New");

        JButton openButton =
            new JButton("Open");

        JButton saveButton =
            new JButton("Save");

        newButton.addActionListener(
            e -> newDocument()
        );

        openButton.addActionListener(
            e -> openFile()
        );

        saveButton.addActionListener(
            e -> saveFile()
        );

        toolBar.add(newButton);
        toolBar.add(openButton);
        toolBar.add(saveButton);

        // -----------------------------------------
        // Word Count
        // -----------------------------------------

        wordCountLabel =
            new JLabel("Words: 0");

        toolBar.addSeparator();
        toolBar.add(wordCountLabel);

        // -----------------------------------------
        // Document Listener
        // -----------------------------------------

        textArea.getDocument()
            .addDocumentListener(
                new DocumentListener() {

                    @Override
                    public void insertUpdate(
                        DocumentEvent e
                    ) {
                        updateWordCount();
                    }

                    @Override
                    public void removeUpdate(
                        DocumentEvent e
                    ) {
                        updateWordCount();
                    }

                    @Override
                    public void changedUpdate(
                        DocumentEvent e
                    ) {
                        updateWordCount();
                    }
                }
            );

        // -----------------------------------------
        // Status Bar
        // -----------------------------------------

        JLabel statusLabel =
            new JLabel("Ready");

        statusLabel.setBorder(
            BorderFactory.createEmptyBorder(
                5, 10, 5, 10
            )
        );

        // -----------------------------------------
        // Layout
        // -----------------------------------------

        frame.setLayout(
            new BorderLayout()
        );

        frame.add(
            toolBar,
            BorderLayout.NORTH
        );

        frame.add(
            scrollPane,
            BorderLayout.CENTER
        );

        frame.add(
            statusLabel,
            BorderLayout.SOUTH
        );

        frame.setVisible(true);
    }

    // ---------------------------------------------
    // Update Word Count
    // ---------------------------------------------

    private void updateWordCount() {

        String text =
            textArea.getText().trim();

        if (text.isEmpty()) {

            wordCountLabel.setText(
                "Words: 0"
            );

            return;
        }

        String[] words =
            text.split("\\s+");

        wordCountLabel.setText(
            "Words: " + words.length
        );
    }

    // ---------------------------------------------
    // New Document
    // ---------------------------------------------

    private void newDocument() {

        int result =
            JOptionPane.showConfirmDialog(
                frame,
                "Clear the current document?",
                "New Document",
                JOptionPane.YES_NO_OPTION
            );

        if (result ==
            JOptionPane.YES_OPTION) {

            textArea.setText("");

            currentFile = null;
        }
    }

    // ---------------------------------------------
    // Open File
    // ---------------------------------------------

    private void openFile() {

        JFileChooser chooser =
            new JFileChooser();

        chooser.setFileFilter(
            new FileNameExtensionFilter(
                "Text Files (*.txt)",
                "txt"
            )
        );

        int result =
            chooser.showOpenDialog(frame);

        if (result ==
            JFileChooser.APPROVE_OPTION) {

            try {

                currentFile =
                    chooser.getSelectedFile()
                        .toPath();

                String content =
                    Files.readString(currentFile);

                textArea.setText(content);

                frame.setTitle(
                    "Mini Text Editor — "
                    + currentFile.getFileName()
                );

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                    frame,
                    "Could not open the file.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // ---------------------------------------------
    // Save File
    // ---------------------------------------------

    private void saveFile() {

        if (currentFile == null) {

            saveFileAs();

            return;
        }

        try {

            Files.writeString(
                currentFile,
                textArea.getText()
            );

            JOptionPane.showMessageDialog(
                frame,
                "File saved successfully.",
                "Save",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(
                frame,
                "Could not save the file.",
                "File Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // ---------------------------------------------
    // Save As
    // ---------------------------------------------

    private void saveFileAs() {

        JFileChooser chooser =
            new JFileChooser();

        chooser.setFileFilter(
            new FileNameExtensionFilter(
                "Text Files (*.txt)",
                "txt"
            )
        );

        int result =
            chooser.showSaveDialog(frame);

        if (result ==
            JFileChooser.APPROVE_OPTION) {

            try {

                currentFile =
                    chooser.getSelectedFile()
                        .toPath();

                Files.writeString(
                    currentFile,
                    textArea.getText()
                );

                frame.setTitle(
                    "Mini Text Editor — "
                    + currentFile.getFileName()
                );

                JOptionPane.showMessageDialog(
                    frame,
                    "File saved successfully.",
                    "Save As",
                    JOptionPane.INFORMATION_MESSAGE
                );

            } catch (IOException ex) {

                JOptionPane.showMessageDialog(
                    frame,
                    "Could not save the file.",
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    // ---------------------------------------------
    // Exit Application
    // ---------------------------------------------

    private void exitApplication() {

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
    }
}