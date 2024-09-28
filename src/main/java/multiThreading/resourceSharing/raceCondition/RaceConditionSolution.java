package multiThreading.resourceSharing.raceCondition;

public class RaceConditionSolution {

    public static void main(String[] args) throws InterruptedException {
//        Shared resource
        InventoryCounter inventoryCounter = new InventoryCounter();

        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

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

        public synchronized void increment() {
            items++;
        }

        public synchronized void decrement() {
            items--;
        }

        public int getItems() {
            return items;
        }
    }
}
