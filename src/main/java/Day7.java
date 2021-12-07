import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.stream;

public class Day7 {

    public static void main(String[] args) throws IOException {
        String filename = "input/inputd7.txt";
        part2(filename);
    }

    public static void part1(String filename) throws IOException {
        List<String> lines = readAllLines(Paths.get(filename));
        int[] crabs = stream(lines.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(crabs);
        System.out.println("Minimum = " + crabs[0]);
        System.out.println("Maximum = " + crabs[crabs.length - 1]);
        int bestFuelCumsomption = Integer.MAX_VALUE;
        for (int i = crabs[0]; i < crabs[crabs.length - 1]; i++) {
            int sumFuel = 0;
            for (int crab : crabs) {
                sumFuel += Math.abs(crab - i);
            }
            if (sumFuel < bestFuelCumsomption) bestFuelCumsomption = sumFuel;
        }

        System.out.println(bestFuelCumsomption);
    }

    public static void part2(String filename) throws IOException {
        List<String> lines = readAllLines(Paths.get(filename));
        int[] crabs = stream(lines.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        Arrays.sort(crabs);
        System.out.println("Minimum = " + crabs[0]);
        System.out.println("Maximum = " + crabs[crabs.length - 1]);
        int bestFuelCumsomption = Integer.MAX_VALUE;
        for (int i = crabs[0]; i < crabs[crabs.length - 1]; i++) {
            int sumFuel = 0;
            for (int crab : crabs) {
                int steps = Math.abs(crab - i);
                for (int s = 1; s <= steps; s++) {
                    sumFuel = sumFuel + s;
                }
            }
            if (sumFuel < bestFuelCumsomption) bestFuelCumsomption = sumFuel;
        }

        System.out.println(bestFuelCumsomption);
    }
}
