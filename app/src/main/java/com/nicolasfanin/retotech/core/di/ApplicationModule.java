package com.nicolasfanin.retotech.core.di;

import android.app.Application;
import android.content.Context;

import com.nicolasfanin.retotech.core.firebase.FirebaseApi;
import com.nicolasfanin.retotech.data.repository.LoginRepoImpl;
import com.nicolasfanin.retotech.domain.repository.LoginRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    public LoginRepo providesLoginRepo(FirebaseApi firebaseApi) {
        return new LoginRepoImpl(firebaseApi);
    }

}
