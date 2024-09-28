package multiThreading.resourceSharing.deadlock;

/*
 * Acquire lock in same order to avoid deadlock
 * */
public class DeadLockSolution {
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
            while (true) {
                try {
                    Thread.sleep(5);
                    this.intersection.takeRoadA();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static class TrainB implements Runnable {
        private Intersection intersection;

        public TrainB(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5);
                    this.intersection.takeRoadB();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
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
                synchronized (roadB) {
                    System.out.println("Train is passing through road A");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

        public void takeRoadB() {
            synchronized (roadA) {
                System.out.println("Road A is locked by thread " + Thread.currentThread().getName());
                synchronized (roadB) {
                    System.out.println("Train is passing through road B");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

}
