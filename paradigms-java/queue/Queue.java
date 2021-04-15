package queue;

public interface Queue {
    // Pred: e != null
    // Post: n = n' + 1 && a[n] == e && forall i = 1...n':  a[i] == a'[i]
    void enqueue(Object e);
    // Pred: n > 0
    // Post: res == a[1] && n == n' && forall i = 1...n: a[i] == a'[i]
    Object element();
    // Pred: n > 0
    // Post: res == a[1] && n == n' - 1 && forall i = 1...n: a[i] == a'[i + 1]
    Object dequeue();
    // Pred: true
    // Post: res == n && n == n' && forall i = 1...n:  a[i] == a'[i]
    int size();
    // Pred: true
    // Post: res == (n == 0) && n == n' && forall i = 1...n:  a[i] == a'[i]
    boolean isEmpty();
    // Pred: true
    // Post: n == 0
    void clear();
    // Pred: k > 0
    // Post: res = queue.currentType(this.n == n / k, forall i = 1...n / k: this.a[i] == a[i * k]) && n == n' && forall i == 1...n: a[i] == a'[i]
    Queue getNth(int k);
    // Pred: k > 0
    // Post: n = n' - n' / k && forall i = 1...n: a[i] = a'[i + i / k]
    void dropNth(int k);
    // Pred: k > 0
    // Post: n = n - n' / k && forall i = 1...n: a[i] = a'[i + i / k] && res = queue.currentType(this.n == n' / k, forall i = 1...n' / k: this.a[i] == a'[i * k])
    Queue removeNth(int k);
}
