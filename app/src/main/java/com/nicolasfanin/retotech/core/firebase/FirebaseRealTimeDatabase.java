package com.nicolasfanin.retotech.core.firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    public String createClient(ClientModel clientModel) {
        databaseReference.setValue(clientModel);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("Firebase", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Firebase", "Failed to read value.", error.toException());
            }
        });
        return "";
    }
}
