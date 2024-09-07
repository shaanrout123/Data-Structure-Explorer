import javax.swing.*;
import java.awt.*;

public class Main {
    private static JTextArea stackResultArea;
    private static JTextArea queueResultArea;
    private static JTextArea singlyLinkedListResultArea;
    private static JTextArea doublyLinkedListResultArea;
    private static JTextArea binaryTreeResultArea;

    private static StackVisualizer stackVisualizer;
    private static QueueVisualizer queueVisualizer;
    private static SinglyLinkedListVisualizer singlyLinkedListVisualizer;
    private static DoublyLinkedListVisualizer doublyLinkedListVisualizer;
    private static BinaryTreeVisualizer binaryTreeVisualizer;

    private static JPanel controlPanel;

    public static void main(String[] args) {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Initialize result areas
        stackResultArea = new JTextArea(4, 30);
        stackResultArea.setEditable(false);
       
        queueResultArea = new JTextArea(4, 30);
        queueResultArea.setEditable(false);
        singlyLinkedListResultArea = new JTextArea(4, 30);
        singlyLinkedListResultArea.setEditable(false);
        doublyLinkedListResultArea = new JTextArea(4, 30);
        doublyLinkedListResultArea.setEditable(false);
        binaryTreeResultArea = new JTextArea(4, 30);
        binaryTreeResultArea.setEditable(false);

        // Initialize visualizers
        stackVisualizer = new StackVisualizer(stackResultArea);
        queueVisualizer = new QueueVisualizer(queueResultArea);
        singlyLinkedListVisualizer = new SinglyLinkedListVisualizer();
        doublyLinkedListVisualizer = new DoublyLinkedListVisualizer();
        binaryTreeVisualizer = new BinaryTreeVisualizer();

        // Add tabs
        tabbedPane.addTab("Stack Visualizer", stackVisualizer);
        tabbedPane.addTab("Queue Visualizer", queueVisualizer);
        tabbedPane.addTab("Singly Linked List Visualizer", singlyLinkedListVisualizer);
        tabbedPane.addTab("Doubly Linked List Visualizer", doublyLinkedListVisualizer);
        tabbedPane.addTab("Binary Tree Visualizer", binaryTreeVisualizer);

        controlPanel = new JPanel();
        updateControlPanel(tabbedPane.getSelectedIndex());

        tabbedPane.addChangeListener(e -> updateControlPanel(tabbedPane.getSelectedIndex()));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        JFrame frame = new JFrame("Data Structure Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private static void updateControlPanel(int selectedIndex) {
        controlPanel.removeAll(); // Clear existing buttons

        if (selectedIndex == 0) { // Stack Visualizer
            JTextField stackValueField = new JTextField(5);
            JButton stackPushButton = new JButton("Push Element");
            JButton stackPopButton = new JButton("Pop Element");
            JButton stackUndoButton = new JButton("Undo Pop");
            JButton stackPeekButton = new JButton("Peek");
            JButton stackClearButton = new JButton("Clear Stack");

            stackPushButton.addActionListener(e -> {
                try {
                    int value = Integer.parseInt(stackValueField.getText());
                    stackVisualizer.push(value);
                    stackValueField.setText("");
                } catch (NumberFormatException ex) {
                    stackResultArea.setText("Invalid input! Please enter a valid number.");
                }
            });

            stackPopButton.addActionListener(e -> stackVisualizer.pop());
            stackUndoButton.addActionListener(e -> stackVisualizer.undo());
            stackPeekButton.addActionListener(e -> stackVisualizer.peek());
            stackClearButton.addActionListener(e -> stackVisualizer.clear());

            controlPanel.add(new JLabel("Value:"));
            controlPanel.add(stackValueField);
            controlPanel.add(stackPushButton);
            controlPanel.add(stackPopButton);
            controlPanel.add(stackUndoButton);
            controlPanel.add(stackPeekButton);
            controlPanel.add(stackClearButton);

        } else if (selectedIndex == 1) { // Queue Visualizer
            JTextField queueValueField = new JTextField(5);
            JButton queueEnqueueButton = new JButton("Enqueue");
            JButton queueDequeueButton = new JButton("Dequeue");
            JButton queueUndoButton = new JButton("Undo Dequeue");
            JButton queuePeekButton = new JButton("Peek");
            JButton queueClearButton = new JButton("Clear Queue");

            queueEnqueueButton.addActionListener(e -> {
                try {
                    int value = Integer.parseInt(queueValueField.getText());
                    queueVisualizer.enqueue(value);
                    queueValueField.setText("");
                } catch (NumberFormatException ex) {
                    queueResultArea.setText("Invalid input! Please enter a valid number.");
                }
            });

            queueDequeueButton.addActionListener(e -> queueVisualizer.dequeue());
            queueUndoButton.addActionListener(e -> queueVisualizer.undo());
            queuePeekButton.addActionListener(e -> queueVisualizer.peek());
            queueClearButton.addActionListener(e -> queueVisualizer.clearQueue());

            controlPanel.add(new JLabel("Value:"));
            controlPanel.add(queueValueField);
            controlPanel.add(queueEnqueueButton);
            controlPanel.add(queueDequeueButton);
            controlPanel.add(queueUndoButton);
            controlPanel.add(queuePeekButton);
            controlPanel.add(queueClearButton);

        } else if (selectedIndex == 2) { // Singly Linked List Visualizer
            JTextField singlyLinkedListValueField = new JTextField(5);
            JTextField singlyLinkedListPositionField = new JTextField(3);
            JButton singlyLinkedListAddButton = new JButton("Add Node");
            JButton singlyLinkedListAddAtButton = new JButton("Add Node at Position");
            JButton singlyLinkedListRemoveButton = new JButton("Remove Node");
            JButton singlyLinkedListUndoButton = new JButton("Undo Remove");
            JButton singlyLinkedListPeekButton = new JButton("Peek");
            JButton singlyLinkedListClearButton = new JButton("Clear List");
            JButton singlyLinkedListSearchButton = new JButton("Search Node");

            singlyLinkedListAddButton.addActionListener(e -> {
                try {
                    int value = Integer.parseInt(singlyLinkedListValueField.getText());
                    singlyLinkedListVisualizer.addNode(value);
                    singlyLinkedListValueField.setText("");
                } catch (NumberFormatException ex) {
                    singlyLinkedListResultArea.setText("Invalid input! Please enter a valid number.");
                }
            });

            singlyLinkedListAddAtButton.addActionListener(e -> {
                try {
                    int value = Integer.parseInt(singlyLinkedListValueField.getText());
                    int position = Integer.parseInt(singlyLinkedListPositionField.getText());
                    singlyLinkedListVisualizer.addNode(value, position);
                    singlyLinkedListValueField.setText("");
                    singlyLinkedListPositionField.setText("");
                } catch (NumberFormatException ex) {
                    singlyLinkedListResultArea.setText("Invalid input! Please enter valid numbers.");
                }
            });

            singlyLinkedListRemoveButton.addActionListener(e -> {
                try {
                    int position = Integer.parseInt(singlyLinkedListPositionField.getText());
                    singlyLinkedListVisualizer.removeNode(position);
                    singlyLinkedListPositionField.setText("");
                } catch (NumberFormatException ex) {
                    singlyLinkedListResultArea.setText("Invalid input! Please enter a valid number.");
                }
            });

            singlyLinkedListUndoButton.addActionListener(e -> singlyLinkedListVisualizer.undoRemove());
            singlyLinkedListPeekButton.addActionListener(e -> singlyLinkedListResultArea.setText(singlyLinkedListVisualizer.peek()));
            singlyLinkedListClearButton.addActionListener(e -> singlyLinkedListVisualizer.clear());
            singlyLinkedListSearchButton.addActionListener(e -> {
                String searchText = singlyLinkedListValueField.getText();
                try {
                    int valueOrPosition = Integer.parseInt(searchText);
                    singlyLinkedListResultArea.setText(singlyLinkedListVisualizer.searchNode(valueOrPosition));
                } catch (NumberFormatException ex) {
                    singlyLinkedListResultArea.setText("Invalid input for search.");
                }
            });

            controlPanel.add(new JLabel("Value:"));
            controlPanel.add(singlyLinkedListValueField);
            controlPanel.add(new JLabel("Position:"));
            controlPanel.add(singlyLinkedListPositionField);
            controlPanel.add(singlyLinkedListAddButton);
            controlPanel.add(singlyLinkedListAddAtButton);
            controlPanel.add(singlyLinkedListRemoveButton);
            controlPanel.add(singlyLinkedListUndoButton);
            controlPanel.add(singlyLinkedListPeekButton);
            controlPanel.add(singlyLinkedListClearButton);
            controlPanel.add(singlyLinkedListSearchButton);

        } else if (selectedIndex == 3) { // Doubly Linked List Visualizer
            JTextField doublyLinkedListValueField = new JTextField(5);
            JTextField doublyLinkedListPositionField = new JTextField(3);
            JButton doublyLinkedListAddButton = new JButton("Add Node");
            JButton doublyLinkedListAddAtButton = new JButton("Add Node at Position");
            JButton doublyLinkedListRemoveButton = new JButton("Remove Node");
            JButton doublyLinkedListUndoButton = new JButton("Undo Remove");
            JButton doublyLinkedListPeekButton = new JButton("Peek");
            JButton doublyLinkedListClearButton = new JButton("Clear List");
            JButton doublyLinkedListSearchButton = new JButton("Search Node");

            doublyLinkedListAddButton.addActionListener(e -> {
                try {
                    int value = Integer.parseInt(doublyLinkedListValueField.getText());
                    doublyLinkedListVisualizer.addNode(value);
                    doublyLinkedListValueField.setText("");
                } catch (NumberFormatException ex) {
                    doublyLinkedListResultArea.setText("Invalid input! Please enter a valid number.");
                }
            });

            doublyLinkedListAddAtButton.addActionListener(e -> {
                try {
                    int value = Integer.parseInt(doublyLinkedListValueField.getText());
                    int position = Integer.parseInt(doublyLinkedListPositionField.getText());
                    doublyLinkedListVisualizer.addNode(value, position);
                    doublyLinkedListValueField.setText("");
                    doublyLinkedListPositionField.setText("");
                } catch (NumberFormatException ex) {
                    doublyLinkedListResultArea.setText("Invalid input! Please enter valid numbers.");
                }
            });

            doublyLinkedListRemoveButton.addActionListener(e -> {
                try {
                    int position = Integer.parseInt(doublyLinkedListPositionField.getText());
                    doublyLinkedListVisualizer.removeNode(position);
                    doublyLinkedListPositionField.setText("");
                } catch (NumberFormatException ex) {
                    doublyLinkedListResultArea.setText("Invalid input! Please enter a valid number.");
                }
            });

            doublyLinkedListUndoButton.addActionListener(e -> doublyLinkedListVisualizer.undoRemove());
            doublyLinkedListPeekButton.addActionListener(e -> doublyLinkedListResultArea.setText(doublyLinkedListVisualizer.peek()));
            doublyLinkedListClearButton.addActionListener(e -> doublyLinkedListVisualizer.clear());
            doublyLinkedListSearchButton.addActionListener(e -> {
                String searchText = doublyLinkedListValueField.getText();
                try {
                    int valueOrPosition = Integer.parseInt(searchText);
                    doublyLinkedListResultArea.setText(doublyLinkedListVisualizer.searchNode(valueOrPosition));
                } catch (NumberFormatException ex) {
                    doublyLinkedListResultArea.setText("Invalid input for search.");
                }
            });

            controlPanel.add(new JLabel("Value:"));
            controlPanel.add(doublyLinkedListValueField);
            controlPanel.add(new JLabel("Position:"));
            controlPanel.add(doublyLinkedListPositionField);
            controlPanel.add(doublyLinkedListAddButton);
            controlPanel.add(doublyLinkedListAddAtButton);
            controlPanel.add(doublyLinkedListRemoveButton);
            controlPanel.add(doublyLinkedListUndoButton);
            controlPanel.add(doublyLinkedListPeekButton);
            controlPanel.add(doublyLinkedListClearButton);
            controlPanel.add(doublyLinkedListSearchButton);

        } else if (selectedIndex == 4) { // Binary Tree Visualizer
            JTextField binaryTreeValueField = new JTextField(5);
            JButton binaryTreeAddButton = new JButton("Add Node");
            JButton binaryTreeRemoveButton = new JButton("Remove Node");
            JButton binaryTreeSearchButton = new JButton("Search Node");
            JButton binaryTreeClearButton = new JButton("Clear Tree");

            binaryTreeAddButton.addActionListener(e -> {
                try {
                    // Parse the integer value from the text field
                    int value = Integer.parseInt(binaryTreeValueField.getText());
                    
                    // Insert the value into the binary tree
                    binaryTreeVisualizer.insert(value);
                    
                    // Clear the text field after insertion
                    binaryTreeValueField.setText("");
                    
                    // Update the result area with the comparison result
                    binaryTreeResultArea.setText(binaryTreeVisualizer.comparisonResult.toString());
                } catch (NumberFormatException ex) {
                    // Display error message if input is not a valid number
                    binaryTreeResultArea.setText("Invalid input! Please enter a valid number.");
                }
            });
            

            binaryTreeRemoveButton.addActionListener(e -> {
                try {
                    int value = Integer.parseInt(binaryTreeValueField.getText());
                    binaryTreeVisualizer.remove(value);
                    binaryTreeValueField.setText("");
                } catch (NumberFormatException ex) {
                    binaryTreeResultArea.setText("Invalid input! Please enter a valid number.");
                }
            });

            binaryTreeSearchButton.addActionListener(e -> {
                try {
                    int value = Integer.parseInt(binaryTreeValueField.getText());
                    binaryTreeVisualizer.search(value);
                    String result = binaryTreeVisualizer.getSearchResult();
                    binaryTreeResultArea.setText(result);
                } catch (NumberFormatException ex) {
                    binaryTreeResultArea.setText("Invalid input! Please enter a valid number.");
                }
            });
            

            binaryTreeClearButton.addActionListener(e -> binaryTreeVisualizer.clear());

            controlPanel.add(new JLabel("Value:"));
            controlPanel.add(binaryTreeValueField);
            controlPanel.add(binaryTreeAddButton);
            controlPanel.add(binaryTreeRemoveButton);
            controlPanel.add(binaryTreeSearchButton);
            controlPanel.add(binaryTreeClearButton);
        }

        controlPanel.revalidate(); // Refresh the panel
        controlPanel.repaint(); // Repaint the panel
    }
}
