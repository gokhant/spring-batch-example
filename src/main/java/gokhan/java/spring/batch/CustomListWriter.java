package gokhan.java.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;

public class CustomListWriter<T> implements ItemWriter<T>, StepExecutionListener {
    Logger logger = LoggerFactory.getLogger(CustomListWriter.class);
    private String name;
    private List<T> list = new ArrayList<T>();

    public CustomListWriter(String name) {
        this.name = name;
        logger.info("CustomListWriter has been constructed with " + name);
    }

    @Override
    public void write(List<? extends T> items) throws Exception {
        for (T item : items) {
            logger.debug("writing " + item);
            list.add(item);
        }
        logger.info(items.size() + " items have been written");
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {}

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution.getJobExecution().getExecutionContext().put(name, list);
        logger.info(list.size() + " items have been written to execution context");
        return stepExecution.getExitStatus();
    }
}