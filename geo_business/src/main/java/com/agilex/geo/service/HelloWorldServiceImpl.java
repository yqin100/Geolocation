package com.agilex.geo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.agilex.geo.model.HelloWorldResponse;

@Service
public class HelloWorldServiceImpl implements HelloWorldService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
