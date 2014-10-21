package com.agilex.geo.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class HelloWorldValidator implements MyValidator<String, HelloWorldValidator.ValidatorResult> {
	@Value("${error.validate.helloWorld.input.empty}")
	private String emptyMsg;
	@Value("${error.validate.helloWorld.input.tooLong}")
	private String tooLongMsg;

	@Override
	public HelloWorldValidator.ValidatorResult validate(String request) {
		List<String> errors = new ArrayList<String>();
		int maxChars = 10;
		if (StringUtils.isEmpty(request)) {
			errors.add(emptyMsg);
			return new ValidatorResult(false, errors);
		}
		else if (request.length() > maxChars) {
			String errorMsg = tooLongMsg.replaceAll("\\{inputParam}", request);
			errorMsg = errorMsg.replaceAll("\\{maxChars}", "" + maxChars);
			errors.add(errorMsg);
			return new ValidatorResult(false, errors);
		}
		return new ValidatorResult(true);
	}
	
	public static class ValidatorResult {
		private boolean isValid = false;
		private List<String> errors;

		public ValidatorResult(boolean isValid) {
			super();
			this.isValid = isValid;
		}

		public ValidatorResult(boolean isValid, List<String> errors) {
			this(isValid);
			this.errors = errors;
		}

		public boolean isValid() {
			return isValid;
		}

		public List<String> getErrors() {
			return errors;
		}
	}
}
