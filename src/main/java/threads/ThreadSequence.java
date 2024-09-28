package threads;

public class ThreadSequence {
    private static final int LIMIT = 10;
    private int counter = 1;
    private int turn = 1;

    private synchronized void printNumber(int threadNumber) {
        while (counter <= LIMIT) {
//            System.out.println("turn:"+turn);
            if (turn != threadNumber) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (counter > LIMIT) break;
            System.out.println("Thread-" + threadNumber + ": " + counter++);
            turn = turn % 3 + 1;
//            System.out.println("Next turn:"+turn +"\n");
            notifyAll();
        }
    }

    public static void main(String[] args) {
        ThreadSequence threadSequence = new ThreadSequence();
        Thread thread1 = new Thread(new Task(threadSequence, 1));
        Thread thread2 = new Thread(new Task(threadSequence, 2));
        Thread thread3 = new Thread(new Task(threadSequence, 3));

        thread1.start();
        thread2.start();
        thread3.start();
    }

    static class Task implements Runnable {
        private final ThreadSequence threadSequence;
        private final int threadNumber;

        Task(ThreadSequence threadSequence, int threadNumber) {
            this.threadNumber = threadNumber;
            this.threadSequence = threadSequence;
        }

        @Override
        public void run() {
            threadSequence.printNumber(threadNumber);
        }
    }
}
