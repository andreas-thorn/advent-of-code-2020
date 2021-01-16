import java.io.InputStream;
import java.util.*;

public class Day14 {
    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNextLine())
            result.add(scanner.nextLine());
        return result;
    }

    private static String applyMask(String mask, String value) {
        if(mask.length() != value.length())
            value = "0".repeat(mask.length() - value.length()).concat(value);

        char[] result = value.toCharArray();
        for(int i = 0; i < mask.length(); i++) {
            if(mask.charAt(i) == '1') {
                result[i] = '1';
            }
            else if(mask.charAt(i) == '0'){
                result[i] = '0';
            }
        }
        String res = "";
        for(char c : result){
            String app = Character.toString(c);
            res = res.concat(app);
        }
        return res;
    }

    private static String applyMaskToMemory(String mask, String value) {
        if(mask.length() != value.length())
            value = "0".repeat(mask.length() - value.length()).concat(value);

        char[] result = value.toCharArray();
        for(int i = 0; i < mask.length(); i++) {
            if(mask.charAt(i) == '1') {
                result[i] = '1';
            }
            else if(mask.charAt(i) == 'X'){
                result[i] = 'X';
            }
        }
        String res = "";
        for(char c : result){
            String app = Character.toString(c);
            res = res.concat(app);
        }
        return res;
    }

    private static List<String> replaceFloatings(String memory) {
        if(!memory.contains("X")) {
            var a = new ArrayList<String>();
            a.add(memory);
            return a;
        }

        List<String> res = new ArrayList<>();
        for(int i = 0; i < memory.length(); i++) {
            if(memory.charAt(i) == 'X'){
                String a = memory.substring(0,i);
                String zeroes = a.concat("0");
                String ones = a.concat("1");
                List<String> c = replaceFloatings(memory.substring(i+1));
                if (c.size() == 0) {
                    res.add(zeroes);
                    res.add(ones);
                    return res;
                }
                for(String d : c) {
                    res.add(zeroes.concat(d));
                    res.add(ones.concat(d));
                }

                return res;
            }
        }
        return res;
    }

    private static long firstTask(ArrayList<String> input) {
        HashMap<String, Long> memory = new HashMap<>();
        String mask = "";
        for(var inputLine : input){
            String[] inputLineSplit = inputLine.split(" = ");

            if(inputLine.startsWith("mask")){
                mask = inputLineSplit[1];
                continue;
            }

            String memoryString = inputLineSplit[0].substring(4, inputLineSplit[0].length() - 1);
            String binaryValue = Integer.toBinaryString(Integer.parseInt(inputLineSplit[1]));
            String valueAfterMask = applyMask(mask, binaryValue);
            memory.put(memoryString, Long.parseLong(valueAfterMask, 2));
        }

        return memory.keySet().stream().mapToLong(memory::get).sum();
    }

    private static long secondTask(ArrayList<String> input) {
        HashMap<Long, Long> memory = new HashMap<>();
        String mask = "";
        for(var inputLine : input){
            String[] inputLineSplit = inputLine.split(" = ");

            if(inputLine.startsWith("mask")){
                mask = inputLineSplit[1];
                continue;
            }

            int memoryString = Integer.parseInt(inputLineSplit[0].substring(4, inputLineSplit[0].length() - 1));
            long value = Long.parseLong(inputLineSplit[1]);

            String binaryMemory = Integer.toBinaryString(memoryString);
            String memoryAfterMask = applyMaskToMemory(mask, binaryMemory);

            List<String> memoryPermutations = replaceFloatings(memoryAfterMask);
            memoryPermutations.forEach(permutation -> memory.put(Long.parseLong(permutation, 2), value));
        }
        return memory.keySet().stream().mapToLong(memory::get).sum();
    }

    public static void main(String[] args) {
        var input = new ArrayList<>(
                readInput(Day14.class.getClassLoader().getResourceAsStream("day_14_input.txt")));

        System.out.printf("First task: %d%n", firstTask(input));
        System.out.printf("Second task: %d%n", secondTask(input));
    }
}
