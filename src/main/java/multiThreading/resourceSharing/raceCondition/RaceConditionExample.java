package multiThreading.resourceSharing.raceCondition;

/*
 * Condition when multiple threads are accessing a shared resource
 * At least one thread is modifying the resource
 * The timing of the threads scheduling may cause incorrect results
 * The core of the problem is non-atomic operations performed on a shared resource
 * */
public class RaceConditionExample {
    public static void main(String[] args) throws InterruptedException {
//        Shared resource
        InventoryCounter inventoryCounter = new InventoryCounter();
//        2 threads trying to modify shared resource by non-atomic operations
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();
//       Can produce unexpected results
        System.out.println("We currently have " + inventoryCounter.getItems() + " items");
    }

    private static class IncrementingThread extends Thread {

        private InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                this.inventoryCounter.increment();
            }
        }
    }

    private static class DecrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                this.inventoryCounter.decrement();
            }
        }
    }

    private static class InventoryCounter {
        private int items;

        public InventoryCounter() {
            this.items = 0;
        }

        public void increment() {
            items++;
        }

        public void decrement() {
            items--;
        }

        public int getItems() {
            return items;
        }
    }
}
