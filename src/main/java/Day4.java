import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;

public class Day4 {
    public static void main(String[] args) throws IOException {
        String filename = "input/inputd4.txt";
        part2(filename);
    }

    public static void part1(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        int[] picked_numbers = extractPickedNumbers(lines.get(0));
        List<Board> boards = extractBoards(lines.subList(1, lines.size()));

        Board found_winning_board = null;
        int last_number = 0;
        for (int picked_number : picked_numbers) {
            for (Board board : boards) {
                if (board.winWith(picked_number)) {
                    found_winning_board = board;
                    last_number = picked_number;
                }
            }
            if (found_winning_board != null) break;
        }
        System.out.println(found_winning_board);
        System.out.println(last_number);
        System.out.println(found_winning_board.score(last_number));
    }

    public static void part2(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        int[] picked_numbers = extractPickedNumbers(lines.get(0));
        List<Board> boards = extractBoards(lines.subList(1, lines.size()));

        Board found_winning_board = null;
        int last_number = 0;
        for (int picked_number : picked_numbers) {
            for (Board board : boards) {
                if (board.notWonYet(picked_number)) {
                    found_winning_board = board;
                    last_number = picked_number;
                }
            }
        }
        System.out.println(found_winning_board);
        System.out.println(last_number);
        System.out.println(found_winning_board.score(last_number));
    }

    private static List<Board> extractBoards(List<String> boardInput) {
        Board board = null;
        List<Board> boards = new ArrayList<>();
        for (String line : boardInput) {
            if (line.isBlank()) {
                board = new Board();
                boards.add(board);
            } else {
                board.add(line);
            }
        }
        return boards;
    }

    private static int[] extractPickedNumbers(String row) {
        return stream(row.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    private static class Board {
        int[][] data = new int[5][5];
        int[][] marking = new int[5][5];
        int rowIndex = 0;
        boolean won = false;

        public void add(String line) {
            int columnIndex = 0;
            for (String number : line.split(" ")) {
                if (number.isBlank()) continue;

                data[rowIndex][columnIndex] = Integer.parseInt(number);
                columnIndex++;
            }
            rowIndex++;
        }

        @Override
        public String toString() {
            StringBuilder output = new StringBuilder();
            for (int[] datum : data) {
                output.append(Arrays.toString(datum)).append("\n");
            }
            return output.toString();
        }

        public boolean winWith(int picked_number) {
            for (int row = 0; row < data.length; row++) {
                for (int column = 0; column < data[row].length; column++) {
                    if (data[row][column] == picked_number) {
                        marking[row][column] = 1;
                    }
                }
            }

            int winning_row = -1;
            // check rows
            for (int row = 0; row < marking.length; row++) {
                int total_markers = 0;
                for (int column = 0; column < marking[row].length; column++) {
                    total_markers += marking[row][column];
                }
                if (total_markers == 5) {
                    winning_row = row;
                    break;
                }
            }

            int winning_column = -1;
            // check column
            for (int column = 0; column < marking.length; column++) {
                int total_markers = 0;
                for (int row = 0; row < marking[column].length; row++) {
                    total_markers += marking[row][column];
                }
                if (total_markers == 5) {
                    winning_column = column;
                    break;
                }
            }

            return won = winning_column != -1 || winning_row != -1;
        }

        public int score(int last_number) {
            int sum = 0;
            for (int row = 0; row < marking.length; row++) {
                for (int column = 0; column < marking[row].length; column++) {
                    if (marking[row][column] == 0) {
                        sum += data[row][column];
                    }
                }
            }
            return sum * last_number;
        }

        public boolean notWonYet(int picked_number) {
            if (won) return false;

            return winWith(picked_number);
        }
    }
}
