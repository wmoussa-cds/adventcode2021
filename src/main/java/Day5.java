import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.*;

public class Day5 {

    public static void main(String[] args) throws IOException {
        String filename = "input/inputd5.txt";
        part1(filename);
        part2(filename);
    }

    public static void part2(String filename) throws IOException {
        List<String> lines = readAllLines(Paths.get(filename));
        int[] x_y = findGridSize(lines);
        int[][] markers = new int[x_y[0] + 1][x_y[1] + 1];

        fillUp(markers, lines, true);

        printMarker(markers);

        System.out.println(findNumberOfOverlap(markers));
    }

    public static void part1(String filename) throws IOException {
        List<String> lines = readAllLines(Paths.get(filename));
        int[] x_y = findGridSize(lines);

        int[][] markers = new int[x_y[0] + 1][x_y[1] + 1];

        fillUp(markers, lines, false);

        printMarker(markers);

        System.out.println(findNumberOfOverlap(markers));
    }

    private static int findNumberOfOverlap(int[][] markers) {
        int count = 0;
        for (int[] marker : markers) {
            for (int i : marker) {
                if (i > 1) count++;
            }
        }
        return count;
    }

    private static void printMarker(int[][] markers) {
        for (int[] marker : markers) {
            for (int j = 0; j < markers.length; j++) {
                if (marker[j] == 0) {
                    System.out.print(".");
                } else {
                    System.out.print(marker[j]);
                }
            }
            System.out.println();
        }
    }

    private static void fillUp(int[][] markers, List<String> lines, boolean includeDiagonal) {
        for (String line : lines) {
            final String[] coordinates = line.split(" -> ");
            int[] starting_point = stream(coordinates[0].split(",")).mapToInt(Integer::parseInt).toArray();
            int[] ending_point = stream(coordinates[1].split(",")).mapToInt(Integer::parseInt).toArray();

            // vertical
            if (starting_point[0] == ending_point[0]) {
                if (starting_point[1] < ending_point[1]) {
                    for (int i = starting_point[1]; i <= ending_point[1]; i++) {
                        markers[i][starting_point[0]]++;
                    }
                } else {
                    for (int i = ending_point[1]; i <= starting_point[1]; i++) {
                        markers[i][starting_point[0]]++;
                    }
                }
            }

            // horizontal
            else if (starting_point[1] == ending_point[1]) {
                if (starting_point[0] < ending_point[0]) {
                    for (int i = starting_point[0]; i <= ending_point[0]; i++) {
                        markers[ending_point[1]][i]++;
                    }
                } else {
                    for (int i = ending_point[0]; i <= starting_point[0]; i++) {
                        markers[ending_point[1]][i]++;
                    }
                }
            } else if (includeDiagonal && (Math.abs(starting_point[0] - ending_point[0]) == Math.abs(starting_point[0] - ending_point[0]))) {
                int x = starting_point[0], y = starting_point[1];
                markers[y][x]++;
                if (starting_point[0] < ending_point[0] && starting_point[1] < ending_point[1]) {
                    // going right down
                    while (x != ending_point[0] && y != ending_point[1]) {
                        x++; // right
                        y++; // down
                        markers[y][x]++;
                    }
                } else if (starting_point[0] > ending_point[0] && starting_point[1] > ending_point[1]) {
                    // going left up
                    while (x != ending_point[0] && y != ending_point[1]) {
                        x--; // left
                        y--; // up
                        markers[y][x]++;
                    }
                } else if (starting_point[0] > ending_point[0]) {
                    // going left down
                    while (x != ending_point[0] && y != ending_point[1]) {
                        x--; // left
                        y++; // down
                        markers[y][x]++;
                    }
                } else {
                    // going right up
                    while (x != ending_point[0] && y != ending_point[1]) {
                        x++; // right
                        y--; // up
                        markers[y][x]++;
                    }
                }
            }


        }
    }

    private static int[] findGridSize(List<String> lines) {
        int[] x_y = new int[2];
        for (String line : lines) {
            for (String coordinate : line.split(" -> ")) {
                final int[] ints = stream(coordinate.split(",")).mapToInt(Integer::parseInt).toArray();
                if (ints[0] > x_y[0]) {
                    x_y[0] = ints[0];
                }
                if (ints[1] > x_y[1]) {
                    x_y[1] = ints[1];
                }
            }
        }
        return x_y;
    }
}
