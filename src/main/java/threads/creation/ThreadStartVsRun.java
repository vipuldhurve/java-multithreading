package threads.creation;

/**
 * This program demonstrates the difference between Thread.start() and Thread.run() methods.
 * <p>
 * - The start() method is used to start a new thread. When myThread.start() is called,
 * the Java Virtual Machine (JVM) creates a new thread, and the run() method of the MyThread
 * class is executed in that new thread.
 * <p>
 * - The run() method defines the code that constitutes the new thread. It is called by the JVM
 * when the thread is started using the start() method.
 * <p>
 * - If you directly call the run() method, as in myThread.run(), it doesn't start a new thread.
 * Instead, the run() method is executed in the current thread (in this case, the main thread).
 * <p>
 * The output of the program will show that the run() method is executed in a new thread when
 * started with start(), and in the main thread when called directly.
 */
public class ThreadStartVsRun {
    public static void main(String[] args) {
        // Creating a new thread
        MyThread myThread = new MyThread();
        myThread.setName("My Thread");

        // Using start() method to launch a new thread
        myThread.start(); // This will call the run() method in a new thread

        // Using run() method directly
        myThread.run(); // This will execute the run() method in the main thread
    }

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Inside run method, executed by thread: " + Thread.currentThread().getName());
        }
    }
}
