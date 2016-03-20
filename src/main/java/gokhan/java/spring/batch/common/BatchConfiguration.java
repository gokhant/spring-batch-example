package gokhan.java.spring.batch.common;

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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private StepExecutionListener stepListener;

    @Autowired
    private JobBuilderFactory jobFactory;

    @Autowired
    private StepBuilderFactory stepFactory;

    public BatchConfiguration() {
    }

    @Bean
    public ItemReader<Measurement> csvFileReader() {
        FlatFileItemReader<Measurement> reader = new FlatFileItemReader<Measurement>();
        reader.setResource(new ClassPathResource("3G_Samples.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(lineMapper());
        return reader;
    }

    @Bean
    public ItemProcessor<Measurement, Result> csvItemProcessor() {
        return new Processor();
    }

    @Bean
    public ItemWriter<Result> resultWriter() {
        return resultWriter(
                new FileSystemResource("results.csv")
        );
    }

    @Bean
    public Job measurementProcessorJob(Step step, JobExecutionListener listener) {
        return jobFactory.get("JobProcessMeasurement")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step)
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemReader<Measurement> reader, ItemWriter<Result> writer, ItemProcessor<Measurement, Result> processor) {
        return stepFactory.get("FirstStep")
                .<Measurement, Result> chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(stepListener)
                .build();
    }

    private FieldSetMapper<Measurement> fieldSetMapper() {
        return new FieldSetMapper3G();
    }

    private LineTokenizer tokenizer() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames(new String[]{"cell_id", "data_time", "pd0", "pd1", "pd2", "pd3", "pd4"});
        return tokenizer;
    }

    private LineMapper<Measurement> lineMapper() {
        DefaultLineMapper<Measurement> lineMapper = new DefaultLineMapper<Measurement>();
        lineMapper.setLineTokenizer(tokenizer());
        lineMapper.setFieldSetMapper(fieldSetMapper());
        return lineMapper;
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
}
