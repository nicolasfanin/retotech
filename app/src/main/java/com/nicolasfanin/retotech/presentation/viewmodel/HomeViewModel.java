package com.nicolasfanin.retotech.presentation.viewmodel;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.nicolasfanin.retotech.core.platform.BaseViewModel;
import com.nicolasfanin.retotech.domain.model.ClientModel;
import com.nicolasfanin.retotech.domain.usecase.AuthenticateUserUseCase;
import com.nicolasfanin.retotech.domain.usecase.CreateClientUseCase;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends BaseViewModel {

    @Inject
    CreateClientUseCase createClientUseCase;
    @Inject
    AuthenticateUserUseCase authenticateUserUseCase;

    public MutableLiveData<String> clientCreated = new MutableLiveData<>();

    private DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
            clientCreated.setValue(ref.getKey());
        }
    };

    @Inject
    public HomeViewModel() {
    }

    public void createClient(ClientModel clientModel) {
        createClientUseCase.createClient(clientModel, listener);
    }

    public void signOut() {
        authenticateUserUseCase.signOut();
    }
}
