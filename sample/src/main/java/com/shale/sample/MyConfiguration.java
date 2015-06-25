package com.shale.sample;

import com.shale.common.Configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Configuration
//@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyConfiguration {

    int foo();
    boolean bar();
    String name();
}
