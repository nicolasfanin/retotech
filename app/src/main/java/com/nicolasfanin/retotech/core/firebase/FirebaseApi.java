package com.nicolasfanin.retotech.core.firebase;

import android.util.Log;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;


public class FirebaseApi implements Executor {

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Inject
    public FirebaseApi() {
        auth = FirebaseAuth.getInstance();
        auth.setLanguageCode("es");
    }

    public void verifyPhoneNumber(String phoneNumber, PhoneAuthProvider.OnVerificationStateChangedCallbacks authCallback) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60L,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                authCallback);        // OnVerificationStateChangedCallbacks
    }

    public PhoneAuthCredential getCredential(String verificationId, String code) {
        return PhoneAuthProvider.getCredential(verificationId, code);
    }

    public MutableLiveData<FirebaseUser> signInUser(PhoneAuthCredential credential) {
        MutableLiveData<FirebaseUser> mutableLiveDataUser = new MutableLiveData<>();
        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                boolean isNewUser = task.getResult().getAdditionalUserInfo().isNewUser();
                // Sign in success, update UI with the signed-in user's information
                Log.d("Firebase::", "signInWithCredential:success");
                if (task.getResult() != null) {
                    //user = task.getResult().getUser();
                    mutableLiveDataUser.setValue(task.getResult().getUser());
                }
        }});
        return mutableLiveDataUser;
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }

    public FirebaseUser getSignedInUser() {
        return user;
    }

    public void signOut() {
        auth.signOut();
    }

    //credential.setValue(phoneAuthCredential);
    //task.getResult().getUser().getPhoneNumber()
    //task.getResult().getUser().getUid()
}
