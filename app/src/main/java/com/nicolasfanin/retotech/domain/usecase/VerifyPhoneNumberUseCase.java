package com.nicolasfanin.retotech.domain.usecase;

import com.nicolasfanin.retotech.domain.repository.LoginRepo;

import javax.inject.Inject;

public class VerifyPhoneNumberUseCase {

    private LoginRepo repository;

    @Inject
    public VerifyPhoneNumberUseCase(LoginRepo repository) {
        this.repository = repository;
    }

    public void invoke(String phoneNumber) {
        repository.verifyPhoneNumber(phoneNumber);
    }
}
