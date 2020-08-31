package com.nicolasfanin.retotech.core.firebase;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class FirebaseApi implements Executor {

    private FirebaseAuth auth;

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

    public Task<AuthResult> signInUser(PhoneAuthCredential credential) {
        return auth.signInWithCredential(credential);
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }

    public FirebaseUser getSignedInUser() {
        return auth.getCurrentUser();
    }

    public void signOut() {
        auth.signOut();
    }


    //credential.setValue(phoneAuthCredential);
    //task.getResult().getUser().getPhoneNumber()
    //task.getResult().getUser().getUid()
}
