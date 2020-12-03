import java.io.InputStream;
import java.util.*;

public class Day03 {

    private final static char TREE_CHAR = '#';

    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNextLine()) {
            result.add(scanner.nextLine());
        }
        return result;
    }

    private static int traverseMap(final LinkedList<String> map, final int stepX, final int stepY) {
        final int mapWidth = map.getFirst().length();
        int treeCount = 0, y = 0;

        for(int x = 0; x < map.size(); x+=stepX) {
            if(map.get(x).charAt(y) == TREE_CHAR)
                treeCount++;

            y = (y+stepY) % mapWidth;
        }

        return treeCount;
    }

    private static int firstTask(final LinkedList<String> input) {
        return traverseMap(input, 1, 3);
    }

    private static long secondTask(final LinkedList<String> input) {
        List<List<Integer>> traverses = Arrays.asList(
                Arrays.asList(1,1),
                Arrays.asList(1,3),
                Arrays.asList(1,5),
                Arrays.asList(1,7),
                Arrays.asList(2,1)
        );

        long result = 1;
        for(var traverse : traverses)
            result *= traverseMap(input, traverse.get(0), traverse.get(1));

        return result;
    }

    public static void main(String[] args) {
        LinkedList<String> input = new LinkedList<>(
                readInput(Day03.class.getClassLoader().getResourceAsStream("day_03_input.txt")));

        System.out.printf("First task: %d%n", firstTask(input));
        System.out.printf("First task: %d%n", secondTask(input));
    }


}
