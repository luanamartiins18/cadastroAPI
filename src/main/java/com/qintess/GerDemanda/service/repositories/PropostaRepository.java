package com.qintess.GerDemanda.service.repositories;


import com.qintess.GerDemanda.model.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Integer> {

    Proposta findPropostaById(Integer id);

    @Query(
            value = "SELECT * FROM proposta ORDER BY id  DESC LIMIT 1",
            nativeQuery = true
    )
    Proposta findUltimaProposta();

    Proposta findFirstById(Integer id);
}
