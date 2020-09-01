package com.nicolasfanin.retotech.domain.usecase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.nicolasfanin.retotech.domain.model.ClientModel;
import com.nicolasfanin.retotech.domain.repository.CreateClientRepo;

import javax.inject.Inject;


public class CreateClientUseCase {

    private CreateClientRepo repository;

    @Inject
    public CreateClientUseCase(CreateClientRepo repository) {
        this.repository = repository;
    }

    public void createClient(ClientModel clientModel,
            DatabaseReference.CompletionListener listener) {
        repository.createClient(clientModel, listener);
    }

}
