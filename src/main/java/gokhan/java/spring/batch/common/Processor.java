package gokhan.java.spring.batch.common;

import gokhan.java.spring.batch.model.Counter;
import gokhan.java.spring.batch.model.Measurement;
import gokhan.java.spring.batch.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Collections;
import java.util.List;

public class Processor implements ItemProcessor<Measurement, Result> {
    Logger logger = LoggerFactory.getLogger(Processor.class);
    Measurement previous;

    @Override
    public Result process(Measurement measurement) throws Exception {
        Result result = null;
        logger.debug("[process] processing " + measurement);
        if (previous == null) {
            previous = measurement;
        } else {
            if (measurement.isSame(previous)) {
                previous.merge(measurement);
            } else {
                result = calculate(previous);
                previous = measurement;
            }
        }
        return result;
    }

    private Result calculate(Measurement previous) {
        List<Counter> counters = previous.getCounters();
        Counter max = Collections.max(counters);
        logger.debug("Max counter is " + max);
        Result result = new Result();
        result.setCellId(previous.getCellId());
        result.setCounter(max);
        return result;
    }
}
