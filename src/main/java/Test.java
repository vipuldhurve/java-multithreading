public class Test {

    private static class Counter{
        int count;
        public Counter(int count) {
            this.count = count;
        }

        public void increment(){
            this.count++;
        }
    }

    private static class IncrementCounter implements Runnable{
        volatile int counter;
        public IncrementCounter() {
            this.counter = 0;
        }

        @Override
        public synchronized void run() {
            for(int i =0; i<10000; i++)  counter++;
        }
    }

    public static void main(String[] args) {
        Counter counter = new Counter(0);
        IncrementCounter incrementCounter = new IncrementCounter();

        Thread thread1 = new Thread(incrementCounter);
        Thread thread2 = new Thread(incrementCounter);

        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Value of counter = " + incrementCounter.counter);
    }
}
