package com.javarush.task.task30.task3003;

import java.util.concurrent.TransferQueue;

public class Consumer implements Runnable {
    private TransferQueue<ShareItem> queue;

    public Consumer(TransferQueue<ShareItem> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(450);
//                queue.take();
                System.out.format("Processing " + queue.take().toString());
                System.out.printf("\n");
            } catch (InterruptedException e) {
            }
        }
    }
}
