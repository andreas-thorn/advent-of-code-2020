import org.javatuples.Pair;

import java.io.InputStream;
import java.util.*;

public class Day08 {
    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNextLine())
            result.add(scanner.nextLine());
        return result;
    }

    public static class Computer {
        int pointerPosition;
        int accumulator;
        List<Integer> pointerHistory;

        public Computer(){
            pointerPosition = 0;
            accumulator = 0;
            pointerHistory = new ArrayList<>();
        }

        public void compute(final String operation, final int operationArgument) {
            pointerHistory.add(pointerPosition);

            switch (operation) {
                case "nop":
                    pointerPosition++;
                    break;
                case "acc":
                    accumulator += operationArgument;
                    pointerPosition++;
                    break;
                case "jmp":
                    pointerPosition += operationArgument;
                    break;
                default:
                    break;
            }
        }
    }

    public enum ExitCode {
        TERMINATED,
        LOOP_DETECTED
    }

    private static Pair<String, Integer> parseCommand(final String command) {
        String[] commandSplit = command.split(" ");
        return new Pair<>(commandSplit[0], Integer.parseInt(commandSplit[1]));
    }

    private static Pair<ExitCode, Integer> runInstructions(final LinkedList<String> instructions) {
        Computer computer = new Computer();

        while(!computer.pointerHistory.contains(computer.pointerPosition)) {
            if(computer.pointerPosition >= instructions.size())
                return new Pair<>(ExitCode.TERMINATED, computer.accumulator);

            var command = parseCommand(instructions.get(computer.pointerPosition));
            computer.compute(command.getValue0(), command.getValue1());
        }

        return new Pair<>(ExitCode.LOOP_DETECTED, computer.accumulator);
    }

    private static int firstTask(final LinkedList<String> input) {
        return runInstructions(input).getValue1();
    }

    private static String swapInstruction(final String command) {
        return command.contains("jmp") ? command.replace("jmp", "nop") : command.replace("nop", "jmp");
    }

    private static int secondTask(final LinkedList<String> input) {
        for(int i = 0; i < input.size(); i++) {
            if(input.get(i).matches("^(jmp|nop).*$")) {
                LinkedList<String> copyInput = new LinkedList<>(input);
                copyInput.set(i, swapInstruction(input.get(i)));
                var executionResult = runInstructions(copyInput);
                if(executionResult.getValue0() == ExitCode.TERMINATED)
                    return executionResult.getValue1();
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        LinkedList<String> input = new LinkedList<>(
                readInput(Day08.class.getClassLoader().getResourceAsStream("day_08_input.txt")));

        System.out.printf("First task: %d%n", firstTask(input));
        System.out.printf("Second task: %d%n", secondTask(input));
    }
}