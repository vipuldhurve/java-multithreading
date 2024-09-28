package multiThreading.threads.creation;

public class ThreadCreation {

    public static void main(String[] args) throws InterruptedException {

//        Method 1 : by extending Thread class
        Thread thread1 = new ExtendedThread();
        thread1.start();

//        Method 2: Implementing the runnable interface and passing it  as argument to thread
        Runnable runnableExample = new implementRunnableExample();
        Thread thread2 = new Thread(runnableExample);
        thread2.start();

//        Method 3: Using an anonymous runnable implementation class
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Running thread by anonymous runnable implementation class: " + Thread.currentThread().getName());
            }
        });
        thread3.start();

//        Method 4: Using lambda expression (Java 8+)
        Thread thread4 = new Thread(() ->
                System.out.println("Running thread using lambda expression: " + Thread.currentThread().getName())
        );
        thread4.start();

//        Method 5: Using thread constructor with a subclass of runnable
        Thread thread5 = new Thread(new RunnableSubclass());
        thread5.start();
    }

    private static class ExtendedThread extends Thread {
        @Override
        public void run() {
            System.out.println("Running thread by extending thread class: " + Thread.currentThread().getName());
        }
    }

    private static class implementRunnableExample implements Runnable {
        @Override
        public void run() {
            System.out.println("Running thread by implementing runnable interface: " + Thread.currentThread().getName());
        }
    }

    private static class RunnableSubclass extends Thread {
        @Override
        public void run() {
            System.out.println("Running thread using thread constructor with runnable sub class: " + Thread.currentThread().getName());
        }
    }
}
