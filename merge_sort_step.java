package algorithm;

import java.io.*;
import java.util.*;

public class MergeSortStep {
    public static void main(String[] args) {
        String filename = "dataset_sample_1000.csv";
        int startRow = 1;
        int endRow = 7;

        ArrayList<Pair> data = readCSV(filename, startRow, endRow);
        ArrayList<String> log = new ArrayList<>();

        mergeSort(data, 0, data.size() - 1, log);

        String outputFilename = String.format("merge_sort_step_%d_%d.txt", startRow, endRow);
        writeSteps(log, outputFilename);
    }

    public static ArrayList<Pair> readCSV(String filename, int start, int end) {
        ArrayList<Pair> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int lineNum = 1;
            String line;
            while ((line = br.readLine()) != null) {
                if (lineNum >= start && lineNum <= end) {
                    String[] parts = line.split(",", 2);
                    result.add(new Pair(Integer.parseInt(parts[0]), parts[1]));
                }
                lineNum++;
                if (lineNum > end) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void writeSteps(List<String> steps, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String line : steps) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mergeSort(ArrayList<Pair> arr, int left, int right, ArrayList<String> log) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, log);
            mergeSort(arr, mid + 1, right, log);
            merge(arr, left, mid, right);
            log.add(arrayToString(arr));
        }
    }

    public static void merge(ArrayList<Pair> arr, int l, int m, int r) {
        ArrayList<Pair> temp = new ArrayList<>(arr.subList(l, r + 1));
        int i = 0, j = m - l + 1, k = l;

        while (i <= m - l && j <= r - l) {
            if (temp.get(i).number <= temp.get(j).number) {
                arr.set(k++, temp.get(i++));
            } else {
                arr.set(k++, temp.get(j++));
            }
        }

        while (i <= m - l) arr.set(k++, temp.get(i++));
        while (j <= r - l) arr.set(k++, temp.get(j++));
    }

    public static String arrayToString(ArrayList<Pair> arr) {
        return arr.stream().map(Pair::toString).toList().toString();
    }
}
