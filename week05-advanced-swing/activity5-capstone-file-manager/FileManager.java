import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileManager {

    private JFrame frame;
    private JTree tree;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    private File currentDirectory;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new FileManager().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {

        frame = new JFrame("File Manager");

        frame.setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE
        );

        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);

        // -----------------------------------------
        // Create Tree
        // -----------------------------------------

        File home =
            new File(System.getProperty("user.home"));

        DefaultMutableTreeNode root =
            new DefaultMutableTreeNode(home);

        buildTree(root, home);

        tree = new JTree(root);

        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);

        tree.addTreeSelectionListener(e -> {

            DefaultMutableTreeNode node =
                (DefaultMutableTreeNode)
                tree.getLastSelectedPathComponent();

            if (node == null) {
                return;
            }

            Object userObject =
                node.getUserObject();

            if (userObject instanceof File) {

                File selectedFile =
                    (File) userObject;

                if (selectedFile.isDirectory()) {

                    currentDirectory =
                        selectedFile;

                    loadDirectory(selectedFile);
                }
            }
        });

        // -----------------------------------------
        // Create Table
        // -----------------------------------------

        String[] columns = {
            "Name",
            "Type",
            "Size",
            "Modified"
        };

        tableModel =
            new DefaultTableModel(
                columns,
                0
            ) {

                @Override
                public boolean isCellEditable(
                    int row,
                    int column
                ) {
                    return false;
                }
            };

        table = new JTable(tableModel);

        table.setRowHeight(25);

        table.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        table.getTableHeader().setFont(
            new Font(
                "Arial",
                Font.BOLD,
                13
            )
        );

        // -----------------------------------------
        // Double-click table row
        // -----------------------------------------

        table.addMouseListener(
            new MouseAdapter() {

                @Override
                public void mouseClicked(
                    MouseEvent e
                ) {

                    if (e.getClickCount() == 2 &&
                        SwingUtilities.isLeftMouseButton(e)) {

                        int row =
                            table.getSelectedRow();

                        if (row == -1) {
                            return;
                        }

                        String name =
                            tableModel
                                .getValueAt(row, 0)
                                .toString();

                        File selected =
                            new File(
                                currentDirectory,
                                name
                            );

                        if (selected.isDirectory()) {

                            currentDirectory =
                                selected;

                            loadDirectory(selected);

                        } else {

                            JOptionPane.showMessageDialog(
                                frame,
                                "Would open: "
                                + selected.getAbsolutePath(),
                                "File",
                                JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    }
                }
            }
        );

        // -----------------------------------------
        // Create Split Pane
        // -----------------------------------------

        JSplitPane splitPane =
            new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(tree),
                new JScrollPane(table)
            );

        splitPane.setDividerLocation(300);

        // -----------------------------------------
        // Toolbar
        // -----------------------------------------

        JToolBar toolbar =
            new JToolBar();

        JButton homeButton =
            new JButton("Home");

        JButton upButton =
            new JButton("Up Folder");

        JButton refreshButton =
            new JButton("Refresh");

        homeButton.addActionListener(e -> {

            File homeDirectory =
                new File(
                    System.getProperty("user.home")
                );

            currentDirectory =
                homeDirectory;

            loadDirectory(homeDirectory);
        });

        upButton.addActionListener(e -> {

            if (currentDirectory != null) {

                File parent =
                    currentDirectory.getParentFile();

                if (parent != null) {

                    currentDirectory =
                        parent;

                    loadDirectory(parent);
                }
            }
        });

        refreshButton.addActionListener(e -> {

            if (currentDirectory != null) {

                loadDirectory(
                    currentDirectory
                );
            }
        });

        toolbar.add(homeButton);
        toolbar.add(upButton);
        toolbar.add(refreshButton);

        // -----------------------------------------
        // Menu Bar
        // -----------------------------------------

        JMenuBar menuBar =
            new JMenuBar();

        JMenu fileMenu =
            new JMenu("File");

        JMenuItem refreshItem =
            new JMenuItem("Refresh");

        JMenuItem exitItem =
            new JMenuItem("Exit");

        JMenu helpMenu =
            new JMenu("Help");

        JMenuItem aboutItem =
            new JMenuItem("About");

        refreshItem.addActionListener(e -> {

            if (currentDirectory != null) {
                loadDirectory(
                    currentDirectory
                );
            }
        });

        exitItem.addActionListener(e ->
            System.exit(0)
        );

        aboutItem.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                frame,
                "File Manager\n\n"
                + "Java Swing Capstone\n"
                + "Demonstrates JTree, JTable,\n"
                + "menus, toolbar and dialogs.",
                "About",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        fileMenu.add(refreshItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);

        // -----------------------------------------
        // Status Bar
        // -----------------------------------------

        statusLabel =
            new JLabel("Ready");

        statusLabel.setBorder(
            BorderFactory.createEmptyBorder(
                5,
                10,
                5,
                10
            )
        );

        // -----------------------------------------
        // Main Layout
        // -----------------------------------------

        frame.setLayout(
            new BorderLayout()
        );

        frame.add(
            toolbar,
            BorderLayout.NORTH
        );

        frame.add(
            splitPane,
            BorderLayout.CENTER
        );

        frame.add(
            statusLabel,
            BorderLayout.SOUTH
        );

        // Start at Home
        currentDirectory =
            home;

        loadDirectory(home);

        frame.setVisible(true);
    }

    // ---------------------------------------------
    // Build Tree
    // ---------------------------------------------

    private void buildTree(
        DefaultMutableTreeNode parent,
        File directory
    ) {

        File[] files =
            directory.listFiles();

        if (files == null) {
            return;
        }

        for (File file : files) {

            if (file.isDirectory()) {

                DefaultMutableTreeNode child =
                    new DefaultMutableTreeNode(file);

                parent.add(child);

                buildTree(child, file);
            }
        }
    }

    // ---------------------------------------------
    // Load Directory
    // ---------------------------------------------

    private void loadDirectory(
        File directory
    ) {

        if (directory == null ||
            !directory.isDirectory()) {
            return;
        }

        currentDirectory =
            directory;

        tableModel.setRowCount(0);

        File[] files =
            directory.listFiles();

        if (files == null) {

            statusLabel.setText(
                "Cannot access: "
                + directory.getAbsolutePath()
            );

            return;
        }

        SimpleDateFormat dateFormat =
            new SimpleDateFormat(
                "dd MMM yyyy"
            );

        for (File file : files) {

            String type;

            if (file.isDirectory()) {
                type = "Folder";
            } else {
                type = "File";
            }

            String size;

            if (file.isDirectory()) {
                size = "-";
            } else {
                size =
                    String.valueOf(
                        file.length()
                    );
            }

            String modified =
                dateFormat.format(
                    new Date(
                        file.lastModified()
                    )
                );

            tableModel.addRow(
                new Object[]{
                    file.getName(),
                    type,
                    size,
                    modified
                }
            );
        }

        statusLabel.setText(
            files.length
            + " item(s) — "
            + directory.getAbsolutePath()
        );
    }
}