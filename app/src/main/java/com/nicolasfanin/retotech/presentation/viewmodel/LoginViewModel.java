package com.nicolasfanin.retotech.presentation.viewmodel;

import com.nicolasfanin.retotech.domain.usecase.VerifyPhoneNumberUseCase;

import javax.inject.Inject;

import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    @Inject
    VerifyPhoneNumberUseCase verifyPhoneNumberUseCase;

    @Inject
    public LoginViewModel() { }

    public void verifyPhoneNumber(String phoneNumber) {
        verifyPhoneNumberUseCase.invoke(phoneNumber);
    }

}
