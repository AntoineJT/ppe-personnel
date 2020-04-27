package com.github.antoinejt.ppepersonnel.config;

import java.util.function.Predicate;

public class ActionValidator<T> extends Validator<T> {
    private final Action action;

    public ActionValidator(Predicate<T> predicate, Action action) {
        this.predicate = predicate;
        this.action = action;
    }

    @Override
    public void validate(T obj) {
        if (!predicate.test(obj)) {
            action.run();
        }
    }
}
