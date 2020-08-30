package com.nicolasfanin.retotech.presentation.activity;

import android.os.Bundle;

import com.nicolasfanin.retotech.R;
import com.nicolasfanin.retotech.core.platform.BaseActivity;

import androidx.annotation.Nullable;

public class AuthenticateActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

}
