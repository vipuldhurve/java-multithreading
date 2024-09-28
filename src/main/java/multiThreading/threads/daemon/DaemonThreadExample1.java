package multiThreading.threads.daemon;

public class DaemonThreadExample1 {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Daemon thread running");
                int count = 1;
                while (true) {
                    System.out.println(count);
                    count++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

//        set the thread as daemon and start
        daemonThread.setDaemon(true);
        daemonThread.start();

//        Main thread sleeps for 5 second then exists
        try {
            System.out.println("Main thread sleeping....");
            Thread.sleep(5000);
            System.out.println("Main thread exiting....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
