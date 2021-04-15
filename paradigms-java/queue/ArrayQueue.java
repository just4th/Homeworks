package queue;

import java.util.Arrays;
import java.util.Objects;

// Model:
//    a[1...n]
//    n - queue size
// Inv:
//    n >= 0
//    forall i = 1...n: a[i] != null

public class ArrayQueue extends AbstractQueue {
    private int head = 0;
    private Object[] elements = new Object[1];
    protected void enqueueImpl(Object e) {
        ensureCapacity(size + 1);
        elements[(head + size) % elements.length] = e;
    }
    // Pred: e != null
    // Post: n = n' + 1 && a[1] == e && forall i = 1...n':  a[i + 1] == a'[i]
    public void push(Object e) {
        Objects.requireNonNull(e);
        ensureCapacity(size + 1);
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = e;
        size++;
    }
    protected Object elementImpl() {
        return elements[head];
    }
    // Pred: n > 0
    // Post: res == a[n] && n == n' && forall i = 1...n: a[i] == a'[i]
    public Object peek() {
        assert size > 0;
        return elements[(head + size - 1) % elements.length];
    }
    protected Object dequeueImpl() {
        Object res = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return res;
    }
    // Pred: n > 0
    // Post: res == a[n] && n == n' - 1 && forall i = 1...n: a[i] == a'[i]
    public Object remove() {
        assert size > 0;
        Object res = peek();
        elements[(head + size - 1) % elements.length] = null;
        size--;
        return res;
    }
    // Pred: true
    // Post: res == a[1...n] && n == n' && forall i = 1...n:  a[i] == a'[i]
    public Object[] toArray() {
        return toArray(size);
    }
    private Object[] toArray(int capacity) {
        Object[] res = new Object[capacity];
        int t = Math.min(elements.length - head, Math.min(size, capacity));
        System.arraycopy(elements, head, res, 0, t);
        System.arraycopy(elements, 0, res, t, Math.min(size, capacity) - t);
        return res;
    }
    // Pred: true
    // Post: res == string(a[1...n]) && n == n' && forall i = 1...n:  a[i] == a'[i]
    public String toStr() {
        StringBuilder res = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i != 0) res.append(", ");
            res.append(elements[(head + i) % elements.length]);
        }
        res.append("]");
        return res.toString();
    }
    private void ensureCapacity(final int capacity) {
        if (elements.length < capacity) {
            Object[] newElements = toArray(capacity * 2);
            head = 0;
            elements = newElements;
        }
    }
    protected ArrayQueue create() {
        return new ArrayQueue();
    }
//    protected void nth(int k, boolean del, AbstractQueue q) {
//        if (q != null) {
//            for (int i = 1; i <= size / k; i++) {
//                q.enqueue(elements[(head + k * i - 1) % elements.length]);
//            }
//        }
//        if (del) {
//            Object[] newElements = new Object[elements.length];
//            for (int i = 1; i <= size; i++) {
//                if (i % k != 0) {
//                    newElements[i - 1 - i / k] = elements[(head + i - 1) % elements.length];
//                }
//            }
//            elements = newElements;
//            head = 0;
//        }
//    }
}
