package resourceSharing.dataRace;

/*
---------------- Data race Solution
* 1. Synchronization of methods which modify shared resource
 which ensures only one thread can access shared resource at a time

* 2. Declaration of shared variables with the volatile keyword
* */
public class DataRaceSolution {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRace();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Threads execution finished");
    }

    private static class SharedClass {
        private volatile int x;
        private volatile int y;
        public SharedClass() {
            this.x = 0;
            this.y = 0;
        }

        public void increment() {
//            Both operations do not depend on each other so compiler can
//            change the order of execution for optimization producing unexpected results
            x++;
            y++;
        }

        public void checkForDataRace() {
            if (y > x) System.out.println("Y > X - Data race detected!");
        }
    }
}
