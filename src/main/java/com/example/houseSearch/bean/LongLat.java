package com.example.houseSearch.bean;

import java.util.Objects;

public class LongLat {

    String Position;
    Float Lat;
    Float Lng;

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public Float getLat() {
        return Lat;
    }

    public void setLat(Float lat) {
        Lat = lat;
    }

    public Float getLng() {
        return Lng;
    }

    public void setLng(Float lng) {
        Lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongLat longLat = (LongLat) o;
        return Objects.equals(Position, longLat.Position) && Objects.equals(Lat, longLat.Lat) && Objects.equals(Lng, longLat.Lng);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Position, Lat, Lng);
    }
}
