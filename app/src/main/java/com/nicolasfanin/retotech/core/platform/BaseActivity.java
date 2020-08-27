package com.nicolasfanin.retotech.core.platform;

import android.os.Bundle;

import com.nicolasfanin.retotech.AndroidApplication;
import com.nicolasfanin.retotech.core.di.ApplicationComponent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    public ApplicationComponent appComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appComponent = ((AndroidApplication) getApplicationContext()).appComponent;
    }
}
