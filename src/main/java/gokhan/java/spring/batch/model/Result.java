package gokhan.java.spring.batch.model;

public class Result {
    String cellId;
    Counter counter;

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "Result{" +
                "cellId='" + cellId + '\'' +
                ", counter=" + counter +
                '}';
    }
}
