package com.nicolasfanin.retotech.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.nicolasfanin.retotech.R;
import com.nicolasfanin.retotech.core.platform.BaseFragment;
import com.nicolasfanin.retotech.presentation.viewmodel.AuthViewModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

public class AuthenticationFragment extends BaseFragment {

    @Inject
    AuthViewModel viewModel;

    private View rootView;
    private static final String VERIFICATION_IN_PROCESS = "VERIFICATION_IN_PROCESS";
    private Boolean verificationInProcess = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_authentication, container, false);
        appComponent.inject(this);

        initViews();

        viewModel.verificationId.observe(getViewLifecycleOwner(), s -> onVerificationIdReceived(s));
        viewModel.credential.observe(getViewLifecycleOwner(), phoneAuthCredential -> onCredentialReceived(phoneAuthCredential));
        viewModel.authResult.observe(getViewLifecycleOwner(), authResult -> onAuthResult(authResult));

        return rootView;
    }

    private void initViews() {
        final EditText authenticatePhoneEditText = rootView.findViewById(R.id.authenticate_phone_number_edit_text);

        rootView.findViewById(R.id.send_phone_number_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = authenticatePhoneEditText.getText().toString();
                if (isValidPhoneNumber(phoneNumber)) {
                    viewModel.verifyPhoneNumber("+549" + phoneNumber);
                    verificationInProcess = true;
                } else {
                    Toast.makeText(getContext(), getString(R.string.login_please_verify_phone_number_format), Toast.LENGTH_LONG).show();
                }
            }
        });

        final EditText loginPhoneEditText = rootView.findViewById(R.id.authenticate_code_edit_text);

        rootView.findViewById(R.id.authenticate_send_code_button).setOnClickListener(v -> viewModel.verifyCode(loginPhoneEditText.getText()
                                                                                                                                 .toString()));
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^[0-9-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (verificationInProcess) {
            showCodeVerification();
        }
    }

    private void showCodeVerification() {
        ((TextView) rootView.findViewById(R.id.instructions_text)).setText(R.string.login_code_validation_message_txt);
        rootView.findViewById(R.id.phone_validation_card_view).setVisibility(View.GONE);
        rootView.findViewById(R.id.code_validation_card_view).setVisibility(View.VISIBLE);
    }

    private void hideCodeVerification() {
        ((TextView) rootView.findViewById(R.id.instructions_text)).setText(R.string.login_welcome_message_txt);
        rootView.findViewById(R.id.phone_validation_card_view).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.code_validation_card_view).setVisibility(View.GONE);
    }

    private void onVerificationIdReceived(String s) {
        showCodeVerification();
    }

    private void onCredentialReceived(PhoneAuthCredential phoneAuthCredential) {
        viewModel.signInUser(phoneAuthCredential);
    }

    private void onAuthResult(AuthResult authResult) {
        if (!authResult.getUser().getUid().isEmpty()) {
            Navigation.findNavController(requireView()).navigate(R.id.action_authenticationFragment_to_homeFragment);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(VERIFICATION_IN_PROCESS, verificationInProcess);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(VERIFICATION_IN_PROCESS)) {
            verificationInProcess = savedInstanceState.getBoolean(VERIFICATION_IN_PROCESS);
        }
    }
}
