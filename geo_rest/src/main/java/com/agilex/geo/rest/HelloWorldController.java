package com.agilex.geo.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.agilex.geo.model.HelloWorldResponse;
import com.agilex.geo.service.HelloWorldService;

@RestController
@RequestMapping(value="/helloWorld")
public class HelloWorldController extends AbstractRestController {
    private final Logger log = Logger.getLogger(this.getClass());

	@Autowired
	private HelloWorldService helloWorldService;
	
	@RequestMapping(value="/get", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public HelloWorldResponse helloWorldJsonGET(@RequestParam(value = "input", required = true) String input) {
		log.debug("Getting hello world json");
		return helloWorldService.getHelloObject(input);
	}
	
	@RequestMapping(value="/get.xml", method = RequestMethod.GET, produces={MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public HelloWorldResponse helloWorldXmlGET(@RequestParam(value = "input", required = true) String input) {
		log.debug("Getting hello world xml");
		return helloWorldService.getHelloObject(input);
	}
}