package com.agilex.geo.esri;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agilex.geo.exception.GeoEsriException;
import com.agilex.geo.provider.GeoPoint;
import com.agilex.geo.provider.GeoProvider;
import com.agilex.geo.provider.GeoResult;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.LinearUnit;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.tasks.geocode.Locator;
import com.esri.core.tasks.geocode.LocatorFindParameters;
import com.esri.core.tasks.geocode.LocatorGeocodeResult;

public class EsriUtility implements GeoProvider<List<GeoResult>, GeoEsriException> {
	// spatial reference of points stored in lat-lon coordinates: WGS84 (wkid: 4326)
	private static int SPATIAL_REFERENCE_WIKID = 4326;
	private static String REST_URL = "http://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer";
    private final Logger log = LoggerFactory.getLogger(getClass());
	private enum Mode {
		DEVELOPER,
		BASIC,
		STANDARD;
	}
	public enum RESULT_MAP {
		POINT,
		REQUEST_ADDRESS,
		RESPONSE_ADDRESS,
		CONFIDENCE_LEVEL
	}
    private Mode currentMode = Mode.DEVELOPER;
    
    public EsriUtility() {
    	this(null, null, null);
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
        	log.info("Set REST URL for ESRI to be => " + REST_URL);
    	}
    	if (clientID != null) {
    		// TODO Uncomment this for production
    		//ArcGISRuntime.setClientID(clientID);
    		currentMode = Mode.BASIC;
    	}
    	if (license != null) {
    		// TODO Uncomment this for production
    		//ArcGISRuntime.License.setLicense(license);
    		currentMode = Mode.STANDARD;
    	}
    	
    	if (currentMode == Mode.BASIC) {
        	log.info("ESRI running in BASIC mode");
    	}
    	else if (currentMode == Mode.STANDARD) {
        	log.info("ESRI running in STANDARD mode");
    	}
    	else {
        	log.info("ESRI running in DEVELOPER mode");
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
    
    @Override
    public List<GeoResult> getGeocodePoint(String address) throws GeoEsriException {
    	return getGeocodePoint(address, 1);
    }
    
    @Override
    public List<GeoResult> getGeocodePoint(String address, int numResults) throws GeoEsriException {
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
			return convertResults(results);
		} catch (Exception e) {
			throw new GeoEsriException(e);
		} finally {
			locator.dispose();
		}
    }
    
    private List<GeoResult> convertResults(List<LocatorGeocodeResult> results) {
    	List<GeoResult> retList = new ArrayList<GeoResult>();
    	for (LocatorGeocodeResult result : results) {
    		GeoResult retResult = new GeoResult();
    		retResult.getResultMap().put(RESULT_MAP.POINT.toString(), new GeoPoint(result.getLocation().getX(), result.getLocation().getY()));
    		retResult.getResultMap().put(RESULT_MAP.CONFIDENCE_LEVEL.toString(), result.getScore());
    		retResult.getResultMap().put(RESULT_MAP.REQUEST_ADDRESS.toString(), result.getAddress());
    		retResult.getResultMap().put(RESULT_MAP.RESPONSE_ADDRESS.toString(), result.getAttributes().get("Address"));
			retList.add(retResult);
		}
    	return retList;
    }

    @Override
    public double getDistance(GeoPoint point1, GeoPoint point2) {
		return getDistance(point1, point2, LinearUnit.Code.MILE_STATUTE);
    }
    
    /**
     * Calculates the distance between two points give the units to return.
     * @param point1 First point to measure
     * @param point2 Second point to measure
     * @param unit LinearUnit.Code for units
     * @return The distance, in unit measurement, between the two points
     */
    @Override
    public double getDistance(GeoPoint point1, GeoPoint point2, int unit) {
    	checkLicensed();
    	
		return GeometryEngine.geodesicDistance(new Point(point1.getxLat(), point1.getyLon()), new Point(point2.getxLat(), point2.getyLon()), SpatialReference.create(SPATIAL_REFERENCE_WIKID), (LinearUnit)LinearUnit.create(unit));
    }
}