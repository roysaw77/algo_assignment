import java.util.*;
import java.io.*;

public class quick_sort {
    public static class Pair {
        int i;
        String s;

        Pair(int i, String s) {
            this.i = i;
            this.s = s;
        }

        @Override
        public String toString() {
            return i + "," + s;
        }
    }

    public static void main(String[] args) {
        final String fn = "dataset_sample_1000.csv";
        final String sorted = "quick_sort_1000000.csv";

        ArrayList<Pair> data = readCSV(fn);

        long startTime = System.currentTimeMillis();
        quickSort(data, 0, data.size() - 1);
        long endTime = System.currentTimeMillis();

        writeCSV(data, sorted);

        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }

    public static ArrayList<Pair> readCSV(String fn) {
        ArrayList<Pair> arr = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fn))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    int i = Integer.parseInt(parts[0].trim());
                    String s = parts[1].trim();
                    arr.add(new Pair(i, s));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading dataset: " + e.getMessage());
        }
        return arr;
    }

    public static void writeCSV(ArrayList<Pair> data, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Pair p : data) {
                writer.write(p.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing output file: " + e.getMessage());
        }
    }

    public static void quickSort(ArrayList<Pair> arr, int left, int right) {
        if (left < right) {
            int pi = partition(arr, left, right);
            quickSort(arr, left, pi - 1);
            quickSort(arr, pi + 1, right);
        }
    }

    public static int partition(ArrayList<Pair> arr, int low, int high) {
        Pair pi = arr.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr.get(j).i <= pi.i) {
                Collections.swap(arr, ++i, j);
            }
        }
        Collections.swap(arr, i + 1, high);
        return i + 1;
    }
}
