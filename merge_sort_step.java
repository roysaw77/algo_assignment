import java.io.*;
import java.util.*;

public class merge_sort_step {
    public static class Pair {
        int number;
        String word;

        public Pair(int number, String word) {
            this.number = number;
            this.word = word;
        }

        @Override
        public String toString() {
            return number + "/" + word;
        }
    }

    public static void main(String[] args) {
        String filename = "dataset_sample_1000.csv";
        int startRow = 1; // 以 1 为起始下标
        int endRow = 7;

        ArrayList<Pair> data = readCSVRange(filename, startRow, endRow);

        List<String> stepLogs = new ArrayList<>();
        stepLogs.add(toStringList(data)); // 初始状态

        mergeSort(data, 0, data.size() - 1, stepLogs);

        String outFile = String.format("merge_sort_step_%d_%d.txt", startRow, endRow);
        writeStepsToFile(stepLogs, outFile);
    }

    // 读取CSV指定范围行的数据
    public static ArrayList<Pair> readCSVRange(String filename, int start, int end) {
        ArrayList<Pair> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int currentRow = 1;
            while ((line = br.readLine()) != null) {
                if (currentRow >= start && currentRow <= end) {
                    String[] parts = line.split(",", 2);
                    if (parts.length == 2) {
                        int num = Integer.parseInt(parts[0].trim());
                        String str = parts[1].trim();
                        list.add(new Pair(num, str));
                    }
                }
                currentRow++;
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
        return list;
    }

    // Merge Sort 主函数
    public static void mergeSort(List<Pair> arr, int left, int right, List<String> logs) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid, logs);
            mergeSort(arr, mid + 1, right, logs);
            merge(arr, left, mid, right, logs);
        }
    }

    // Merge 两个子数组并记录步骤
    public static void merge(List<Pair> arr, int left, int mid, int right, List<String> logs) {
        List<Pair> leftList = new ArrayList<>(arr.subList(left, mid + 1));
        List<Pair> rightList = new ArrayList<>(arr.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;

        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).number <= rightList.get(j).number) {
                arr.set(k++, leftList.get(i++));
            } else {
                arr.set(k++, rightList.get(j++));
            }
        }
        while (i < leftList.size()) arr.set(k++, leftList.get(i++));
        while (j < rightList.size()) arr.set(k++, rightList.get(j++));

        logs.add(toStringList(arr));
    }

    // 将当前排序状态转换为字符串
    public static String toStringList(List<Pair> list) {
        return list.toString().replaceAll("[\\[\\]]", ""); // 去除方括号
    }

    // 输出所有步骤到文件
    public static void writeStepsToFile(List<String> steps, String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String step : steps) {
                bw.write(step);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing steps: " + e.getMessage());
        }
    }
}
