package com.meuProjeto.repository;

import java.util.List;
import com.meuProjeto.model.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public List<Cliente> findAllClientes() {
        return listAll();
    }
    
    //Método feito para verificar no banco se o e-mail já está cadastrado.
    public boolean emailExistente(String email) {
        return count("email", email) > 0;
    }

    public void salvar(Cliente novoCliente) {
        persist(novoCliente);
    }

}
