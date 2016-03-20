package gokhan.java.spring.batch.common;

import gokhan.java.spring.batch.model.Cell;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

public class CellGeneratorUtil {
    private static final int startCellId = 14324;
    private static final double startLat = -33.894582;
    private static final double startLon = 151.239057;
    private static final double increment = 0.01;
    private static final int[] beamWidths = {360, 65, 80, 50, 72, 65, 80, 50, 72, 65, 80, 50, 72, 65, 80, 50, 72, 65, 80, 50, 72};
    private static final int azimuthMax = 360;
    private static final Random random = new Random(System.currentTimeMillis());

    private double latlon(double value, double min, double max) {
        if (value > max) {
            value = value - max;
        } else if (value < min) {
            value = value - min;
        }
        return value;
    }
    public Map<Integer,Cell> createCells(int numberOfCells) {
        Map<Integer, Cell> cells = new Hashtable<Integer, Cell>(numberOfCells);
        Cell cell;
        double lat, lon;
        for (int i=0; i<numberOfCells; i++) {
            cell = new Cell();
            cell.setCellId(startCellId + i);
            cell.setAzimuth(azimuth());
            cell.setBeamWidth(beamWidth());
            lat = startLat + i * increment;
            lat = latlon(lat, -90, 90);
            lon = startLon + i * increment;
            lon = latlon(lon, -180, 180);
            cell.setLatitude(lat);
            cell.setLongitude(lon);
            cells.put(cell.getCellId(), cell);
        }
        return cells;
    }

    private int azimuth() {
        return random.nextInt(azimuthMax) + 1;
    }

    private int beamWidth() {
        return beamWidths[random.nextInt(beamWidths.length)];
    }
}
