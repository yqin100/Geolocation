package com.agilex.geo.rest;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.agilex.geo.model.HelloWorldResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/resources/META-INF/spring/spring-application-context.xml",
		})
public class TestHelloWorldController extends Assert {
	@Test
	public void testHelloWorldJsonGET() {
		String input = "testInput";
		String output = "Hello World " + input;
		String uri = "http://localhost:9080/geo/rest/helloWorld/get?input=" + input;
		
		RestTemplate restTemplate = new RestTemplate();
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		
		HelloWorldResponse response = new HelloWorldResponse(input, output);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			json = ow.writeValueAsString(response);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			assertTrue(false);
		}

		mockServer.expect(requestTo(uri))
			.andExpect(method(HttpMethod.GET))
			.andRespond(withStatus(HttpStatus.OK)
					.body(json)
					.contentType(MediaType.APPLICATION_JSON));

		HttpEntity<String> requestEntity = new HttpEntity<String>(new HttpHeaders());		
		ResponseEntity<HelloWorldResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, HelloWorldResponse.class);

		mockServer.verify();
		
		assertTrue(responseEntity.getStatusCode().value() == HttpStatus.OK.value());
		assertEquals(input, responseEntity.getBody().getInput());
		assertEquals(output, responseEntity.getBody().getOutput());
	}

}
