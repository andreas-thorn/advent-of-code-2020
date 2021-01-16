import java.io.InputStream;
import java.util.*;

public class Day22 {
    private static ArrayList<LinkedList<Integer>> readInput(InputStream stream) {
        ArrayList<LinkedList<Integer>> result = new ArrayList<>();
        Scanner scanner = new Scanner(stream);
        LinkedList<Integer> userCards = new LinkedList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.startsWith("Player"))
                continue;

            if(line.equals("")) {
                result.add(userCards);
                userCards = new LinkedList<>();
            }
            else
                userCards.add(Integer.parseInt(line));
        }
        result.add(userCards);
        return result;
    }

    private static long calculateScore(LinkedList<Integer> cards) {
        int multiplier = cards.size();
        long result = 0;
        for(int i = 0; i < cards.size(); i++) {
            result += cards.get(i) * multiplier--;
        }
        return result;
    }

    private static long firstTask(LinkedList<Integer> player1, LinkedList<Integer> player2) {
        while(!player1.isEmpty() && !player2.isEmpty()) {
            int player1Card = player1.removeFirst();
            int player2Card = player2.removeFirst();
            if(player1Card > player2Card) {
                player1.add(player1Card);
                player1.add(player2Card);
            }
            else{
                player2.add(player2Card);
                player2.add(player1Card);
            }
        }
        return player1.size() > 0 ? calculateScore(player1) : calculateScore(player2);
    }

    private static boolean playSubGame(LinkedList<Integer> player1, LinkedList<Integer> player2) {
        HashSet<String> playedGames = new HashSet<>();

        while(!player1.isEmpty() && !player2.isEmpty()) {
            if(playedGames.contains(player1.toString()))
                return true;

            playedGames.add(player1.toString());
            int player1Card = player1.removeFirst();
            int player2Card = player2.removeFirst();

            if (player1Card >= player1.size() || player2Card >= player2.size()) {
                if (player1Card > player2Card)
                {
                    player1.add(player1Card);
                    player1.add(player2Card);
                }
                else {
                    player2.add(player2Card);
                    player2.add(player1Card);
                }
            }
            else {
                if(playSubGame(new LinkedList<>(player1.subList(0, player1Card)),
                        new LinkedList<>(player2.subList(0, player2Card)))) {
                    player1.add(player1Card);
                    player1.add(player2Card);
                }
                else {
                    player2.add(player2Card);
                    player2.add(player1Card);
                }
            }
        }

        return !player1.isEmpty();
    }

    private static long secondTask(LinkedList<Integer> player1, LinkedList<Integer> player2) {

        while(!player1.isEmpty() && !player2.isEmpty()) {
            int player1Card = player1.removeFirst();
            int player2Card = player2.removeFirst();

            if(player1Card <= player1.size() && player2Card <= player2.size()) {
                if(playSubGame(new LinkedList<>(player1.subList(0, player1Card)),
                        new LinkedList<>(player2.subList(0, player2Card))))
                {
                    player1.add(player1Card);
                    player1.add(player2Card);
                }
                else {
                    player2.add(player2Card);
                    player2.add(player1Card);
                }
            }
            else {
                if(player1Card > player2Card) {
                    player1.add(player1Card);
                    player1.add(player2Card);
                }
                else {
                    player2.add(player2Card);
                    player2.add(player1Card);
                }
            }
        }

        return player1.size() > 0 ? calculateScore(player1) : calculateScore(player2);
    }

    public static void main(String[] args) {
        var input = new ArrayList<>(
                readInput(Day22.class.getClassLoader().getResourceAsStream("day_22_input.txt")));

        System.out.println(firstTask(new LinkedList<>(input.get(0)), new LinkedList<>(input.get(1))));
        System.out.println(secondTask(new LinkedList<>(input.get(0)), new LinkedList<>(input.get(1))));
    }
}