package com.nicolasfanin.retotech.presentation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.nicolasfanin.retotech.R;
import com.nicolasfanin.retotech.core.platform.BaseActivity;
import com.nicolasfanin.retotech.presentation.viewmodel.LoginViewModel;

import javax.inject.Inject;

import androidx.annotation.Nullable;

public class LoginActivity extends BaseActivity {

    @Inject
    LoginViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        appComponent.inject(this);

        initViews();
    }

    private void initViews() {
        final EditText loginPhoneEditText = findViewById(R.id.login_phone_edit_text);

        findViewById(R.id.send_phone_number_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.verifyPhoneNumber(loginPhoneEditText.getText().toString());
            }
        });
    }
}
