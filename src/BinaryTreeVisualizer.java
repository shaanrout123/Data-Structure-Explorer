/* import javax.swing.*;
import java.awt.*;
import java.util.Stack;

class TreeNode {
    int value;
    TreeNode left, right;

    public TreeNode(int item) {
        value = item;
        left = right = null;
    }
}

public class BinaryTreeVisualizer extends JPanel {
    private TreeNode root;
    private String searchResult = "";
    private StringBuilder comparisonResult = new StringBuilder();
    private Stack<TreeNode> undoStack = new Stack<>();
    private Stack<TreeNode> redoStack = new Stack<>();

    public BinaryTreeVisualizer() {
        setPreferredSize(new Dimension(600, 400));
    }

    public void insert(int value) {
        saveState(); // Save current state for undo
        comparisonResult.setLength(0); // Clear previous comparisons
        root = insertRec(root, value);
        repaint();
    }

    private TreeNode insertRec(TreeNode root, int value) {
        if (root == null) {
            comparisonResult.append("Inserted ").append(value).append(" at root.\n");
            return new TreeNode(value);
        }
        if (value < root.value) {
            comparisonResult.append("Comparing ").append(value).append(" with ").append(root.value).append(": go left.\n");
            root.left = insertRec(root.left, value);
        } else if (value > root.value) {
            comparisonResult.append("Comparing ").append(value).append(" with ").append(root.value).append(": go right.\n");
            root.right = insertRec(root.right, value);
        }
        return root;
    }

    public void remove(int value) {
        saveState(); // Save current state for undo
        comparisonResult.setLength(0); // Clear previous comparisons
        root = removeRec(root, value);
        repaint();
    }

    private TreeNode removeRec(TreeNode root, int value) {
        if (root == null) {
            comparisonResult.append("Value ").append(value).append(" not found.\n");
            return null;
        }
        if (value < root.value) {
            comparisonResult.append("Comparing ").append(value).append(" with ").append(root.value).append(": go left.\n");
            root.left = removeRec(root.left, value);
        } else if (value > root.value) {
            comparisonResult.append("Comparing ").append(value).append(" with ").append(root.value).append(": go right.\n");
            root.right = removeRec(root.right, value);
        } else {
            comparisonResult.append("Found ").append(value).append(".\n");
            if (root.left == null)
                return root.right;
            if (root.right == null)
                return root.left;
            TreeNode temp = minValueNode(root.right);
            root.value = temp.value;
            root.right = removeRec(root.right, temp.value);
        }
        return root;
    }

    private TreeNode minValueNode(TreeNode root) {
        TreeNode current = root;
        while (current.left != null)
            current = current.left;
        return current;
    }

    public void search(int value) {
        boolean found = searchRec(root, value);
        searchResult=found ? "Node found" : "Node not found";
        repaint();
    }
    public String getSearchResult(){
        return searchResult;
    }

    private boolean searchRec(TreeNode root, int value) {
        if (root == null) {
            comparisonResult.append("Value ").append(value).append(" not found.\n");
            return false;
        }
        comparisonResult.append("Comparing ").append(value).append(" with ").append(root.value).append(".\n");
        if (root.value == value) {
            comparisonResult.append("Value ").append(value).append(" found.\n");
            return true;
        }
        if (value < root.value) {
            return searchRec(root.left, value);
        } else {
            return searchRec(root.right, value);
        }
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            redoStack.push(root); // Save current state for redo
            root = undoStack.pop(); // Restore previous state
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No action to undo!", "Undo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            undoStack.push(root); // Save current state for undo
            root = redoStack.pop(); // Restore next state
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No action to redo!", "Redo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void clear() {
        saveState(); // Save current state for undo
        root = null;
        comparisonResult.setLength(0);
        repaint();
    }

    private void saveState() {
        // Save a deep copy of the current state for undo functionality
        undoStack.push(copyTree(root));
        redoStack.clear(); // Clear redo stack on new action
    }

    private TreeNode copyTree(TreeNode node) {
        if (node == null) return null;
        TreeNode newNode = new TreeNode(node.value);
        newNode.left = copyTree(node.left);
        newNode.right = copyTree(node.right);
        return newNode;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        drawTree(g, root, getWidth() / 2, 30, getWidth() / 4);
    }

    private void drawTree(Graphics g, TreeNode root, int x, int y, int xOffset) {
        if (root != null) {
            g.drawOval(x - 15, y - 15, 30, 30);
            g.drawString(Integer.toString(root.value), x - 10, y);
            if (root.left != null) {
                g.drawLine(x - 5, y + 5, x - xOffset + 5, y + 50 - 5);
                drawTree(g, root.left, x - xOffset, y + 50, xOffset / 2);
            }
            if (root.right != null) {
                g.drawLine(x + 5, y + 5, x + xOffset - 5, y + 50 - 5);
                drawTree(g, root.right, x + xOffset, y + 50, xOffset / 2);
            }
        }
    }

    public static void main(String[] args) {
        
        /* SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Binary Tree Visualizer");
            BinaryTreeVisualizer visualizer = new BinaryTreeVisualizer();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(visualizer, BorderLayout.CENTER);

            JPanel controlPanel = new JPanel();
            JTextField valueField = new JTextField(5);
            JButton insertButton = new JButton("Insert Node");
            JButton removeButton = new JButton("Remove Node");
            JButton searchButton = new JButton("Search Node");
            JButton undoButton = new JButton("Undo");
            JButton redoButton = new JButton("Redo");
            JButton clearButton = new JButton("Clear Tree");
            JTextArea resultArea = new JTextArea(10, 30);
            resultArea.setEditable(false);

            insertButton.addActionListener(e -> {
                try {
                    int value = Integer.parseInt(valueField.getText());
                    visualizer.insert(value);
                    resultArea.setText(visualizer.comparisonResult.toString());
                    valueField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid value!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            removeButton.addActionListener(e -> {
                try {
                    int value = Integer.parseInt(valueField.getText());
                    visualizer.remove(value);
                    resultArea.setText(visualizer.comparisonResult.toString());
                    valueField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid value!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            searchButton.addActionListener(e -> {
                try {
                    int value = Integer.parseInt(valueField.getText());
                    visualizer.search(value);
                    valueField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid value!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                resultArea.setText(visualizer.comparisonResult.toString());
            });

            undoButton.addActionListener(e -> {
                visualizer.undo();
                resultArea.setText(visualizer.comparisonResult.toString());
            });

            redoButton.addActionListener(e -> {
                visualizer.redo();
                resultArea.setText(visualizer.comparisonResult.toString());
            });

            clearButton.addActionListener(e -> {
                visualizer.clear();
                resultArea.setText("Tree cleared.");
            });

            controlPanel.add(new JLabel("Value:"));
            controlPanel.add(valueField);
            controlPanel.add(insertButton);
            controlPanel.add(removeButton);
            controlPanel.add(searchButton);
            controlPanel.add(undoButton);
            controlPanel.add(redoButton);
            controlPanel.add(clearButton);

            frame.add(controlPanel, BorderLayout.SOUTH);
            frame.add(new JScrollPane(resultArea), BorderLayout.NORTH);
            frame.pack();
            frame.setVisible(true);
        });
         
    }
}
    */
    import javax.swing.*;
    import java.awt.*;
    import java.util.Stack;
    
    class TreeNode {
        int value;
        TreeNode left, right;
    
        public TreeNode(int item) {
            value = item;
            left = right = null;
        }
    }
    
    public class BinaryTreeVisualizer extends JPanel {
        private TreeNode root;
        private String searchResult = "";
        StringBuilder comparisonResult = new StringBuilder();
        private Stack<TreeNode> undoStack = new Stack<>();
        private Stack<TreeNode> redoStack = new Stack<>();
    
        public BinaryTreeVisualizer() {
            setPreferredSize(new Dimension(600, 400));
        }
    
        public void insert(int value) {
            saveState(); // Save current state for undo
            comparisonResult.setLength(0); // Clear previous comparisons
            root = insertRec(root, value);
            repaint();
        }
    
        private TreeNode insertRec(TreeNode root, int value) {
            if (root == null) {
                comparisonResult.append("Inserted ").append(value).append(" at root.\n");
                return new TreeNode(value);
            }
            if (value < root.value) {
                comparisonResult.append("Comparing ").append(value).append(" with ").append(root.value).append(": go left.\n");
                root.left = insertRec(root.left, value);
            } else if (value > root.value) {
                comparisonResult.append("Comparing ").append(value).append(" with ").append(root.value).append(": go right.\n");
                root.right = insertRec(root.right, value);
            }
            return root;
        }
    
        public void remove(int value) {
            saveState(); // Save current state for undo
            comparisonResult.setLength(0); // Clear previous comparisons
            root = removeRec(root, value);
            repaint();
        }
    
        private TreeNode removeRec(TreeNode root, int value) {
            if (root == null) {
                comparisonResult.append("Value ").append(value).append(" not found.\n");
                return null;
            }
            if (value < root.value) {
                comparisonResult.append("Comparing ").append(value).append(" with ").append(root.value).append(": go left.\n");
                root.left = removeRec(root.left, value);
            } else if (value > root.value) {
                comparisonResult.append("Comparing ").append(value).append(" with ").append(root.value).append(": go right.\n");
                root.right = removeRec(root.right, value);
            } else {
                comparisonResult.append("Found ").append(value).append(".\n");
                if (root.left == null)
                    return root.right;
                if (root.right == null)
                    return root.left;
                TreeNode temp = minValueNode(root.right);
                root.value = temp.value;
                root.right = removeRec(root.right, temp.value);
            }
            return root;
        }
    
        private TreeNode minValueNode(TreeNode root) {
            TreeNode current = root;
            while (current.left != null)
                current = current.left;
            return current;
        }
    
        public void search(int value) {
            searchResult = searchRec(root, value) ? "Node found" : "Node not found";
            repaint();
        }
    
        public String getSearchResult() {
            return searchResult;
        }
    
        private boolean searchRec(TreeNode root, int value) {
            if (root == null) {
                comparisonResult.append("Value ").append(value).append(" not found.\n");
                return false;
            }
            comparisonResult.append("Comparing ").append(value).append(" with ").append(root.value).append(".\n");
            if (root.value == value) {
                comparisonResult.append("Value ").append(value).append(" found.\n");
                return true;
            }
            if (value < root.value) {
                return searchRec(root.left, value);
            } else {
                return searchRec(root.right, value);
            }
        }
    
        public void undo() {
            if (!undoStack.isEmpty()) {
                redoStack.push(root); // Save current state for redo
                root = undoStack.pop(); // Restore previous state
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "No action to undo!", "Undo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    
        public void redo() {
            if (!redoStack.isEmpty()) {
                undoStack.push(root); // Save current state for undo
                root = redoStack.pop(); // Restore next state
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "No action to redo!", "Redo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    
        public void clear() {
            saveState(); // Save current state for undo
            root = null;
            comparisonResult.setLength(0);
            repaint();
        }
    
        private void saveState() {
            // Save a deep copy of the current state for undo functionality
            undoStack.push(copyTree(root));
            redoStack.clear(); // Clear redo stack on new action
        }
    
        private TreeNode copyTree(TreeNode node) {
            if (node == null) return null;
            TreeNode newNode = new TreeNode(node.value);
            newNode.left = copyTree(node.left);
            newNode.right = copyTree(node.right);
            return newNode;
        }
    
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            drawTree(g, root, getWidth() / 2, 30, getWidth() / 4);
        }
    
        private void drawTree(Graphics g, TreeNode root, int x, int y, int xOffset) {
            if (root != null) {
                g.drawOval(x - 15, y - 15, 30, 30);
                g.drawString(Integer.toString(root.value), x - 10, y);
                if (root.left != null) {
                    g.drawLine(x - 5, y + 5, x - xOffset + 5, y + 50 - 5);
                    drawTree(g, root.left, x - xOffset, y + 50, xOffset / 2);
                }
                if (root.right != null) {
                    g.drawLine(x + 5, y + 5, x + xOffset - 5, y + 50 - 5);
                    drawTree(g, root.right, x + xOffset, y + 50, xOffset / 2);
                }
            }
        }
    }
    
