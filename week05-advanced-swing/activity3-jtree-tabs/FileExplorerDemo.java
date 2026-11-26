import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;

public class FileExplorerDemo {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame(
                "File Explorer — JTree + JTabbedPane"
            );

            frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
            );

            frame.setSize(800, 500);
            frame.setLocationRelativeTo(null);

            // -----------------------------------------
            // Create the tree structure
            // -----------------------------------------

            DefaultMutableTreeNode root =
                new DefaultMutableTreeNode("My Computer");

            DefaultMutableTreeNode documents =
                new DefaultMutableTreeNode("Documents");

            documents.add(
                new DefaultMutableTreeNode("Resume.docx")
            );

            documents.add(
                new DefaultMutableTreeNode("Assignment.pdf")
            );

            documents.add(
                new DefaultMutableTreeNode("Notes.txt")
            );

            DefaultMutableTreeNode pictures =
                new DefaultMutableTreeNode("Pictures");

            pictures.add(
                new DefaultMutableTreeNode("Photo1.jpg")
            );

            pictures.add(
                new DefaultMutableTreeNode("Photo2.png")
            );

            DefaultMutableTreeNode projects =
                new DefaultMutableTreeNode("Projects");

            DefaultMutableTreeNode javaApp =
                new DefaultMutableTreeNode("JavaApp");

            javaApp.add(
                new DefaultMutableTreeNode("Main.java")
            );

            javaApp.add(
                new DefaultMutableTreeNode("README.md")
            );

            projects.add(javaApp);

            root.add(documents);
            root.add(pictures);
            root.add(projects);

            // -----------------------------------------
            // Create JTree
            // -----------------------------------------

            JTree tree = new JTree(root);

            tree.setRootVisible(true);
            tree.setShowsRootHandles(true);

            // -----------------------------------------
            // Information area
            // -----------------------------------------

            JTextArea infoArea =
                new JTextArea();

            infoArea.setEditable(false);
            infoArea.setFont(
                new Font("Arial", Font.PLAIN, 14)
            );

            infoArea.setText(
                "Select a file or folder from the tree."
            );

            // -----------------------------------------
            // Tree selection listener
            // -----------------------------------------

            tree.addTreeSelectionListener(e -> {

                TreePath path =
                    e.getPath();

                DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode)
                    path.getLastPathComponent();

                String nodeName =
                    node.getUserObject().toString();

                int level =
                    node.getLevel();

                infoArea.setText(
                    "Selected: " + nodeName
                    + "\nLevel: " + level
                );
            });

            // -----------------------------------------
            // Create tabs
            // -----------------------------------------

            JTabbedPane tabs =
                new JTabbedPane();

            JPanel generalPanel =
                new JPanel(
                    new BorderLayout()
                );

            generalPanel.add(
                new JLabel(
                    "General Information",
                    SwingConstants.CENTER
                ),
                BorderLayout.NORTH
            );

            JTextArea generalInfo =
                new JTextArea();

            generalInfo.setEditable(false);

            generalInfo.setText(
                "This is the General tab.\n\n"
                + "Use the tree on the left to explore "
                + "folders and files."
            );

            generalPanel.add(
                generalInfo,
                BorderLayout.CENTER
            );

            JPanel appearancePanel =
                new JPanel(
                    new BorderLayout()
                );

            appearancePanel.add(
                new JLabel(
                    "Appearance Settings",
                    SwingConstants.CENTER
                ),
                BorderLayout.NORTH
            );

            JTextArea appearanceInfo =
                new JTextArea();

            appearanceInfo.setEditable(false);

            appearanceInfo.setText(
                "This is the Appearance tab.\n\n"
                + "The application uses standard "
                + "Swing components."
            );

            appearancePanel.add(
                appearanceInfo,
                BorderLayout.CENTER
            );

            JPanel aboutPanel =
                new JPanel(
                    new BorderLayout()
                );

            aboutPanel.add(
                new JLabel(
                    "About File Explorer",
                    SwingConstants.CENTER
                ),
                BorderLayout.NORTH
            );

            JTextArea aboutInfo =
                new JTextArea();

            aboutInfo.setEditable(false);

            aboutInfo.setText(
                "File Explorer Demo\n\n"
                + "Built using Java Swing.\n"
                + "Demonstrates JTree, JTabbedPane "
                + "and JSplitPane."
            );

            aboutPanel.add(
                aboutInfo,
                BorderLayout.CENTER
            );

            tabs.addTab(
                "General",
                generalPanel
            );

            tabs.addTab(
                "Appearance",
                appearancePanel
            );

            tabs.addTab(
                "About",
                aboutPanel
            );

            // -----------------------------------------
            // Create split pane
            // -----------------------------------------

            JSplitPane splitPane =
                new JSplitPane(
                    JSplitPane.HORIZONTAL_SPLIT,
                    new JScrollPane(tree),
                    tabs
                );

            splitPane.setDividerLocation(250);

            // -----------------------------------------
            // Main layout
            // -----------------------------------------

            frame.setLayout(
                new BorderLayout()
            );

            frame.add(
                splitPane,
                BorderLayout.CENTER
            );

            frame.add(
                new JScrollPane(infoArea),
                BorderLayout.SOUTH
            );

            frame.setVisible(true);
        });
    }
}