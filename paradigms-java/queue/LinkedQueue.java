package queue;

import java.util.Objects;

public class LinkedQueue extends AbstractQueue {

    private Node head, tail;
    private class Node {
        private Node next;
        private final Object value;
        public Node (Node prev, final Object value) {
            if (prev != null) {
                prev.next = this;
            }
            this.value = value;
        }
    }
    protected void enqueueImpl(Object e) {
        tail = new Node(tail, e);
        if (size == 0) {
            head = tail;
        }
    }
    protected Object elementImpl() {
        return head.value;
    }
    protected Object dequeueImpl() {
        Object ans = head.value;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return ans;
    }
    protected LinkedQueue create() {
        return new LinkedQueue();
    }
//    protected void nth(int k, boolean del, AbstractQueue q) {
//        Node cur = head;
//        while (cur != null) {
//            for (int i = 1; cur != null && i < k; i++) {
//                cur = cur.next;
//            }
//            if (cur == null) {
//                break;
//            }
//            if (q != null) {
//                q.enqueue(cur.value);
//            }
//            if (del) {
//                if (cur.prev != null) {
//                    cur.prev.next = cur.next;
//                } else {
//                    head = head.next;
//                }
//                if (cur.next != null) {
//                    cur.next.prev = cur.prev;
//                } else {
//                    tail = tail.prev;
//                }
//            }
//            cur = cur.next;
//        }
//    }
}
