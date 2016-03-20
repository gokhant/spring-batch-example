package gokhan.java.spring.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class BatchStarter {
    public static void main(String[] args) {
        SpringApplication.run(BatchStarter.class, args);
    }
}
