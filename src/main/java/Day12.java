import org.javatuples.Pair;

import java.io.InputStream;
import java.util.*;

public class Day12 {
    private static ArrayList<Pair<Character, Integer>> readInput(InputStream stream) {
        ArrayList<Pair<Character, Integer>> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNextLine()) {
            var scanned = scanner.nextLine();
            result.add(new Pair<>(scanned.substring(0, 1).charAt(0), Integer.parseInt(scanned.substring(1))));
        }
        return result;
    }

    private final static char NORTH = 'N';
    private final static char EAST = 'E';
    private final static char SOUTH = 'S';
    private final static char WEST = 'W';
    private final static char LEFT = 'L';
    private final static char RIGHT = 'R';
    private final static char FORWARD = 'F';

    private static List<Character> directions = Arrays.asList(NORTH, EAST, SOUTH, WEST);

    private static class ShipNavigation {
        int shipNorth;
        int shipEast;
        char shipDirection;

        public ShipNavigation() {
            shipNorth = 0;
            shipEast = 0;
            shipDirection = EAST;
        }

        public void move(char direction, int value) {
            switch (direction) {
                case NORTH:
                    this.shipNorth += value;
                    break;
                case EAST:
                    this.shipEast += value;
                    break;
                case SOUTH:
                    this.shipNorth -= value;
                    break;
                case WEST:
                    this.shipEast -= value;
                    break;
                case FORWARD:
                    move(this.shipDirection, value);
                    break;
                case RIGHT:
                case LEFT:
                    turnShip(direction, value);
                    break;
            }
        }

        private void turnShip(char turnDirection, int degrees) {
            int turns = degrees/90;
            if(turnDirection == LEFT)
                turns = turns * -1;

            int updateDirectionIndex = (directions.indexOf(shipDirection) + turns + 4) % 4;
            shipDirection = directions.get(updateDirectionIndex);
        }
    }

    private static class WaypointNavigation {
        int waypointNorth;
        int waypointEast;
        int shipNorth;
        int shipEast;

        public WaypointNavigation() {
            waypointNorth = 1;
            waypointEast = 10;
            shipNorth = 0;
            shipEast = 0;
        }

        public void moveShip(int value) {
            shipNorth = shipNorth + waypointNorth * value;
            shipEast = shipEast + waypointEast * value;
        }

        public void move(char direction, int value) {
            switch(direction) {
                case NORTH:
                    waypointNorth += value;
                    break;
                case SOUTH:
                    waypointNorth -= value;
                    break;
                case EAST:
                    waypointEast += value;
                    break;
                case WEST:
                    waypointEast -= value;
                    break;
                case FORWARD:
                    moveShip(value);
                    break;
                case RIGHT:
                case LEFT:
                    turnWaypoint(direction, value);
            }
        }

        public void turnWaypoint(char direction, int degrees) {
            for(int i = 0; i < degrees/90; i++) {
                if (direction == LEFT) {
                    int tmpNorth = waypointNorth;
                    waypointNorth = waypointEast;
                    waypointEast = tmpNorth * -1;
                } else if (direction == RIGHT) {
                    int tmpEast = waypointEast;
                    waypointEast = waypointNorth;
                    waypointNorth = tmpEast * -1;
                }
            }
        }
    }

    private static int firstTask(ArrayList<Pair<Character, Integer>> instructions){
        ShipNavigation shipNavigation = new ShipNavigation();
        for(var instruction : instructions)
            shipNavigation.move(instruction.getValue0(), instruction.getValue1());

        return Math.abs(shipNavigation.shipEast) + Math.abs(shipNavigation.shipNorth);
    }

    private static int secondTask(ArrayList<Pair<Character, Integer>> instructions){
        WaypointNavigation waypointNavigation = new WaypointNavigation();

        for(var instruction : instructions) {
            waypointNavigation.move(instruction.getValue0(), instruction.getValue1());
        }

        return Math.abs(waypointNavigation.shipEast) + Math.abs(waypointNavigation.shipNorth);
    }

    public static void main(String[] args) {
        var input = new ArrayList<>(
                readInput(Day12.class.getClassLoader().getResourceAsStream("day_12_input.txt")));

        System.out.printf("First task: %d%n", firstTask(input));
        System.out.printf("Second task: %d%n", secondTask(input));
    }
}