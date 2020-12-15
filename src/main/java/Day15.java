import java.util.*;

public class Day15 {
    private static HashMap<Integer,Integer> appendStartNumbers(ArrayList<Integer> startNumbers) {
        HashMap<Integer, Integer> result = new HashMap<>();
        for(int i = 0; i < startNumbers.size(); i++)
            result.put(startNumbers.get(i), i+1);

        return result;
    }

    private static int getLastSpokenNumber(ArrayList<Integer> startNumbers, int target){
        var saidNumbers = appendStartNumbers(startNumbers);
        for(int i = 0; i < startNumbers.size(); i++){
            saidNumbers.put(startNumbers.get(i), i+1);
        }

        int turnCounter = saidNumbers.keySet().size() + 1;
        int lastSaidNumber = 0;

        while(turnCounter != target) {
            if(saidNumbers.containsKey(lastSaidNumber)){
                int lastSaidTurn  = saidNumbers.get(lastSaidNumber);
                saidNumbers.put(lastSaidNumber, turnCounter);
                lastSaidNumber = turnCounter - lastSaidTurn;
            }
            else {
                saidNumbers.put(lastSaidNumber, turnCounter);
                lastSaidNumber = 0;
            }
            turnCounter++;
        }

        return lastSaidNumber;
    }

    private static int firstTask(final ArrayList<Integer> startNumbers) {
        return getLastSpokenNumber(startNumbers, 2020);
    }

    private static int secondTask(final ArrayList<Integer> startNumbers) {
        return getLastSpokenNumber(startNumbers, 30000000);
    }

    public static void main(String[] args) {
        String inputNumbersStr = "18,11,9,0,5,1";

        ArrayList<Integer> numbers = new ArrayList<>();
        for(var number : inputNumbersStr.split(","))
            numbers.add(Integer.parseInt(number));

        System.out.printf("First task: %d%n", firstTask(numbers));
        System.out.printf("Second task: %d%n", secondTask(numbers));
    }
}