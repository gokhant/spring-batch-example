package gokhan.java.spring.batch;

import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

public interface CustomListenAndReadDef<T> extends StepExecutionListener, ItemReader<T> {
}
