package com.agilex.geo.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.agilex.geo.exception.GeoEsriException;
import com.agilex.geo.model.HelloWorldResponse;
import com.agilex.geo.provider.GeoProvider;
import com.agilex.geo.provider.GeoResult;

@RunWith(MockitoJUnitRunner.class)
public class TestHelloWorldService extends Assert {
    @Mock
    GeoProvider<List<GeoResult>, GeoEsriException> esriExternalProvider;
    @InjectMocks
	private HelloWorldServiceImpl helloWorldService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    
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
