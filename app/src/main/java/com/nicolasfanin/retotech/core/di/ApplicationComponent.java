package com.nicolasfanin.retotech.core.di;


import com.nicolasfanin.retotech.AndroidApplication;
import com.nicolasfanin.retotech.presentation.activity.MainActivity;
import com.nicolasfanin.retotech.presentation.fragment.AuthenticationFragment;
import com.nicolasfanin.retotech.presentation.fragment.HomeFragment;
import com.nicolasfanin.retotech.presentation.fragment.SplashFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(AndroidApplication application);

    void inject(MainActivity mainActivity);

    void inject(AuthenticationFragment authenticationFragment);

    void inject(HomeFragment homefragment);

    void inject(SplashFragment splashFragment);
}
