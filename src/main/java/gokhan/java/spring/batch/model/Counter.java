package gokhan.java.spring.batch.model;

public class Counter implements Comparable<Counter> {
    private String name;
    private int value;

    public Counter(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    public void merge(Counter other) {
        value += other.getValue();
    }

    @Override
    public int compareTo(Counter other) {
        return value - other.getValue();
    }
}
