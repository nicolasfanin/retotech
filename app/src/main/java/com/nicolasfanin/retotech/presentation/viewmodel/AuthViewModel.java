package com.nicolasfanin.retotech.presentation.viewmodel;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.nicolasfanin.retotech.core.platform.BaseViewModel;
import com.nicolasfanin.retotech.domain.usecase.AuthenticateUserUseCase;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;


public class AuthViewModel extends BaseViewModel {

    @Inject
    AuthenticateUserUseCase authenticateUserUseCase;

    private PhoneAuthProvider.ForceResendingToken resendToken;

    public MutableLiveData<String> verificationId = new MutableLiveData<>();
    public MutableLiveData<PhoneAuthCredential> credential = new MutableLiveData<>();
    public MutableLiveData<AuthResult> authResult = new MutableLiveData<>();

    @Inject
    public AuthViewModel() {
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.d("FirebaseCompleted!", phoneAuthCredential.getSignInMethod());
            if (phoneAuthCredential.getSmsCode() != null && phoneAuthCredential.getSmsCode().isEmpty()) {

            } else {
                credential.setValue(phoneAuthCredential);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.d("FirebaseError", e.getMessage());
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            Log.d("FirebaseCodeSent", s);
            verificationId.setValue(s);
            resendToken = forceResendingToken;
        }
    };

    public void verifyPhoneNumber(String phoneNumber) {
        authenticateUserUseCase.verifyPhoneNumber(phoneNumber, mCallbacks);
    }

    public void verifyCode(String code) {
        compositeDisposable.add(
                authenticateUserUseCase.verifyCode(verificationId.getValue(), code)
                                       .observeOn(AndroidSchedulers.mainThread())
                                       .subscribe(value -> credential.setValue(value))
        );
    }

    public void signInUser(PhoneAuthCredential credential) {
        compositeDisposable.add(
                authenticateUserUseCase.signInUser(credential)
                                       .observeOn(AndroidSchedulers.mainThread())
                                       .subscribe(value -> {
                                                   value.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<AuthResult> task) {
                                                           if (task.isSuccessful()) {
                                                               authResult.setValue(value.getResult());
                                                           } else {
                                                               authResult.setValue(null);
                                                           }
                                                       }
                                                   });
                                               }
                                               , error -> handleError(error))
        );
    }

    private void handleError(Throwable error) {
        Log.d("ERROR::::", error.getMessage());
    }

}
