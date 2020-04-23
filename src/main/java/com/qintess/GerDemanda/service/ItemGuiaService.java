package com.qintess.GerDemanda.service;

import com.qintess.GerDemanda.model.ItemGuia;
import com.qintess.GerDemanda.repositories.ItemGuiaRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemGuiaService {

    @Autowired
    ItemGuiaRepository itemGuiaRepository;

    public ItemGuia findById(Integer id) {
        return itemGuiaRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("id", ItemGuia.class.getName()));
    }

    public void deletaItemTarefa(Integer id) {
        ItemGuia itemGuia = this.findById(id);
        itemGuiaRepository.delete(itemGuia);
    }

}