import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class sortingvisualization extends JFrame {

    public static final int ARRAY_SIZE = 10000;
    public static final int SLEEP_TIME = 50; // Adjust this for the sorting speed (lower value for faster animation)
    public static final int WINDOW_WIDTH = 10000000;
    public static final int WINDOW_HEIGHT = 10000000;
    public static final int NUM_BARS = 1000000;
    public static final Random rand = new Random();

    public Integer[] array;
    public DrawingPanel drawingPanel;
    public int[] indices;
    public int[] barHeights;

    public sortingvisualization(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        initializeArray();
        initializeDrawingPanel();

        add(drawingPanel);

        startAnimation(); // Start the animation
    }

    public void initializeArray() {
        array = new Integer[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = i + 1;
        }
        shuffleArray();
    }

    public void shuffleArray() {
        Collections.shuffle(Arrays.asList(array));
    }

    public void initializeDrawingPanel() {
        drawingPanel = new DrawingPanel();
        drawingPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT)); // Set preferred size
    }

    public void startAnimation() {
        barHeights = new int[NUM_BARS];
        Timer timer = new Timer(100, e -> {
            // Simulate bars changing position (e.g., increment Y position)
            for (int i = 0; i < NUM_BARS; i++) {
                barHeights[i] += rand.nextInt(10) - 5; // Random change (-5 to +5)
                barHeights[i] = Math.max(0, barHeights[i]); // Ensure non-negative heights
            }
            drawingPanel.repaint(); // Repaint the panel
        });
        timer.start();
    }

    public void visualizeSortingAlgorithm(SortingAlgorithm sortingAlgorithm) {
        new Thread(() -> {
            sortingAlgorithm.sort(array, this);
            drawingPanel.repaint();
        }).start();
    }

    public void setHighlightedIndices(int[] indices) {
        this.indices = indices;
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        drawingPanel.repaint();
    }

    public static void main(String[] args) {
        sortingvisualization visualization = new sortingvisualization("Sorting Visualization");
        visualization.setVisible(true);

        BubbleSort bubbleSort = new BubbleSort();
        visualization.visualizeSortingAlgorithm(bubbleSort);
    }

    public class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            int barWidth = width / ARRAY_SIZE;

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            g.setColor(Color.BLACK);
            for (int i = 0; i < ARRAY_SIZE; i++) {
                int x = i * barWidth;
                int barHeight = (array[i] * height) / ARRAY_SIZE;
                int y = height - barHeight;
                g.fillRect(x, y, barWidth, barHeight);
            }

            if (indices != null) {
                g.setColor(Color.RED);
                for (int index : indices) {
                    int x = index * barWidth;
                    int barHeight = (array[index] * height) / ARRAY_SIZE;
                    int y = height - barHeight;
                    g.fillRect(x, y, barWidth, barHeight);
                }
            }
        }
    }
}

interface SortingAlgorithm {
    void sort(Integer[] array, sortingvisualization visualization);
}

class BubbleSort implements SortingAlgorithm {

    @Override
    public void sort(Integer[] array, sortingvisualization visualization) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
                visualization.setHighlightedIndices(new int[]{j, j + 1});
            }
        }
    }
}