import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day13 {
    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNextLine())
            result.add(scanner.nextLine());
        return result;
    }

    private static int firstTask(int arrivalTime, String[] busDepartures) {
        int waitingTime = Integer.MAX_VALUE;
        int optimalBusId = -1;

        for (var busId : busDepartures) {
            if (busId.equals("x"))
                continue;

            int id = Integer.parseInt(busId);
            int timeToDeparture = id - arrivalTime % id;
            if (timeToDeparture < waitingTime) {
                optimalBusId = id;
                waitingTime = timeToDeparture;
            }
        }

        return waitingTime * optimalBusId;
    }

    private static long secondTask(String[] busDepartures) {
        long timestamp = 1;
        long steps = 1;

        for(int i = 0; i < busDepartures.length; i++){
            String busIdStr = busDepartures[i];
            if(busIdStr.equals("x"))
                continue;

            int busId = Integer.parseInt(busIdStr);
            while(true) {
                if((timestamp + i) % busId == 0) {
                    steps *= busId;
                    break;
                }
                timestamp += steps;
            }
        }
        return timestamp;
    }

    public static void main(String[] args) {
        var input = new ArrayList<>(
                readInput(Day13.class.getClassLoader().getResourceAsStream("day_13_input.txt")));

        int arrivalTime = Integer.parseInt(input.get(0));
        String[] busDepartures = input.get(1).split(",");

        System.out.printf("First task: %d%n", firstTask(arrivalTime, busDepartures));
        System.out.printf("Second task: %d%n", secondTask(busDepartures));
    }

}
