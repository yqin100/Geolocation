package com.agilex.geo.esri;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.agilex.geo.esri.EsriUtility.RESULT_MAP;
import com.agilex.geo.exception.GeoEsriException;
import com.agilex.geo.provider.GeoPoint;
import com.agilex.geo.provider.GeoResult;
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;

public class TestEsriUtility extends Assert {
	private static AddressToPoint addressToPoint_1;
	private static AddressToPoint addressToPoint_2;
	private static AddressToPoint addressToPoint_3;
	private static EsriUtility esriUtility;
	
	@BeforeClass
	public static void setupOnce() {
		addressToPoint_1 = new AddressToPoint("5155 Parkstone Dr, Chantilly, VA 20151", new Point(-77.4634138746498, 38.87251227532232));
		addressToPoint_2 = new AddressToPoint("Chantilly, VA", new Point(-77.43109594199967, 38.89427912800045));
		addressToPoint_3 = new AddressToPoint("Washington Monument", new Point(-77.03480711599963, 38.88946905500046));
		esriUtility = new EsriUtility();
	}
	
	@Test
	public void testGeocode() throws Exception {
		List<AddressToPoint> addressToPoints = Arrays.<AddressToPoint>asList(addressToPoint_1, addressToPoint_2, addressToPoint_3);

		for (AddressToPoint addressToPoint : addressToPoints) {
			GeoPoint resultPoint = (GeoPoint)esriUtility.getGeocodePoint(addressToPoint.getAddress()).get(0).getResultMap().get(RESULT_MAP.POINT.toString());
			Point knownPoint = addressToPoint.getPoint();
			assertEquals(knownPoint.getX(), resultPoint.getxLat(), 0);
			assertEquals(knownPoint.getY(), resultPoint.getyLon(), 0);
		}
	}
	
	@Test(expected=GeoEsriException.class)
	public void testZeroResults() throws GeoEsriException {
		esriUtility.getGeocodePoint(addressToPoint_1.getAddress(), 0);
	}
	
	@Test
	public void testNumresults() throws GeoEsriException {
		String address = addressToPoint_1.getAddress();
		List<GeoResult> results;
		int numResults;
		
		numResults = 1;
		results = esriUtility.getGeocodePoint(address, numResults);
		assertEquals(numResults, results.size());

		numResults = 3;
		results = esriUtility.getGeocodePoint(address, numResults);
		assertEquals(numResults, results.size());

		numResults = 10;
		results = esriUtility.getGeocodePoint(address, numResults);
		assertEquals(numResults, results.size());

		numResults = 20;
		results = esriUtility.getGeocodePoint(address, numResults);
		assertEquals(numResults, results.size());
	}
	
	@Test
	public void testDistance() {
		GeoPoint point1 = new GeoPoint(addressToPoint_1.getPoint().getX(), addressToPoint_1.getPoint().getY());
		GeoPoint point2 = new GeoPoint(addressToPoint_3.getPoint().getX(), addressToPoint_3.getPoint().getY());
		double distance;
		
		distance = esriUtility.getDistance(point1, point2);
		assertEquals(distance, 23.138840497716494, 0);
		
		distance = esriUtility.getDistance(point1, point2, LinearUnit.Code.CENTIMETER);
		assertEquals(distance, 3723835.4121957053, 0);
		
		distance = esriUtility.getDistance(point1, point2, LinearUnit.Code.FOOT_US);
		assertEquals(distance, 122172.83348178741, 0);
		
		distance = esriUtility.getDistance(point1, point2, LinearUnit.Code.INCH_US);
		assertEquals(distance, 1466074.001781449, 0);
		
		distance = esriUtility.getDistance(point1, point2, LinearUnit.Code.MILE_US);
		assertEquals(distance, 23.138794220035496, 0);
		
		distance = esriUtility.getDistance(point1, point2, LinearUnit.Code.YARD_US);
		assertEquals(distance, 40724.27782726248, 0);
	}
	
	private static class AddressToPoint {
		private String address;
		private Point point;
		
		public AddressToPoint(String address, Point point) {
			super();
			this.address = address;
			this.point = point;
		}

		public String getAddress() {
			return address;
		}

		public Point getPoint() {
			return point;
		}

		@Override
		public String toString() {
			return "AddressToPoint [address=" + address + ", point=" + point
					+ "]";
		}
	}
}
