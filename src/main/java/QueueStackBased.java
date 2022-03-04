import java.util.LinkedList;

/**
 * A queue data structured based on two internal stacks. The idea is that the transfer from one stack to another, empty
 * stack by pop and push results in an inverted order. So, last-in on the old stack is first-in on the new stack,
 * and first-in on the old stack is last-in on the new stack. This way we can obtain the FIFO behaviour by combining
 * to LIFO behaviours.
 * <br>
 * enqueue(T value) in O(1)
 * dequeue(T value) in O(1)
 * peekFront() in O(1)
 * peekBack() in O(1)
 */
public class QueueStackBased<T> {

    private final LinkedList<T> stackEnqueue = new LinkedList<>();
    private final LinkedList<T> stackDequeue = new LinkedList<>();

    public int size() {
        return stackDequeue.size() + stackEnqueue.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void enqueue(T value) {
        stackEnqueue.push(value);
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty. Cannot dequeue.");
        }
        if (stackDequeue.isEmpty()) {
            transfer(stackEnqueue, stackDequeue);
        }
        return stackDequeue.remove();
    }

    public T getFirst() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty. Cannot peek.");
        }
        if (stackDequeue.isEmpty()) {
            transfer(stackEnqueue, stackDequeue);
        }
        return stackDequeue.peek();
    }

    public T getLast() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty. Cannot peek.");
        }
        if (stackEnqueue.isEmpty()) {
            transfer(stackDequeue, stackEnqueue);
        }
        return stackEnqueue.peek();
    }

    private void transfer(LinkedList<T> fromStack, LinkedList<T> toStack) {
        if (!toStack.isEmpty()) {
            throw new IllegalStateException("The toStack must be empty when transferring values from fromStack.");
        }
        while (!fromStack.isEmpty()) {
            toStack.push(fromStack.remove());
        }
    }

}
