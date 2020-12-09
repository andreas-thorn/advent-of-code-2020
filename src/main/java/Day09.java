import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Day09 {
    private static List<Long> readInput(InputStream stream) {
        List<Long> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNextLine())
            result.add(Long.parseLong(scanner.nextLine()));
        return result;
    }

    private static final int PREAMBLE_LENGTH = 25;

    private static boolean isValidValueAtIndex(final List<Long> input, long targetValue) {
        for(Long candidate : input){
            if(input.contains(targetValue - candidate))
                return true;
        }
        return false;
    }

    private static long firstTask(final ArrayList<Long> input) {
        for(int i = PREAMBLE_LENGTH; i < input.size(); i++) {
            List<Long> validateValues = input.subList(i - PREAMBLE_LENGTH, i);
            if(!isValidValueAtIndex(validateValues, input.get(i)))
                return input.get(i);
        }
        return -1;
    }

    private static long secondTask(final ArrayList<Long> input, final long targetValue) {
        int startIndex = 0;
        int endIndex = 1;

        while(true) {
            long currentValue = input.subList(startIndex, endIndex).stream().reduce(Long::sum).get();

            if(currentValue == targetValue) {
                var sequence = input.subList(startIndex, endIndex).stream()
                        .sorted()
                        .collect(Collectors.toCollection(LinkedList::new));
                return sequence.getFirst() + sequence.getLast();
            }
            else if (currentValue < targetValue)
                endIndex++;
            else
                startIndex++;
        }
    }

    public static void main(String[] args) {
        final ArrayList<Long> input = new ArrayList<>(
                readInput(Day09.class.getClassLoader().getResourceAsStream("day_09_input.txt")));

        long resultFirstTask = firstTask(input);
        System.out.printf("First task: %d%n", resultFirstTask);
        System.out.printf("Second task: %d%n", secondTask(input, resultFirstTask));
    }
}
