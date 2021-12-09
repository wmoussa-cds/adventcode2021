import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.*;

public class Day8 {

    public static void main(String[] args) throws IOException {
        String filename = "input/inputd8.txt";
        part2(filename);
    }

    public static void part1(String filename) throws IOException {
        List<String> lines = readAllLines(Paths.get(filename));
        int count = 0;
        for (String line : lines) {
            String[] output = line.split("\\|")[1].split(" ");
            for (String s : output) {
                switch (s.trim().length()) {
                    case 7, 4, 3, 2 -> count++;
                }
            }
        }
        System.out.println(count);
    }

    public static void part2(String filename) throws IOException {
        List<String> lines = readAllLines(Paths.get(filename));

        int total = 0;
        for (String line : lines) {
            String[] patternMap = new String[10];
            String[] patterns = line.split("\\|")[0].split(" ");

            // find the known pattern
            for (String pattern : patterns) {
                switch (pattern.length()) {
                    case 7 -> patternMap[8] = sortPattern(pattern);
                    case 4 -> patternMap[4] = sortPattern(pattern);
                    case 3 -> patternMap[7] = sortPattern(pattern);
                    case 2 -> patternMap[1] = sortPattern(pattern);
                }
            }

            // deduction starts here
            for (String pattern : patterns) {
                if (pattern.length() == 5) {
                    // could be 3, 5 and 2
                    if (commonSegmentsOf(patternMap[1], pattern) == 2) {
                        patternMap[3] = sortPattern(pattern);
                    } else if (commonSegmentsOf(patternMap[4], pattern) == 3) {
                        patternMap[5] = sortPattern(pattern);
                    } else if (commonSegmentsOf(patternMap[4], pattern) == 2) {
                        patternMap[2] = sortPattern(pattern);
                    }
                }

                if (pattern.length() == 6) {
                    // could be 6, 9 and 0
                    if (commonSegmentsOf(patternMap[1], pattern) == 1) {
                        patternMap[6] = sortPattern(pattern);
                    } else if (commonSegmentsOf(patternMap[4], pattern) == 4) {
                        patternMap[9] = sortPattern(pattern);
                    } else if (commonSegmentsOf(patternMap[4], pattern) == 3) {
                        patternMap[0] = sortPattern(pattern);
                    }
                }
            }

            String[] output = line.split("\\|")[1].split(" ");
            StringBuilder stringDigit = new StringBuilder();
            for (String digit : output) {
                stringDigit.append(findInMap(digit, patternMap));
            }

            total += Integer.parseInt(stringDigit.toString());
        }
        System.out.println(total);
    }

    private static String findInMap(String digit, String[] patternMap) {
        String sortDigit = sortPattern(digit);
        for (int i = 0; i <= 9; i++) {
            if (patternMap[i].equals(sortDigit)) {
                return String.valueOf(i);
            }
        }
        return "";
    }

    private static int commonSegmentsOf(String testPattern, String pattern) {
        int count = 0;
        for (char c : testPattern.toCharArray()) {
            for (char c1 : pattern.toCharArray()) {
                if (c == c1) count++;
            }
        }
        return count;
    }

    private static String sortPattern(String pattern) {
        char[] temp = pattern.toCharArray();
        sort(temp);
        return new String(temp);
    }


}
