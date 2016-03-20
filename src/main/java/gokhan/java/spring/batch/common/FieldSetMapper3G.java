package gokhan.java.spring.batch.common;

import gokhan.java.spring.batch.model.Measurement;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class FieldSetMapper3G implements FieldSetMapper<Measurement> {
    @Override
    public Measurement mapFieldSet(FieldSet fs) throws BindException {
        if (fs == null) {
            return null;
        }
        Measurement m = new Measurement();
        m.setCellId(fs.readString("cell_id"));
        m.setDataTime(fs.readString("data_time"));
        m.addCounter("pd0", fs.readInt("pd0"));
        m.addCounter("pd1", fs.readInt("pd1"));
        m.addCounter("pd2", fs.readInt("pd2"));
        m.addCounter("pd3", fs.readInt("pd3"));
        m.addCounter("pd4", fs.readInt("pd4"));
        return m;
    }
}
