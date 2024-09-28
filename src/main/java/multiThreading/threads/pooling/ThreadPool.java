package multiThreading.threads.pooling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * This Java program demonstrates the basic usage of a fixed-size thread pool
 * for executing multiple tasks concurrently. A thread pool with four worker
 * threads is created using the Executors.newFixedThreadPool() method. The
 * program then submits ten tasks to the thread pool, where each task prints
 * its unique ID and the name of the thread executing it. Finally, the thread
 * pool is gracefully shut down after all tasks have been completed.
 */
public class ThreadPool {
    public static void main(String[] args) {
//        Create a fixed-size thread pool with 4 worker threads
        ExecutorService executorService = Executors.newFixedThreadPool(4);

//        Submit 10 tasks to thread pool
        for (int i = 0; i < 10; i++) {
            int taskId = i;
            executorService.submit(() -> {
//                Each task prints its ID and the name of the thread executing it
                System.out.println("Executing Task " + taskId + " in thread " + Thread.currentThread().getName());
            });
        }
//        Shut down the thread pool once all tasks are completed
        executorService.shutdown();
    }

}

