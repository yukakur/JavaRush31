package com.javarush.task.task30.task3004;

import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

public class BinaryRepresentationTask extends RecursiveTask<String> {
    int x;
    public BinaryRepresentationTask(int x) {
        this.x = x;
    }

    @Override
    protected String compute() {
        BinaryRepresentationTask task = new BinaryRepresentationTask(x);
        task.fork();
    }
}
