package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int size = 0;
    // Pred: e != null
    // Post: n = n' + 1 && a[n] == e && forall i = 1...n':  a[i] == a'[i]
    public void enqueue(Object e) {
        Objects.requireNonNull(e);
        enqueueImpl(e);
        size++;
    }
    protected abstract void enqueueImpl(Object e);
    // Pred: n > 0
    // Post: res == a[1] && n == n' && forall i = 1...n: a[i] == a'[i]
    public Object element(){
        assert size > 0;
        return elementImpl();
    }
    protected abstract Object elementImpl();
    // Pred: n > 0
    // Post: res == a[1] && n == n' - 1 && forall i = 1...n: a[i] == a'[i + 1]
    public Object dequeue() {
        assert size > 0;
        Object ans = dequeueImpl();
        size--;
        return ans;
    }
    protected abstract Object dequeueImpl();
    // Pred: true
    // Post: res == n && n == n' && forall i = 1...n:  a[i] == a'[i]
    public int size() {
        return size;
    }
    // Pred: true
    // Post: res == (n == 0) && n == n' && forall i = 1...n:  a[i] == a'[i]
    public boolean isEmpty() {
        return size == 0;
    }
    // Pred: true
    // Post: n == 0
    public void clear() {
        dropNth(1);
    }
    // Pred: k > 0
    // Post: res = queue.currentType(this.n == n / k, forall i = 1...n / k: this.a[i] == a[i * k]) && n == n' && forall i == 1...n: a[i] == a'[i]
    public AbstractQueue getNth(int k) {
        assert k > 0;
        AbstractQueue ans = create();
        nth(k, false, ans);
        return ans;
    }
    // Pred: k > 0
    // Post: n = n - n' / k && forall i = 1...n: a[i] = a'[i + i / k]
    public void dropNth(int k) {
        assert k > 0;
        nth(k, true, null);
    }
    // Pred: k > 0
    // Post: n = n - n' / k && forall i = 1...n: a[i] = a'[i + i / k] && res = queue.currentType(this.n == n' / k, forall i = 1...n' / k: this.a[i] == a'[i * k])
    public AbstractQueue removeNth(int k) {
        assert k > 0;
        AbstractQueue ans = create();
        nth(k, true, ans);
        return ans;
    }
    protected abstract AbstractQueue create();
    protected void nth(int k, boolean del, AbstractQueue q) {
        int t = size;
        for (int i = 1; i <= t; i++) {
            Object cur = dequeue();
            if (i % k == 0) {
                if (q != null) {
                    q.enqueue(cur);
                }
                if (!del) {
                    enqueue(cur);
                }
            } else {
                enqueue(cur);
            }
        }
    }
}
