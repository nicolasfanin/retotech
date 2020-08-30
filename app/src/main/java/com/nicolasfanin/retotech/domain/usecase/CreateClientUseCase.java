package com.nicolasfanin.retotech.domain.usecase;

import com.nicolasfanin.retotech.domain.model.ClientModel;
import com.nicolasfanin.retotech.domain.repository.CreateClientRepo;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CreateClientUseCase {

    private CreateClientRepo repository;

    @Inject
    public CreateClientUseCase(CreateClientRepo repository) {
        this.repository = repository;
    }

    public Single<String> createClient(ClientModel clientModel) {
        return Single.fromCallable(() -> (repository.createClient(clientModel))).subscribeOn(Schedulers.io());
    }


}
