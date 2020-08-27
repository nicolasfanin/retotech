package com.nicolasfanin.retotech.presentation.activity;

import android.os.Bundle;

import com.nicolasfanin.retotech.core.platform.BaseActivity;
import com.nicolasfanin.retotech.presentation.viewmodel.HomeViewModel;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public class HomeActivity extends BaseActivity {

    @Inject
    HomeViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appComponent.inject(this);
    }
}
