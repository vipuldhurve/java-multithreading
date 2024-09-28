package examples.HackerThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class VaultHacker {
    private static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {
//        Create random password generator and pass to vault object
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));
        System.out.println("Password is " + vault.password);
//        Create hacker and police thread
        List<Thread> threads = new ArrayList<>(Arrays.asList(
                new AscendingHacker(vault),
                new DescendingHacker(vault),
                new PoliceThread()
        ));
//        Start hacker and police threads
        for (Thread thread : threads) {
            thread.start();
        }
    }

    private static class Vault {
        private int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isPasswordcorrect(int pass) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return this.password == pass;
        }
    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Started thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingHacker extends HackerThread {
        public AscendingHacker(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = 0; guess <= MAX_PASSWORD; guess++) {
                if (vault.isPasswordcorrect(guess)) {
                    System.out.println(this.getName() + " guessed the password:" + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHacker extends HackerThread {

        public DescendingHacker(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
                if (vault.isPasswordcorrect(guess)) {
                    System.out.println(this.getName() + " guessed the password:" + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {
        @Override
        public void run() {
            for (int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Police thread interrupted due to some exception");
                }
                System.out.println(i);
            }
            System.out.println("Game over for you hackers");
            System.exit(0);
        }
    }
}
