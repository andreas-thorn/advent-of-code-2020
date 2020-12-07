import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class Day06 {
    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream).useDelimiter(Pattern.compile("\\n\\n", Pattern.MULTILINE));
        while (scanner.hasNext())
            result.add(scanner.next());
        return result;
    }

    private static int firstTask(LinkedList<String> input) {
        int result = 0;
        for(String answers : input){
            Set<Character> chars = new HashSet<>();
            for (char ch: answers.replace("\n", "").toCharArray())
                chars.add(ch);

            result += chars.size();
        }
        return result;
    }

    private static int countOccurrences(String val, String targetChar) {
        return val.length() - val.replace(targetChar, "").length();
    }

    private static int secondTask(LinkedList<String> input) {
        int result = 0;
        for(String answer : input) {
            int groupSize = countOccurrences(answer, "\n") + 1;
            String firstRow = answer.split("\n")[0];
            char[] charArray = firstRow.toCharArray();
            for (char letter : charArray) {
                int letterCount = countOccurrences(answer, Character.toString(letter));
                if (letterCount == groupSize)
                    result++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        LinkedList<String> input = new LinkedList<>(
                readInput(Day06.class.getClassLoader().getResourceAsStream("day_06_input.txt")));

        System.out.println("First task: " + firstTask(input));
        System.out.println("Second task: " + secondTask(input));
    }
}
