package com.agilex.geo.provider;

import java.util.HashMap;
import java.util.Map;

public class GeoResult {
	private final Map<String, Object> resultMap = new HashMap<>();
	
	public GeoResult() {
		
	}

	public Map<String, Object> getResultMap() {
		return resultMap;
	}
}
