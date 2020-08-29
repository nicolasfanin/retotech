package com.nicolasfanin.retotech.domain.usecase;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nicolasfanin.retotech.domain.repository.LoginRepo;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class AuthenticateUserUseCase {

    private LoginRepo repository;

    @Inject
    public AuthenticateUserUseCase(LoginRepo repository) {
        this.repository = repository;
    }

    public void verifyPhoneNumber(String phoneNumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks authCallback) {
        repository.verifyPhoneNumber(phoneNumber, authCallback);
    }

    public Single<PhoneAuthCredential> verifyCode(String verificationId, String code) {
       return Single.fromCallable(() -> repository.getCredential(verificationId, code));
    }

    public MutableLiveData<FirebaseUser> signInUser(PhoneAuthCredential credential) {
        return repository.signInUser(credential);
    }

    public FirebaseUser getSignedInUser() {
        return repository.getSignedInUser();
    }
}
