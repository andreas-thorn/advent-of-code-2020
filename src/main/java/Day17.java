import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day17 {
    private static List<String> readInput(InputStream stream) {
        List<String> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        while (scanner.hasNextLine())
            result.add(scanner.nextLine());
        return result;
    }

    private static final int ITERATIONS = 6;
    private static final int[] NEARBY_COORDS = new int[]{-1, 0, 1};

    private static int checkAdjacentCells3d(char[][][] grid, int[] position, int wIndex) {
        int activeAdjacentCells = 0;

        for(int z : NEARBY_COORDS) {
            int zIndex = position[2] + z;
            if(zIndex < 0 || zIndex > grid.length - 1)
                continue;

            for(int y : NEARBY_COORDS) {
                int yIndex = position[1] + y;
                if(yIndex < 0 || yIndex > grid[zIndex].length - 1)
                    continue;

                for(int x : NEARBY_COORDS) {
                    int xIndex = position[0] + x;
                    if(xIndex < 0 || xIndex > grid[zIndex][yIndex].length - 1)
                        continue;

                    if(x == 0 && y == 0 && z == 0 && wIndex == 0)
                        continue;

                    char cell = grid[zIndex][yIndex][xIndex];
                    if(cell == '#') {
                        activeAdjacentCells++;
                    }
                }
            }
        }
        return activeAdjacentCells;
    }

    private static int checkAdjacentCells4d(char[][][][] grid, int[] position) {
        int activeAdjacentCells = 0;

        for(int w : NEARBY_COORDS) {
            int wIndex = w + position[3];
            if (wIndex <= 0 || wIndex >= grid.length)
                continue;

            activeAdjacentCells += checkAdjacentCells3d(grid[w + position[3]], position, w);
        }
        return activeAdjacentCells;
    }

    private static char[][][] copyGrid3d(char[][][] grid) {
        char[][][] gridCopy = new char[grid.length][grid[0].length][grid[0][0].length];
        for(int z = 0; z < grid.length; z++)
            for (int y = 0; y < grid[z].length; y++)
                System.arraycopy(grid[z][y], 0, gridCopy[z][y], 0, grid[z][y].length);

        return gridCopy;
    }

    private static char[][][][] copyGrid4d(char[][][][] grid) {
       return Arrays.stream(grid).map(Day17::copyGrid3d).toArray(char[][][][]::new);
    }

    private static int activeCells3d(char[][][] grid) {
        int count = 0;

        for (char[][] XY : grid) {
            for (char[] xRow : XY) {
                for (char x : xRow) {
                    if (x == '#')
                        count++;
                }
            }
        }
        return count;
    }

    private static int activeCells4d(char[][][][] grid) {
        return Arrays.stream(grid).map(grid3d -> activeCells3d(grid3d)).reduce(0, Integer::sum);
    }

    private static int firstTask(char[][][] grid) {
        for(int i = 0; i < 6; i++) {
            char[][][] gridCopy = copyGrid3d(grid);
            for (int zIndex = 0; zIndex < grid.length; zIndex++) {
                for (int yIndex = 0; yIndex < grid[zIndex].length; yIndex++) {
                    for (int xIndex = 0; xIndex < grid[zIndex][yIndex].length; xIndex++) {
                        char currentVal = grid[zIndex][yIndex][xIndex];
                        int activeAdjacentCells = checkAdjacentCells3d(grid, new int[]{xIndex, yIndex, zIndex}, 0);

                        if (currentVal == '.' && activeAdjacentCells == 3) {
                            gridCopy[zIndex][yIndex][xIndex] = '#';
                        } else if (currentVal == '#' && (activeAdjacentCells < 2 || activeAdjacentCells > 3)) {
                            gridCopy[zIndex][yIndex][xIndex] = '.';
                        }
                    }
                }
            }
            grid = copyGrid3d(gridCopy);
        }

        return activeCells3d(grid);
    }

    private static int secondTask(char[][][][] grid) {
        for(int i = 0; i < 6; i++) {
            char[][][][] gridCopy = copyGrid4d(grid);
            for(int wIndex = 0; wIndex < grid.length; wIndex++) {
                for (int zIndex = 0; zIndex < grid[wIndex].length; zIndex++) {
                    for (int yIndex = 0; yIndex < grid[wIndex][zIndex].length; yIndex++) {
                        for (int xIndex = 0; xIndex < grid[wIndex][zIndex][yIndex].length; xIndex++) {
                            char currentVal = grid[wIndex][zIndex][yIndex][xIndex];
                            int activeAdjacentCells = checkAdjacentCells4d(grid, new int[]{xIndex, yIndex, zIndex, wIndex});

                            if (currentVal == '.' && activeAdjacentCells == 3) {
                                gridCopy[wIndex][zIndex][yIndex][xIndex] = '#';
                            } else if (currentVal == '#' && (activeAdjacentCells < 2 || activeAdjacentCells > 3)) {
                                gridCopy[wIndex][zIndex][yIndex][xIndex] = '.';
                            }
                        }
                    }
                }
            }
            grid = copyGrid4d(gridCopy);
        }

        return activeCells4d(grid);
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

    public static void main(String[] args) {
        var input = new ArrayList<>(
                readInput(Day17.class.getClassLoader().getResourceAsStream("day_17_input.txt")));

        int xLength = input.get(0).length() + (2 * ITERATIONS);
        int yLength = input.size() + (2 * ITERATIONS);
        int zLength = 1 + 2 * ITERATIONS;
        int wLength = 1 + 2 * ITERATIONS;

        char[][][] gridTask1 = emptyGrid(xLength, yLength, zLength);

        char[][][][] gridTask2 = new char[wLength][zLength][yLength][xLength];
        for(int i = 0; i < wLength; i++)
            gridTask2[i] = emptyGrid(xLength, yLength, zLength);


        for(int a = 0; a < input.size(); a++) {
            char[] inputRow = input.get(a).toCharArray();
            for(int b = 0; b < inputRow.length; b++) {
                if(inputRow[b] == '#')
                    gridTask1[ITERATIONS][ITERATIONS + a][ITERATIONS + b] = '#';
            }
        }

        gridTask2[6] = copyGrid3d(gridTask1);

        System.out.printf("First task: %d%n", firstTask(gridTask1));
        System.out.printf("Second task: %d%n", secondTask(gridTask2));
    }
}
