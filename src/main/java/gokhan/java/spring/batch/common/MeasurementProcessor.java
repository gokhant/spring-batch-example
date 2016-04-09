package gokhan.java.spring.batch.common;

import gokhan.java.spring.batch.cache.CacheServiceDef;
import gokhan.java.spring.batch.model.Cell;
import gokhan.java.spring.batch.model.Counter;
import gokhan.java.spring.batch.model.Measurement;
import gokhan.java.spring.batch.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

public class MeasurementProcessor implements ItemProcessor<Measurement, Result> {
    Logger logger = LoggerFactory.getLogger(MeasurementProcessor.class);
    @Autowired
    CacheServiceDef cacheService;

    @Override
    public synchronized Result process(Measurement measurement) throws Exception {
        return calculate(measurement);
    }

    private Result calculate(Measurement measurement) {
        Result result = null;
        logger.info("calculating " + measurement.getCellId());
        List<Counter> counters = measurement.getCounters();
        Counter max = Collections.max(counters);
        logger.debug("Max counter is " + max);
        Cell cell = cacheService.getCell(measurement.getCellId());
        if (cell == null) {
            logger.warn("Cell " + measurement.getCellId() + " does not exist in cache");
        } else {
            result = new Result();
            result.setCounter(max);
            result.setCell(cell);
        }
        return result;
    }
}
