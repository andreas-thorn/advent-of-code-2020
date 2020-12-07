import java.io.InputStream;
import java.util.*;

public class Day07 {
    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNextLine())
            result.add(scanner.nextLine());
        return result;
    }

    private static String SHINY_GOLD_BAG = "shiny gold";
    private static String EMPTY_BAG = "no other";
    private static HashMap<String, HashMap<String, Integer>> bags = new HashMap<>();

    private static void parseBagRules(final LinkedList<String> rules) {
        for(String bagRule : rules) {
            String strippedRule = bagRule.replaceAll("bags|bag", "").replace(" .", "");
            String[] res = strippedRule.split(" contain ");

            HashMap<String, Integer> bagPayload = new HashMap<>();
            for(String payload : Arrays.asList(res[1].split(","))) {
                if (payload.equals(EMPTY_BAG)) break;

                payload = payload.strip();
                String[] payloadSplit = payload.split(" ", 2);
                bagPayload.put(payloadSplit[1], Integer.parseInt(payloadSplit[0]));
            }
            bags.put(res[0].strip(), bagPayload);
        }
    }

    private static boolean canContainGoldenBag(String bag) {
        if(!bags.containsKey(bag)) return false;
        if(bags.get(bag).keySet().stream().anyMatch(bag1 -> bag1.contains(SHINY_GOLD_BAG))) return true;
        return bags.get(bag).keySet().stream().anyMatch(Day07::canContainGoldenBag);
    }

    private static int firstTask() {
        return (int) bags.keySet().stream().filter(Day07::canContainGoldenBag).count();
    }

    private static int totalBags(String bag) {
        int result = 1;
        for (Map.Entry<String,Integer> entry : bags.get(bag).entrySet())
            result += (totalBags(entry.getKey()) * entry.getValue());

        return result;
    }

    private static long secondTask() {
        return totalBags(SHINY_GOLD_BAG) - 1; // Subtract shiny gold bag.
    }

    public static void main(String[] args) {
        LinkedList<String> input = new LinkedList<>(
                readInput(Day07.class.getClassLoader().getResourceAsStream("day_07_input.txt")));

        parseBagRules(input);
        System.out.printf("First task: %d%n", firstTask());
        System.out.printf("Second task: %d%n", secondTask());
    }
}