import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class SinglyLinkedListVisualizer extends JPanel {
    private LinkedList<Integer> list = new LinkedList<>();
    private Integer lastRemovedValue = null;
    private Integer lastRemovedPosition = null;

    public SinglyLinkedListVisualizer() {
        setPreferredSize(new Dimension(500, 300));
    }

    public void addNode(int value) {
        list.add(value);
        repaint();
    }

    public void addNode(int value, int position) {
        if (position < 1 || position > list.size() + 1) {
            JOptionPane.showMessageDialog(this, "Invalid position!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        list.add(position - 1, value);
        repaint();
    }

    public void removeNode(int position) {
        if (position < 1 || position > list.size()) {
            JOptionPane.showMessageDialog(this, "Invalid position!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        lastRemovedValue = list.remove(position - 1);
        lastRemovedPosition = position; // Store the position of the removed node
        repaint();
    }

    public void undoRemove() {
        if (lastRemovedValue != null && lastRemovedPosition != null) {
            // Restore the node at its original position
            list.add(lastRemovedPosition - 1, lastRemovedValue);
            lastRemovedValue = null; // Reset the last removed value
            lastRemovedPosition = null; // Reset the last removed position
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No node to undo!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String searchNode(int value) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == value) {
                result.append("Value ").append(value).append(" found at position ").append(i + 1).append("\n");
            }
        }
        if (result.length() == 0) {
            return "Value " + value + " not found in the list.";
        }
        return result.toString();
    }

    public String searchNodeByPosition(int position) {
        if (position < 1 || position > list.size()) {
            return "Invalid position!";
        }
        return "Value at position " + position + " is " + list.get(position - 1) + ".";
    }

    public String peek() {
        if (list.isEmpty()) {
            return "List is empty!";
        }
        return "Front element is " + list.getFirst() + ".";
    }

    public void clear() {
        list.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int width = 100;
        int height = 40;
        int startX = 50;
        int startY = getHeight() / 2;

        for (int i = 0; i < list.size(); i++) {
            // Draw the node box (data part)
            g.drawRect(startX + i * width, startY - height / 2, width / 2, height);
            g.drawString(list.get(i).toString(), startX + i * width + 15, startY);

            // Draw the next part (arrow)
            g.drawRect(startX + i * width + width / 2, startY - height / 2, width / 2, height);
            if (i < list.size() - 1) {
                g.drawLine(startX + i * width + width, startY, startX + (i + 1) * width, startY);
                g.drawLine(startX + i * width + width - 10, startY - 10, startX + i * width + width, startY);
                g.drawLine(startX + i * width + width - 10, startY + 10, startX + i * width + width, startY);
            }

            // Draw the index label
            g.drawString("Pos " + (i + 1), startX + i * width + 20, startY - height);
        }
    }

    public static void main(String[] args) {
        
        /* SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Singly Linked List Visualizer");
            SinglyLinkedListVisualizer visualizer = new SinglyLinkedListVisualizer();

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.add(visualizer, BorderLayout.CENTER);

            JPanel controlPanel = new JPanel();
            JTextField valueField = new JTextField(5);
            JTextField positionField = new JTextField(3);
            JTextField searchField = new JTextField(5);
            JButton addButton = new JButton("Add Node");
            JButton removeButton = new JButton("Remove Node");
            JButton undoButton = new JButton("Undo Remove");
            JButton searchButton = new JButton("Search Value");
            JButton peekButton = new JButton("Peek");
            JButton clearButton = new JButton("Clear Linked List");
            JTextArea resultArea = new JTextArea(4, 30);
            resultArea.setEditable(false);

            addButton.addActionListener(e -> {
                try {
                    int value = Integer.parseInt(valueField.getText());
                    String positionText = positionField.getText();
                    if (positionText.isEmpty()) {
                        visualizer.addNode(value);
                    } else {
                        int position = Integer.parseInt(positionText);
                        visualizer.addNode(value, position);
                    }
                    valueField.setText("");
                    positionField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid value or position!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            removeButton.addActionListener(e -> {
                try {
                    int position = Integer.parseInt(positionField.getText());
                    visualizer.removeNode(position);
                    positionField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid position!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            undoButton.addActionListener(e -> visualizer.undoRemove());

            searchButton.addActionListener(e -> {
                String searchText = searchField.getText();
                try {
                    int valueOrPosition = Integer.parseInt(searchText);
                    String result;
                    if (searchText.matches("\\d+")) {
                        result = visualizer.searchNode(valueOrPosition);
                    } else {
                        result = visualizer.searchNodeByPosition(valueOrPosition);
                    }
                    resultArea.setText(result);
                } catch (NumberFormatException ex) {
                    resultArea.setText("Invalid input for search.");
                }
            });

            peekButton.addActionListener(e -> resultArea.setText(visualizer.peek()));

            clearButton.addActionListener(e -> {
                visualizer.clear();
                resultArea.setText("List cleared!");
            });

            controlPanel.add(new JLabel("Value:"));
            controlPanel.add(valueField);
            controlPanel.add(new JLabel("Position:"));
            controlPanel.add(positionField);
            controlPanel.add(addButton);
            controlPanel.add(removeButton);
            controlPanel.add(undoButton);
            controlPanel.add(new JLabel("Search:"));
            controlPanel.add(searchField);
            controlPanel.add(searchButton);
            controlPanel.add(peekButton);
            controlPanel.add(clearButton);

            frame.add(controlPanel, BorderLayout.SOUTH);
            frame.add(new JScrollPane(resultArea), BorderLayout.NORTH);
            frame.pack();
            frame.setVisible(true);
        }); */
    }
        
}

 