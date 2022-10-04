
package com.qintess.GerDemanda.service.mapper;


import com.qintess.GerDemanda.model.Cliente;

import com.qintess.GerDemanda.service.dto.ClienteDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {

}