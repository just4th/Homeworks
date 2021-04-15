package game;

import java.util.Arrays;
import java.util.Map;

public class MNKBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.B, ' '
    );
    private int k;
    protected int m, n, empty;
    protected final Cell[][] cells;
    private Cell turn;
    public MNKBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        empty = m * n;
        this.cells = new Cell[m][n];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public int getHeight() {
        return m;
    }

    @Override
    public int getWidth() {
        return n;
    }

    @Override
    public Position getPosition() {
        return new PositionProxy(this);
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        int x = move.getRow(), y = move.getColumn();
        cells[x][y] = move.getValue();
        empty--;
        int inDiag1 = counter(x, y, 1, 1) + counter(x, y, -1, -1) - 1;
        int inDiag2 = counter(x, y, 1, -1) + counter(x, y, -1, 1) - 1;
        int inRow = counter(x, y, 1, 0) + counter(x, y, -1, 0) - 1;
        int inCol = counter(x, y, 0, 1) + counter(x, y, 0, -1) - 1;
        if (inDiag1 == k || inDiag2 == k || inRow == k || inCol == k) {
            return Result.WIN;
        }
        if (empty == 0) {
            return Result.DRAW;
        }
        if (inDiag1 >= 4 || inDiag2 >= 4 || inRow >= 4 || inCol >= 4) {
            return Result.NEW;
        }
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < m
                && 0 <= move.getColumn() && move.getColumn() < n
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        int lm = String.valueOf(m - 1).length();
        int ln = String.valueOf(n - 1).length();
        StringBuilder sb = new StringBuilder();
        sb.append(" ".repeat(lm + 1));
        for (int i = 0; i < n; i++) {
            String t = String.valueOf(i);
            sb.append(t);
            sb.append(" ".repeat(ln - t.length() + 1));
        }
        sb.append('\n');
        for (int i = 0; i < m; i++) {
            String t = String.valueOf(i);
            sb.append(t);
            sb.append(" ".repeat(lm - t.length() + 1));
            for (int j = 0; j < n; j++) {
                sb.append(SYMBOLS.get(cells[i][j]));
                sb.append(" ".repeat(ln));
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private int counter (int x, int y, int dx, int dy) {
        int ans = 0;
        while (x >= 0 && x < m && y >= 0 && y < n && cells[x][y] == turn) {
            ans++;
            x += dx;
            y += dy;
        }
        return ans;
    }
}
