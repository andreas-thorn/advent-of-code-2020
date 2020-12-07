import org.javatuples.Pair;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Day07 {
    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNextLine())
            result.add(scanner.nextLine());
        return result;
    }

    private static final String SHINY_GOLD_BAG = "shiny gold";
    private static final String EMPTY_BAG = "no other";
    private static HashMap<String, HashMap<String, Integer>> bags;

    private static Pair<String, List<String>> formatRuleText(String ruleText) {
        String strippedRule = ruleText.replaceAll("bags|bag", "").replace(" .", "");
        String[] ruleSplit = strippedRule.split(" contain ");

        String bagName = ruleSplit[0].strip();
        List<String> payload = Arrays.stream(ruleSplit[1].split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        return new Pair<>(bagName, payload);
    }

    private static HashMap<String, HashMap<String, Integer>> parseRules(final LinkedList<String> rules) {
        HashMap<String, HashMap<String, Integer>> result = new HashMap<>();

        for(String bagRule : rules) {
            var formattedBagRule = formatRuleText(bagRule);

            HashMap<String, Integer> bagPayload = new HashMap<>();
            for(String payload : formattedBagRule.getValue1()) {
                if (payload.equals(EMPTY_BAG)) break;

                String[] payloadSplit = payload.split(" ", 2);
                bagPayload.put(payloadSplit[1], Integer.parseInt(payloadSplit[0]));
            }
            result.put(formattedBagRule.getValue0(), bagPayload);
        }
        return result;
    }

    private static boolean canContainGoldenBag(String bag) {
        if(bags.get(bag).keySet().stream().anyMatch(b -> b.contains(SHINY_GOLD_BAG)))
            return true;
        return bags.get(bag).keySet().stream().anyMatch(Day07::canContainGoldenBag);
    }

    private static int firstTask() {
        return (int) bags.keySet().stream()
                .filter(Day07::canContainGoldenBag)
                .count();
    }

    private static int bagsRequired(String bag) {
        int result = 1;
        var bagPayload = bags.get(bag);
        for (Map.Entry<String,Integer> entry : bagPayload.entrySet())
            result += (bagsRequired(entry.getKey()) * entry.getValue());

        return result;
    }

    private static long secondTask() {
        return bagsRequired(SHINY_GOLD_BAG) - 1; // Subtract shiny gold bag.
    }

    public static void main(String[] args) {
        LinkedList<String> input = new LinkedList<>(
                readInput(Day07.class.getClassLoader().getResourceAsStream("day_07_input.txt")));

        bags = parseRules(input);
        System.out.printf("First task: %d%n", firstTask());
        System.out.printf("Second task: %d%n", secondTask());
    }
}