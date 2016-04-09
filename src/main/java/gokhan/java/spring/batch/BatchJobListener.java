package gokhan.java.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class BatchJobListener implements JobExecutionListener {
    Logger logger = LoggerFactory.getLogger(BatchJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Job " + jobExecution.getJobInstance().getJobName() + " starting");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("Job " + jobExecution.getJobInstance().getJobName() + " completed with " + jobExecution.getStatus());
    }
}
