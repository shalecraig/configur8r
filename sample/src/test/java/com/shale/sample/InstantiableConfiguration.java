package com.shale.sample;

import java.lang.annotation.Annotation;

/**
 * Created by shale on 6/27/15.
 */
@SuppressWarnings("ClassExplicitlyAnnotation")
public class InstantiableConfiguration implements MyConfiguration {

    private final int foo;
    private final boolean bar;
    private final String name;

    InstantiableConfiguration(int foo, boolean bar, String name) {
        this.foo = foo;
        this.bar = bar;
        this.name = name;
    }


    @Override
    public Class<? extends Annotation> annotationType() {
        return MyConfiguration.class;
    }

    @Override
    public int foo() {
        return foo;
    }

    @Override
    public boolean bar() {
        return bar;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("Shouldn't call hashCode of this.");
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("Call other.equals(this), not the other way around.");
    }
}
