package game;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class Main {
    public static void main(String[] args) {
        final Game game = new Game(true, new SequentialPlayer(), new RandomPlayer());
        int result;
        do {
            result = game.play(new RhombusBoard(15, 8));
            System.out.println("Game result: " + result);
        } while (result != 0);
    }
}
