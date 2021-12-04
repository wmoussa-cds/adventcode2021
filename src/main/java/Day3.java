import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day3 {

    public static void main(String[] args) throws IOException {
        String filename = "input/inputd3.txt";
        part1(filename);
        part2(filename);
    }

    public static void part2(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        int numberOfBit = lines.get(0).length();
        int oxygen_generator_rating = getRating(lines, numberOfBit, "1", "0");
        int co2_scrubbing_rating = getRating(lines, numberOfBit, "0", "1");
        System.out.println("Part 2: " + oxygen_generator_rating * co2_scrubbing_rating);
    }

    private static int getRating(List<String> lines, int numberOfBit, String mostCommon, String leastCommon) {
        for (int i = 0; i < numberOfBit; i++) {
            if (lines.size() == 1) break;
            int[] counters = bitCounter(lines);
            if (counters[i] >= lines.size() - counters[i]) {
                lines = filter(lines, mostCommon, i);
            } else {
                lines = filter(lines, leastCommon, i);
            }
        }
        return Integer.parseInt(lines.get(0), 2);
    }

    public static List<String> filter(List<String> lines, String condition, int position) {
        List<String> filtered = new ArrayList<>();
        for (String line : lines) {
            String[] bits = line.split("");
            if (Objects.equals(bits[position], condition)) filtered.add(line);
        }
        return filtered;
    }

    public static int[] bitCounter(List<String> lines) {
        int[] counter = new int[lines.get(0).length()];
        for (String line : lines) {
            String[] bits = line.split("");
            int index = 0;
            for (String bit : bits) {
                if (Objects.equals(bit, "1")) {
                    counter[index]++;
                }
                index++;
            }
        }
        return counter;
    }

    public static void part1(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        int[] counter = new int[12];
        for (String line : lines) {
            String[] bits = line.split("");
            int index = 0;
            for (String bit : bits) {
                if (Objects.equals(bit, "1")) {
                    counter[index]++;
                }
                index++;
            }
        }
        String gamma = "", epsilon = "";
        for (int i : counter) {
            if ((double) i / (double) lines.size() > 0.5) {
                gamma += "1";
                epsilon += "0";
            } else {
                gamma += "0";
                epsilon += "1";
            }
        }
        System.out.println("Part 1: " + Integer.parseInt(epsilon, 2) * Integer.parseInt(gamma, 2));
    }
}
