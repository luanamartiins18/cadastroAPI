package com.qintess.GerDemanda.service.mapper.repositories;


import com.qintess.GerDemanda.model.Bu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface BuRepository extends JpaRepository<Bu, Integer> {

    List<Bu> findByOrderByDescricaoAsc();
}
