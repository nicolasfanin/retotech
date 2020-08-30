package com.nicolasfanin.retotech.domain.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import androidx.lifecycle.MutableLiveData;

public interface LoginRepo {

    void verifyPhoneNumber(String phoneNumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks authCallback);

    PhoneAuthCredential getCredential(String verificationId, String code);

    Task<AuthResult> signInUser(PhoneAuthCredential credential);

    FirebaseUser getSignedInUser();
}
