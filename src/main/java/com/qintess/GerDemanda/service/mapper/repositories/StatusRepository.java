package com.qintess.GerDemanda.service.mapper.repositories;

import com.qintess.GerDemanda.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

    List<Status> findByOrderByDescricaoAsc();
}
