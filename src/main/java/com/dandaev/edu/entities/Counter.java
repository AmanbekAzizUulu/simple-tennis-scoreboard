package com.dandaev.edu.entities;

public class Counter {
    private int count;

    public Counter(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increaseCount() {
        count = count + 1;
    }

    public void decreaseCount() {
        count = count - 1;
    }

    @Override
    public String toString() {
        return "Counter { " +
                "\n\tcounter = " + count +
                "\n}";
    }
}
