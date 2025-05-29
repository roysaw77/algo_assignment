import java.util.*;
import java.io.*;

public class binary_search_step {

    public static class Pair {
        int i;
        String s;

        Pair(int i, String s) {
            this.i = i;
            this.s = s;
        }

        @Override
        public String toString() {
            return i + ": " + s;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final String filename = "quick_sort_1000000.csv";

        System.out.print("Enter target integer: ");
        long target = sc.nextLong();

        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Pair> traceSteps = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim().replace(",", "/");  // 🔧 Replace comma with slash
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        long[] keys = new long[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split("/");
            keys[i] = Long.parseLong(parts[0]);  // ✅ Use Long
        }

        // Run modified binary search with trace
        int index = binarySearchWithTrace(keys, lines, target, traceSteps);

        // Output trace to file
        String outputFile = "binary_search_step_" + target + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {
            for (Pair p : traceSteps) {
                bw.write(p.toString());
                bw.newLine();
            }
            System.out.println("Binary search trace written to: " + outputFile);
        } catch (IOException e) {
            System.out.println("Error writing output: " + e.getMessage());
        }
    }

    public static int binarySearchWithTrace(long[] array, ArrayList<String> fullLines, long target, List<Pair> traceSteps) {
    int left = 0;
    int right = array.length - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;

        // Assuming your index in the file is 1-based (e.g., line 500 corresponds to index 499)
        traceSteps.add(new Pair(mid + 1, fullLines.get(mid)));

        if (array[mid] == target) {
            return mid;
        } else if (array[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return -1;
}

}
