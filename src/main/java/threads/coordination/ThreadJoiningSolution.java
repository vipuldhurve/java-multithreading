package threads.coordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadJoiningSolution {
    public static void main(String[] args) throws InterruptedException {
        List<Long> inputNumbers = Arrays.asList(100_000_000L, 4L, 35435L, 2324L, 4656L, 23L, 5556L);
        List<FactorialThread> threads = new ArrayList<>();

        for (Long inputNumber : inputNumbers) {
            threads.add(new FactorialThread(inputNumber));
        }

        for (FactorialThread thread : threads) {
            thread.start();
        }

//        Join threads to main thread so main thread will wait for each thread
//        for given time to complete else continue
        for (FactorialThread factorialThread : threads) {
            factorialThread.join(2000);
        }

//        Checking result from factorial threads
        for (FactorialThread factorialThread : threads) {
//            RACE CONDITION
            if (factorialThread.isFinished) {
                System.out.println("Factorial for number " + factorialThread.getInputNumber()
                        + " is: " + factorialThread.getResult());
            } else {
                System.out.println("Calculation for " + factorialThread.getInputNumber()
                        + "  is still in progress....");
            }
        }

//        The application will wait for all threads to complete there task
    }

    private static class FactorialThread extends Thread {
        private Long inputNumber;
        private BigInteger result;
        private boolean isFinished;

        public FactorialThread(Long inputNumber) {
            this.inputNumber = inputNumber;
            this.result = BigInteger.ZERO;
            this.isFinished = false;
        }

        @Override
        public void run() {
            this.result = factorial(this.inputNumber);
            this.isFinished = true;
        }

        private static BigInteger factorial(Long n) {
            BigInteger tempResult = BigInteger.ONE;
            for (long i = 1; i <= n; i++) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return tempResult;
        }

        public Long getInputNumber() {
            return inputNumber;
        }

        public BigInteger getResult() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }
    }
}
