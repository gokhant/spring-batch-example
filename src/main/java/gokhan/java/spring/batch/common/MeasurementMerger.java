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

public class MeasurementMerger implements ItemProcessor<Measurement, Measurement> {
    Logger logger = LoggerFactory.getLogger(MeasurementMerger.class);
    Measurement previous;

    @Override
    public Measurement process(Measurement measurement) throws Exception {
        Measurement m = null;
        logger.debug("[process] processing " + measurement);
        if (previous == null) {
            previous = measurement;
        } else {
            if (measurement.isSame(previous)) {
                previous.merge(measurement);
            } else {
                m = previous;
                previous = measurement;
            }
        }
        return m;
    }
}
