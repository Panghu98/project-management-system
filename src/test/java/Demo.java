import java.util.concurrent.ConcurrentLinkedQueue;

public class Demo {

    public static void main(String[] args) {

        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 5; i++) {
            concurrentLinkedQueue.add(i);
        }
    }
}
