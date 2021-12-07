
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.Files.readAllLines;
import static java.util.Arrays.stream;

public class Day6 {

    public static void main(String[] args) throws IOException {
        String filename = "input/inputd6.txt";
        part2(filename);
    }

    public static void part1(String filename) throws IOException {
        List<String> lines = readAllLines(Paths.get(filename));
        List<Fish> fishes = new ArrayList<>();
        for (String timer : lines.get(0).split(",")) {
            fishes.add(new Fish(timer));
        }
        System.out.println("Initial state: " + fishes);

        for (int day = 1; day < 80; day++) {
            List<Fish> new_fishes = one_day_passes(fishes);
            System.out.println("Total number of fishes: " + fishes.size());
            fishes.addAll(new_fishes);
        }
    }

    public static void part2(String filename) throws IOException {
        List<String> lines = readAllLines(Paths.get(filename));
        Map<Integer, Double> fish_count_by_age = new HashMap<>();

        for (int i = 0; i <= 9; i++) {
            fish_count_by_age.put(i, 0.0);
        }

        for (int timer : stream(lines.get(0).split(",")).mapToInt(Integer::parseInt).toArray()) {
            fish_count_by_age.put(timer, fish_count_by_age.get(timer) + 1);
        }
        System.out.println("Initial state: " + fish_count_by_age);

        for (int day = 1; day < 257; day++) {
            Double number_of_new_fishes = fish_count_by_age.get(0);
            fish_count_by_age = one_day_passes_int(fish_count_by_age);
            fish_count_by_age.put(6, fish_count_by_age.get(6) + number_of_new_fishes);
            fish_count_by_age.put(8, number_of_new_fishes);
            System.out.printf(" after %d day: %s - total: %f\n", day, fish_count_by_age, totalFish(fish_count_by_age));
        }
    }

    private static double totalFish(Map<Integer, Double> fish_count_by_age) {
        double sum = 0;
        for (Double counter : fish_count_by_age.values()) {
            sum += counter;
        }
        return sum;
    }

    private static Map<Integer, Double> one_day_passes_int(Map<Integer, Double> fishes) {
        Map<Integer, Double> next_day = new HashMap<>();
        for (int i = 0; i <= 9; i++) {
            next_day.put(i, 0.0);
        }
        for (int i = 0; i <= 8; i++) {
            next_day.put(i, fishes.get(i + 1));
        }
        return next_day;
    }

    private static List<Fish> one_day_passes(List<Fish> fishes) {
        List<Fish> new_fishes = new ArrayList<>();
        for (Fish fish : fishes) {
            fish.decrement();
            if (fish.ready_to_spawn()) {
                new_fishes.add(new Fish());
            }
        }
        return new_fishes;
    }

    private static class Fish {
        int internal_timer = 9;

        public Fish() {
        }

        public Fish(String timer) {
            internal_timer = Integer.parseInt(timer);
        }

        @Override
        public String toString() {
            return String.valueOf(internal_timer);
        }

        public void decrement() {
            if (internal_timer == 0) {
                internal_timer = 6;
            } else {
                internal_timer--;
            }
        }

        public boolean ready_to_spawn() {
            return internal_timer == 0;
        }

    }

}
