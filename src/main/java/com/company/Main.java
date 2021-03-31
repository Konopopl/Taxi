package com.company;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static int amountTaxi = 100;
    static ArrayList<Taxi> taxsists = new ArrayList<>(amountTaxi);
    public static volatile ArrayBlockingQueue<Taxi> queueTaxi = new ArrayBlockingQueue<>(1000);

    public static void creatingFillingInAnArrayOfTaxiDriversAddingThemToTheQueue() {
        for (int i = 0; i < amountTaxi; i++) {
            taxsists.add(new Taxi(i));
        }
        for (Taxi c : taxsists) {
            queueTaxi.add(c);
        }
    }


    public static class Disp extends Thread {
        @Override
        public void run() {
            while (true) {

                Order order = new Order();
                Taxi executor = null;

                try {
                    executor = queueTaxi.take();
                } catch (InterruptedException ignore) {
                    ignore.printStackTrace();
                }
                executor.setCondition("placeOrder");
                System.out.println("Taxi:" + executor.number + ", placeOrder" + order.name);
                synchronized (executor) {
                    executor.notify();
                }
            }
        }
    }
    public static class Taxi extends Thread {
        public String condition;
        public int number;
        public Taxi() {
        }

        public Taxi(int number) {
            this.number = number;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public static int random() {
            int lowestValue = 0;
            int highestМalue = 10;
            int random_number = lowestValue + (int) (Math.random() * highestМalue);
            return random_number;
        }

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    while (!(this.condition == null)) {
                        try {
                            sleep(random());
                        } catch (InterruptedException ignore) {
                            ignore.printStackTrace();
                        }

                        System.out.println(this.number + "  Hi Hitler");
                        this.condition = null;
                        queueTaxi.add(this);
                        try {
                            this.wait();
                        } catch (InterruptedException ignore) {
                            ignore.printStackTrace();
                        }

                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        creatingFillingInAnArrayOfTaxiDriversAddingThemToTheQueue();
        Disp dispetcher = new Disp();
        dispetcher.start();
        for (Taxi c : taxsists) {
            c.start();
        }
    }
}