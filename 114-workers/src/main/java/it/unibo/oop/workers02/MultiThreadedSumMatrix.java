package it.unibo.oop.workers02;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public final class MultiThreadedSumMatrix implements SumMatrix{

    private final int nThread;

    public MultiThreadedSumMatrix(final int nThread) {
        this.nThread = nThread;
    }

    private static class Worker extends Thread {
        private final List<Double> list;
        private final int startPos;
        private final int nElem;
        private double res;

        Worker(final List<Double> list, final int startPos, final int nElem) {
            super();
            this.list = list;
            this.startPos = startPos;
            this.nElem = nElem;
        }

        @Override
        @SuppressWarnings("PMD.SystemPrintln")
        public void run() {
            System.out.println("Working from position " + startPos + " to position " + (startPos + nElem - 1));
            for (int i = startPos; i < list.size() && i < startPos + nElem; i++) {
                this.res += this.list.get(i);
            }
        }

        public double getResult() {
            return this.res;
        }
    }

    @Override
    public double sum(double[][] matrix) {
        int nRows = matrix.length;
        int nColumn = matrix[0].length;
        final List<Double> listOfDouble = new ArrayList<>();
        for(int i = 0; i < nRows ; i++) {
            for(int j = 0 ; j < nColumn ; j++) {
                listOfDouble.add(matrix[i][j]);
            }
        }

        final List<Worker> workers = new ArrayList<>(nThread);
        final int size = listOfDouble.size() / nThread + listOfDouble.size() % nThread;
        for (int start = 0; start < listOfDouble.size(); start += size) {
            workers.add(new Worker(listOfDouble, start, size));
        }
        for (final Worker w: workers) {
            w.start();
        }
        double sum = 0;
        for (final Worker w: workers) {
            try {
                w.join();
                sum += w.getResult();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }

        return sum;
    }
    
}
