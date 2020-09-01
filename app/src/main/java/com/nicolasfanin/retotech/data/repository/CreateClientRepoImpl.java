package com.nicolasfanin.retotech.data.repository;

import com.google.firebase.database.DatabaseReference;
import com.nicolasfanin.retotech.core.firebase.FirebaseRealTimeDatabase;
import com.nicolasfanin.retotech.domain.model.ClientModel;
import com.nicolasfanin.retotech.domain.repository.CreateClientRepo;

import javax.inject.Inject;

public class CreateClientRepoImpl implements CreateClientRepo {

    FirebaseRealTimeDatabase firebaseRealTimeDatabase;

    @Inject
    public CreateClientRepoImpl(FirebaseRealTimeDatabase firebaseRealTimeDatabase) {
        this.firebaseRealTimeDatabase = firebaseRealTimeDatabase;
    }

    @Override
    public void createClient(ClientModel clientModel, DatabaseReference.CompletionListener listener) {
        firebaseRealTimeDatabase.createClient(clientModel, listener);
    }
}
