package resourceSharing.deadlock;

public class DeadLockProblem {
    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread trainThreadA = new Thread(new TrainA(intersection));
        trainThreadA.setName("Train-A");
        Thread trainThreadB = new Thread(new TrainB(intersection));
        trainThreadB.setName("Train-B");

        trainThreadA.start();
        trainThreadB.start();
    }

    private static class TrainA implements Runnable {
        private Intersection intersection;
        public TrainA(Intersection intersection) {
            this.intersection = intersection;
        }
        @Override
        public void run() {
            while (true) this.intersection.takeRoadA();
        }
    }

    private static class TrainB implements Runnable {
        private Intersection intersection;
        public TrainB(Intersection intersection) {
            this.intersection = intersection;
        }
        @Override
        public void run() {
            while (true) this.intersection.takeRoadB();
        }
    }

    private static class Intersection {
        private Object roadA;
        private Object roadB;
        public Intersection() {
            roadA = new Object();
            roadB = new Object();
        }

        public void takeRoadA() {
            synchronized (roadA) {
                System.out.println("Road A is locked by thread " + Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // Block road B
                synchronized (roadB) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " is passing through road A");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

        public void takeRoadB() {
            synchronized (roadB) {
                System.out.println("Road B is locked by thread " + Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // Block road A
                synchronized (roadA) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " is passing through road B");
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

}
