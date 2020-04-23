package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.repositories.CargoRepository;
import com.qintess.GerDemanda.service.dto.CargoDTO;
import com.qintess.GerDemanda.service.dto.UsuarioDTO;
import com.qintess.GerDemanda.service.mapper.CargoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    CargoRepository cargoRepository;
    @Autowired
    CargoMapper cargoMapper;

    public CargoDTO getCargoUsuarioByRe(String re) {
        UsuarioDTO usuarioDTO = this.usuarioService.getUsuarioByRe(re);
        return usuarioDTO.getCargo();
    }

    public List<CargoDTO> getCargo() {
        return cargoMapper.toDto(cargoRepository.findByOrderByDescricaoAsc());
    }

}