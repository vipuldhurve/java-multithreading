package producerConsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MyBlockingQueueImpl<E> {
    private int max;
    private Queue<E> queue;

    public MyBlockingQueueImpl(int size) {
        queue = new LinkedList<>();
        this.max = size;
    }

//   Using conditions
    ReentrantLock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();


    public void put(E e) {
        lock.lock();
        try {
            while (queue.size() == max) {
//                block the producer
                notFull.await();
            }
            queue.add(e);
//            Process it and signal consumer
            System.out.println("Producer: " + this);
            notEmpty.signalAll();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            lock.unlock();
        }
    }

    public E take() {
        lock.lock();
        try {
            while (queue.size() == 0) {
//                block the consumer
                notEmpty.await();
            }
            E item = queue.remove();
//            Process it and signal producer
            System.out.println("Consumer: " + item);
            notFull.signalAll();
            return item;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

//    private Object notEmpty = new Object();
//    private Object notFull = new Object();
//
//    public synchronized void put(E e) throws InterruptedException {
//
//            while (queue.size() == max) {
////                block the producer
//                wait();
//            }
//
//
//            queue.add(e);
//            notEmpty.notifyAll();
//
//    }
//
//    public synchronized E take() throws InterruptedException {
//
//            while (queue.size() == 0) {
////                block the consumer
//                notEmpty.wait();
//            }
//
//
//            E item = queue.remove();
//            notFull.notifyAll();
//
//        return item;
//    }

    @Override
    public String toString() {
        synchronized (queue) {
            return "MyBlockingQueue{" +
                    "queue=" + queue.toString() +
                    '}';
        }
    }

    public static void main(String[] args) {
        MyBlockingQueueImpl<Integer> myBlockingQueue = new MyBlockingQueueImpl(1);
//        Producer
        Runnable producer = () -> {
            Integer i = 1;
            while (true) {
                myBlockingQueue.put(i++);
            }
        };
//        Consumer
        Runnable consumer = () -> {
            while (true) {
//                Consumer sleeps for 1 sec
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                take item from queue
                myBlockingQueue.take();
            }
        };
//        Start producer consumer threads
        new Thread(producer, "Producer").start();
        new Thread(consumer, "Consumer").start();
    }
}
