import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day11 {
    private static ArrayList<String> readInput(InputStream stream) {
        ArrayList<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNextLine())
            result.add(scanner.nextLine());
        return result;
    }

    private static final char OCCUPIED_SEAT = '#';
    private static final char AVAILABLE_SEAT = 'L';

    private static class Direction{
        int row;
        int column;

        Direction(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static List<Direction> directions = Arrays.asList( new Direction(-1, 0), new Direction(-1, 1), new Direction(0, 1),
            new Direction(1, 1), new Direction(1, 0), new Direction(1, -1), new Direction(0, -1), new Direction(-1, -1));

    private static int occupiedAdjacentSeats(int rowIndex, int columnIndex, ArrayList<String> seatLayout) {
        int occupiedAdjacentSeats = 0;

        for(var direction : directions) {
            int targetRow = rowIndex + direction.row;
            int targetColumn = columnIndex + direction.column;

            if (targetRow < 0 || targetRow >= seatLayout.size() || targetColumn < 0 || targetColumn >= seatLayout.get(0).length())
                continue;

            if(seatLayout.get(targetRow).charAt(targetColumn) == OCCUPIED_SEAT)
                occupiedAdjacentSeats++;
        }

        return occupiedAdjacentSeats;
    }

    private static int occupiedVisibleSeats(int row, int column, ArrayList<String> seatLayout) {
        int occupiedVisibleSeats = 0;
        ArrayList<Direction> completeDirections = new ArrayList<>();

        for(int step = 1; step < seatLayout.size(); step++) {
            if(completeDirections.size() == directions.size())
                break;

            for(var direction : directions) {
                if(completeDirections.contains(direction))
                    continue;

                int targetRow = row + direction.row * step;
                int targetColumn = column + direction.column * step;

                if (targetRow < 0 || targetRow >= seatLayout.size() || targetColumn < 0 || targetColumn >= seatLayout.get(0).length()) {
                    completeDirections.add(direction);
                    continue;
                }

                char seat = seatLayout.get(targetRow).charAt(targetColumn);
                if (seat == OCCUPIED_SEAT) {
                    completeDirections.add(direction);
                    occupiedVisibleSeats++;
                }
                else if(seat == AVAILABLE_SEAT)
                    completeDirections.add(direction);
            }
        }
        return occupiedVisibleSeats;
    }

    private static ArrayList<String> applySeating(ArrayList<String> currentSeating, int occupancyThreshold, boolean isFirst) {
        ArrayList<String> seatingResult = new ArrayList<>(currentSeating);
        for(int rowIndex = 0; rowIndex < currentSeating.size(); rowIndex++) {
            for(int columnIndex = 0; columnIndex < currentSeating.get(rowIndex).length(); columnIndex++) {
                char currentSeat = currentSeating.get(rowIndex).charAt(columnIndex);
                if(currentSeat == '.')
                    continue;

                int occupiedSeats = isFirst ? occupiedAdjacentSeats(rowIndex, columnIndex, currentSeating) : occupiedVisibleSeats(rowIndex, columnIndex, currentSeating);

                if(occupiedSeats == 0  && currentSeat == AVAILABLE_SEAT) {
                    StringBuilder newRow = new StringBuilder(seatingResult.get(rowIndex));
                    newRow.setCharAt(columnIndex, OCCUPIED_SEAT);
                    seatingResult.set(rowIndex, newRow.toString());
                }
                else if(occupiedSeats >= occupancyThreshold && currentSeat == OCCUPIED_SEAT) {
                    StringBuilder newRow = new StringBuilder(seatingResult.get(rowIndex));
                    newRow.setCharAt(columnIndex, AVAILABLE_SEAT);
                    seatingResult.set(rowIndex, newRow.toString());
                }
            }
        }
        return seatingResult;
    }

    private static ArrayList<String> getStableLayout(ArrayList<String> seatLayout, int occupancyThreshold, boolean first) {
        ArrayList<String> tmpSeatings = null;

        while(!seatLayout.equals(tmpSeatings)) {
            tmpSeatings = new ArrayList<>(seatLayout);
            seatLayout = applySeating(seatLayout, occupancyThreshold, first);
        }
        return seatLayout;
    }

    private static int firstTask(ArrayList<String> input){
        var finalSeatLayout = getStableLayout(input, 4, true);
        return finalSeatLayout.stream().mapToInt(seatRow -> (int) seatRow.chars().filter(seat -> seat == OCCUPIED_SEAT).count()).sum();
    }

    private static int secondTask(ArrayList<String> input){
        var finalSeatLayout = getStableLayout(input, 5, false);
        return finalSeatLayout.stream().mapToInt(seatRow -> (int) seatRow.chars().filter(seat -> seat == OCCUPIED_SEAT).count()).sum();
    }

    public static void main(String[] args) {
        ArrayList<String> input = new ArrayList<>(
                readInput(Day11.class.getClassLoader().getResourceAsStream("day_11_input.txt")));

        System.out.printf("First task: %d%n", firstTask(input));
        System.out.printf("Second task: %d%n", secondTask(input));
    }
}