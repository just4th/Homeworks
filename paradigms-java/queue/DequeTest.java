package queue;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Random;

public class DequeTest {
    private static int size = 0;
    private static ArrayQueueADT q1 = ArrayQueueADT.create();
    private static ArrayQueue q2 = new ArrayQueue();
    private static ArrayDeque<Object> q = new ArrayDeque<Object>();

    private static void check(Object expected, Object actual, String type) {
        if (!Objects.deepEquals(expected, actual)) {
            throw new AssertionError(type + "\nexpexted: " + expected + "\nactual: " + actual);
        }
    }
    // 0
    private static void enqueue(Object e) {
        size++;
        ArrayQueueModule.enqueue(e);
        ArrayQueueADT.enqueue(q1, e);
        q2.enqueue(e);
        q.addLast(e);
    }
    // 1
    private static void push(Object e) {
        size++;
        ArrayQueueModule.push(e);
        ArrayQueueADT.push(q1, e);
        q2.push(e);
        q.addFirst(e);
    }
    // 2
    private static void element() {
        if (size == 0) return;
        Object expected = q.getFirst();
        check(expected, ArrayQueueModule.element(), "Module");
        check(expected, ArrayQueueADT.element(q1), "ADT");
        check(expected, q2.element(), "Class");
    }
    // 3
    private static void peek() {
        if (size == 0) return;
        Object expected = q.getLast();
        check(expected, ArrayQueueModule.peek(), "Module");
        check(expected, ArrayQueueADT.peek(q1), "ADT");
        check(expected, q2.peek(), "Class");
    }
    // 4
    private static void dequeue() {
        if (size == 0) return;
        size--;
        Object expected = q.removeFirst();
        check(expected, ArrayQueueModule.dequeue(), "Module");
        check(expected, ArrayQueueADT.dequeue(q1), "ADT");
        check(expected, q2.dequeue(), "Class");
    }
    // 5
    private static void remove() {
        if (size == 0) return;
        size--;
        Object expected = q.removeLast();
        check(expected, ArrayQueueModule.remove(), "Module");
        check(expected, ArrayQueueADT.remove(q1), "ADT");
        check(expected, q2.remove(), "Class");
    }
    // 6
    private static void size() {
        Integer expected = q.size();
        check(expected, ArrayQueueModule.size(), "Module");
        check(expected, ArrayQueueADT.size(q1), "ADT");
        check(expected, q2.size(), "Class");
    }
    // 7
    private static void isEmpty() {
        Boolean expected = q.isEmpty();
        check(expected, ArrayQueueModule.isEmpty(), "Module");
        check(expected, ArrayQueueADT.isEmpty(q1), "ADT");
        check(expected, q2.isEmpty(), "Class");
    }
    // 8
    private static void clear() {
        size = 0;
        ArrayQueueModule.clear();
        ArrayQueueADT.clear(q1);
        q2.clear();
        q.clear();
    }
    // 9
    private static void toArray() {
        Object[] expected = q.toArray();
        check(expected, ArrayQueueModule.toArray(), "Module");
        check(expected, ArrayQueueADT.toArray(q1), "ADT");
        check(expected, q2.toArray(), "Class");
    }
    // 10
    private static void toStr() {
        String expected = q.toString();
        check(expected, ArrayQueueModule.toStr(), "Module");
        check(expected, ArrayQueueADT.toStr(q1), "ADT");
        check(expected, q2.toStr(), "Class");
    }
    public static void main(String[] args) {
        Random c = new Random();
        for (int i = 0; i < 1000; i++) {
            if (c.nextInt(11) == 0) {
                enqueue(c.nextInt());
            } else if (c.nextInt(11) == 1) {
                push(c.nextInt());
            } else if (c.nextInt(11) == 2) {
                element();
            } else if (c.nextInt(11) == 3) {
                peek();
            } else if (c.nextInt(11) == 4) {
                dequeue();
            } else if (c.nextInt(11) == 5) {
                remove();
            } else if (c.nextInt(11) == 6) {
                size();
            } else if (c.nextInt(11) == 7) {
                isEmpty();
            } else if (c.nextInt(11) == 8) {
                clear();
            } else if (c.nextInt(11) == 9) {
                toArray();
            } else if (c.nextInt(11) == 10) {
                toStr();
            }
        }
    }
}
