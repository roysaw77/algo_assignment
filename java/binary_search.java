import java.io.*;
import java.util.*;

public class binary_search {

    public static void main(String[] args) {
        final String filename = "merge_sort_1000000.csv";  // Input file
        final String outputFile = "binary_search_1000000.txt";  // Output file

        List<Long> keys = new ArrayList<>();

        // Step 1: Load the data from CSV
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] parts = line.split(",");  // Assuming first value is key
                try {
                    keys.add(Long.parseLong(parts[0].trim()));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        long[] array = keys.stream().mapToLong(Long::longValue).toArray();
        int n = array.length;

        // Warm-up phase to allow JVM optimizations before actual timing
        for (int i = 0; i < 5000; i++) {
            binarySearch(array, array[n / 2]);       // Best case warm-up
            binarySearch(array, array[n - 1] + 1);   // Worst case warm-up
            binarySearch(array, array[i % n]);       // Average case warm-up
        }

        int iterations = 1000;     // Number of times to repeat best and worst case timing
        int avgIterations = 100;   // Number of repetitions for average case timing for stability

        // Worst Case: Search for an element not in the array
        long worstTarget = array[n - 1] + 1;
        long totalWorstTime = 0;
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            binarySearch(array, worstTarget);
            long end = System.nanoTime();
            totalWorstTime += (end - start);
        }
        double worstTimeMs = totalWorstTime / (iterations * 1_000_000.0);

        // Average Case: Search for every element multiple times and average
        long totalAvgTime = 0;
        for (int iter = 0; iter < avgIterations; iter++) {
            long start = System.nanoTime();
            for (int i = 0; i < n; i++) {
                binarySearch(array, array[i]);
            }
            long end = System.nanoTime();
            totalAvgTime += (end - start);
        }
        double avgTimeMs = totalAvgTime / (avgIterations * n * 1_000_000.0);

        // Best Case: Search for the middle element (found immediately)
        long bestTarget = array[n / 2];
        long totalBestTime = 0;
        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            binarySearch(array, bestTarget);
            long end = System.nanoTime();
            totalBestTime += (end - start);
        }
        double bestTimeMs = totalBestTime / (iterations * 1_000_000.0);

        // Write results to output file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            bw.write(String.format("Worst Case: %.6f ms%n", worstTimeMs));
            bw.write(String.format("Average Case: %.6f ms%n", avgTimeMs));
            bw.write(String.format("Best Case: %.6f ms%n", bestTimeMs));
            System.out.println("Timing results written to " + outputFile);
        } catch (IOException e) {
            System.out.println("Error writing to output: " + e.getMessage());
        }
    }

    // Standard binary search method
    public static int binarySearch(long[] array, long target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (array[mid] == target) return mid;
            else if (array[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
}
