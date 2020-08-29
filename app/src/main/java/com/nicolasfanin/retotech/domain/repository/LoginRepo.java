package com.nicolasfanin.retotech.domain.repository;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.core.Completable;

public interface LoginRepo {

    void verifyPhoneNumber(String phoneNumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks authCallback);

    PhoneAuthCredential getCredential(String verificationId, String code);

    MutableLiveData<FirebaseUser> signInUser(PhoneAuthCredential credential);

    FirebaseUser getSignedInUser();
}
