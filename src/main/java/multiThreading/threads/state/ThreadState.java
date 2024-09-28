package multiThreading.threads.state;

/**
 * The ThreadState class demonstrates the various states that a thread
 * can be in during its lifecycle in Java. It uses two threads, t1 and t2,
 * to illustrate the transition between states such as NEW, RUNNABLE, TIMED_WAITING,
 * and TERMINATED.
 * <p>
 * At the beginning (0ms):
 * - t1 is in the NEW state.
 * <p>
 * After t1.start() is called (approximately 0ms):
 * - t1 moves to the RUNNABLE state.
 * <p>
 * After t2.start() is called inside the run() method of t1 (approximately 0ms):
 * - t2 is in the NEW state before start() is called.
 * - t2 moves to the RUNNABLE state after start() is called.
 * <p>
 * After t1.sleep() and t2.sleep() is called for both threads in their run() methods (approximately 0ms) (order can be different)
 * - t1 is in TIMED_WAITING state
 * - t2 is in TIMED_WAITING state
 * <p>
 * At approximately 100ms:
 * - t2 completes its first sleep of 100ms and exits the TIMED_WAITING state.
 * - t1 may still be in the TIMED_WAITING state due to its 200ms sleep.
 * <p>
 * At approximately 200ms:
 * - t1 completes its 200ms sleep and exits the TIMED_WAITING state.
 * - If t1 immediately calls t2.join(), it enters the WAITING state until t2 completes.
 * - t2 might be in the RUNNABLE or TIMED_WAITING state depending on its execution progress.
 * <p>
 * At approximately 300ms (after t2's second sleep ends):
 * - t2 completes its execution and moves to the TERMINATED state.
 * - t1, which was waiting for t2 to complete, now resumes execution and eventually moves to the TERMINATED state.
 * <p>
 * The output of the program demonstrates these state transitions, highlighting the behavior of threads as they move through different states in their lifecycle.
 */

// ThreadState class implements the interface Runnable
public class ThreadState {
    private static Thread t1;
    private static Thread t2;

    public static void main(String[] args) {
        t1 = new Thread1();
        t1.setName("t1");
//        thread1 is spawned
//        The thread1 is currently in NEW or BORN state
        System.out.println("Thread t1 state after spawning it - " + t1.getState());

//        Invoking the start() method on thread1
        t1.start();
//        thread1 moves to runnable state
        System.out.println("The state of thread t1 after invoking the method start() - " + t1.getState());

        try {
            t1.join();
            System.out.println("The state of thread t1 when it has completed it's execution - " + t1.getState());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // Thread1 class implements the interface Runnable
    static class Thread1 extends Thread {
        public void run() {
            System.out.println("The state of " + Thread.currentThread().getName()
                    + " when running it - " + Thread.currentThread().getState());
            t2 = new Thread2();
            t2.setName("t2");

            // thread2 is created and is currently in the NEW state.
            System.out.println("The state of thread t2 after spawning it - "
                    + t2.getState());

            t2.start();

            // thread2 is moved to the RUNNABLE state
            System.out.println("the state of thread t2 after calling the method start() - "
                    + t2.getState());

            try {
                // moving the thread1 to the state TIMED_WAITING waiting
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            System.out.println("The state of thread t2 after invoking the method sleep() - "
                    + t2.getState());

            // try-catch block for the smooth flow of the  program
            try {
                // waiting for thread t2 to complete its execution
                t2.join();
                System.out.println("The state of thread t2 when it has completed it's execution - "
                        + t2.getState());
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }

        }
    }

    // Thread2 class implements the interface Runnable
    static class Thread2 extends Thread {
        public void run() {
            try {
                // moving thread2 to the state timed waiting
                Thread.sleep(2000);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            System.out.println("The state of thread t1 while it invoked "
                    + "method join() on thread t2 - " + ThreadState.t1.getState());

            try {
                Thread.sleep(200);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
}
