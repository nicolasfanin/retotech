package com.nicolasfanin.retotech.presentation.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.nicolasfanin.retotech.R;
import com.nicolasfanin.retotech.core.platform.BaseFragment;
import com.nicolasfanin.retotech.presentation.viewmodel.AuthViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

public class AuthenticateFragment extends BaseFragment {

    @Inject
    AuthViewModel viewModel;

    private View rootView;
    private static final String VERIFICATION_IN_PROCESS = "VERIFICATION_IN_PROCESS";
    private Boolean verificationInProcess = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_authenticate, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        appComponent.inject(this);

        initViews();

        viewModel.getVerificationId().observe(getViewLifecycleOwner(), s -> onVerificationIdReceived(s));
        viewModel.getCredential().observe(getViewLifecycleOwner(), phoneAuthCredential -> onCredentialReceived(phoneAuthCredential));
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> onUserSignedIn(user));

        return rootView;
    }

    private void initViews() {
        final EditText authenticatePhoneEditText = rootView.findViewById(R.id.authenticate_phone_number_edit_text);

        rootView.findViewById(R.id.send_phone_number_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.verifyPhoneNumber(authenticatePhoneEditText.getText().toString());
                verificationInProcess = true;
            }
        });

        final EditText loginPhoneEditText = rootView.findViewById(R.id.authenticate_code_edit_text);

        rootView.findViewById(R.id.authenticate_send_code_button).setOnClickListener(v -> viewModel.verifyCode(loginPhoneEditText.getText().toString()));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (verificationInProcess) {
            showCodeVerification();
        }
    }

    private void showCodeVerification() {
        rootView.findViewById(R.id.phone_validation_card_view).setVisibility(View.GONE);
        rootView.findViewById(R.id.code_validation_card_view).setVisibility(View.VISIBLE);
    }

    private void hideCodeVerification() {
        rootView.findViewById(R.id.phone_validation_card_view).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.code_validation_card_view).setVisibility(View.GONE);
    }

    private void onVerificationIdReceived(String s) {
        showCodeVerification();
    }

    private void onCredentialReceived(PhoneAuthCredential phoneAuthCredential) {
        viewModel.signInUser(phoneAuthCredential);
    }

    private void onUserSignedIn(FirebaseUser user) {
        //TODO: User is null because is not waiting it!
        //Log.d("FirebaseUser", user.getDisplayName());
        Navigation.findNavController(requireView()).navigate(R.id.action_authenticateFragment_to_homeFragment);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(VERIFICATION_IN_PROCESS, verificationInProcess);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState!= null && savedInstanceState.containsKey(VERIFICATION_IN_PROCESS)) {
            verificationInProcess = savedInstanceState.getBoolean(VERIFICATION_IN_PROCESS);
        }
    }
}
