package com.nicolasfanin.retotech.data.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nicolasfanin.retotech.core.firebase.FirebaseApi;
import com.nicolasfanin.retotech.domain.repository.LoginRepo;

import javax.inject.Inject;

public class LoginRepoImpl implements LoginRepo {

    FirebaseApi firebaseApi;

    @Inject
    public LoginRepoImpl(FirebaseApi firebaseApi) {
        this.firebaseApi = firebaseApi;
    }

    @Override
    public void verifyPhoneNumber(String phoneNumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks authCallback) {
        firebaseApi.verifyPhoneNumber(phoneNumber, authCallback);
    }

    @Override
    public PhoneAuthCredential getCredential(String verificationId, String code) {
        return firebaseApi.getCredential(verificationId, code);
    }

    @Override
    public Task<AuthResult> signInUser(PhoneAuthCredential credential) {
        return firebaseApi.signInUser(credential);
    }

    @Override
    public FirebaseUser getSignedInUser() {
        return firebaseApi.getSignedInUser();
    }
}
