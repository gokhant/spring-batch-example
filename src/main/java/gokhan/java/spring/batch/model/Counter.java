package gokhan.java.spring.batch.model;

/**
 * Created by gokhant on 06/03/2016.
 */
public class Counter {
    private String name;
    private int value;

    public Counter(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
