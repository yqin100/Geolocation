package com.agilex.geo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.agilex.geo.exception.GeoEsriException;
import com.agilex.geo.model.HelloWorldResponse;
import com.agilex.geo.provider.GeoProvider;
import com.agilex.geo.provider.GeoResult;

@Service
public class HelloWorldServiceImpl implements HelloWorldService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
	@Qualifier("geoProvider1")
    GeoProvider<List<GeoResult>, GeoEsriException> esriExternalProvider;

	@Override
	public String getHelloString(String input) {
		log.debug("Get hello world string");
		return "Hello World " + input;
	}

	@Override
	public HelloWorldResponse getHelloObject(String input) {
		log.debug("Get hello world object");
		return new HelloWorldResponse(input, getHelloString(input));
	}
}
