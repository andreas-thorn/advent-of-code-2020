import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Day05 {

    final static int MAX_ROW = 127;
    final static int MAX_COLUMN = 7;
    final static char ROW_LOWER_PARTITION = 'F';
    final static char COLUMN_LOWER_PARTITION = 'L';

    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNextLine())
            result.add(scanner.nextLine());

        return result;
    }

    private static int decodeTicket(String ticketColumn, int columnLow, int columnHigh) {
        if (columnLow == columnHigh) return columnHigh;

        char partition = ticketColumn.charAt(0);
        if (partition == COLUMN_LOWER_PARTITION || partition == ROW_LOWER_PARTITION)
            return decodeTicket(ticketColumn.substring(1), columnLow, (columnLow+columnHigh)/2);
        else
            return decodeTicket(ticketColumn.substring(1), (columnLow+columnHigh+1)/2, columnHigh);
    }

    private static int getSeatId(String ticket) {
        int row = decodeTicket(ticket.substring(0, 7), 0, MAX_ROW);
        int column = decodeTicket(ticket.substring(7, 10), 0, MAX_COLUMN);
        return row * 8 + column;
    }

    private static int getMissingNumberInSequence(LinkedList<Integer> sortedSequence) {
        int missingInitialSum = ((sortedSequence.getFirst() - 1) * sortedSequence.getFirst()) / 2;
        int correctSum = ((sortedSequence.getLast() * (sortedSequence.getLast() + 1)) / 2) - missingInitialSum;
        int actualSum = sortedSequence.stream().reduce(0, Integer::sum);

        return correctSum - actualSum;
    }

    private static int firstTask(final LinkedList<String> input) {
        return input.stream()
                .map(Day05::getSeatId)
                .max(Comparator.naturalOrder()).get();
    }

    private static long secondTask(final LinkedList<String> input) {
        var sortedSeatIds = input.stream()
                .map(Day05::getSeatId)
                .sorted()
                .collect(Collectors.toCollection(LinkedList::new));

        return getMissingNumberInSequence(sortedSeatIds);
    }

    public static void main(String[] args) {
        LinkedList<String> input = new LinkedList<>(
                readInput(Day05.class.getClassLoader().getResourceAsStream("day_05_input.txt")));

        System.out.printf("First task: %d%n", firstTask(input));
        System.out.printf("Second task: %d%n", secondTask(input));
    }
}
