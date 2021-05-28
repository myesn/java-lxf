package com.company.myesn.annotation;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD, ElementType.TYPE})
public @interface ReportContainer {
    Report[] value();
}
