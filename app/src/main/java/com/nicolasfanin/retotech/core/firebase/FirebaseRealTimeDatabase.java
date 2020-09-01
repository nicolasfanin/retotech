package com.nicolasfanin.retotech.core.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nicolasfanin.retotech.domain.model.ClientModel;

import javax.inject.Inject;

public class FirebaseRealTimeDatabase {

    private FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Inject
    public FirebaseRealTimeDatabase() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }

    public void createClient(ClientModel clientModel, DatabaseReference.CompletionListener listener) {
        databaseReference.push().setValue(clientModel, listener);
    }

}
