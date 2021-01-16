import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Day19 {
    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNextLine())
            result.add(scanner.nextLine());
        return result;
    }

    private static InputData parseInput(ArrayList<String> input) {
        InputData inputData = new InputData();

        for(String inputRow : input) {
            if(inputRow.equals(""))
                continue;

            if(inputRow.contains(":")) {
                var s = inputRow.split(":");
                inputData.rules.put(Integer.parseInt(s[0]), s[1].strip());
            }
            else
                inputData.messages.add(inputRow);
        }
        return inputData;
    }

    private static long firstTask(InputData inputData) {
        String rule = inputData.rules.get(0);
        while (rule.matches(".*\\d.*")) {
            StringBuilder builder = new StringBuilder();
            for(String a : rule.split(" ")) {
                if(a.matches("[0-9]+")) {
                    builder.append(" ( " + inputData.rules.get(Integer.parseInt(a)) + " ) ");
                } else {
                    builder.append(a).append(" ");
                }
            }
            rule = builder.toString();
        }
        String regex = rule.replace(" ", "").replace("\"", "");
        regex = "^" + regex + "$";

        int validMessages = 0;
        for(var message : inputData.messages) {
            if(message.matches(regex))
                validMessages++;
        }

        return validMessages;
    }

    private static long secondTask(InputData inputData) {
        String rule31 = inputData.rules.get(31);
        String rule42 = inputData.rules.get(42);

        while (rule31.matches(".*\\d.*")) {
            StringBuilder builder = new StringBuilder();
            for(String a : rule31.split(" ")) {
                if(a.matches("[0-9]+")) {
                    builder.append(" ( " + inputData.rules.get(Integer.parseInt(a)) + " ) ");
                } else {
                    builder.append(a).append(" ");
                }
            }
            rule31 = builder.toString();
        }

        while (rule42.matches(".*\\d.*")) {
            StringBuilder builder = new StringBuilder();
            for(String a : rule42.split(" ")) {
                if(a.matches("[0-9]+")) {
                    builder.append(" ( " + inputData.rules.get(Integer.parseInt(a)) + " ) ");
                } else {
                    builder.append(a).append(" ");
                }
            }
            rule42 = builder.toString();
        }

        rule31 = "(" + rule31.replace(" ", "").replace("\"", "") + ")";
        rule42 = "(" + rule42.replace(" ", "").replace("\"", "") + ")";

        String regex = "^((42)+ ((42 31) | (42{2} 31{2}) | (42{3} 31{3}) | (42{4} 31{4}) | (42{5} 31{5})))$";
        regex = regex.replace("42", rule42).replace("31", rule31);
        regex = regex.replace(" ", "");
        int validMessages = 0;
        for(var message : inputData.messages) {
            if(message.matches(regex))
                validMessages++;
        }

        return validMessages;
    }

    public static void main(String[] args) {
        var input = new ArrayList<>(
                readInput(Day19.class.getClassLoader().getResourceAsStream("day_19_input.txt")));

        var inputData = parseInput(input);
        System.out.printf("First task: %d%n", firstTask(inputData));
        System.out.printf("Second task: %d%n", secondTask(inputData));
    }

    static class InputData {
        public HashMap<Integer, String> rules = new HashMap<>();
        public ArrayList<String> messages = new ArrayList<>();
    }
}
