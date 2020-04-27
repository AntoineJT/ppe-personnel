package com.github.antoinejt.ppepersonnel.config;

import java.util.function.Predicate;

public abstract class Validator<T> {
    protected Predicate<T> predicate;

    public abstract void validate(T value);
}
