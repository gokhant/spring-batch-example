package gokhan.java.spring.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class TemporaryListener implements StepExecutionListener {
    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("* * * * BEFORE");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("* * * * AFTER");
        return null;
    }
}
