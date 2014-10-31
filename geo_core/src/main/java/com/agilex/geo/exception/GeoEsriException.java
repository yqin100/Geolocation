package com.agilex.geo.exception;

public class GeoEsriException extends Exception {
	private static final long serialVersionUID = 1L;

	public GeoEsriException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeoEsriException(String message) {
		super(message);
	}

	public GeoEsriException(Throwable cause) {
		super(cause);
	}
}