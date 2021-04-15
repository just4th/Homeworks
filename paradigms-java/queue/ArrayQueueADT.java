package queue;

import java.util.Arrays;
import java.util.Objects;

// Model a[1...n]
// Inv:
//    n >= 0
//    forall i = 1...n: a[i] != null

public class ArrayQueueADT {
    private int head = 0, size = 0;
    private Object[] elements = new Object[1];

    public static ArrayQueueADT create() {
        return new ArrayQueueADT();
    }
    // Pred: q != null && e != null
    // Post: n = n' + 1 && a[n] == e && forall i = 1...n':  a[i] == a'[i]
    public static void enqueue(ArrayQueueADT q, Object e) {
        Objects.requireNonNull(e);
        ensureCapacity(q, q.size + 1);
        q.elements[(q.head + q.size) % q.elements.length] = e;
        q.size++;
    }
    // Pred: q != null && e != null
    // Post: n = n' + 1 && a[1] == e && forall i = 1...n':  a[i + 1] == a'[i]
    public static void push(ArrayQueueADT q, Object e) {
        Objects.requireNonNull(e);
        ensureCapacity(q,q.size + 1);
        q.head = (q.head - 1 + q.elements.length) % q.elements.length;
        q.elements[q.head] = e;
        q.size++;
    }
    // Pred: q != null && n > 0
    // Post: res == a[1] && n == n' && forall i = 1...n:  a[i] == a'[i]
    public static Object element(ArrayQueueADT q) {
        assert q.size > 0;
        return q.elements[q.head];
    }
    // Pred: q != null && n > 0
    // Post: res == a[n] && n == n' && forall i = 1...n: a[i] == a'[i]
    public static Object peek(ArrayQueueADT q) {
        assert q.size > 0;
        return q.elements[(q.head + q.size - 1) % q.elements.length];
    }
    // Pred: q != null && n > 0
    // Post: res == a[1] && n == n' - 1 && forall i = 1...n:  a[i] == a'[i + 1]
    public static Object dequeue(ArrayQueueADT q) {
        assert q.size > 0;
        Object res = q.elements[q.head];
        q.elements[q.head] = null;
        q.head = (q.head + 1) % q.elements.length;
        q.size--;
        return res;
    }
    // Pred: q != null && n > 0
    // Post: res == a[n] && n == n' - 1 && forall i = 1...n: a[i] == a'[i]
    public static Object remove(ArrayQueueADT q) {
        assert q.size > 0;
        Object res = peek(q);
        q.elements[(q.head + q.size - 1) % q.elements.length] = null;
        q.size--;
        return res;
    }
    // Pred: q != null
    // Post: res == n && n == n' && forall i = 1...n:  a[i] == a'[i]
    public static int size(ArrayQueueADT q) {
        return q.size;
    }
    // Pred: q != null
    // Post: res == (n == 0) && n == n' && forall i = 1...n:  a[i] == a'[i]
    public static boolean isEmpty(ArrayQueueADT q) {
        return q.size == 0;
    }
    // Pred: q != null
    // Post: n == 0
    public static void clear(ArrayQueueADT q) {
        Arrays.fill(q.elements, null);
        q.head = q.size = 0;
    }
    // Pred: q != null
    // Post: res == a[1...n] && n == n' && forall i = 1...n:  a[i] == a'[i]
    public static Object[] toArray(ArrayQueueADT q) {
        return toArray(q, q.size);
    }
    private static Object[] toArray(ArrayQueueADT q, int capacity) {
        Object[] res = new Object[capacity];
        int t = Math.min(q.elements.length - q.head, Math.min(q.size, capacity));
        System.arraycopy(q.elements, q.head, res, 0, t);
        System.arraycopy(q.elements, 0, res, t, Math.min(q.size, capacity) - t);
        return res;
    }
    // Pred: q != null
    // Post: res == string(a[1...n]) && n == n' && forall i = 1...n:  a[i] == a'[i]
    public static String toStr(ArrayQueueADT q) {
        StringBuilder res = new StringBuilder("[");
        for (int i = 0; i < q.size; i++) {
            if (i != 0) res.append(", ");
            res.append(q.elements[(q.head + i) % q.elements.length]);
        }
        res.append("]");
        return res.toString();
    }
    private static void ensureCapacity(ArrayQueueADT q, final int capacity) {
        if (q.elements.length < capacity) {
            Object[] newElements = toArray(q, capacity * 2);
            q.head = 0;
            q.elements = newElements;
        }
    }
}
