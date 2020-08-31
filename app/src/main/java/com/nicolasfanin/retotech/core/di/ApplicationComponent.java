package com.nicolasfanin.retotech.core.di;


import com.nicolasfanin.retotech.AndroidApplication;
import com.nicolasfanin.retotech.presentation.activity.AuthenticateActivity;
import com.nicolasfanin.retotech.presentation.activity.HomeActivity;
import com.nicolasfanin.retotech.presentation.fragment.AuthenticateFragment;
import com.nicolasfanin.retotech.presentation.fragment.HomeFragment;
import com.nicolasfanin.retotech.presentation.fragment.SplashFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(AndroidApplication application);

    void inject(HomeActivity homeActivity);

    void inject(AuthenticateFragment authenticateFragment);

    void inject(AuthenticateActivity authenticateActivity);

    void inject(HomeFragment homefragment);

    void inject(SplashFragment splashFragment);
}
