package com.nicolasfanin.retotech.presentation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.PhoneAuthCredential;
import com.nicolasfanin.retotech.R;
import com.nicolasfanin.retotech.core.platform.BaseFragment;
import com.nicolasfanin.retotech.databinding.FragmentAuthenticationBinding;
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

    private FragmentAuthenticationBinding binding;

    private static final String VERIFICATION_IN_PROCESS = "VERIFICATION_IN_PROCESS";
    private Boolean verificationInProcess = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentAuthenticationBinding.inflate(getLayoutInflater());
        appComponent.inject(this);

        initViews();

        viewModel.verificationId.observe(getViewLifecycleOwner(), s -> onVerificationIdReceived(s));
        viewModel.credential.observe(getViewLifecycleOwner(), phoneAuthCredential -> onCredentialReceived(phoneAuthCredential));
        viewModel.authResult.observe(getViewLifecycleOwner(), authResult -> onAuthResult(authResult));

        return binding.getRoot();
    }

    private void initViews() {
        final EditText authenticatePhoneEditText = binding.authenticatePhoneNumberEditText;

        binding.sendPhoneNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoader();
                String phoneNumber = authenticatePhoneEditText.getText().toString();
                if (isValidPhoneNumber(phoneNumber)) {
                    viewModel.verifyPhoneNumber("+549" + phoneNumber);
                    verificationInProcess = true;
                } else {
                    Toast.makeText(getContext(), getString(R.string.login_please_verify_phone_number_format), Toast.LENGTH_LONG).show();
                }
            }
        });

        final EditText loginPhoneEditText = binding.authenticateCodeEditText;

        binding.authenticateSendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoader();
                viewModel.verifyCode(loginPhoneEditText.getText().toString());
            }
        });

        hideLoader();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void showCodeVerification() {
        binding.instructionsText.setText(R.string.login_code_validation_message_txt);
        binding.phoneValidationCardView.setVisibility(View.GONE);
        binding.codeValidationCardView.setVisibility(View.VISIBLE);
    }

    private void hideCodeVerification() {
        binding.instructionsText.setText(R.string.login_welcome_message_txt);
        binding.phoneValidationCardView.setVisibility(View.VISIBLE);
        binding.codeValidationCardView.setVisibility(View.GONE);
    }

    private void onVerificationIdReceived(String s) {
        hideLoader();
        showCodeVerification();
    }

    private void onCredentialReceived(PhoneAuthCredential phoneAuthCredential) {
        viewModel.signInUser(phoneAuthCredential);
    }

    private void onAuthResult(AuthResult authResult) {
        if(authResult != null) {
            Navigation.findNavController(requireView()).navigate(R.id.action_authenticationFragment_to_homeFragment);
        } else {
            Toast.makeText(getContext(), R.string.code_verification_error_message, Toast.LENGTH_LONG).show();
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

    private void showLoader() {
        binding.authenticationProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        binding.authenticationProgressBar.setVisibility(View.INVISIBLE);
    }
}
