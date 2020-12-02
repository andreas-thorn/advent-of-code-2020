import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day01 {
    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNextLine()) {
            result.add(scanner.nextLine());
        }
        return result;
    }

    private static int firstTask(LinkedList<Integer> input, int target) {
        int candidate;
        for(var number : input) {
            candidate = target - number;
            if (input.contains(candidate))
                return candidate * (target - candidate);
        }

        return -1;
    }

    private static int secondTask(LinkedList<Integer> input, int target) {
        while(input.size() > 0) {
            var candidate = input.removeFirst();
            var otherPart = firstTask(input, target - candidate);
            if (otherPart != -1)
                return candidate * otherPart;
        }

        return -1;
    }

    public static void main(String[] args) {
        LinkedList<Integer> input = readInput(Day01.class.getClassLoader().getResourceAsStream("day_01_input.txt")).stream()
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toCollection(LinkedList::new));

        System.out.println("First task: " + firstTask(input, 2020));
        System.out.println("Second task: " + secondTask(input, 2020));
    }
}
