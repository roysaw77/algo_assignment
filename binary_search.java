import java.io.*;
import java.util.*;

public class binary_search {

    public static void main(String[] args) {
        final String filename = "merge_sort_1000000.csv";
        final String outputFile = "binary_search_1000000.txt";

        List<Long> keys = new ArrayList<>();
        List<String> lines = new ArrayList<>();

        // Step 1: Load the data
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line.trim());
                String[] parts = line.split("/");
                keys.add(Long.parseLong(parts[0].trim()));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        long[] array = keys.stream().mapToLong(Long::longValue).toArray();
        int n = array.length;

        // Step 2: Perform searches for best, average, and worst case

        // Best case: Target is the middle element
        long bestTarget = array[n / 2];
        long bestStart = System.nanoTime();
        binarySearch(array, bestTarget);
        long bestEnd = System.nanoTime();
        long bestTime = bestEnd - bestStart;

        // Average case: Perform n searches and average the time
        long avgStart = System.nanoTime();
        for (int i = 0; i < n; i++) {
            binarySearch(array, array[i]);
        }
        long avgEnd = System.nanoTime();
        long avgTime = (avgEnd - avgStart) / n;

        // Worst case: Target is not in the array (e.g., a value greater than any in the dataset)
        long worstTarget = array[n - 1] + 1;
        long worstStart = System.nanoTime();
        binarySearch(array, worstTarget);
        long worstEnd = System.nanoTime();
        long worstTime = worstEnd - worstStart;

        // Step 3: Write output to file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            bw.write("Best Case: " + bestTime + " ns\n");
            bw.write("Average Case: " + avgTime + " ns\n");
            bw.write("Worst Case: " + worstTime + " ns\n");
            System.out.println("Timing results written to " + outputFile);
        } catch (IOException e) {
            System.out.println("Error writing to output: " + e.getMessage());
        }
    }

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
