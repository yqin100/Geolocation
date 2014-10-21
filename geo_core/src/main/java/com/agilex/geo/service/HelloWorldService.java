package com.agilex.geo.service;

import com.agilex.geo.model.HelloWorldResponse;

public interface HelloWorldService {
	String getHelloString(String input);
	HelloWorldResponse getHelloObject(String input);
}
