package com.github.antoinejt.ppepersonnel.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ExceptionThrower<T extends Exception> {
	private final Class<T> exceptionClass;
	private final String exceptionMessage;

	public ExceptionThrower(Class<T> exceptionClass, String exceptionMessage) {
		this.exceptionClass = exceptionClass;
		this.exceptionMessage = exceptionMessage;
	}

	public void throwException() throws T, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Constructor<T> constructor = exceptionClass.getConstructor(String.class);
		throw constructor.newInstance(exceptionMessage);
	}
}
