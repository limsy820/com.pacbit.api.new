package com.bitkrx.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bitkrx.config.dbinfo.DataSourceType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonDataSource {
    DataSourceType value() default DataSourceType.OPRBIT;
}
