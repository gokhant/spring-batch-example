package gokhan.java.spring.batch.model;

public class Result {
    Cell cell;
    Counter counter;

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "Result{" +
                "cell=" + cell +
                ", counter=" + counter +
                '}';
    }
}
