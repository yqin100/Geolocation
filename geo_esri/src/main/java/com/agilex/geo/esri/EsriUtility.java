package com.agilex.geo.esri;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilex.geo.exception.GeoEsriException;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.Unit;
import com.esri.core.tasks.geocode.Locator;
import com.esri.core.tasks.geocode.LocatorFindParameters;
import com.esri.core.tasks.geocode.LocatorGeocodeResult;
import com.esri.runtime.ArcGISRuntime;

public class EsriUtility {
	// spatial reference of points stored in lat-lon coordinates: WGS84 (wkid: 4326)
	private static int SPATIAL_REFERENCE_WIKID = 4326;
	private static String REST_URL = "http://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer";
    private final Logger log = LoggerFactory.getLogger(getClass());
	private enum Mode {
		DEVELOPER,
		BASIC,
		STANDARD;
	}
    private Mode currentMode = Mode.DEVELOPER;
    
    public EsriUtility() {
    }
    
    public EsriUtility(String restURL) {
    	this(restURL, null);
    }
    
    public EsriUtility(String restURL, String clientID) {
    	this(restURL, clientID, null);
    }
    
    public EsriUtility(String restURL, String clientID, String license) {
    	if (restURL != null) {
        	REST_URL = restURL;
    	}
    	if (clientID != null) {
    		ArcGISRuntime.setClientID(clientID);
    		currentMode = Mode.BASIC;
    	}
    	if (license != null) {
    		ArcGISRuntime.License.setLicense(license);
    		currentMode = Mode.STANDARD;
    	}
    }
    
    private void checkLicensed() {
    	switch (currentMode) {
	    	case STANDARD:
	    		// placeholder to exit quicker if all licenses are setup correctly
	    		break;
	    	case BASIC:
	    		log.warn("Running in '" + Mode.BASIC + "' mode.  "
	    				+ "If you do any '" + Mode.STANDARD + "' functionality, an ESRI exception will be thrown.  "
						+ "You need to call the 'init' methods to work properly.");
	    		break;
	    	case DEVELOPER:
	    		log.warn("Running in '" + Mode.DEVELOPER + "' mode.  "
	    				+ "If you do any '" + Mode.BASIC + "' or '" + Mode.STANDARD + "' functionality, an ESRI exception will be thrown.  "
						+ "You need to call the 'init' methods to work properly.");
	    		break;
    	}
    }
    
    public List<LocatorGeocodeResult> getGeocodePoint(String address) throws Exception {
    	return getGeocodePoint(address, 1);
    }
    
    public List<LocatorGeocodeResult> getGeocodePoint(String address, int numResults) throws GeoEsriException {
    	checkLicensed();
    	
    	if (numResults < 0) {
    		throw new GeoEsriException("Parameter 'numResults' has to be greater than zero.");
    	}
    	
		Locator locator = Locator.createOnlineLocator(REST_URL);
		LocatorFindParameters parameters = new LocatorFindParameters(address);
		parameters.setMaxLocations(numResults);
		List<LocatorGeocodeResult> results;
		try {
			results = locator.find(parameters);
			return results;
		} catch (Exception e) {
			throw new GeoEsriException(e);
		} finally {
			locator.dispose();
		}
    }

    public double getDistance(Point point1, Point point2) {
		return getDistance(point1, point2, LinearUnit.Code.MILE_STATUTE);
    }
    
    /**
     * Calculates the distance between two points give the units to return.
     * @param point1 First point to measure
     * @param point2 Second point to measure
     * @param unit LinearUnit.Code for units
     * @return The distance, in unit measurement, between the two points
     */
    public double getDistance(Point point1, Point point2, int unit) {
    	checkLicensed();
    	
		return GeometryEngine.geodesicDistance(point1, point2, SpatialReference.create(SPATIAL_REFERENCE_WIKID), (LinearUnit)LinearUnit.create(unit));
    }
    
    public static void main(String[] args) throws Exception {
		EsriUtility esri = new EsriUtility();
		
		double distance1 = GeometryEngine.geodesicDistance(
				new Point(-77.4634138746498, 38.87251227532232),
				new Point(-77.03480711599963, 38.88946905500046),
				SpatialReference.create(4326),
				new LinearUnit(4326));
		System.out.println("Distance *** " + distance1);
		
		double distance = esri.getDistance(new Point(-77.4634138746498, 38.87251227532232), new Point(-77.03480711599963, 38.88946905500046));
		System.out.println("Distance (default) = " + distance);
		
		distance = esri.getDistance(new Point(-77.4634138746498, 38.87251227532232), new Point(-77.03480711599963, 38.88946905500046), LinearUnit.Code.CENTIMETER);
		System.out.println("Distance (centimeter) = " + distance);
		
		distance = esri.getDistance(new Point(-77.4634138746498, 38.87251227532232), new Point(-77.03480711599963, 38.88946905500046), LinearUnit.Code.FOOT_US);
		System.out.println("Distance (foot) = " + distance);
		
		distance = esri.getDistance(new Point(-77.4634138746498, 38.87251227532232), new Point(-77.03480711599963, 38.88946905500046), LinearUnit.Code.INCH_US);
		System.out.println("Distance (inch) = " + distance);
		
		distance = esri.getDistance(new Point(-77.4634138746498, 38.87251227532232), new Point(-77.03480711599963, 38.88946905500046), LinearUnit.Code.MILE_US);
		System.out.println("Distance (meter) = " + distance);
		
		distance = esri.getDistance(new Point(-77.4634138746498, 38.87251227532232), new Point(-77.03480711599963, 38.88946905500046), LinearUnit.Code.MILE_US);
		System.out.println("Distance (mile) = " + distance);

		distance = esri.getDistance(new Point(-77.4634138746498, 38.87251227532232), new Point(-77.03480711599963, 38.88946905500046), LinearUnit.Code.YARD_US);
		System.out.println("Distance (yard) = " + distance);
		
//		Distance (mile_Statute) = 23.138840497716494
//		Distance (centimeter) = 3723835.4121957053
//				Distance (foot) = 122172.83348178741
//				Distance (inch) = 1466074.001781449
//				Distance (meter) = 23.138794220035496
//				Distance (mile) = 23.138794220035496
//				Distance (yard) = 40724.27782726248
    }
    
}