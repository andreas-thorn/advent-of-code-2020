import java.io.InputStream;
import java.util.ArrayList;
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

    private static int firstTask(List<Integer> input, int target) {
        int candidate = 0;
        for(int number : input) {
            candidate = target - number;
            if (input.contains(candidate))
                return candidate * (target - candidate);
        }

        return 0;
    }

    private static int secondTask(List<Integer> input, int target) {
        for(int i = 0; i < input.size(); i++) {
            for(int j = i; j < input.size(); j++){
                for(int k = j; k < input.size(); k++){
                    if(input.get(i)+input.get(j)+input.get(k) == target)
                        return (input.get(i)*input.get(j)*input.get(k));
                }
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        List<Integer> input = readInput(Day01.class.getClassLoader().getResourceAsStream("day_01_input.txt")).stream()
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());;

        System.out.println("First task: " + firstTask(input, 2020));
        System.out.println("Second task: " + secondTask(input, 2020));
    }
}
