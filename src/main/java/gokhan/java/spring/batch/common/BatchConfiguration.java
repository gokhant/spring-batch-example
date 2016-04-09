package gokhan.java.spring.batch.common;

import gokhan.java.spring.batch.*;
import gokhan.java.spring.batch.model.Measurement;
import gokhan.java.spring.batch.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private JobExecutionListener jobListener;

    @Autowired
    private JobBuilderFactory jobFactory;

    @Autowired
    private StepBuilderFactory stepFactory;

    public BatchConfiguration() {
    }

    private FieldSetMapper<Measurement> fieldSetMapper() {
        return new FieldSetMapper3G();
    }

    private LineTokenizer tokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(";");
        tokenizer.setNames(new String[]{"cell_id", "data_time", "pd0", "pd1", "pd2", "pd3", "pd4"});
        return tokenizer;
    }

    private LineMapper<Measurement> lineMapper() {
        DefaultLineMapper<Measurement> lineMapper = new DefaultLineMapper<Measurement>();
        lineMapper.setLineTokenizer(tokenizer());
        lineMapper.setFieldSetMapper(fieldSetMapper());
        return lineMapper;
    }

    @Bean(name="MeasurementReader")
    public ItemReader<Measurement> measurementReader() {
        FlatFileItemReader<Measurement> reader = new FlatFileItemReader<Measurement>();
        reader.setResource(new ClassPathResource("3G_Samples.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper());
        return reader;
    }

    @Bean(name="MeasurementMerger")
    public ItemProcessor<Measurement, Measurement> measurementMerger() {
        return new MeasurementMerger();
    }

    @Bean(name="MeasurementWriterToList")
    public ItemWriter<Measurement> listWriter() {
        return new CustomListWriter<Measurement>("deliDana");
//        return new ListItemWriter<Measurement>();
    }

    @Bean(name="FirstStep")
    public Step step1(@Qualifier("MeasurementReader") ItemReader<Measurement> reader, @Qualifier("MeasurementWriterToList") ItemWriter<Measurement> writer, @Qualifier("MeasurementMerger") ItemProcessor<Measurement, Measurement> processor) {
        return stepFactory.get("FirstStep")
                .<Measurement, Measurement> chunk(500)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(listenerForFirstStep())
//                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean(name="FirstListener")
    public StepExecutionListener listenerForFirstStep() {
        return new BatchStepListener<Measurement, Measurement>();
    }

    @Bean
//    public Job measurementProcessorJob(@Qualifier("FirstStep") Step stepOne, JobExecutionListener listener) {
    public Job measurementProcessorJob(@Qualifier("FirstStep") Step stepOne, @Qualifier("SecondStep") Step stepTwo, JobExecutionListener listener) {
        return jobFactory.get("JobProcessMeasurement")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(stepOne)
                .next(stepTwo)
                .preventRestart()
                .build();
    }

    @Bean(name="SecondListener")
    public StepExecutionListener listenerForSecondStep() {
        return new BatchStepListener<Measurement, Result>();
    }

    @Bean(name="SecondStep")
    public Step step2(@Qualifier("MeasurementReaderFromList") CustomListenAndReadDef<Measurement> reader,
                      @Qualifier("MeasurementWriterToFile") ItemWriter<Result> writer,
                      @Qualifier("MeasurementProcessor") ItemProcessor<Measurement, Result> processor) {
        return stepFactory.get("SecondStep")
                .<Measurement, Result> chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                //.taskExecutor(taskExecutor())
                .listener(listenerForSecondStep())
                .listener(new TemporaryListener())
                .listener(reader)
                .build();
    }

    @Bean(name="MeasurementReaderFromList")
    @StepScope
    public CustomListenAndReadDef<Measurement> measurementReaderFromList() {
        return new CustomListReader<Measurement>("deliDana");
    }
//    public ItemReader<Measurement> measurementReaderFromList() {
//        return new CustomListReader<Measurement>("deliDana");
//    }

    @Bean(name="MeasurementProcessor")
    public ItemProcessor<Measurement, Result> measurementProcessor() {
        return new MeasurementProcessor();
    }

    @Bean(name="MeasurementWriterToFile")
    public ItemWriter<Result> resultWriter() {
        return resultWriter(
                new FileSystemResource("results.csv")
        );
    }
    private ItemWriter<Result> resultWriter(FileSystemResource fsr) {
        FlatFileItemWriter<Result> writer = new FlatFileItemWriter<Result>();
        writer.setResource(fsr);
        writer.setShouldDeleteIfExists(true);
        DelimitedLineAggregator<Result> lineAggregator = new DelimitedLineAggregator<Result>();
        lineAggregator.setDelimiter(",");
        BeanWrapperFieldExtractor<Result> fExtractor = new BeanWrapperFieldExtractor<Result>();
        fExtractor.setNames(new String[] {"cell.cellId", "cell.latitude", "cell.longitude", "cell.azimuth", "cell.beamWidth", "counter.name", "counter.value"});
        lineAggregator.setFieldExtractor(fExtractor);
        writer.setLineAggregator(lineAggregator);
        return writer;
    }

//    @Bean
//    public TaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setMaxPoolSize(15);
//        taskExecutor.afterPropertiesSet();
//        taskExecutor.setThreadNamePrefix("GokhanThread");
//        new Thread(new ExecutorMonitor(taskExecutor)).start();
//        return taskExecutor;
//    }
}