package gokhan.java.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.stereotype.Component;

@Component
public class BatchStepListener<T, S> implements StepExecutionListener, ItemProcessListener<T, S>{
    Logger logger = LoggerFactory.getLogger(BatchStepListener.class);
    int count = 0;
    int modulo = 1000;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.info(stepExecution.getStepName() + " starting");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info(stepExecution.getStepName() + " completed with " + stepExecution.getSummary());
        return null;
    }

    @Override
    public void beforeProcess(T item) {
        logger.debug("Will process " + item);
    }

    @Override
    public void afterProcess(T item, S result) {
        count++;
        if (count % modulo == 0) {
            logger.info("Processed " + count + " items");
        }
        logger.debug("Processed " + item + " and converted to " + result);
    }

    @Override
    public void onProcessError(T item, Exception e) {
        logger.error("An error occured while processing " + item);
    }
}
