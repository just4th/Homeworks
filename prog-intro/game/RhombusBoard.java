package game;

import java.util.Arrays;

public class RhombusBoard extends MNKBoard{
    RhombusBoard (int n, int k) {
        super(n % 2 == 0 ? n - 1: n , n, k);
        int t = (n - 1) / 2;
        for (int i = 0; i < m; i++) {
            int b = Math.abs(t - i);
            empty -= b * 2;
            Arrays.fill(cells[i], 0, b, Cell.B);
            Arrays.fill(cells[i], n - b, n, Cell.B);
        }
    }
}
