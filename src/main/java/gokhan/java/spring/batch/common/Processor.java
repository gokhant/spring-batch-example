package gokhan.java.spring.batch.common;

import gokhan.java.spring.batch.model.Measurement;
import gokhan.java.spring.batch.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by gokhant on 06/03/2016.
 */
public class Processor implements ItemProcessor<Measurement, Result> {
    Logger logger = LoggerFactory.getLogger(Processor.class);

    @Override
    public Result process(Measurement item) throws Exception {
        logger.debug("[process] processing " + item);
        return new Result();
    }
}
