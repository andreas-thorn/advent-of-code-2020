import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day02 {

    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNextLine()) {
            result.add(scanner.nextLine());
        }
        return result;
    }

    public static class PasswordPolicy{
        private final int minLength;
        private final int maxLength;
        private final char letter;

        public PasswordPolicy(String policy){
            String[] policyInfo = policy.split(" ", -1);
            String[] passwordRange = policyInfo[0].split("-", -1);
            minLength = Integer.parseInt(passwordRange[0]);
            maxLength = Integer.parseInt(passwordRange[1]);
            letter = policyInfo[1].charAt(0);
        }
    }

    private static boolean validPasswordGen1(String password, PasswordPolicy policy) {
        long characterCount = password.chars().filter(character -> character == policy.letter).count();
        return characterCount >= policy.minLength && characterCount <= policy.maxLength;
    }

    private static int firstTask(List<String> input) {
        var validPasswords = 0;

        for(String row : input) {
            String[] rowData = row.split(":", -1);
            PasswordPolicy pwdPolicy = new PasswordPolicy(rowData[0]);

            if(validPasswordGen1(rowData[1], pwdPolicy)) validPasswords++;
        }

        return validPasswords;
    }

    private static boolean validPasswordGen2(String password, PasswordPolicy policy) {
        return password.charAt(policy.minLength - 1) == policy.letter ^
                password.charAt(policy.maxLength - 1) == policy.letter;
    }

    private static int secondTask(List<String> input) {
        var validPasswords = 0;

        for(String row : input) {
            String[] rowData = row.split(":", -1);
            PasswordPolicy pwdPolicy = new PasswordPolicy(rowData[0]);

            if(validPasswordGen2(rowData[1].strip(), pwdPolicy)) validPasswords++;
        }

        return validPasswords;
    }

    public static void main(String[] args) {
        List<String> input = readInput(Day02.class.getClassLoader().getResourceAsStream("day_02_input.txt"));
        System.out.println("First task: " + firstTask(input));
        System.out.println("Second task: " + secondTask(input));
    }

}
