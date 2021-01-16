import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Day16 {
    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNextLine())
            result.add(scanner.nextLine());
        return result;
    }

    static class Range {
        int low;
        int high;

        public Range(String range) {
            String[] rangeSplit = range.split("-");
            this.low = Integer.parseInt(rangeSplit[0]);
            this.high = Integer.parseInt(rangeSplit[1]);
        }

        private boolean withinRange(int number) {
            return (number >= low && number <= high);
        }
    }

    static class Rule {
        String name;
        ArrayList<Range> ranges;

        public Rule(String name, String ranges) {
            this.name = name;
            this.ranges = new ArrayList<>();
            for(String rangeString : ranges.split(" or "))
                this.ranges.add(new Range(rangeString));
        }

        private boolean validValue(int number) {
            return this.ranges.stream().anyMatch(range -> range.withinRange(number));
        }
    }

    private static ArrayList<Rule> parseRules(ArrayList<String> rawRules) {
        return rawRules.stream()
                .map(rawRule -> rawRule.split(": "))
                .map(ruleSplit -> new Rule(ruleSplit[0], ruleSplit[1]))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private static boolean fieldCompliesWithAnyRule(int ticketValue, ArrayList<Rule> rules) {
        return rules.stream().anyMatch(rule -> rule.validValue(ticketValue));
    }

    private static int invalidTicketValues(ArrayList<Rule> rules, ArrayList<String> tickets) {
        return tickets.stream().map(ticket -> ticket.split(",")).mapToInt((ticket) ->
        {
            int invalidValues = 0;
            for (var ticketValue: ticket) {
                if(!fieldCompliesWithAnyRule(Integer.parseInt(ticketValue), rules))
                    invalidValues += Integer.parseInt(ticketValue);

            }
            return invalidValues;
        }).reduce(0, Integer::sum);
    }

    private static int firstTask(ArrayList<Rule> rules, ArrayList<String> tickets) {
        return invalidTicketValues(rules, tickets);
    }

    private static ArrayList<String> filterValidTickets(ArrayList<String> tickets, ArrayList<Rule> rules) {
        ArrayList<String> validTickets = new ArrayList<>();

        for(var ticket : tickets) {
            int incorrectValues = 0;
            for(var ticketValue : ticket.split(",")) {
                if(!fieldCompliesWithAnyRule(Integer.parseInt(ticketValue), rules)) {
                    incorrectValues += Integer.parseInt(ticketValue);
                    if(incorrectValues == 0){
                        incorrectValues += 1;
                    }
                    break;
                }
            }

            if(incorrectValues == 0)
                validTickets.add(ticket);
        }

        return validTickets;
    }

    private static long determineFieldOrder(ArrayList<Rule> rules, ArrayList<String> tickets, String myTicket) {
        HashMap<Integer, ArrayList<Rule>> alternatives = new HashMap<>();
        int ticketLength = tickets.get(0).split(",").length;

        for(int i = 0; i < ticketLength; i++) {
            alternatives.put(i, new ArrayList<>(rules));
        }

        for(int i = 0; i < ticketLength; i++) {
            ArrayList<Integer> ticketValues = new ArrayList<>();
            for (var ticket : tickets) {
                String[] ticketVals = ticket.split(",");
                ticketValues.add(Integer.parseInt(ticketVals[i]));
            }

            for (var rule : rules) {
                int count = 0;
                for (var ticketValue : ticketValues) {
                    if (rule.validValue(ticketValue))
                        count++;
                }
                if(count < tickets.size()) {
                    alternatives.get(i).remove(rule);
                }
            }
        }

        ArrayList<Rule> determinedFields = new ArrayList<>();
        while(determinedFields.size() < 20) {
            for(var key : alternatives.keySet()){
                var alternativesList = alternatives.get(key);
                if (alternativesList.size() == 1){
                    if(!determinedFields.contains(alternativesList.get(0)))
                        determinedFields.add(alternativesList.get(0));
                    continue;
                }
                alternativesList.removeAll(determinedFields);
            }
        }

        long result = 1;
        String[] myTicketSplit = myTicket.split(",");
        for(int i = 0; i < alternatives.size(); i++) {
            if(alternatives.get(i).size() == 1 && alternatives.get(i).get(0).name.startsWith("departure")) {
                result *= Integer.parseInt(myTicketSplit[i]);
            }
        }

        return result;
    }

    private static long secondTask(ArrayList<Rule> rules, ArrayList<String> nearbyTickets, String myTicket) {
        var validTickets = filterValidTickets(nearbyTickets, rules);
        return determineFieldOrder(rules, validTickets, myTicket);
    }

    public static void main(String[] args) {
        var input = new ArrayList<>(
                readInput(Day16.class.getClassLoader().getResourceAsStream("day_16_input.txt")));

        var rules = parseRules(new ArrayList<>(input.subList(0, 20)));
        var ticket = new ArrayList<>(input.subList(22,23));
        var nearbyTickets = new ArrayList<>(input.subList(25, input.size()));
        System.out.printf("First task: %d%n", firstTask(rules, nearbyTickets));
        System.out.printf("Second task: %d%n", secondTask(rules, nearbyTickets, ticket.get(0)));
    }
}