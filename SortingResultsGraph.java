import data.structure.assignment_3.DataStructureAssignment_3;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;

public class SortingResultsGraph extends JFrame {

    public SortingResultsGraph(String title) {
        super(title);

        // Prepare data
        String[] arrayTypes = {"Sorted Array", "Random Array", "Reverse Array"};

        // Create and set up the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1800, 1200);
        setLayout(new GridLayout(3, 3));

        // Create and add charts to the frame for each sorting technique
        add(createChartPanel(arrayTypes, "Bubble Sort", "Run Time (ms)", Color.CYAN));
        add(createChartPanel(arrayTypes, "Bubble Sort", "Comparisons", Color.CYAN));
        add(createChartPanel(arrayTypes, "Bubble Sort", "Interchanges", Color.CYAN));

        add(createChartPanel(arrayTypes, "Quick Sort", "Run Time (ms)", Color.MAGENTA));
        add(createChartPanel(arrayTypes, "Quick Sort", "Comparisons", Color.MAGENTA));
        add(createChartPanel(arrayTypes, "Quick Sort", "Interchanges", Color.MAGENTA));

        add(createChartPanel(arrayTypes, "Counting Sort", "Run Time (ms)", Color.BLUE));
        add(createChartPanel(arrayTypes, "Counting Sort", "Comparisons", Color.BLUE));
        add(createChartPanel(arrayTypes, "Counting Sort", "Interchanges", Color.BLUE));
    }

    private ChartPanel createChartPanel(String[] arrayTypes, String sortType, String yAxisLabel, Color color) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int[][] results = getResults(sortType, yAxisLabel);

        for (int i = 0; i < arrayTypes.length; i++) {
            dataset.addValue(results[i][0], sortType, arrayTypes[i]);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                sortType + " - " + yAxisLabel, // Chart title
                "Array Type",                  // X-axis label
                yAxisLabel,                    // Y-axis label
                dataset                        // Dataset
        );
        customizeBarRenderer(chart, color);
        return new ChartPanel(chart);
    }

    private void customizeBarRenderer(JFreeChart chart, Color color) {
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, color);
    }

    private int[][] getResults(String sortType, String yAxisLabel) {
        int[][] results = new int[3][3];
        for (int i = 0; i < 3; i++) {
            Integer[] array = generateArray(i);
            switch (yAxisLabel) {
                case "Run Time (ms)":
                    results[i] = runtimeResults(sortType, array.clone());
                    break;
                case "Comparisons":
                    results[i] = comparisonResults(sortType, array.clone());
                    break;
                case "Interchanges":
                    results[i] = interchangeResults(sortType, array.clone());
                    break;
            }
        }
        return results;
    }

    private Integer[] generateArray(int type) {
        int size = 10000;
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            switch (type) {
                case 0:
                    array[i] = i + 1;  // Sorted Array
                    break;
                case 1:
                    array[i] = (int) (Math.random() * size) + 1;  // Random Array
                    break;
                case 2:
                    array[i] = size - i;  // Reverse Array
                    break;
            }
        }
        return array;
    }

    private int[] runtimeResults(String sortType, Integer[] array) {
        long startTime = System.nanoTime();
        switch (sortType) {
            case "Bubble Sort":
                DataStructureAssignment_3.bubbleSort(array, "Dummy Array");
                break;
            case "Quick Sort":
                DataStructureAssignment_3.quickSort(array, "Dummy Array");
                break;
            case "Counting Sort":
                DataStructureAssignment_3.countingSort(array, "Dummy Array");
                break;
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        return new int[]{(int) duration, 0, 0}; // Runtime results, no comparisons or interchanges
    }

    private int[] comparisonResults(String sortType, Integer[] array) {
        switch (sortType) {
            case "Bubble Sort":
                DataStructureAssignment_3.bubbleSort(array, "Dummy Array");
                break;
            case "Quick Sort":
                DataStructureAssignment_3.quickSort(array, "Dummy Array");
                break;
            case "Counting Sort":
                DataStructureAssignment_3.countingSort(array, "Dummy Array");
                break;
        }
        return new int[]{DataStructureAssignment_3.comparisons, 0, 0}; // Comparison results, no runtime or interchanges
    }

    private int[] interchangeResults(String sortType, Integer[] array) {
        switch (sortType) {
            case "Bubble Sort":
                DataStructureAssignment_3.bubbleSort(array, "Dummy Array");
                break;
            case "Quick Sort":
                DataStructureAssignment_3.quickSort(array, "Dummy Array");
                break;
            case "Counting Sort":
                DataStructureAssignment_3.countingSort(array, "Dummy Array");
                break;
        }
        return new int[]{DataStructureAssignment_3.interchanges, 0, 0}; // Interchange results, no runtime or comparisons
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SortingResultsGraph graph = new SortingResultsGraph("Sorting Technique Performance");
            graph.setVisible(true);
        });
    }
}