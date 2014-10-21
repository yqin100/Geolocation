package com.agilex.geo.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.agilex.geo.model.HelloWorldResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/spring-application-context.xml"})
public class TestHelloWorldService extends Assert {
	@Autowired
	private HelloWorldService helloWorldService;

	@Test
	public void testServiceString() {
		String input = "testInputString";
		String output = helloWorldService.getHelloString(input);
		
		assertEquals("Hello World " + input, output);
	}
	
	@Test
	public void testServiceObject() {
		String input = "testInputString";
		HelloWorldResponse output = helloWorldService.getHelloObject(input);
		
		assertEquals(input, output.getInput());
		assertEquals("Hello World " + input, output.getOutput());
	}
}
