package step.learning.Async;

import java.util.Random;

public class AsyncDemo {
    private String resultString = "";
    private final Object stringLocker = new Object();
    private final char[] chars = {'A', 'B', 'C', 'D', '1', '2', '3', '4'};

    public void run() {
        System.out.println("Async String Demo");

        int length = 10;
        Thread[] threads = new Thread[length];

        for (int i = 0; i < length; i++) {
            threads[i] = new Thread(new CharTask());
            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException ignore) {}
        }

        String finalResult = resultString + ".jpg";
        System.out.println("Result string (mx): " + finalResult);
    }

    class CharTask implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(300);
            } catch (InterruptedException ignore) {}

            char randomChar = chars[new Random().nextInt(chars.length)];

            synchronized (stringLocker) {
                resultString += randomChar;
                System.out.println("Added: " + randomChar + ", current: " + resultString);
            }
        }
    }
}