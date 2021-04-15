package game;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            boolean entered = false;
            Move move = new Move(0, 0, Cell.E);
            do {
                try {
                    entered = true;
                    move = new Move(in.nextInt(), in.nextInt(), cell);
                    // :NOTE: Плохо
                } catch (InputMismatchException e) {
                    entered = false;
                    in.next();
                    System.out.println("Wrong input");
                } catch (NoSuchElementException e) {
                    System.out.println("");
                    System.exit(0);
                }
            } while (!entered);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid");
        }
    }
}
