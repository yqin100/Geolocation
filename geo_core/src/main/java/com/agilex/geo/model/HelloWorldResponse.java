package com.agilex.geo.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HelloWorldResponse implements Serializable {
	private static final long serialVersionUID = 5126365573442481021L;
	private String input;
	private String output;
	
	public HelloWorldResponse() {
		super();
	}

	public HelloWorldResponse(String input, String output) {
		this();
		this.input = input;
		this.output = output;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	@Override
	public String toString() {
		return "HelloWorldResponse [input=" + input + ", output=" + output
				+ "]";
	}
}