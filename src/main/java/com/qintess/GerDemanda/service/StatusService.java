package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.service.dto.StatusDTO;
import com.qintess.GerDemanda.service.mapper.StatusMapper;
import com.qintess.GerDemanda.service.mapper.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    @Autowired
    StatusRepository statusRepository;
    @Autowired
    StatusMapper statusMapper;

    public List<StatusDTO> getStatus() {
        return statusMapper.toDto(statusRepository.findByOrderByDescricaoAsc());
    }
}
