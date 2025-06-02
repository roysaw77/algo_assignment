import java.io.*;
import java.util.*;

public class merge_sort {
    public static class Pair {
        int i;
        String s;

        public Pair(int i, String s) {
            this.i = i;
            this.s = s;
        }

        @Override
        public String toString() {
            return i + "," + s;
        }
    }

    public static void main(String[] args) {
        final String inputFile = "dataset_sample_1000.csv"; // 修改为你的数据集
        final String outputFile = "merge_sort_1000000.csv";    // 根据数据集命名

        ArrayList<Pair> data = readCSV(inputFile);

        long startTime = System.currentTimeMillis();
        mergeSort(data, 0, data.size() - 1);
        long endTime = System.currentTimeMillis();

        writeCSV(data, outputFile);

        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }

    // 读取全部数据
    public static ArrayList<Pair> readCSV(String filename) {
        ArrayList<Pair> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    int number = Integer.parseInt(parts[0].trim());
                    String word = parts[1].trim();
                    list.add(new Pair(number, word));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading dataset: " + e.getMessage());
        }
        return list;
    }

    // Merge Sort 主函数
    public static void mergeSort(ArrayList<Pair> arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    // 合并两个有序子数组
    public static void merge(ArrayList<Pair> arr, int left, int mid, int right) {
        List<Pair> leftList = new ArrayList<>(arr.subList(left, mid + 1));
        List<Pair> rightList = new ArrayList<>(arr.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;

        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).i <= rightList.get(j).i) {
                arr.set(k++, leftList.get(i++));
            } else {
                arr.set(k++, rightList.get(j++));
            }
        }

        while (i < leftList.size()) arr.set(k++, leftList.get(i++));
        while (j < rightList.size()) arr.set(k++, rightList.get(j++));
    }

    // 写入排序结果
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
}
