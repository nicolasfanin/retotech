package com.nicolasfanin.retotech.data.repository;

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
    public void verifyPhoneNumber(String phoneNumber) {
        firebaseApi.verifyPhoneNumber(phoneNumber);
    }
}
