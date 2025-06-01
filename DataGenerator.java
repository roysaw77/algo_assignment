import java.io.*;
import java.util.*;

public class DataGenerator {
    private static final int DATA_SIZE = 1000; // 可自定义数量
    private static final String OUTPUT_FILE = "output/generated_dataset.csv";

    public static void main(String[] args) {
        Random rand = new Random();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            for (int i = 0; i < DATA_SIZE; i++) {
                int number = rand.nextInt(100000); // 随机整数范围
                String text = generateRandomString(rand, 5); // 生成5字符字符串
                writer.write(number + "," + text);
                writer.newLine();
            }
            System.out.println("Generated " + DATA_SIZE + " records to " + OUTPUT_FILE);
        } catch (IOException e) {
            System.err.println("Error writing dataset: " + e.getMessage());
        }
    }

    private static String generateRandomString(Random rand, int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(alphabet.charAt(rand.nextInt(alphabet.length())));
        }
        return sb.toString();
    }
}
