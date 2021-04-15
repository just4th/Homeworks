package search;

public class BinarySearchMax {
    // S(a) = len > 0 && exists j: forall t, k: (t < k <= j || t >= k > j) => a[t] < a[k] <= a[j]
    // Pred: S(a)
    // Post: a[res] == max(a)
    private static int iBinarySearch(final int[] a) {
        int l = 0, r = a.length - 1;
        //Inv: (a[l - 1] < a[l] || l == 0) && (a[r + 1] < a[r] || r == len - 1)
        while (r - l > 1) {
            final int m = (l + r) / 2;
            // m == l => r - l <= 1 impossible
            // m == r => l == r impossible
            // l < m < r
            if (a[m - 1] < a[m]) {
                l = m;
                // l > l' => r - l < r' - l'
                // a[l - 1] < a[l] && (a[r + 1] < a[r] || r == len - 1)
            } else {
                // a[m - 1] >= a[m] => a[m] > a[m + 1]
                r = m;
                // (a[l - 1] < a[l] || l == 0) && a[r + 1] < a[r]
                // r < r' => r - l < r' - l'
            }
        }
        if (a[l] >= a[r]) {
            // ((a[l - 1] < a[l] || l == 0) && a[l + 1] <= a[l]) => a[l] == max(a)
            return l;
        } else {
            // a[r - 1] < a[r] && (a[r + 1] <= a[r] || r == len - 1) => a[r] == max(a)
            return r;
        }
    }
    // Pred: S(a) && (a[l - 1] < a[l] || l == 0) && (a[r + 1] < a[r] || r == len - 1) && r > l
    // Post: a[res] == max(a)
    private static int rBinarySearch(final int[] a, final int l, final int r) {
        if (r - l <= 1) {
            if (a[l] >= a[r]) {
                // (a[l - 1] < a[l] || l == 0) && a[l + 1] <= a[l] => a[l] == max(a)
                return l;
            } else {
                // a[r - 1] < a[r] && (a[r + 1] <= a[r] || r == len - 1) => a[r] == max(a)
                return r;
            }
        }

        // :NOTE: Arrays.fill(a, 0);
        final int m = (l + r) / 2;
        // m == l => r - l <= 1 impossible
        // m == r => l == r impossible
        // l < m < r
        if (a[m - 1] < a[m]) {
            // m > l => r - m < r - l
            // S(a) && a[m - 1] < a[m] && (a[r + 1] < a[r] || r == len - 1) && r > m
            return rBinarySearch(a, m, r);
            // a[res] == max(a)
        } else {
            // m < r => m - l < r - l
            // a[m - 1] >= a[m] => a[m] > a[m + 1]
            // S(a) && (a[l - 1] < a[l] || l == 0) && a[m + 1] < a[m] && m > l
            return rBinarySearch(a, l, m);
            // a[res] == max(a)
        }
    }

    // Pred: (forall i = 0...len - 1 : args[i] - string number) && S(int(args))
    // Post: res == max(int(args)) || len == 0 && res == 0
    public static void main(final String[] args) {
        final int n = args.length;
        // n == len;
        final int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = Integer.parseInt(args[i]);
        // forall i = 0 ... len - 1: a[i] = int(args[i])
        // S(a)
        System.out.println(a[rBinarySearch(a, 0, a.length - 1)]);
        // res == max(a)

    }
}
