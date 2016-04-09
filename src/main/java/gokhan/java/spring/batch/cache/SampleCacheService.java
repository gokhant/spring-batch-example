package gokhan.java.spring.batch.cache;

import gokhan.java.spring.batch.common.CellGeneratorUtil;
import gokhan.java.spring.batch.model.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class SampleCacheService implements CacheServiceDef{
    Logger logger = LoggerFactory.getLogger(SampleCacheService.class);
    Map<Integer, Cell> cells = null;
    @Override
    public Cell getCell(int cellId) {
        return cells.get(cellId);
    }

    @PostConstruct
    public void initialize() {
        cells = new CellGeneratorUtil().createCells(70000);
        logger.debug(cells.size() + " cells have been cached");
    }
}
