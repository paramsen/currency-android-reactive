package com.amsen.par.cewlrency.base.dependency.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Pär Amsen @ Nixon Nixon / 2016
 */
@Scope
@Retention(RUNTIME)
public @interface AppScope {
}
