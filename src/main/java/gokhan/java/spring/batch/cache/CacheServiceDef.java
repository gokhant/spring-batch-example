package gokhan.java.spring.batch.cache;

import gokhan.java.spring.batch.model.Cell;

public interface CacheServiceDef {
    Cell getCell(int cellId);
}
