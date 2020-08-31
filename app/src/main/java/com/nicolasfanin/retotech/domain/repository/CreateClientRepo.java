package com.nicolasfanin.retotech.domain.repository;

import com.google.firebase.database.DatabaseReference;
import com.nicolasfanin.retotech.domain.model.ClientModel;

public interface CreateClientRepo {

    void createClient(ClientModel clientModel, DatabaseReference.CompletionListener listener);
}
