import java.io.InputStream;
import java.util.*;
import java.util.regex.Pattern;

public class Day04 {
    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream).useDelimiter(Pattern.compile("\\n\\n", Pattern.MULTILINE));
        while (scanner.hasNext()) {
            result.add(scanner.next());
        }
        return result;
    }

    public final static List<String> validEyeColors = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    private final static List<String> requiredFields = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

    public static class Passport{
        private final int birthYear;
        private final int issueYear;
        private final int expirationYear;
        private final String height;
        private final String hairColor;
        private final String eyeColor;
        private final String passportId;

        private Passport(Dictionary<String, String> passportInfo) {
            this.birthYear = Integer.parseInt(passportInfo.get("byr"));
            this.issueYear = Integer.parseInt(passportInfo.get("iyr"));
            this.expirationYear = Integer.parseInt(passportInfo.get("eyr"));
            this.height = passportInfo.get("hgt");
            this.hairColor = passportInfo.get("hcl");
            this.eyeColor = passportInfo.get("ecl");
            this.passportId = passportInfo.get("pid");
        }
    }

    private static List<Dictionary<String, String>> getValidPassports(LinkedList<String> input) {
        List<Dictionary<String, String>> validPassports = new LinkedList();
        for (String passportText : input) {
            Dictionary<String, String> passportKeyVal = new Hashtable<>();
            for (String field :  passportText.split(" |\n")) {
                String[] keyValue = field.split(":");
                passportKeyVal.put(keyValue[0], keyValue[1]);
            }
            if (Collections.list(passportKeyVal.keys()).containsAll(requiredFields))
                validPassports.add(passportKeyVal);
        }
        return validPassports;
    }

    private static int firstTask(LinkedList<String> input) {
        return getValidPassports(input).size();
    }

    private static boolean validatePassportFields(Passport passport) {
        if (1920 > passport.birthYear || passport.birthYear > 2002)
            return false;
        if (2010 > passport.issueYear || passport.issueYear > 2020)
            return false;
        if (2020 > passport.expirationYear || passport.expirationYear > 2030)
            return false;
        int heightValue = Integer.parseInt(passport.height.replaceAll("[a-z]", ""));
        if (!(passport.height.contains("cm") || passport.height.contains("in")) ||
                (passport.height.contains("cm") && (heightValue < 150 || heightValue > 193)) ||
                (passport.height.contains("in") && (heightValue < 59 ||heightValue > 76)))
            return false;
        if(!passport.hairColor.matches("#[a-f0-9]{6}"))
            return false;
        if(!validEyeColors.contains(passport.eyeColor))
            return false;
        if (passport.passportId.length() != 9)
            return false;

        return true;
    }

    private static int secondTask(LinkedList<String> input) {
        return (int) getValidPassports(input).stream()
                .map(Passport::new)
                .filter(passport -> validatePassportFields(passport))
                .count();
    }

    public static void main(String[] args) {
        LinkedList<String> input = new LinkedList<>(
                readInput(Day04.class.getClassLoader().getResourceAsStream("day_04_input.txt")));

        System.out.println("First task: " + firstTask(input));
        System.out.println("Second task: " + secondTask(input));
    }
}