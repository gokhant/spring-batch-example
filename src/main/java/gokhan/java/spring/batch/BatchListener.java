package gokhan.java.spring.batch;

import gokhan.java.spring.batch.model.Measurement;
import gokhan.java.spring.batch.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.stereotype.Component;

@Component
public class BatchListener implements StepExecutionListener, ItemProcessListener<Measurement, Result>, JobExecutionListener {
    Logger logger = LoggerFactory.getLogger(BatchListener.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug(stepExecution.getStepName() + " starting");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug(stepExecution.getStepName() + " completed with " + stepExecution.getSummary());
        return null;
    }

    @Override
    public void beforeProcess(Measurement item) {
        logger.debug("Will process " + item);
    }

    @Override
    public void afterProcess(Measurement item, Result result) {
        logger.debug("Processed " + item + " and converted to " + result);
    }

    @Override
    public void onProcessError(Measurement item, Exception e) {
        logger.error("An error occured while processing " + item);
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.debug("Job " + jobExecution.getJobInstance().getJobName() + " starting");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.debug("Job " + jobExecution.getJobInstance().getJobName() + " completed with " + jobExecution.getStatus());
    }
}
