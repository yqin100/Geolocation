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

import com.agilex.geo.exception.GeoValidationException;
import com.agilex.geo.model.HelloWorldResponse;
import com.agilex.geo.service.HelloWorldService;
import com.agilex.geo.validator.HelloWorldValidator;

@RestController
@RequestMapping(value="/helloWorld")
public class HelloWorldController extends AbstractRestController {
    private final Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private HelloWorldService helloWorldService;
	@Autowired
	private HelloWorldValidator helloWorldValidator;
	
	@RequestMapping(value="/get", method = RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public HelloWorldResponse helloWorldJsonGET(@RequestParam(value = "input", required = true) String input) throws GeoValidationException {
		log.debug("Getting hello world json");
		validate(input);
		return helloWorldService.getHelloObject(input);
	}
	
	@RequestMapping(value="/get.xml", method = RequestMethod.GET, produces={MediaType.APPLICATION_XML_VALUE})
	@ResponseStatus(HttpStatus.OK)
	public HelloWorldResponse helloWorldXmlGET(@RequestParam(value = "input", required = true) String input) throws GeoValidationException {
		log.debug("Getting hello world xml");
		validate(input);
		return helloWorldService.getHelloObject(input);
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
}