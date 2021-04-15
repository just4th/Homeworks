package queue;

import java.util.Arrays;
import java.util.Objects;

// Model a[1...n]
// Inv:
//    n >= 0
//    forall i = 1...n: a[i] != null

public class ArrayQueueModule {
    private static int head = 0, size = 0;
    private static Object[] elements = new Object[1];
    // Pred: e != null
    // Post: n = n' + 1 && a[n] == e && forall i = 1...n':  a[i] == a'[i]
    public static void enqueue(Object e) {
        Objects.requireNonNull(e);
        ensureCapacity(size + 1);
        elements[(head + size) % elements.length] = e;
        size++;
    }
    // Pred: e != null
    // Post: n = n' + 1 && a[1] == e && forall i = 1...n':  a[i + 1] == a'[i]
    public static void push(Object e) {
        Objects.requireNonNull(e);
        ensureCapacity(size + 1);
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = e;
        size++;
    }
    // Pred: n > 0
    // Post: res == a[1] && n == n' && forall i = 1...n:  a[i] == a'[i]
    public static Object element() {
        assert size > 0;
        return elements[head];
    }
    // Pred: n > 0
    // Post: res == a[n] && n == n' && forall i = 1...n: a[i] == a'[i]
    public static Object peek() {
        assert size > 0;
        return elements[(head + size - 1) % elements.length];
    }
    // Pred: n > 0
    // Post: res == a[1] && n == n' - 1 && forall i = 1...n:  a[i] == a'[i + 1]
    public static Object dequeue() {
        assert size > 0;
        Object res = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return res;
    }
    // Pred: n > 0
    // Post: res == a[n] && n == n' - 1 && forall i = 1...n: a[i] == a'[i]
    public static Object remove() {
        assert size > 0;
        Object res = peek();
        elements[(head + size - 1) % elements.length] = null;
        size--;
        return res;
    }
    // Pred: true
    // Post: res == n && n == n' && forall i = 1...n:  a[i] == a'[i]
    public static int size() {
        return size;
    }
    // Pred: true
    // Post: res == (n == 0) && n == n' && forall i = 1...n:  a[i] == a'[i]
    public static boolean isEmpty() {
        return size == 0;
    }
    // Pred: true
    // Post: n == 0
    public static void clear() {
        Arrays.fill(elements, null);
        head = size = 0;
    }
    // Pred: true
    // Post: res == a[1...n] && n == n' && forall i = 1...n:  a[i] == a'[i]
    public static Object[] toArray() {
        return toArray(size);
    }
    private static Object[] toArray(int capacity) {
        Object[] res = new Object[capacity];
        int t = Math.min(elements.length - head, Math.min(size, capacity));
        System.arraycopy(elements, head, res, 0, t);
        System.arraycopy(elements, 0, res, t, Math.min(size, capacity) - t);
        return res;
    }
    // Pred: true
    // Post: res == string(a[1...n]) && n == n' && forall i = 1...n:  a[i] == a'[i]
    public static String toStr() {
        StringBuilder res = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i != 0) res.append(", ");
            res.append(elements[(head + i) % elements.length]);
        }
        res.append("]");
        return res.toString();
    }
    private static void ensureCapacity(final int capacity) {
        if (elements.length < capacity) {
            Object[] newElements = toArray(capacity * 2);
            head = 0;
            elements = newElements;
        }
    }
}
