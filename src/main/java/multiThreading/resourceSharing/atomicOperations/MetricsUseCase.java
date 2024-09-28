package multiThreading.resourceSharing.atomicOperations;

import java.util.Random;

public class MetricsUseCase {

    public static void main(String[] args) {
//        Shared Metrics resource
        Metrics metrics = new Metrics();
//        Create 2 businessThreads and a metricsPrinter to calculate average response time
        BusinessLogic businessLogic1 = new BusinessLogic(metrics);
        BusinessLogic businessLogic2 = new BusinessLogic(metrics);
        MetricsPrinter metricsPrinter = new MetricsPrinter(metrics);
//        Start threads
        businessLogic1.start();
        businessLogic2.start();
        metricsPrinter.start();
//        gracefully stop threads using volatile boolean in threads after 3 seconds
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        businessLogic1.stopThread();
        businessLogic2.stopThread();
        metricsPrinter.stopThread();
    }

    private static class MetricsPrinter extends Thread {
        private volatile boolean running = true;
        private Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Current average is: " + metrics.getAverage());
            }
        }

        public void stopThread() {
            running = false;
        }
    }

    private static class BusinessLogic extends Thread {
        private volatile boolean running = true;
        private Metrics metrics;
        private Random random;

        public BusinessLogic(Metrics metrics) {
            this.metrics = metrics;
            this.random = new Random();
        }

        @Override
        public void run() {
            while (running) {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long end = System.currentTimeMillis();
                metrics.addSample(end - start);
            }
        }

        public void stopThread() {
            running = false;
        }
    }

    private static class Metrics {
        private long count;
        private volatile double average;

        public Metrics() {
            this.count = 0;
            this.average = 0.0;
        }

        public synchronized void addSample(long sample) {
            double currentSum = this.average * this.count;
            count++;
            this.average = (currentSum + sample) / count;
        }

        public double getAverage() {
            return this.average;
        }
    }
}
