package com.nicolasfanin.retotech.core.di;


import com.nicolasfanin.retotech.AndroidApplication;
import com.nicolasfanin.retotech.presentation.activity.HomeActivity;
import com.nicolasfanin.retotech.presentation.activity.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(AndroidApplication application);
    void inject(HomeActivity homeActivity);
    void inject(LoginActivity loginActivity);
}
