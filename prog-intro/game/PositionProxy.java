package game;

public class PositionProxy implements Position {
    Position position;
    PositionProxy(Position position) {
        this.position = position;
    }

    @Override
    public boolean isValid(Move move) {
        return position.isValid(move);
    }

    @Override
    public Cell getCell(int r, int c) {
        return position.getCell(r, c);
    }

    @Override
    public String toString() {
        return position.toString();
    }

    @Override
    public int getHeight() {
        return position.getHeight();
    }

    @Override
    public int getWidth() {
        return position.getWidth();
    }
}
