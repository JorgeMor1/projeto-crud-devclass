package com.meuProjeto;

import java.util.List;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;


@ApplicationScoped
public class ClientesRepository implements PanacheRepository<Clientes> {

    //Método para buscar todos os clientes cadastrados.
    public List<Clientes> findAllClientes() {
        return listAll();
    }
    
    //Método para verificar se o e-mail já está cadastrado.
    public boolean emailExistente(String email) {
        long count = count("email", email);
        return count > 0;
    }
    
    @Transactional
    public Clientes salvar(Clientes novoCliente) {
        if (emailExistente(novoCliente.getEmail())) {
            throw new BadRequestException("E-mail já cadastrado.");
        }
        persist(novoCliente);
        return novoCliente;
    }

}
