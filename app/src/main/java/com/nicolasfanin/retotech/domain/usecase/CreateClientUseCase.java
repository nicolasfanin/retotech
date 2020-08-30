package com.nicolasfanin.retotech.domain.usecase;

import com.nicolasfanin.retotech.domain.model.ClientModel;
import com.nicolasfanin.retotech.domain.repository.CreateClientRepo;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public class CreateClientUseCase {

    private CreateClientRepo repository;

    @Inject
    public CreateClientUseCase(CreateClientRepo repository) {
        this.repository = repository;
    }

    public Single<String> createClient(ClientModel clientModel) {
        return Single.fromCallable(() -> (repository.createClient(clientModel)));
    }


}
