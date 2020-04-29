package com.github.antoinejt.ppepersonnel.config;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

public class ExceptionValidator<T, E extends Exception> extends Validator<T> {
	private final ExceptionThrower<E> exceptionThrower;

	public ExceptionValidator(Predicate<T> predicate, ExceptionThrower<E> exceptionThrower) {
		this.predicate = predicate;
		this.exceptionThrower = exceptionThrower;
	}

	@Override
	public void validate(T obj) {
		try {
			validateAndThrow(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void validateAndThrow(T obj) throws E {
		if (!predicate.test(obj)) {
			try {
				exceptionThrower.throwException();
			} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
				e.printStackTrace();
			}
		}
	}
}
