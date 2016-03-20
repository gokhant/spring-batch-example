package gokhan.java.spring.batch.model;

public class Cell {
    private int cellId;
    private double latitude;
    private double longitude;
    private int azimuth;
    private int beamWidth;

    public Cell() {
    }

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(int azimuth) {
        this.azimuth = azimuth;
    }

    public int getBeamWidth() {
        return beamWidth;
    }

    public void setBeamWidth(int beamWidth) {
        this.beamWidth = beamWidth;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "cellId=" + cellId +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", azimuth=" + azimuth +
                ", beamWidth=" + beamWidth +
                '}';
    }
}