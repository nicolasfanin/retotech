package com.nicolasfanin.retotech.presentation.viewmodel;

import com.nicolasfanin.retotech.core.platform.BaseViewModel;
import com.nicolasfanin.retotech.domain.model.ClientModel;
import com.nicolasfanin.retotech.domain.usecase.AuthenticateUserUseCase;
import com.nicolasfanin.retotech.domain.usecase.CreateClientUseCase;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;


public class HomeViewModel extends BaseViewModel {

    @Inject
    CreateClientUseCase createClientUseCase;
    @Inject
    AuthenticateUserUseCase authenticateUserUseCase;

    public MutableLiveData<String> clientCreated = new MutableLiveData<>();

    @Inject
    public HomeViewModel() { }

    public void createClient(ClientModel clientModel) {
        compositeDisposable.add(
                createClientUseCase.createClient(clientModel)
                                   .observeOn(AndroidSchedulers.mainThread())
                                   .subscribe(value -> clientCreated.setValue(value))
        );
    }

    public MutableLiveData<String> getCreatedClient() {
        return clientCreated;
    }

    public void signOut() {
        authenticateUserUseCase.signOut();
    }
}
