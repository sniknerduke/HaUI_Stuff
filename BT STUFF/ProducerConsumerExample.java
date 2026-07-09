import java.util.concurrent.*;

// Lớp Producer
class Producer implements Runnable {
    protected BlockingQueue<Object> queue;

    Producer(BlockingQueue<Object> theQueue) {
        this.queue = theQueue;
    }

    public void run() {
        try {
            while (true) {
                Object justProduced = getResource();
                queue.put(justProduced); // Chờ nếu bộ đệm đầy
                System.out.println("Produced resource. Kích thước bây giờ là: " + queue.size());
            }
        } catch (InterruptedException ex) {
            System.out.println("Producer INTERRUPTED");
        }
    }

    Object getResource() throws InterruptedException {
        Thread.sleep(100); // Mô phỏng sản xuất
        return new Object();
    }
}

// Lớp Consumer
class Consumer implements Runnable {
    protected BlockingQueue<Object> queue;

    Consumer(BlockingQueue<Object> theQueue) {
        this.queue = theQueue;
    }

    public void run() {
        try {
            while (true) {
                Object obj = queue.take(); // Chờ nếu bộ đệm trống
                System.out.println("Consumed resource. Queue size now = " + queue.size());
                take(obj);
            }
        } catch (InterruptedException ex) {
            System.out.println("CONSUMER INTERRUPTED");
        }
    }

    void take(Object obj) throws InterruptedException {
        Thread.sleep(100); // Mô phỏng tiêu thụ
        System.out.println("Consuming object: " + obj);
    }
}

// Lớp thực thi
public class ProducerConsumerExample {
    public static void main(String[] args) throws InterruptedException {
        int numProducers = 4;
        int numConsumers = 3;
        BlockingQueue<Object> myQueue = new LinkedBlockingQueue<>(20); // Kích thước tối đa 20

        for (int i = 0; i < numProducers; i++) {
            new Thread(new Producer(myQueue)).start();
        }
        for (int i = 0; i < numConsumers; i++) {
            new Thread(new Consumer(myQueue)).start();
        }

        Thread.sleep(10000); // Chờ 10 giây
        System.exit(0);
    }
}
