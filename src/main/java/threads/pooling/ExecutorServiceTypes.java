package threads.pooling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTypes {
    public static void main(String[] args) {
//        ------------------ Create a Fixed-Size thread pool with 4 worker threads
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        System.out.println("---------------------------- Fixed Size Thread Pool Working");
        performTask(fixedThreadPool, 10);
        fixedThreadPool.shutdown();

        try {
            // Wait for the first ExecutorService to terminate
            if (!fixedThreadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("Fixed ExecutorService did not terminate in the specified time.");
                fixedThreadPool.shutdownNow(); // Force shutdown
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for the Fixed ExecutorService to terminate.");
            fixedThreadPool.shutdownNow(); // Force shutdown
        }


//          ------------------  Create a Cached thread pool
//        Creates a thread pool that creates new threads as needed,
//        but reuses previously constructed threads if they are available.
//        Threads that are idle for 60 seconds are terminated and removed from the pool.
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        System.out.println("---------------------------- Cached Thread Pool Working");
        performTask(cachedThreadPool, 10);
        cachedThreadPool.shutdown();

        try {
            // Wait for the cached ExecutorService to terminate
            if (!cachedThreadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("Cached ExecutorService did not terminate in the specified time.");
                cachedThreadPool.shutdownNow(); // Force shutdown
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for the Cached ExecutorService to terminate.");
            cachedThreadPool.shutdownNow(); // Force shutdown
        }

//        ------------------ Create a Single Threaded thread pool
        ExecutorService singleThreadedPool = Executors.newSingleThreadExecutor();
        System.out.println("---------------------------- Single Threaded Thread Pool Working");
        performTask(singleThreadedPool, 5);
        singleThreadedPool.shutdown();

        try {
            // Wait for the second ExecutorService to terminate
            if (!singleThreadedPool.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("Single Threaded ExecutorService did not terminate in the specified time.");
                singleThreadedPool.shutdownNow(); // Force shutdown
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for the Single Threaded ExecutorService to terminate.");
            singleThreadedPool.shutdownNow(); // Force shutdown
        }


//        ------------------ Create a Scheduled thread pool with 4 worker threads
        ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
        System.out.println("---------------------------- Scheduled Thread Pool Working");
        performTask(scheduledThreadPool, 10);
        scheduledThreadPool.shutdown();

        try {
            // Wait for the second ExecutorService to terminate
            if (!scheduledThreadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("Single Threaded ExecutorService did not terminate in the specified time.");
                scheduledThreadPool.shutdownNow(); // Force shutdown
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for the Single Threaded ExecutorService to terminate.");
            scheduledThreadPool.shutdownNow(); // Force shutdown
        }

//        ------------------ Create a Work Stealing thread pool with 4 worker threads
        ExecutorService workStealingPool = Executors.newWorkStealingPool();
        System.out.println("---------------------------- Work Stealing Thread Pool Working");
        performTask(workStealingPool, 10);
        workStealingPool.shutdown();

        try {
            // Wait for the second ExecutorService to terminate
            if (!workStealingPool.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("Single Threaded ExecutorService did not terminate in the specified time.");
                workStealingPool.shutdownNow(); // Force shutdown
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for the Single Threaded ExecutorService to terminate.");
            workStealingPool.shutdownNow(); // Force shutdown
        }
    }

    private static void performTask(ExecutorService executorService, int tasks) {
//        Submit 10 tasks to thread pool
        for (int i = 1; i <= tasks; i++) {
            int taskId = i;
            executorService.submit(() -> {
//                Each task prints its ID and the name of the thread executing it
                System.out.println("Executing Task " + taskId + " in thread " + Thread.currentThread().getName());
            });
        }
    }

}
