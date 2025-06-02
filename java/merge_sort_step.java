import java.io.*;
import java.util.*;

public class merge_sort_step {
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

        public String toFormatted() {
            return i + "/" + s;
        }
    }

    public static void main(String[] args) {
        final String inputFile = "dataset_sample_1000.csv";
        final int startRow = 1; // inclusive, 1-based
        final int endRow = 7;   // inclusive
        final String outputFile = String.format("merge_sort_step_%d_%d.txt", startRow, endRow);

        ArrayList<Pair> data = readCSV(inputFile, startRow, endRow);

        List<String> stepLogs = new ArrayList<>();
        stepLogs.add(toStepString(data)); // initial state

        mergeSort(data, 0, data.size() - 1, stepLogs);

        writeSteps(stepLogs, outputFile);
    }

    // 读取指定范围的数据行
    public static ArrayList<Pair> readCSV(String filename, int start, int end) {
        ArrayList<Pair> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int currentRow = 1;
            while ((line = br.readLine()) != null) {
                if (currentRow >= start && currentRow <= end) {
                    String[] parts = line.split(",", 2);
                    if (parts.length == 2) {
                        int number = Integer.parseInt(parts[0].trim());
                        String word = parts[1].trim();
                        list.add(new Pair(number, word));
                    }
                }
                currentRow++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return list;
    }

    // Merge Sort 过程
    public static void mergeSort(ArrayList<Pair> arr, int left, int right, List<String> logs) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, logs);
            mergeSort(arr, mid + 1, right, logs);
            merge(arr, left, mid, right, logs);
        }
    }

    // 合并两个子数组并记录当前排序状态
    public static void merge(ArrayList<Pair> arr, int left, int mid, int right, List<String> logs) {
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

        logs.add(toStepString(arr));
    }

    // 将当前数组状态转为格式化字符串
    public static String toStepString(List<Pair> list) {
        List<String> formatted = new ArrayList<>();
        for (Pair p : list) {
            formatted.add(p.toFormatted());
        }
        return String.join(", ", formatted);
    }

    // 写入所有排序步骤到文件
    public static void writeSteps(List<String> steps, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String step : steps) {
                bw.write(step);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}

