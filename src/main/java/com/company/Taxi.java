package com.company;
import java.util.Queue;
import java.util.Random;

public class Taxi implements Runnable {
    Taxi () {
        // System.out.println("Creating" + name );
    }  // git init  включает гит в корневую
    //git status показывает состояние
    @Override
    public void run() {
        // System.out.println("Running " + name );
        try {
            this.t.wait();
        } catch (InterruptedException e) { // ловим t.notify()
            // System.out.println("Thread " + name + " interrupted.");
            try {
                Thread.sleep((long) (Math.random() * 1000000));
                
            } catch (InterruptedException ex) {
                // не хотим ничего ловить, хотим спать
            }
        }
        // System.out.println("Thread " + name + " exiting.");
    }
    public void start() {
        // System.out.println("Starting " + name );
        if (t == null) {
            t = new Thread();
            t.start ();
        }
    }

    void placeOrder(Order order) {}

    public void placeDriver(Queue<Taxi> freeDrivers) {
        freeDrivers.offer(this);
    }

    public static void take_order() {

    }

    private Thread t;
    // private final String name;
}
