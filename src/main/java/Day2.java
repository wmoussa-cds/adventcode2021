import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2 {

    public static void main(String[] args) throws IOException {
        part2();
    }

    public static void part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputd2.txt"));
        int horizontal = 0, depth = 0;
        for (String line : lines) {
            String action = line.split(" ")[0];
            int unit = Integer.parseInt(line.split(" ")[1]);
            switch (action) {
                case "forward" -> horizontal += unit;
                case "down" -> depth += unit;
                case "up" -> depth -= unit;
            }
        }
        System.out.println("horizontal = " + horizontal + " depth = " + depth + " multiply = " + horizontal * depth);
    }

    public static void part2() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputd2.txt"));
        int horizontal = 0, depth = 0, aim = 0;
        for (String line : lines) {
            String action = line.split(" ")[0];
            int unit = Integer.parseInt(line.split(" ")[1]);
            switch (action) {
                case "forward" -> {
                    horizontal += unit;
                    depth += aim * unit;
                }
                case "down" -> aim += unit;
                case "up" -> aim -= unit;
            }
        }
        System.out.println("horizontal = " + horizontal + " depth = " + depth + " multiply = " + horizontal * depth);
    }
}
