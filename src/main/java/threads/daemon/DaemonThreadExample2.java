package threads.daemon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DaemonThreadExample2 {
    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread();
        t1.setName("daemon thread");
        MyThread t2 = new MyThread();
        t2.setName("thread2");
        MyThread t3 = new MyThread();
        t3.setName("thread3");

        List<MyThread> threads = new ArrayList<>(Arrays.asList(t1, t2, t3));

//        setting thread 1 as daemon
        t1.setDaemon(true);


//        starting threads
        for (MyThread t : threads) {
            t.start();
        }

//        Calling join on daemon thread and other threads
        t1.join();
        t2.join();
        t3.join();

//      Main thread will wait for join command to complete and then move ahead
        System.out.println(Thread.currentThread().getName() + "thread exiting...");
    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            if (Thread.currentThread().isDaemon()) {
                //checking for daemon thread
                System.out.println(Thread.currentThread().getName() + ": Daemon thread working...");
                int count = 1;
                while (count <= 5) {
                    System.out.println(count);
                    count++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                System.out.println(Thread.currentThread().getName() + ": thread Working...");
            }
        }
    }
}
