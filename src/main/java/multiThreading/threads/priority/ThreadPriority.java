package multiThreading.threads.priority;

public class ThreadPriority {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("We are in thread: " + Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + " priority is: " + Thread.currentThread().getPriority());
        });

        thread.setName("New Worker Thread");
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("We are in thread " + Thread.currentThread().getName() + " before starting a new thread");
        thread.start();
        System.out.println("We are in thread " + Thread.currentThread().getName() + " after starting a new thread");
    }
}
