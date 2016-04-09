package gokhan.java.spring.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.List;

//public class CustomListReader<T> implements ItemReader<T>, StepExecutionListener {
public class CustomListReader<T> implements CustomListenAndReadDef<T> {
    Logger logger = LoggerFactory.getLogger(CustomListReader.class);
    private String name;
    private List<T> list;

    public CustomListReader(String name) {
        this.name = name;
        logger.info("CustomListReader has been constructed with " + name);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        list = (List<T>) stepExecution.getJobExecution().getExecutionContext().get(name);
        logger.info(stepExecution.getStepName() + " starting with " + list.size() + " items to read");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.info(stepExecution.getStepName() + " completed with " + stepExecution.getSummary());
        return stepExecution.getExitStatus();
    }

    @Override
    public synchronized T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        T removed;
        if (list != null && !list.isEmpty()) {
            removed = list.remove(0);
            logger.debug("reading " + removed);
            return removed;
        }
        return null;
    }
}
