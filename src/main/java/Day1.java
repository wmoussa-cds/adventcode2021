import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day1 {
    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    public static void part2() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputd1.txt"));
        int previous = -1, counter = 0;
        for (int index = 0; index + 3 <= lines.size(); index++) {
            Integer sum = lines.subList(index, index + 3).stream().mapToInt(Integer::parseInt).sum();
            if (previous != -1 && sum > previous) {
                counter++;
            }
            previous = sum;
        }

        System.out.println(counter);

    }


    public static void part1() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("inputd1.txt"));
        int previous = -1, counter = 0;
        for (String line : lines) {
            if (previous != -1 && Integer.parseInt(line) > previous) {
                counter++;
            }
            previous = Integer.parseInt(line);
        }
        System.out.println(counter);
    }
}
