package search;

public class BinarySearch {
    // S(a) = forall i = 1 ... len - 1: a[i] <= a[i - 1]
    // Pred: S(a)
    // Post: (a[res] <= x || res == len) && (res - 1 == -1 || a[res - 1] > x)
    private static int iBinarySearch(int[] a, int x) {
        int l = -1, r = a.length;
        // r - l >= 1
        // Inv: (a[r] <= x || r == len) && (l == -1 || a[l] > x)
        while (r - l > 1) {
            int m = (r + l) / 2;
            // m == l => r - l <= 1 impossible
            // m == r => l == r impossible
            // l < m < r
            if (a[m] > x) {
                l = m;
                // (a[r] <= x || r == len) && a[l] > x
                // l > l' => r - l < r' - l'
            } else {
                r = m;
                // a[r] <= x && (l == -1 || a[l] > x)
                // r < r' => r - l < r' - l'
            }
        }
        return r;
        // (res == r && l == r - 1) => (a[res] <= x || res == len) && (res - 1 == -1 || a[res - 1] > x)
    }
    // Pred: S(a) && r - l >= 1 && (a[r] <= x || r == len) && (l == -1 || a[l] > x)
    // Post: (a[res] <= x || res == len) && (res - 1 == -1 || a[res - 1] > x)
    private static int rBinarySearch (int[] a, int x, int l, int r) {
        if (r - l == 1) {
            return r;
            // (res == r && l == r - 1) => (a[res] <= x || res == len) && (res - 1 == -1 || a[res - 1] > x)
        }
        int m = (l + r) / 2;
        // m == l => r - l <= 1 impossible
        // m == r => l == r impossible
        // l < m < r
        if (a[m] > x) {
            // m > l => r - m < r - l
            // S(a) && r - m >= 1 && (a[r] <= x || r == len) && a[m] > x
            return rBinarySearch(a, x, m, r);
        } else {
            // m < r => m - l < r - l
            // S(a) && m - l >= 1 && a[m] <= x && (l == -1 || a[l] > x)
            return rBinarySearch(a, x, l, m);
        }
    }
    // Pred: (forall i = 0...len - 1 : args[i] - string number) && args.length >= 1 && S(int(args))
    // Post: (int(args[res + 1]) <= int(args[0]) || res + 1 == len) && (res - 1 == -1 || int(args[res]) > int(args[0])
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        // x = int(args[0])
        int n = args.length - 1;
        // n = len - 1
        int[] a = new int[n];
        for (int i = 1; i <= n; i++) a[i - 1] = Integer.parseInt(args[i]);
        // forall i = 0 ... len - 2 : a[i] = int(args[i + 1])
        // S(a)
        System.out.println(iBinarySearch(a, x));
        // (a[res] <= x || res == n) && (res - 1 == -1 || a[res - 1] > x) =>
        //=> (int(args[res + 1]) <= int(args[0]) || res + 1 == len) && (res - 1 == -1 || int(args[res]) > int(args[0])
    }
}
