package com.agilex.geo.provider;

public class GeoPoint {
	private double xLat;
	private double yLon;
	
	public GeoPoint() {
		
	}
	
	public GeoPoint(double xLat, double yLon) {
		super();
		this.xLat = xLat;
		this.yLon = yLon;
	}

	public double getxLat() {
		return xLat;
	}

	public void setxLat(double xLat) {
		this.xLat = xLat;
	}

	public double getyLon() {
		return yLon;
	}

	public void setyLon(double yLon) {
		this.yLon = yLon;
	}

	@Override
	public String toString() {
		return "GeoPoint [xLat=" + xLat + ", yLon=" + yLon + "]";
	}
}