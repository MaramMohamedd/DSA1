
package data.structure.assignment_3;

import java.util.*;

public class DataStructureAssignment_3 {
   public  static int comparisons;
    public static int interchanges;

    public static void main(String[] args) {
        int size = 10000;
        Integer[] sorted = new Integer[size];
        Integer[] random = new Integer[size];
        Integer[] reverse = new Integer[size];

        // Generate numbers
        for (int i = 0; i < size; i++) {
            sorted[i] = i + 1;  // Start from 1 instead of 0
        }

        // Copy and shuffle for random array
        System.arraycopy(sorted, 0, random, 0, size);
        Collections.shuffle(Arrays.asList(random));

        // Copy and reverse for reverse array
        System.arraycopy(sorted, 0, reverse, 0, size);
        Collections.reverse(Arrays.asList(reverse));

        // Perform bubble sort on each array
        System.out.println("Bubble Sort:");
        printTableHeader();
        bubbleSort(sorted.clone(), "Sorted Array");
        bubbleSort(random.clone(), "Random Array");
        bubbleSort(reverse.clone(), "Reverse Array");

        // Perform quick sort on each array
        System.out.println("\nQuick Sort:");
        printTableHeader();
        quickSort(sorted.clone(), "Sorted Array");
        quickSort(random.clone(), "Random Array");
        quickSort(reverse.clone(), "Reverse Array");

        // Perform counting sort on each array
        System.out.println("\nCounting Sort:");
        printTableHeader();
        countingSort(sorted.clone(), "Sorted Array");
        countingSort(random.clone(), "Random Array");
        countingSort(reverse.clone(), "Reverse Array");
    }

    public static void printTableHeader() {
        System.out.printf("%-15s %-15s %-20s %-20s%n", "Array Type", "Run Time (ms)", "Comparisons", "Interchanges");
    }

    public static void bubbleSort(Integer[] array, String arrayType) {
        int size = array.length;
        comparisons = 0;
        interchanges = 0;
        long startTime = System.nanoTime();

        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                comparisons++;
                if (array[j] > array[j + 1]) {
                    // Swap array[j] and array[j+1]
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    interchanges++;
                }
            }
        }

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;  // duration is in milliseconds.

        System.out.printf("%-15s %-15d %-20d %-20d%n", arrayType, duration, comparisons, interchanges);
    }

    public static void quickSort(Integer[] array, String arrayType) {
        int size = array.length;
        comparisons = 0;
        interchanges = 0;
        long startTime = System.nanoTime();

        quickSortHelper(array, 0, size - 1);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;  // duration is in milliseconds.

        System.out.printf("%-15s %-15d %-20d %-20d%n", arrayType, duration, comparisons, interchanges);
    }

    public static void quickSortHelper(Integer[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);

            quickSortHelper(array, low, pi - 1);
            quickSortHelper(array, pi + 1, high);
        }
    }

    public static int partition(Integer[] array, int low, int high) {
        int pivot = array[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            comparisons++;
            if (array[j] <= pivot) {
                i++;

                // swap arr[i] and arr[j]
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                interchanges++;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        interchanges++;

        return i + 1;
    }

    public static void countingSort(Integer[] array, String arrayType) {
        int size = array.length;
        comparisons = 0;  // No comparisons in counting sort
        interchanges = 0;
        long startTime = System.nanoTime();

        // Find the maximum value to know the range of counts
        int max = Arrays.stream(array).max(Integer::compare).get();

        // Initialize count array with zeros
        int[] count = new int[max + 1];

        // Count the occurrence of each number
        for (int i = 0; i < size; i++) {
            count[array[i]]++;
        }

        // Modify the count array
        for (int i = 1; i <= max; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array
        Integer[] output = new Integer[size];
        for (int i = size - 1; i >= 0; i--) {
            output[count[array[i]] - 1] = array[i];
            count[array[i]]--;
            interchanges++;
        }

        // Copy the output array to the original array
        System.arraycopy(output, 0, array, 0, size);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;  // duration is in milliseconds.

        System.out.printf("%-15s %-15d %-20d %-20d%n", arrayType, duration, comparisons, interchanges);
    }
}