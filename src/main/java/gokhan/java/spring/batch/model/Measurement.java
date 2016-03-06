package gokhan.java.spring.batch.model;

import gokhan.java.spring.batch.model.Counter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gokhant on 06/03/2016.
 */
public class Measurement {
    private String cellId;
    private String dataTime;
    private List<Counter> counters = new ArrayList<Counter>();

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
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
}
