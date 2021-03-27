package com.company;

public class Order {
    private static int x = 0;
    Order() {
        ++x;
        name = "order № " + x;
    }
    public String name;

}
