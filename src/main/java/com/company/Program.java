package com.company;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Program {
    private static final Deque<Order> ordersQueue = new ArrayDeque<Order>();
    private static final Deque<Taxi> freeDrivers = new ArrayDeque<Taxi>();
    private static ArrayList<Taxi> drivers = new ArrayList<Taxi>();

    private static class Dispatcher extends Thread {
        @Override
        public void run() {
            Order toAssign;
            while(true) {
                toAssign = getOrder();
                print(toAssign);
            }
        }

        private Order getOrder() {
            while (true) {
                synchronized (ordersQueue) {
                    while (ordersQueue.isEmpty()) {
                        try {
                            ordersQueue.wait();
                        } catch (InterruptedException ignore) {}
                    }
                    Order temporary = ordersQueue.removeFirst();
                    ordersQueue.notify();
                    return temporary;
                }
            }
        }

        private static void print(Order s) { // ctrl+alt+M
            System.out.println(s.name);
        }
    }

    public static class Taxi extends Thread {
        @Override
        public void run() {
            while (true) {

            }
        }

        public void doStuff() {
        }

        public void placeDriver() {
            freeDrivers.add(this);
        }

    }

    private static class Order_generator extends Thread {
        @Override
        public void run() {
            Order newOrder;
            while(true) {
                newOrder = new Order();
                addOrder(newOrder);
            }
        }

        private void addOrder(Order newOrder) {
            while (true) {
                synchronized (ordersQueue) {
                    while (ordersQueue.size() > 1000) {
                        try {
                            ordersQueue.wait();
                        } catch (InterruptedException ignore1) {
                        }
                    }
                    ordersQueue.add(newOrder);
                    ordersQueue.notify();
                    break;
                }
            }
        }

    }

    public static void main(String[] args) {
        Order_generator generator = new Order_generator();
        generator.setName("generator");
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setName("dispatcher");
        generator.start();
        dispatcher.start();


        // int N = 10;
        // for (int i = 0; i < N; ++i) {
        //     Taxi taxi = new Taxi();
        //     taxi.setName("driver" + i);
        //     drivers.add(taxi);
        //     taxi.start();
        // }
        System.out.println("Hello1");
    }
}