package resourceSharing;

/*
----------------- Volatile keyword
* Visibility: Ensures changes to a variable are visible to all threads.

* Ordering: Prevents reordering of reads and writes to the variable.

* Not Atomic: Does not ensure atomicity for compound actions or operations.

* Use Cases: Suitable for flags, simple counters,
and scenarios where visibility is the primary concern, not atomicity.
* */
public class VolatileExample {
    public static void main(String[] args) {
//        Shared Resource
        SharedObject sharedObject = new SharedObject();
//        Create a thread to increment the counter
        Thread incrementThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                sharedObject.incrementCounter();
            }
        });
//        Create a thread to read the counter value
        Thread readThread = new Thread(() -> {
            int value = sharedObject.getCounter();
            System.out.println("Counter value by readThread: " + value);
        });

//        Start both threads
        incrementThread.start();
        readThread.start();

//        Wait for both threads to finish
        try {
            incrementThread.join();
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Print the final counter value
        System.out.println("Final counter value by Main: " + sharedObject.getCounter());
    }


    static class SharedObject {
        // Declare a volatile variable
        private volatile int counter = 0;

        // Method to increment the counter
        public void incrementCounter() {
            counter++;
        }

        // Method to get the current value of the counter
        public int getCounter() {
            return counter;
        }
    }
}
