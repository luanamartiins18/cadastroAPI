package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.Cargo;
import com.qintess.GerDemanda.repositories.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CargoService {

    @Autowired
    CargoRepository cargoRepository;

}
