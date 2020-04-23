package com.qintess.GerDemanda.repositories;

import com.qintess.GerDemanda.model.ItemGuia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemGuiaRepository extends JpaRepository<ItemGuia, Integer> {

}