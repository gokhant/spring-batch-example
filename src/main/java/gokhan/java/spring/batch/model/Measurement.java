package gokhan.java.spring.batch.model;

import gokhan.java.spring.batch.model.Counter;

import java.util.ArrayList;
import java.util.List;

public class Measurement {
    private int cellId;
    private String dataTime;
    private List<Counter> counters = new ArrayList<Counter>();

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public List<Counter> getCounters() {
        return counters;
    }

    public void addCounter(String counterName, int counterValue) {
        counters.add(new Counter(counterName, counterValue));
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "cellId='" + cellId + '\'' +
                ", dataTime='" + dataTime + '\'' +
                ", counters=" + counters +
                '}';
    }

    public boolean isSame(Measurement other) {
        return other.getCellId() == cellId;
    }

    public void merge(Measurement other) {
        List<Counter> otherCounters = other.getCounters();
        for (int i=0; i<otherCounters.size(); i++) {
            counters.get(i).merge(otherCounters.get(i));
        }
    }
}
