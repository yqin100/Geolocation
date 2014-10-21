package com.agilex.geo.ws;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.agilex.geo.exception.GeoValidationException;
import com.agilex.geo.model.HelloWorldResponse;
import com.agilex.geo.service.HelloWorldService;
import com.agilex.geo.validator.HelloWorldValidator;
import com.agilex.geo.xsd.helloworld.WsHelloWorldRequest;
import com.agilex.geo.xsd.helloworld.WsHelloWorldResponse;

@Endpoint
public class HelloWorldEndpoint {
    private final Logger log = Logger.getLogger(this.getClass());
	private static final String NAMESPACE_URI = "http://agilex.com/geo/xsd/helloWorld";
	@Autowired
	private HelloWorldService helloWorldService;
	@Autowired
	private HelloWorldValidator helloWorldValidator;
	
	// ***********************************************************************
	// The "localPart" has to match the xsd element name so it will map accordingly
	// For example: 
	// <xs:element name="wsHelloWorldRequest">
	// localPart = "wsHelloWorldRequest"
	// ***********************************************************************
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "wsHelloWorldRequest")
	@ResponsePayload
	public WsHelloWorldResponse helloWorldPort(@RequestPayload WsHelloWorldRequest request) throws GeoValidationException {
		log.debug("Getting hello world ws");
		validate(request.getInput());
		HelloWorldResponse response = helloWorldService.getHelloObject(request.getInput());
		return getWsResponse(response);
	}
	
	private void validate(String input) throws GeoValidationException {
		HelloWorldValidator.ValidatorResult result = helloWorldValidator.validate(input);
		if (!result.isValid()) {
			String errors = "";
			for (String s : result.getErrors()) {
				errors += s + ",";
			}
			throw new GeoValidationException(errors);
		}		
	}
	
	private WsHelloWorldResponse getWsResponse(HelloWorldResponse response) {
		WsHelloWorldResponse wsResponse = new WsHelloWorldResponse();
		wsResponse.setInput(response.getInput());
		wsResponse.setOutput(response.getOutput());
		return wsResponse;
	}
}