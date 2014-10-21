package com.agilex.geo.validator;

public interface MyValidator<T, R> {
	R validate(T request);
}
