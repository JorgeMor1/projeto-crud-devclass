package com.meuProjeto.repository;

import java.util.List;
import com.meuProjeto.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;


@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public List<Cliente> findAllClientes() {
        return listAll();
    }
    
    //Método feito para verificar se o e-mail já está cadastrado.
    public boolean emailExistente(String email) {
        long count = count("email", email);
        return count > 0;
    }

    @Transactional
    public Cliente salvar(Cliente novoCliente) {
        if (emailExistente(novoCliente.getEmail())) {
            throw new BadRequestException("E-mail já cadastrado.");
        }
        persist(novoCliente);
        return novoCliente;
    }

}
