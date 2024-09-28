package multiThreading.threads.interruption;

import java.math.BigInteger;

public class ExplicitHandleInterruption {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                new LongComputation(new BigInteger("200000"), new BigInteger("100000000"))
        );

        thread.start();
        thread.sleep(2000);
//        If thread is executing some long processing task,
//        and code does not throw interrupted exception
//        then we need to handle interruption explicitly inside thread finding the hotspot area
        thread.interrupt();
    }

    private static class LongComputation implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputation(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private static BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i.add(BigInteger.ONE)) {
//                Explicitly handling interruption inside thread that takes longer time
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Prematurely interrupted computation...");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }
            return result;
        }
    }
}
