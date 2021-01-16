import java.io.InputStream;
import java.util.*;

public class Day24 {
    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNextLine())
            result.add(scanner.nextLine());
        return result;
    }

    static HashMap<String, int[]> moves = new HashMap<>()
    {{
        put("ne", new int[]{1, 0, -1});
        put("e", new int[]{1, -1, 0});
        put("se", new int[]{0, -1, 1});
        put("sw", new int[]{-1, 0, 1});
        put("w", new int[]{-1, 1, 0});
        put("nw", new int[]{0, 1, -1});
    }};

    private static int countBlackCells(char[][][] grid) {
        int blackCount = 0;
        for (char[][] chars : grid) {
            for (char[] aChar : chars) {
                for (char c : aChar) {
                    if (c == '#')
                        blackCount++;
                }
            }
        }
        return blackCount;
    }

    private static ArrayList<String> getDirections(String row) {
        ArrayList<String> directionList = new ArrayList<>();
        while(row.length() > 0) {
            String direction = moves.containsKey(row.substring(0, 1)) ? row.substring(0, 1) : row.substring(0, 2);
            row = row.substring(direction.length());
            directionList.add(direction);
        }
        return directionList;
    }

    private static char[][][] emptyGrid(int x, int y, int z) {
        char[][][] grid = new char[z][y][x];

        for(int i = 0; i < z; i++) {
            char[][] xy = new char[y][x];
            for(int j = 0; j < y; j++) {
                char[] row = new char[x];
                Arrays.fill(row, '.');
                xy[j] = row;
            }
            grid[i] = xy;
        }
        return grid;
    }

    private static int firstTask(ArrayList<String> input) {
        var grid = emptyGrid(100, 100, 100);
        for(String row : input) {
            int x = 50, y = 50, z = 50;
            var directions = getDirections(row);
            for(var direction : directions) {
                var coordinateChange = moves.get(direction);
                x = x + coordinateChange[0];
                y = y + coordinateChange[1];
                z = z + coordinateChange[2];
            }
            if(grid[z][y][x] == '#')
                grid[z][y][x] = '.';
            else
                grid[z][y][x] = '#';
        }
        return countBlackCells(grid);
    }

    private static char[][][] copyGrid(char[][][] grid) {
        char[][][] gridCopy = new char[grid.length][grid[0].length][grid[0][0].length];
        for(int z = 0; z < grid.length; z++)
            for (int y = 0; y < grid[z].length; y++)
                System.arraycopy(grid[z][y], 0, gridCopy[z][y], 0, grid[z][y].length);

        return gridCopy;
    }

    private static long adjacentBlackTiles(char[][][] grid, int x, int y, int z) {
        int count = 0;
        for(var direction : moves.values()) {
            int tmpZ = z + direction[2];
            int tmpY = y + direction[1];
            int tmpX = x + direction[0];

            if(tmpZ < 0|| tmpY < 0|| tmpX < 0)
                continue;

            if(tmpZ > grid.length - 1 || tmpY > grid.length - 1 || tmpX > grid.length - 1)
                continue;

            if(grid[z + direction[2]][y + direction[1]][x + direction[0]] == '#')
                count++;
        }
        return count;
    }

    private static char[][][] move(ArrayList<String> input) {
        var grid = emptyGrid(150, 150, 150);
        for(String row : input) {
            int x = 75, y = 75, z = 75;
            var directions = getDirections(row);
            for(var direction : directions) {
                var coordinateChange = moves.get(direction);
                x = x + coordinateChange[0];
                y = y + coordinateChange[1];
                z = z + coordinateChange[2];
            }
            if(grid[z][y][x] == '#')
                grid[z][y][x] = '.';
            else
                grid[z][y][x] = '#';
        }

        return grid;
    }

    private static int secondTask(ArrayList<String> input) {
        int day = 1;
        var grid = move(input);
        while(day <= 100) {
            var gridCopy = copyGrid(grid);

            for(int z = 0; z < grid.length; z++) {
                for(int y = 0; y < grid[z].length; y++) {
                    for(int x = 0; x < grid[z][y].length; x++) {
                        var blackCount = adjacentBlackTiles(grid, x, y, z);

                        if(grid[z][y][x] == '#' && (blackCount == 0 || blackCount > 2)) {
                            gridCopy[z][y][x] = '.';
                        }
                        else if (grid[z][y][x] == '.' && blackCount == 2)
                            gridCopy[z][y][x] = '#';
                        }
                    }
                }
            grid = copyGrid(gridCopy);
            day++;
        }
        return countBlackCells(grid);
    }

    public static void main(String[] args) {
        var input = new ArrayList<>(
                readInput(Day24.class.getClassLoader().getResourceAsStream("day_24_input.txt")));

        System.out.printf("First task: %d%n", firstTask(input));
        System.out.printf("Second task: %d%n", secondTask(input));
    }
}
