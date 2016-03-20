package gokhan.java.spring.batch.cache;

import gokhan.java.spring.batch.model.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SampleCacheService implements CacheServiceDef{
    Logger logger = LoggerFactory.getLogger(SampleCacheService.class);
    @Override
    public Cell getCell() {
        return null;
    }

    @PostConstruct
    public void initialize() {

    }
}
