package com.amsen.par.cewlrency.base.dependency.application;

import com.amsen.par.cewlrency.base.dependency.view.ViewComponent;
import com.amsen.par.cewlrency.base.dependency.view.ViewModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Pär Amsen 2016
 */
@Singleton
@Component(modules = {ApplicationModule.class, SourceModule.class, ApiModule.class})
public interface ApplicationComponent {
    ViewComponent plus(ViewModule module);
}
