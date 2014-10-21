package com.agilex.geo.service;

import org.springframework.stereotype.Service;

import com.agilex.geo.model.HelloWorldResponse;

@Service
public class HelloWorldServiceImpl implements HelloWorldService {
	@Override
	public String getHelloString(String input) {
		return "Hello World " + input;
	}

	@Override
	public HelloWorldResponse getHelloObject(String input) {
		return new HelloWorldResponse(input, getHelloString(input));
	}
}
