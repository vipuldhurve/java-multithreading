package producerConsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerWithBlockingQueue {
    private static int id = 1;

//    Item
    static class Item {
        int id;
        public Item(int _id) {
            this.id = _id;
        }
    }

    public static void main(String[] args) {
//        BlockingQueue
        BlockingQueue<Item> queue = new ArrayBlockingQueue<>(1);
//        Producer
        final Runnable producer = () -> {
            while (true) {
                try {
//                    Create and add item in queue
                    Item i = new Item(id++);
                    queue.put(i);
                    System.out.println(Thread.currentThread().getName() + ": " + i.id);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
//        Consumer
        final Runnable consumer = () -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    Item i = queue.take();
//                    Process
                    System.out.println(Thread.currentThread().getName() + ": " + i.id);
                } catch (InterruptedException e) {}
            }
        };
//        Start producer and consumer threads
        new Thread(producer, "Producer").start();
        new Thread(consumer, "Consumer").start();
    }
}
