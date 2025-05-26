import java.util.*;
import java.io.*;

public class quick_sort_step {

    public static class Pair {
        int i;
        String s;

        Pair(int i, String s) {
            this.i = i;
            this.s = s;
        }

        @Override
        public String toString() {
            return i + "/" + s;
        }
    }

    public static ArrayList<String> sl = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final String fn = "dataset_sample_1000.csv";

        System.out.print("Enter start row: ");
        int sr = sc.nextInt();

        System.out.print("Enter end row: ");
        int er = sc.nextInt();

        ArrayList<Pair> d = readCSVRange(fn, sr, er);

        // Add initial unsorted list to step log
        sl.add(formatList(d));

        // Apply quick sort with tail recursion optimization
        quickSort(d, 0, d.size() - 1);

        // Write to output file
        String outFile = "quick_sort_step_" + sr + "_" + er + ".txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outFile))) {
            for (String line : sl) {
                bw.write(line);
                bw.newLine();
            }
            System.out.println("Steps written to " + outFile);
        } catch (IOException e) {
            System.out.println("Error writing output: " + e.getMessage());
        }

        sc.close();
    }

    public static ArrayList<Pair> readCSVRange(String fn, int sr, int er) {
        ArrayList<Pair> arr = new ArrayList<Pair>();
        int cr = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(fn))) {
            String l;
            while ((l = br.readLine()) != null) {
                if (cr >= sr && cr <= er) {
                    String[] parts = l.split(",", 2);
                    if (parts.length == 2) {
                        int i = Integer.parseInt(parts[0].trim());
                        String s = parts[1].trim();
                        arr.add(new Pair(i, s));
                    }
                }
                cr++;
                if (cr > er) break;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return arr;
    }

    public static void quickSort(ArrayList<Pair> arr, int left, int right) {
    if (left < right) {
        int pi = partition(arr, left, right);
        sl.add("pi=" + pi + " " + formatList(arr));
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

    public static String formatList(ArrayList<Pair> arr) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.size(); i++) {
            sb.append(arr.get(i));
            if (i < arr.size() - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
