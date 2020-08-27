package com.nicolasfanin.retotech;

import android.app.Application;

import com.nicolasfanin.retotech.core.di.ApplicationComponent;
import com.nicolasfanin.retotech.core.di.ApplicationModule;
import com.nicolasfanin.retotech.core.di.DaggerApplicationComponent;

public class AndroidApplication extends Application {

    public ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerApplicationComponent.builder()
                                                 .applicationModule(new ApplicationModule(this))
                                                 .build();
        appComponent.inject(this);
    }
}
