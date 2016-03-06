package gokhan.java.spring.batch.common;

import gokhan.java.spring.batch.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * Created by gokhant on 06/03/2016.
 */
public class ResultWriter implements ItemWriter<Result> {
    Logger logger = LoggerFactory.getLogger(ResultWriter.class);

    @Override
    public void write(List<? extends Result> results) throws Exception {
        for (Result result : results) {
            logger.debug("[write] " + result);
        }
    }
}
