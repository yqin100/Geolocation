package com.agilex.geo.provider;

public interface GeoProvider<T, E extends Exception> {
    T getGeocodePoint(String address) throws E;
    T getGeocodePoint(String address, int numResults) throws E;
    double getDistance(GeoPoint point1, GeoPoint point2);
    public double getDistance(GeoPoint point1, GeoPoint point2, int unit);
}
