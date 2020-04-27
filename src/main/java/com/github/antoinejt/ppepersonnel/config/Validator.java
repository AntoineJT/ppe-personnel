package com.github.antoinejt.ppepersonnel.config;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

public class Validator<T, E extends Exception> {
    private final Predicate<T> predicate;
    private final ExceptionThrower<E> exceptionThrower;

    public Validator(Predicate<T> predicate, ExceptionThrower<E> exceptionThrower) {
        this.predicate = predicate;
        this.exceptionThrower = exceptionThrower;
    }

    public void validate(T obj) throws E {
        if (!predicate.test(obj)) {
            try {
                exceptionThrower.throwException();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
