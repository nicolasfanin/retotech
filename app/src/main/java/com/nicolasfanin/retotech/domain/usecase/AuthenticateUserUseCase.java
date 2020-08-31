package com.nicolasfanin.retotech.domain.usecase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nicolasfanin.retotech.domain.repository.LoginRepo;

import java.util.Optional;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.nicolasfanin.retotech.core.utils.Constants.EMPTY_VALUE;

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
        return Single.fromCallable(() -> repository.getCredential(verificationId, code)).subscribeOn(Schedulers.io());
    }

    public Single<Task<AuthResult>> signInUser(PhoneAuthCredential credential) {
        return Single.fromCallable(() -> repository.signInUser(credential)).subscribeOn(Schedulers.io());
    }

    public Single<String> getSignedInUser() {
        return Maybe.fromCallable(() -> repository.getSignedInUser().getUid())
                    .defaultIfEmpty(EMPTY_VALUE)
                    .subscribeOn(Schedulers.io());
    }

    public void signOut() {
        repository.signOut();
    }
}
