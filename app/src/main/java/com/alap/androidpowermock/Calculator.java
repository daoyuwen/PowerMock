package com.alap.androidpowermock;
public class Calculator {
    public int addExact(int x, int y) {
        return x + y;
    }

    public int subtractExact(int x, int y) {
        return x - y;
    }

    public int multiplyExact(int x, int y) {
        return x * y;
    }

    // TODO: zero case
    public int intDivide(int x, int y) {
        if (y == 0) {
            return dealZeroCase();
        } else {
            return x / y;
        }
    }

    private int dealZeroCase() {
        return 0;
    }
}