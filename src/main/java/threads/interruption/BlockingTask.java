package threads.interruption;

public class BlockingTask {
    public static void main(String[] args) {
        BlockingThread blockingThread = new BlockingThread();
        blockingThread.start();

//        blocking Thread is executing a code that throws interrupted exception
//        So it can be interrupted
        blockingThread.interrupt();
    }

    private static class BlockingThread extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(50_000);
            } catch (InterruptedException e) {
                System.out.println("Exiting blocking thread...");
            }
        }
    }

}
