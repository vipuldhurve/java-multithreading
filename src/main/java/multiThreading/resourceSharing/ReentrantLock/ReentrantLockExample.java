package multiThreading.resourceSharing.ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    private final ReentrantLock lock = new ReentrantLock();

//    ------ Fair lock
//    ReentrantLock fairLock = new ReentrantLock(true);

//    ------ Unfair lock
//    ReentrantLock fairLock = new ReentrantLock(false);

    private void performTask(){
        lock.lock();
        try {
//            Critical section of code
            System.out.println(Thread.currentThread().getName() + " is performing some task");
        } finally {
//            Always ensure the lock is released in the finally block
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLockExample reentrantLockExample = new ReentrantLockExample();

        Runnable task = reentrantLockExample::performTask;

        Thread thread1 = new Thread(task, "Thread 1");
        Thread thread2 = new Thread(task, "Thread 2");

        thread1.start();
        thread2.start();
    }
}
