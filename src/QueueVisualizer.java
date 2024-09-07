import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class QueueVisualizer extends JPanel {
    private Queue<Integer> queue = new LinkedList<>();
    private Stack<Integer> historyStack = new Stack<>(); // To track dequeued elements for undo
    private int maxSize = 10;
    private JTextArea resultArea;

    public QueueVisualizer(JTextArea resultArea) {
        this.resultArea = resultArea;
        setPreferredSize(new Dimension(400, 300));
    }

    public void enqueue(int value) {
        if (queue.size() < maxSize) {
            queue.offer(value);
            historyStack.clear(); // Clear history when a new element is enqueued
            resultArea.setText("Enqueued: " + value + "\nCurrent Queue: " + queue.toString());
            repaint();
        } else {
            resultArea.setText("Queue is full! Max size is " + maxSize);
        }
    }

    public void dequeue() {
        if (!queue.isEmpty()) {
            int value = queue.poll();
            historyStack.push(value); // Store dequeued value in history for undo
            resultArea.setText("Dequeued: " + value + "\nCurrent Queue: " + queue.toString());
            repaint();
        } else {
            resultArea.setText("Queue is empty!");
        }
    }

    public void undo() {
        if (!historyStack.isEmpty()) {
            int value = historyStack.pop();
            if (queue.size() < maxSize) {
                queue.offer(value); // Re-enqueue the last dequeued value
                resultArea.setText("Undid dequeue: " + value + "\nCurrent Queue: " + queue.toString());
                repaint();
            } else {
                resultArea.setText("Cannot undo! Queue is full.");
            }
        } else {
            resultArea.setText("No operation to undo!");
        }
    }

    public void peek() {
        if (!queue.isEmpty()) {
            resultArea.setText("Front element: " + queue.peek());
        } else {
            resultArea.setText("Queue is empty!");
        }
    }

    public void clearQueue() {
        queue.clear();
        historyStack.clear(); // Clear history as well
        resultArea.setText("Queue cleared!");
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int width = 50;
        int height = 30;
        int startX = 50;
        int startY = getHeight() / 2;

        int i = 0;
        for (Integer value : queue) {
            g.drawRect(startX + i * width, startY - height / 2, width, height);
            g.drawString(value.toString(), startX + i * width + 20, startY);

            // Draw the position label
            g.drawString("Pos " + (i + 1), startX + i * width, startY - height);
            i++;
        }
    }

    public static void main(String[] args) {
        /* JFrame frame = new JFrame("Queue Visualizer");
        JTextArea resultArea = new JTextArea(4, 30);
        resultArea.setEditable(false);
        QueueVisualizer visualizer = new QueueVisualizer(resultArea);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(visualizer, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        JTextField valueField = new JTextField(5);
        JButton enqueueButton = new JButton("Enqueue");
        JButton dequeueButton = new JButton("Dequeue");
        JButton undoButton = new JButton("Undo Dequeue");
        JButton peekButton = new JButton("Peek");
        JButton clearButton = new JButton("Clear Queue");

        enqueueButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(valueField.getText());
                visualizer.enqueue(value);
                valueField.setText("");
            } catch (NumberFormatException ex) {
                resultArea.setText("Invalid input! Please enter a valid number.");
            }
        });

        dequeueButton.addActionListener(e -> visualizer.dequeue());

        undoButton.addActionListener(e -> visualizer.undo());

        peekButton.addActionListener(e -> visualizer.peek());

        clearButton.addActionListener(e -> visualizer.clearQueue());

        controlPanel.add(new JLabel("Value:"));
        controlPanel.add(valueField);
        controlPanel.add(enqueueButton);
        controlPanel.add(dequeueButton);
        controlPanel.add(undoButton);
        controlPanel.add(peekButton);
        controlPanel.add(clearButton);

        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true); */ 
    }
}