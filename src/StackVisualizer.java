import javax.swing.*;
import java.awt.*;
import java.util.Stack;
import java.util.EmptyStackException;

public class StackVisualizer extends JPanel {
    private Stack<Integer> stack = new Stack<>();
    private Stack<Integer> historyStack = new Stack<>(); // To keep track of popped elements
    private JTextArea resultArea;
    private int maxSize = 10;

    public StackVisualizer(JTextArea resultArea) {
        this.resultArea = resultArea;
        setPreferredSize(new Dimension(400, 300));
    }

    public void push(int value) {
        if (stack.size() < maxSize) {
            stack.push(value);
            historyStack.clear(); // Clear history when a new element is pushed
            resultArea.setText("Pushed: " + value + "\nCurrent Stack: " + stack.toString());
            repaint();
        } else {
            resultArea.setText("Stack is full! Max size is " + maxSize);
        }
    }

    public void pop() {
        try {
            int value = stack.pop();
            historyStack.push(value); // Store popped value in history for undo
            resultArea.setText("Popped: " + value + "\nCurrent Stack: " + stack.toString());
            repaint();
        } catch (EmptyStackException e) {
            resultArea.setText("Stack is empty!");
        }
    }

    public void undo() {
        try {
            int value = historyStack.pop(); // Get the last popped value from history
            stack.push(value); // Push it back to the stack
            resultArea.setText("Undid pop: " + value + "\nCurrent Stack: " + stack.toString());
            repaint();
        } catch (EmptyStackException e) {
            resultArea.setText("No operation to undo!");
        }
    }

    public void peek() {
        if (!stack.isEmpty()) {
            resultArea.setText("Top element: " + stack.peek());
        } else {
            resultArea.setText("Stack is empty!");
        }
    }

    public void clear() {
        stack.clear();
        historyStack.clear(); // Clear history as well
        resultArea.setText("Stack cleared!");
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int width = 50;
        int height = 30;
        int startX = getWidth() / 2 - width / 2;
        int startY = getHeight() - 50;

        for (int i = 0; i < stack.size(); i++) {
            // Draw the stack box
            g.drawRect(startX, startY - i * height, width, height);
            g.drawString(stack.get(i).toString(), startX + 20, startY - i * height + 20);

            // Draw the index label
            g.drawString("Index " + (i + 1), startX - 50, startY - i * height + 20);
        }

        // Draw the "Top" label
        if (!stack.isEmpty()) {
            g.drawString("Top", startX - 50, startY - stack.size() * height + 20);
        }
    }

    public static void main(String[] args) {
        /* JFrame frame = new JFrame("Stack Visualizer");
        JTextArea resultArea = new JTextArea(4, 30);
        resultArea.setEditable(false);
        StackVisualizer visualizer = new StackVisualizer(resultArea);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(visualizer, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JTextField valueField = new JTextField(5);
        JButton pushButton = new JButton("Push Element");
        JButton popButton = new JButton("Pop Element");
        JButton undoButton = new JButton("Undo Pop");
        JButton peekButton = new JButton("Peek");
        JButton clearButton = new JButton("Clear stack");

        pushButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int value = Integer.parseInt(valueField.getText());
                    visualizer.push(value);
                    valueField.setText("");
                } catch (NumberFormatException ex) {
                    resultArea.setText("Invalid input! Please enter a valid number.");
                }
            }
        });

        popButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizer.pop();
            }
        });

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizer.undo();
            }
        });

        peekButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizer.peek();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visualizer.clear();
            }
        });

        controlPanel.add(new JLabel("Value:"));
        controlPanel.add(valueField);
        controlPanel.add(pushButton);
        controlPanel.add(popButton);
        controlPanel.add(undoButton);
        controlPanel.add(peekButton);
        controlPanel.add(clearButton);

        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true); */
    }
}
