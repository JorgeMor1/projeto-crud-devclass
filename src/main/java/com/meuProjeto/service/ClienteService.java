package com.meuProjeto.service;

import com.meuProjeto.model.Cliente;
import com.meuProjeto.repository.ClienteRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
public class ClienteService {

@Inject
    ClienteRepository clienteRepository;

public void salvarCliente(Cliente cliente) {
    
        if (clienteRepository.emailExistente(cliente.getEmail())) {
            throw new BadRequestException("O e-mail já está cadastrado.");
        }
        clienteRepository.salvar(cliente);
    }
}
