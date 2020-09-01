package com.nicolasfanin.retotech.presentation.viewmodel;

import com.nicolasfanin.retotech.core.platform.BaseViewModel;
import com.nicolasfanin.retotech.domain.usecase.AuthenticateUserUseCase;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import static com.nicolasfanin.retotech.core.utils.Constants.EMPTY_VALUE;

public class SplashViewModel extends BaseViewModel {

    @Inject
    AuthenticateUserUseCase authenticateUserUseCase;

    public MutableLiveData<String> user = new MutableLiveData<>();

    @Inject
    public SplashViewModel() {
    }

    public void checkIfUserIsSignedIn() {
        compositeDisposable.add(
                authenticateUserUseCase.getSignedInUser()
                                       .observeOn(AndroidSchedulers.mainThread())
                                       .subscribe(value -> user.setValue(value),
                                               error -> user.setValue(EMPTY_VALUE)));
    }
}
